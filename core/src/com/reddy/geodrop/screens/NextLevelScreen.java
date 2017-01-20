package com.reddy.geodrop.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.reddy.geodrop.Main;

/**
 * Created by Hayden on 2017-01-19.
 */

public class NextLevelScreen implements Screen {
    private Main game;
    private Texture menuText, menuDownText, nextText, nextDownText, bg;
    private Drawable drawMenu, drawMenuDown, drawNext, drawNextDown;
    private ImageButton menu, next;
    private Stage stage;
    private Label level;
    private int levelNum;
    private Preferences prefs;
    private Sound buttonSound;

    public NextLevelScreen(Main game, int levelNum){
        this.game = game;
        this.levelNum = levelNum;
        stage = new Stage(new StretchViewport(Main.WIDTH, Main.HEIGHT));
        menuText = game.manager.get("ui/menuup.png");
        menuDownText = game.manager.get("ui/menu.png");
        nextText = game.manager.get("ui/nextup.png");
        nextText.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        nextDownText = game.manager.get("ui/next.png");
        nextDownText.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        drawMenu = new TextureRegionDrawable(new TextureRegion(menuText));
        drawMenuDown = new TextureRegionDrawable(new TextureRegion(menuDownText));
        drawNext = new TextureRegionDrawable(new TextureRegion(nextText));
        drawNextDown = new TextureRegionDrawable(new TextureRegion(nextDownText));
        menu = new ImageButton(drawMenu, drawMenuDown);
        next = new ImageButton(drawNext, drawNextDown);
        level = new Label("Level " + levelNum + " Complete", new Label.LabelStyle(game.font180, Color.WHITE));
        buttonSound = game.manager.get("audio/button.ogg");
        bg = game.manager.get("ui/selectscreen.png");

        prefs = Gdx.app.getPreferences("prefs");
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

       menu.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(prefs.getBoolean("mute") == true) {
                    buttonSound.play(0.25f);
                }
                game.setScreen(new MenuScreen(game));
                stage.clear();
            }
        });

        next.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(prefs.getBoolean("mute") == true) {
                    buttonSound.play(0.25f);
                }
                game.setScreen(new PlayScreen(game, levelNum + 1));
                stage.clear();
            }
        });

        menu.setPosition(360, 300);
        next.setPosition(1060, 300);
        level.setPosition(Main.WIDTH / 2 - level.getWidth() / 2, 800);
        stage.addActor(menu);
        stage.addActor(next);
        stage.addActor(level);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.batch.draw(bg, 0, 0);
        game.batch.end();

        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}