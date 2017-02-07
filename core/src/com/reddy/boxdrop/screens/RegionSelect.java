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
import com.badlogic.gdx.utils.viewport.StretchViewport;

/**
 * Created by Hayden on 2017-01-20.
 */

public class RegionSelect implements Screen {

    private com.reddy.boxdrop.Main game;
    private Stage stage;
    private Texture landText, landDownText, snowText, snowDownText, desertText, desertDownText,
            planetText, planetDownText, snowLockedText, desertLockedText, planetLockedText, backText, backDownText;
    private Drawable drawLand, drawLandDown, drawSnow, drawSnowDown, drawDesert, drawDesertDown,
            drawPlanet, drawPlanetDown, snowLocked, desertLocked, planetLocked, drawBack, drawBackDown;
    private Label landLabel, snowLabel, desertLabel, planetLabel;
    private ImageButton land, snow, desert, planet, back;
    private Sound buttonSound;
    private Preferences prefs;
    private int landLevels, snowLevels, desertLevels, planetLevels;

    public RegionSelect(com.reddy.boxdrop.Main game){
        this.game = game;
        stage = new Stage(new StretchViewport(com.reddy.boxdrop.Main.WIDTH, com.reddy.boxdrop.Main.HEIGHT));
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

        //other prefs and audio
        prefs = Gdx.app.getPreferences("prefs");
        buttonSound = game.manager.get("audio/button.ogg");

        //locked textures and regions
        snowLockedText = game.manager.get("ui/snowlocked.png");
        snowLockedText.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        desertLockedText = game.manager.get("ui/desertlocked.png");
        desertLockedText.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        planetLockedText = game.manager.get("ui/planetlocked.png");
        planetLockedText.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        snowLocked = new TextureRegionDrawable(new TextureRegion(snowLockedText));
        desertLocked = new TextureRegionDrawable(new TextureRegion(desertLockedText));
        planetLocked = new TextureRegionDrawable(new TextureRegion(planetLockedText));


        if(prefs.getInteger("levelsUnlocked") > 15)
            landLevels = 15;
        else
            landLevels = prefs.getInteger("levelsUnlocked");

        if(prefs.getInteger("levelsUnlocked") > 30)
            snowLevels = 15;
        else
            snowLevels = prefs.getInteger("levelsUnlocked") - 15;

        if(prefs.getInteger("levelsUnlocked") > 45)
            desertLevels = 15;
        else
            desertLevels = prefs.getInteger("levelsUnlocked") - 30;

        if(prefs.getInteger("levelsUnlocked") == 50)
            planetLevels = 5;
        else
            planetLevels = prefs.getInteger("levelsUnlocked") - 45;


        landLabel = new Label (landLevels + "/15", new Label.LabelStyle(game.fontBerlin, Color.WHITE));
        snowLabel = new Label (snowLevels + "/15", new Label.LabelStyle(game.fontBerlin, Color.WHITE));
        desertLabel = new Label (desertLevels + "/15", new Label.LabelStyle(game.fontBerlin, Color.WHITE));
        planetLabel = new Label (planetLevels + "/5", new Label.LabelStyle(game.fontBerlin, Color.WHITE));


        initRegions();
    }

    public void initRegions() {
        Gdx.input.setInputProcessor(stage);

        land.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (prefs.getBoolean("mute") == true) {
                    buttonSound.play(0.25f);
                }
                game.setScreen(new ScreenSelect(game, 0, game.manager.get("ui/grassBg.png", Texture.class)));

                stage.clear();
            }
        });
        landLabel.setPosition(240 - landLabel.getWidth() / 2, 800);

        if (prefs.getInteger("levelsUnlocked") >= 15) {
            snow = new ImageButton(drawSnow, drawSnowDown);
            snow.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (prefs.getBoolean("mute") == true) {
                        buttonSound.play(0.25f);
                    }
                    game.setScreen(new ScreenSelect(game, 1, game.manager.get("ui/selectscreen.png", Texture.class)));
                    stage.clear();
                }
            });
        } else
            snow = new ImageButton(snowLocked, snowLocked);


        if (prefs.getInteger("levelsUnlocked") >= 30) {
            desert = new ImageButton(drawDesert, drawDesertDown);
            desert.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (prefs.getBoolean("mute") == true) {
                        buttonSound.play(0.25f);
                    }
                    game.setScreen(new ScreenSelect(game, 2, game.manager.get("ui/desertBg.png", Texture.class)));
                    stage.clear();
                }
            });
        } else
            desert = new ImageButton(desertLocked, desertLocked);

        if (prefs.getInteger("levelsUnlocked") >= 45) {
            planet = new ImageButton(drawPlanet, drawPlanetDown);
            planet.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (prefs.getBoolean("mute") == true) {
                        buttonSound.play(0.25f);
                    }
                    game.setScreen(new ScreenSelect(game, 3, game.manager.get("ui/planetBg.png", Texture.class)));
                    stage.clear();
                }
            });

        } else
            planet = new ImageButton(planetLocked, planetLocked);

        land.setPosition(0, 0);
        snow.setPosition(480, 0);
        desert.setPosition(960, 0);
        planet.setPosition(1440, 0);
        stage.addActor(land);
        stage.addActor(snow);
        stage.addActor(desert);
        stage.addActor(planet);
        stage.addActor(landLabel);
        landLabel.setPosition(240 - landLabel.getWidth() / 2, 800);
        if (prefs.getInteger("levelsUnlocked") >= 15){
            snowLabel.setPosition(720 - snowLabel.getWidth() / 2, 800);
            stage.addActor(snowLabel);
        }
        if(prefs.getInteger("levelsUnlocked") >= 30) {
            desertLabel.setPosition(1200 - desertLabel.getWidth() / 2, 800);
            stage.addActor(desertLabel);
        }
        if(prefs.getInteger("levelsUnlocked") >= 45){
            planetLabel.setPosition(1680 - planetLabel.getWidth() / 2, 800);
            stage.addActor(planetLabel);
        }
    }

    @Override
    public void show() {
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
        buttonSound.dispose();
        landText.dispose();
        landDownText.dispose();
        snowText.dispose();
        snowDownText.dispose();
        desertText.dispose();
        desertDownText.dispose();
        planetText.dispose();
        planetDownText.dispose();
        snowLockedText.dispose();
        desertLockedText.dispose();
        planetLockedText.dispose();
    }
}
