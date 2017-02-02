package com.reddy.geodrop.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Timer;
import com.reddy.geodrop.Main;


/**
 * Created by Hayden on 2017-01-09.
 */

public class SplashScreen implements Screen {

    private Main game;
    private Texture splash;
    private float time;

    public SplashScreen(Main game){
        this.game = game;
        splash = new Texture("ui/logo.png");
        splash.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        time = 0;
    }

    @Override
    public void show() {
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                game.setScreen(new MenuScreen(game));
            }
        }, 2f);
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.setProjectionMatrix(game.camera.combined);

        game.batch.begin();
        game.batch.draw(splash, (Main.WIDTH / 2) - (splash.getWidth() / 2), (Main.HEIGHT / 2) - (splash.getHeight() / 2));
        game.batch.end();

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
        splash.dispose();
        game.dispose();
    }
}
