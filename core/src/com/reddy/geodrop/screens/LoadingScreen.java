package com.reddy.geodrop.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.reddy.geodrop.Main;

/**
 * Created by Hayden on 2017-02-02.
 */

public class LoadingScreen implements Screen {

    public Main game;
    private float progress;
    private Stage stage;
    private Label loading;

    public LoadingScreen(Main game){
        this.game = game;
        stage = new Stage(new StretchViewport(Main.WIDTH, Main.HEIGHT));
        loading = new Label("0", new Label.LabelStyle(game.font180, Color.WHITE));
    }

    private void queueAssets(){
        game.manager.load("actors/player.png", Texture.class);
        game.manager.load("actors/snowplayer.png", Texture.class);
        game.manager.load("actors/desertplayer.png", Texture.class);
        game.manager.load("actors/planetplayer.png", Texture.class);
        game.manager.load("actors/square.png", Texture.class);
        game.manager.load("actors/rectangle.png", Texture.class);
        //ui
        game.manager.load("ui/play.png", Texture.class);
        game.manager.load("ui/credits.png", Texture.class);
        game.manager.load("ui/menu.png", Texture.class);
        game.manager.load("ui/next.png", Texture.class);
        game.manager.load("ui/playup.png", Texture.class);
        game.manager.load("ui/creditsup.png", Texture.class);
        game.manager.load("ui/creditsdown.png", Texture.class);
        game.manager.load("ui/nextup.png", Texture.class);
        game.manager.load("ui/menuup.png", Texture.class);
        game.manager.load("ui/bg.png", Texture.class);
        game.manager.load("ui/buyRect.png", Texture.class);
        game.manager.load("ui/buySquare.png", Texture.class);
        game.manager.load("ui/jump.png", Texture.class);
        game.manager.load("ui/box.png", Texture.class);
        game.manager.load("ui/boxDown.png", Texture.class);
        game.manager.load("ui/grassBg.png", Texture.class);
        game.manager.load("ui/locked.png", Texture.class);
        game.manager.load("ui/credits.png", Texture.class);
        game.manager.load("ui/downarrow.png", Texture.class);
        game.manager.load("ui/leftarrow.png", Texture.class);
        game.manager.load("ui/tutorial.png", Texture.class);
        game.manager.load("ui/tutorialDown.png", Texture.class);
        game.manager.load("ui/mute.png", Texture.class);
        game.manager.load("ui/volume.png", Texture.class);
        game.manager.load("ui/blank.png", Texture.class);
        game.manager.load("ui/selectscreen.png", Texture.class);
        game.manager.load("ui/land.png", Texture.class);
        game.manager.load("ui/landdown.png", Texture.class);
        game.manager.load("ui/snow.png", Texture.class);
        game.manager.load("ui/snowdown.png", Texture.class);
        game.manager.load("ui/desert.png", Texture.class);
        game.manager.load("ui/desertdown.png", Texture.class);
        game.manager.load("ui/planet.png", Texture.class);
        game.manager.load("ui/planetdown.png", Texture.class);
        game.manager.load("ui/desertBg.png", Texture.class);
        game.manager.load("ui/planetBg.png", Texture.class);
        game.manager.load("ui/snowlocked.png", Texture.class);
        game.manager.load("ui/desertlocked.png", Texture.class);
        game.manager.load("ui/planetlocked.png", Texture.class);
        game.manager.load("ui/back.png", Texture.class);
        game.manager.load("ui/backUp.png", Texture.class);
        //load sounds
        game.manager.load("audio/pickup.ogg", Sound.class);
        game.manager.load("audio/death.ogg", Sound.class);
        game.manager.load("audio/victory.ogg", Sound.class);
        game.manager.load("audio/button.ogg", Sound.class);
        game.manager.load("audio/hit.ogg", Sound.class);
        game.manager.load("audio/jump.ogg", Sound.class);
        //load maps
        game.manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        game.manager.load("levels/level1.tmx", TiledMap.class);
        game.manager.load("levels/level2.tmx", TiledMap.class);
        game.manager.load("levels/level3.tmx", TiledMap.class);
        game.manager.load("levels/level4.tmx", TiledMap.class);
        game.manager.load("levels/level5.tmx", TiledMap.class);
        game.manager.load("levels/level6.tmx", TiledMap.class);
        game.manager.load("levels/level7.tmx", TiledMap.class);
        game.manager.load("levels/level8.tmx", TiledMap.class);
        game.manager.load("levels/level9.tmx", TiledMap.class);
        game.manager.load("levels/level10.tmx", TiledMap.class);
        game.manager.load("levels/level11.tmx", TiledMap.class);
        game.manager.load("levels/level12.tmx", TiledMap.class);
        game.manager.load("levels/level13.tmx", TiledMap.class);
        game.manager.load("levels/level14.tmx", TiledMap.class);
        game.manager.load("levels/level15.tmx", TiledMap.class);
        game.manager.load("levels/level16.tmx", TiledMap.class);
        game.manager.load("levels/level17.tmx", TiledMap.class);
        game.manager.load("levels/level18.tmx", TiledMap.class);
        game.manager.load("levels/level19.tmx", TiledMap.class);
        game.manager.load("levels/level20.tmx", TiledMap.class);
        game.manager.load("levels/level21.tmx", TiledMap.class);
        game.manager.load("levels/level22.tmx", TiledMap.class);
        game.manager.load("levels/level23.tmx", TiledMap.class);
        game.manager.load("levels/level24.tmx", TiledMap.class);
        game.manager.load("levels/level25.tmx", TiledMap.class);
        game.manager.load("levels/level26.tmx", TiledMap.class);
        game.manager.load("levels/level27.tmx", TiledMap.class);
        game.manager.load("levels/level28.tmx", TiledMap.class);
        game.manager.load("levels/level29.tmx", TiledMap.class);
        game.manager.load("levels/level30.tmx", TiledMap.class);
        game.manager.load("levels/level31.tmx", TiledMap.class);
        game.manager.load("levels/level32.tmx", TiledMap.class);
        game.manager.load("levels/level33.tmx", TiledMap.class);
        game.manager.load("levels/level34.tmx", TiledMap.class);
        game.manager.load("levels/level35.tmx", TiledMap.class);
        game.manager.load("levels/level36.tmx", TiledMap.class);
        game.manager.load("levels/level37.tmx", TiledMap.class);
        game.manager.load("levels/level38.tmx", TiledMap.class);
        game.manager.load("levels/level39.tmx", TiledMap.class);
        game.manager.load("levels/level40.tmx", TiledMap.class);
        game.manager.load("levels/level41.tmx", TiledMap.class);
        game.manager.load("levels/level42.tmx", TiledMap.class);
        game.manager.load("levels/level43.tmx", TiledMap.class);
        game.manager.load("levels/level44.tmx", TiledMap.class);
        game.manager.load("levels/level45.tmx", TiledMap.class);
        game.manager.load("levels/level46.tmx", TiledMap.class);
        game.manager.load("levels/level47.tmx", TiledMap.class);
        game.manager.load("levels/level48.tmx", TiledMap.class);
        game.manager.load("levels/level49.tmx", TiledMap.class);
        game.manager.load("levels/level50.tmx", TiledMap.class);
        game.manager.load("levels/level999.tmx", TiledMap.class);

    }

    @Override
    public void show() {
        progress = 0f;
        queueAssets();
        loading.setPosition(Main.WIDTH / 2 - (loading.getWidth() / 2), Main.HEIGHT / 2 - loading.getHeight() / 2);
        stage.addActor(loading);
    }

    private void update(float delta){
        progress = game.manager.getProgress() * 100;
        loading.setText((int)progress + "");

        if((int)progress >= 10)
            loading.setPosition(Main.WIDTH / 2 - (loading.getWidth() / 2 + 30), Main.HEIGHT / 2 - loading.getHeight() / 2);

        if((int)progress == 100)
            loading.setPosition(Main.WIDTH / 2 - (loading.getWidth() / 2 + 50), Main.HEIGHT / 2 - loading.getHeight() / 2);

        if(game.manager.update() && progress == 100) {
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    game.setScreen(new MenuScreen(game));
                }
            }, 0.5f);
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

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
