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
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
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
    private ImageTextButton[][] button = new ImageTextButton[3][5];
    private ImageTextButton.ImageTextButtonStyle lockedStyle, unlockedStyle;
    private Texture locked, unlocked, bg, backText, backDownText;
    private Drawable drawLocked, drawUnlocked, drawBack, drawBackDown;
    private Stage stage;
    private Preferences prefs;
    private Sound buttonSound;
    private int region;
    private ImageButton back;

    public ScreenSelect(Main game, int region, Texture bg){
        this.game = game;
        this.region = region;
        this.bg = bg;
        stage = new Stage(new StretchViewport(Main.WIDTH, Main.HEIGHT));
        unlocked = game.manager.get("ui/box.png");
        locked = game.manager.get("ui/locked.png");
        backText = game.manager.get("ui/backUp.png");
        backText.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        backDownText = game.manager.get("ui/back.png");
        backDownText.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        drawUnlocked = new TextureRegionDrawable(new TextureRegion(unlocked));
        drawLocked = new TextureRegionDrawable(new TextureRegion(locked));
        drawBack = new TextureRegionDrawable(new TextureRegion(backText));
        drawBackDown = new TextureRegionDrawable(new TextureRegion(backDownText));
        //buttons
        unlockedStyle = new ImageTextButton.ImageTextButtonStyle(drawUnlocked, drawUnlocked, drawUnlocked, game.font100);
        lockedStyle = new ImageTextButton.ImageTextButtonStyle(drawLocked, drawLocked, drawLocked, game.font100);

        back = new ImageButton(drawBack, drawBackDown);

        //preferences
        prefs = Gdx.app.getPreferences("prefs");
        System.out.println(prefs.getInteger("levelsUnlocked"));

        buttonSound = game.manager.get("audio/button.ogg", Sound.class);
    }

    @Override
    public void show() {
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (prefs.getBoolean("mute") == true) {
                    buttonSound.play(0.25f);
                }
                game.setScreen(new RegionSelect(game));

                stage.clear();
            }
        });

        initLevelSelect();

        back.setPosition(Main.WIDTH / 2 - back.getWidth() / 2, 90);

        stage.addActor(back);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Gdx.input.setInputProcessor(stage);

        game.batch.begin();
        game.batch.draw(bg, 0, 0);
        game.batch.end();

        stage.draw();
    }

    public void initLevelSelect() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                final int id = ((j + 1 + (i * 5)) + (15 * region));
                if(id <= prefs.getInteger("levelsUnlocked") + 1) {
                    button[i][j] = new ImageTextButton(id + "", unlockedStyle);
                    button[i][j].addListener(new ClickListener(){
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            if(prefs.getBoolean("mute") == true) {
                                buttonSound.play(0.25f);
                            }
                            game.setScreen(new PlayScreen(game, id));
                            stage.clear();
                        }
                    });
                }
                else
                    button[i][j] = new ImageTextButton("", lockedStyle);

                button[i][j].setPosition((385) + 250 * j, (stage.getHeight() - 301) - 250 * i);
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
        bg.dispose();
        backText.dispose();
        backDownText.dispose();
        unlocked.dispose();
        locked.dispose();
    }
}
