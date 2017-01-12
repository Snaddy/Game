package com.reddy.geodrop.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.reddy.geodrop.Main;

/**
 * Created by Hayden on 2017-01-10.
 */

public class ScreenSelect implements Screen{

    private Main game;
    private ImageTextButton[][] button = new ImageTextButton[4][5];
    private ImageTextButton.ImageTextButtonStyle lockedStyle, unlockedStyle;
    private Texture locked, unlocked, unlockedDown;
    private Drawable drawLocked, drawUnlocked, drawUnlockedDown;
    private Stage stage;
    private Preferences prefs;
    private Sound buttonSound;

    public ScreenSelect(Main game){
        this.game = game;
        stage = new Stage(new StretchViewport(Main.WIDTH, Main.HEIGHT));
        unlocked = game.manager.get("ui/box.png");
        locked = game.manager.get("ui/locked.png");
        drawUnlocked = new TextureRegionDrawable(new TextureRegion(unlocked));
        drawLocked = new TextureRegionDrawable(new TextureRegion(locked));
        //buttons
        unlockedStyle = new ImageTextButton.ImageTextButtonStyle(drawUnlocked, drawUnlocked, drawUnlocked, game.font100);
        lockedStyle = new ImageTextButton.ImageTextButtonStyle(drawLocked, drawLocked, drawLocked, game.font100);

        //preferences
        prefs = Gdx.app.getPreferences("prefs");

        initLevelSelect();

        buttonSound = game.manager.get("audio/button.ogg", Sound.class);
        System.out.println(stage.getHeight());
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Gdx.input.setInputProcessor(stage);

        game.batch.begin();
        game.batch.draw(game.manager.get("ui/grassBg.png", Texture.class), 0, 0);
        game.batch.end();

        stage.draw();
    }

    public void initLevelSelect() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                final int id = j + 1 + (i * 5);
                if(id <= prefs.getInteger("levelsUnlocked") + 1) {
                    button[i][j] = new ImageTextButton(id + "", unlockedStyle);
                    button[i][j].addListener(new ClickListener(){
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            buttonSound.play(0.25f);
                            game.setScreen(new PlayScreen(game, id));
                            stage.clear();
                        }
                    });
                }
                else
                    button[i][j] = new ImageTextButton("", lockedStyle);

                button[i][j].setPosition((440) + 220 * j, (stage.getHeight() - 326) - 220 * i);
                stage.addActor(button[i][j]);
            }
        }
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
        stage.dispose();
        game.dispose();
    }
}
