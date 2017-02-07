package com.reddy.boxdrop.screens;

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
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.reddy.boxdrop.World.Hud;

/**
 * Created by Hayden on 2017-01-19.
 */

public class NextLevelScreen implements Screen {
    private com.reddy.boxdrop.Main game;
    private Texture menuText, menuDownText, nextText, nextDownText, bg, creditsText, creditsTextDown;
    private Drawable drawMenu, drawMenuDown, drawNext, drawNextDown, drawCredits, drawCreditsDown;
    private ImageButton menu, next, credits;
    private Stage stage;
    private Label level, lastLevel, time, bestTime;
    private int levelNum;
    private Preferences prefs;
    private Sound buttonSound;

    public NextLevelScreen(com.reddy.boxdrop.Main game, int levelNum){
        this.game = game;
        this.levelNum = levelNum;
        prefs = Gdx.app.getPreferences("prefs");
        stage = new Stage(new StretchViewport(com.reddy.boxdrop.Main.WIDTH, com.reddy.boxdrop.Main.HEIGHT));
        menuText = game.manager.get("ui/menuup.png");
        menuDownText = game.manager.get("ui/menu.png");
        nextText = game.manager.get("ui/nextup.png");
        nextText.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        nextDownText = game.manager.get("ui/next.png");
        nextDownText.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        creditsText = game.manager.get("ui/creditsup.png");
        creditsText.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        creditsTextDown = game.manager.get("ui/creditsdown.png");
        creditsTextDown.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        drawCredits = new TextureRegionDrawable(new TextureRegion(creditsText));
        drawCreditsDown = new TextureRegionDrawable(new TextureRegion(creditsTextDown));
        drawMenu = new TextureRegionDrawable(new TextureRegion(menuText));
        drawMenuDown = new TextureRegionDrawable(new TextureRegion(menuDownText));
        drawNext = new TextureRegionDrawable(new TextureRegion(nextText));
        drawNextDown = new TextureRegionDrawable(new TextureRegion(nextDownText));
        menu = new ImageButton(drawMenu, drawMenuDown);
        next = new ImageButton(drawNext, drawNextDown);
        credits = new ImageButton(drawCredits, drawCreditsDown);
        level = new Label("Level " + levelNum + " Complete", new Label.LabelStyle(game.font180, Color.WHITE));
        lastLevel = new Label("Congrats! You've completed every level!\n You are clearly the greatest human on earth!", new Label.LabelStyle(game.font80, Color.WHITE));
        time = new Label("Time: " + String.format("%.2f", Hud.time), new Label.LabelStyle(game.font100, Color.WHITE));
        bestTime = new Label("Best: " + String.format("%.2f", prefs.getFloat("level" + levelNum + "best")), new Label.LabelStyle(game.font100, Color.WHITE));
        lastLevel.setAlignment(Align.center);
        buttonSound = game.manager.get("audio/button.ogg");
        bg = game.manager.get("ui/selectscreen.png");
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
                game.setScreen(new com.reddy.boxdrop.screens.PlayScreen(game, levelNum + 1));
                stage.clear();
            }
        });

        menu.setPosition(460, 400);
        next.setPosition(com.reddy.boxdrop.Main.WIDTH - 860, 400);
        time.setPosition(420, 150);
        bestTime.setPosition(com.reddy.boxdrop.Main.WIDTH - 880, 150);
        credits.setPosition(1060, 300);
        level.setPosition(com.reddy.boxdrop.Main.WIDTH / 2 - level.getWidth() / 2, 700);
        lastLevel.setPosition(com.reddy.boxdrop.Main.WIDTH / 2 - lastLevel.getWidth() / 2, 700);
        stage.addActor(menu);
    if(levelNum == 50) {
        level.setPosition(com.reddy.boxdrop.Main.WIDTH / 2 - level.getWidth() / 2, 900);
        stage.addActor(credits);
        stage.addActor(lastLevel);
    }
    else {
        stage.addActor(next);
    }
        stage.addActor(level);
        stage.addActor(time);
        stage.addActor(bestTime);
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
