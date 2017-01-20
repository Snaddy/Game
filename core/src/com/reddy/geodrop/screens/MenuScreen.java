package com.reddy.geodrop.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
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

    private ImageButton play, credits, tutorial, mute;
    private Texture playText, creditsText, bg, playTextDown, creditsTextDown, tutorialText, tutorialTextDown, muteText, volumeText, blank;
    private Drawable drawPlay, drawCredits, drawPlayDown, drawCreditsDown, drawTutorial, drawTutorialDown, drawMute, drawVolume, drawBlank;
    private Stage stage;
    private Main game;
    private OrthographicCamera gameCam;
    private Viewport viewPort;
    private Sound buttonSound;
    private ImageButton.ImageButtonStyle style;
    private Preferences prefs;

    public MenuScreen(Main game) {
        this.game = game;
        stage = new Stage(new StretchViewport(Main.WIDTH, Main.HEIGHT));
        gameCam = new OrthographicCamera();
        viewPort = new StretchViewport(Main.WIDTH, Main.HEIGHT, gameCam);
        gameCam.setToOrtho(false, Main.WIDTH, Main.HEIGHT);
        playText = game.manager.get("ui/playup.png");
        playText.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        creditsText = game.manager.get("ui/creditsup.png");
        creditsText.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        creditsTextDown = game.manager.get("ui/creditsdown.png");
        creditsTextDown.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        playTextDown = game.manager.get("ui/play.png");
        playTextDown.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        creditsText = game.manager.get("ui/creditsup.png");
        creditsText.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        creditsTextDown = game.manager.get("ui/creditsdown.png");
        creditsTextDown.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        tutorialText = game.manager.get("ui/tutorial.png");
        tutorialText.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        tutorialTextDown = game.manager.get("ui/tutorialDown.png");
        tutorialTextDown.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        bg = game.manager.get("ui/bg.png");
        bg.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        drawPlay = new TextureRegionDrawable(new TextureRegion(playText));
        drawCredits = new TextureRegionDrawable(new TextureRegion(creditsText));
        drawCreditsDown = new TextureRegionDrawable(new TextureRegion(creditsTextDown));
        drawPlayDown = new TextureRegionDrawable(new TextureRegion(playTextDown));
        drawTutorial = new TextureRegionDrawable(new TextureRegion(tutorialText));
        drawTutorialDown = new TextureRegionDrawable(new TextureRegion(tutorialTextDown));
        //buttons
        play = new ImageButton(drawPlay, drawPlayDown);
        credits = new ImageButton(drawCredits, drawCreditsDown);
        tutorial = new ImageButton(drawTutorialDown, drawTutorial);

        prefs = Gdx.app.getPreferences("prefs");


        buttonSound = game.manager.get("audio/button.ogg", Sound.class);

        //audio
        muteText = game.manager.get("ui/mute.png");
        muteText.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        volumeText = game.manager.get("ui/volume.png");
        volumeText.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        drawMute = new TextureRegionDrawable(new TextureRegion(muteText));
        drawVolume = new TextureRegionDrawable(new TextureRegion(volumeText));
        blank = game.manager.get("ui/blank.png");
        drawBlank = new TextureRegionDrawable(new TextureRegion(blank));

        mute = new ImageButton(drawBlank, drawBlank);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        play.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(prefs.getBoolean("mute") == true) {
                    buttonSound.play(0.25f);
                }
                game.setScreen(new ScreenSelect(game));
                stage.clear();
            }
        });

        credits.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(prefs.getBoolean("mute") == true) {
                    buttonSound.play(0.25f);
                }
                game.setScreen(new CreditsScreen(game));
                stage.clear();
            }
        });

        tutorial.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(prefs.getBoolean("mute") == true) {
                    buttonSound.play(0.25f);
                }
                game.setScreen(new PlayScreen(game, 999));
                stage.clear();
            }
        });

        mute.addListener(new ClickListener(){
            @Override
            public void clicked (InputEvent event,float x, float y) {
                if (prefs.getBoolean("mute") == false)
                    prefs.putBoolean("mute", true);
                else
                    prefs.putBoolean("mute", false);

                prefs.flush();
            }
        });

        //puts menu buttons half way on screen
        play.setPosition((Main.WIDTH / 2) - (play.getWidth() / 2), 900);
        credits.setPosition((Main.WIDTH / 2) - (credits.getWidth() / 2),725);
        tutorial.setPosition((Main.WIDTH / 2) - (credits.getWidth() / 2), 550);
        mute.setPosition(50, 50);
        stage.addActor(credits);
        stage.addActor(play);
        stage.addActor(tutorial);
        stage.addActor(mute);
    }

    public void update(){

        if(prefs.getBoolean("mute") == true){
            System.out.println("mute false");
            game.playMusic();
        } else {
            System.out.println("mute true");
            game.stopMusic();
        }

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.setProjectionMatrix(gameCam.combined);

        update();

        game.batch.begin();
        game.batch.draw(bg, 0, 0);
        if(prefs.getBoolean("mute") == false){
            game.batch.draw(muteText, 50, 50);
        } else
            game.batch.draw(volumeText, 50, 50);
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
