package com.reddy.geodrop.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.reddy.geodrop.Main;

/**
 * Created by Hayden on 2017-01-17.
 */

public class CreditsScreen implements Screen{
    private Main game;
    private ImageButton menu;
    private Texture menuUp, menuDown, credits;
    private Drawable drawMenuUp, drawMenuDown;
    private OrthographicCamera gameCam;
    private Viewport viewPort;
    private Sound buttonSound;

    public CreditsScreen(Main game){
        this.game = game;
        gameCam = new OrthographicCamera();
        viewPort = new StretchViewport(Main.WIDTH, Main.HEIGHT, gameCam);
        gameCam.setToOrtho(false, Main.WIDTH, Main.HEIGHT);
        credits = game.manager.get("ui/credits.png");
        menuUp = game.manager.get("ui/menuup.png");
        menuUp.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        menuDown = game.manager.get("ui/menu.png");
        menuDown.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        drawMenuUp = new TextureRegionDrawable(new TextureRegion(menuUp));
        drawMenuDown = new TextureRegionDrawable(new TextureRegion(menuDown));
        //buttons
        menu = new ImageButton(drawMenuUp, drawMenuDown);

        buttonSound = game.manager.get("audio/button.ogg", Sound.class);
    }

    public void handleInput(){
        if(Gdx.input.justTouched()){
            game.setScreen(new MenuScreen(game));
        }
    }

    public void update(){
        gameCam.position.y = gameCam.position.y - 1.5f;
        System.out.println(gameCam.position.y);
        if(gameCam.position.y < -1300) {
            game.setScreen(new MenuScreen(game));
        }

        handleInput();
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameCam.update();
        update();

        game.batch.setProjectionMatrix(gameCam.combined);

        game.batch.begin();
        game.batch.draw(credits, 0, -1000);
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
        credits.dispose();
        menuDown.dispose();
        menuUp.dispose();
    }
}
