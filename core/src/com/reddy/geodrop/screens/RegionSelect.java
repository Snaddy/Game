package com.reddy.geodrop.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.reddy.geodrop.Main;

/**
 * Created by Hayden on 2017-01-20.
 */

public class RegionSelect implements Screen {

    private Main game;
    private Stage stage;
    private Texture landText, landDownText, snowText, snowDownText, desertText, desertDownText, planetText, planetDownText;
    private Drawable drawLand, drawLandDown, drawSnow, drawSnowDown, drawDesert, drawDesertDown, drawPlanet, drawPlanetDown;
    private ImageButton land, snow, desert, planet;
    private Sound button;

    public RegionSelect(Main game){
        this.game = game;
        stage = new Stage(new StretchViewport(Main.WIDTH, Main.HEIGHT));
        landText = game.manager.get("ui/land.png");
        landDownText = game.manager.get("ui/landdown.png");
        snowText = game.manager.get("ui/snow.png");
        snowDownText = game.manager.get("ui/snowdown.png");
        desertText = game.manager.get("ui/desert.png");
        desertDownText = game.manager.get("ui/desertdown.png");
        planetText = game.manager.get("ui/planet.png");
        planetDownText = game.manager.get("ui/planetdown.png");
        drawLand = new TextureRegionDrawable(new TextureRegion(landText));
        drawLandDown = new TextureRegionDrawable(new TextureRegion(landDownText));
        drawSnow = new TextureRegionDrawable(new TextureRegion(snowText));
        drawSnowDown = new TextureRegionDrawable(new TextureRegion(snowDownText));
        drawDesert = new TextureRegionDrawable(new TextureRegion(desertText));
        drawDesertDown = new TextureRegionDrawable(new TextureRegion(desertDownText));
        drawPlanet = new TextureRegionDrawable(new TextureRegion(planetText));
        drawPlanetDown = new TextureRegionDrawable(new TextureRegion(planetDownText));
        land = new ImageButton(drawLand, drawLandDown);
        snow = new ImageButton(drawSnow, drawSnowDown);
        desert = new ImageButton(drawDesert, drawDesertDown);
        planet = new ImageButton(drawPlanet, drawPlanetDown);
    }
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        land.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
               game.setScreen(new ScreenSelect(game, 0, game.manager.get("ui/grassBg.png", Texture.class)));
                stage.clear();
            }
        });

        snow.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new ScreenSelect(game, 1, game.manager.get("ui/selectscreen.png", Texture.class)));
                stage.clear();
            }
        });

        desert.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new ScreenSelect(game, 2, game.manager.get("ui/desertBg.png", Texture.class)));
                stage.clear();
            }
        });

        planet.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new ScreenSelect(game, 3, game.manager.get("ui/planetBg.png", Texture.class)));
                stage.clear();
            }
        });

        land.setPosition(0, 0);
        snow.setPosition(480,0);
        desert.setPosition(960, 0);
        planet.setPosition(1440, 0);
        stage.addActor(land);
        stage.addActor(snow);
        stage.addActor(desert);
        stage.addActor(planet);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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
