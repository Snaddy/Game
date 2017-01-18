package com.reddy.geodrop.World;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.reddy.geodrop.Main;

/**
 * Created by Hayden on 2017-01-04.
 */

public class Hud implements Disposable{
    public Stage stage;
    private Viewport viewport;
    private OrthographicCamera hudCam;
    private static Label scoreLabel, levelLabel;
    public BitmapFont font100;
    public static int score, level;

    public Hud(SpriteBatch sb, int level){
        this.level = level;
        hudCam = new OrthographicCamera();
        viewport = new StretchViewport(Main.WIDTH, Main.HEIGHT, hudCam);
        stage = new Stage(viewport, sb);
        score = 0;
        if(level == 999){
            score = 9999;
        }

        initFont();

        scoreLabel = new Label(String.format("%04d", score), new Label.LabelStyle(font100, Color.WHITE));
        scoreLabel.setPosition(Main.WIDTH / 2 - scoreLabel.getWidth() / 2, Main.HEIGHT - 150);
        levelLabel = new Label("Level " + level, new Label.LabelStyle(font100, Color.WHITE));
        if(level == 999){
            levelLabel = new Label("Tutorial", new Label.LabelStyle(font100, Color.WHITE));
        }
        levelLabel.setPosition(Main.WIDTH - levelLabel.getWidth() - 175, Main.HEIGHT - 150);
        stage.addActor(scoreLabel);
        stage.addActor(levelLabel);
    }

    public static void addScore(int value){
        score += value;
        scoreLabel.setText(String.format("%04d", score));
    }

    public static int getScore(){
        return score;
    }

    private void initFont() {
        FreeTypeFontGenerator g = new FreeTypeFontGenerator(Gdx.files.internal("fonts/soupofjustice.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();

        params.size = 100;
        params.minFilter = Texture.TextureFilter.Linear;
        params.magFilter = Texture.TextureFilter.Linear;
        font100 = g.generateFont(params);
    }

    @Override
    public void dispose() {
        stage.dispose();
        font100.dispose();
    }
}
