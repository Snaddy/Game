package com.reddy.geodrop.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.reddy.geodrop.Main;

/**
 * Created by Hayden on 2017-01-09.
 */

public class MenuScreen implements Screen {

    private ImageButton play, credits;
    private Texture playText, creditsText, bg;
    private Drawable drawPlay, drawCredits;
    private Stage stage;
    private Preferences prefs;
    private Main game;
    private OrthographicCamera gameCam;
    private Viewport viewPort;
    private ImageButton.ImageButtonStyle style;

    public MenuScreen(Main game) {
        this.game = game;
        stage = new Stage(new StretchViewport(Main.WIDTH, Main.HEIGHT));
        gameCam = new OrthographicCamera();
        viewPort = new StretchViewport(Main.WIDTH, Main.HEIGHT, gameCam);
        gameCam.setToOrtho(false, Main.WIDTH, Main.HEIGHT);
        playText = game.manager.get("ui/play.png");
        creditsText = game.manager.get("ui/credits.png");
        bg = game.manager.get("ui/bg.png");
        drawPlay = new TextureRegionDrawable(new TextureRegion(playText));
        drawCredits = new TextureRegionDrawable(new TextureRegion(creditsText));
        //buttons
        play = new ImageButton(drawPlay);
        credits = new ImageButton(drawCredits);
        System.out.println(gameCam.position);
        game.playMusic();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        play.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new PlayScreen(game, 1));
                stage.clear();
            }
        });

        credits.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stage.clear();
            }
        });

        //puts menu buttons half way on screen
        play.setPosition((Main.WIDTH / 2) - (play.getWidth() / 2), 590);
        credits.setPosition((Main.WIDTH / 2) - (credits.getWidth() / 2), 290);
        stage.addActor(credits);
        stage.addActor(play);
    }

    public void update(){
        gameCam.position.x = gameCam.position.x + 6f;
        gameCam.update();
        if(gameCam.position.x >= 3117){
            gameCam.position.x = 960;
        }
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update();

        game.batch.setProjectionMatrix(gameCam.combined);

        game.batch.begin();
        game.batch.draw(bg, 0, 0, 4320, 1152);
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
        game.dispose();
        playText.dispose();
        creditsText.dispose();
        stage.dispose();
        bg.dispose();
    }
}
