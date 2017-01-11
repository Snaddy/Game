package com.reddy.geodrop.actors;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Timer;
import com.reddy.geodrop.Main;
import com.reddy.geodrop.screens.PlayScreen;

import static com.badlogic.gdx.scenes.scene2d.ui.Table.Debug.cell;
import static com.reddy.geodrop.screens.PlayScreen.game;
import static com.reddy.geodrop.screens.PlayScreen.level;

/**
 * Created by Hayden on 2017-01-03.
 */

public class Finish implements Disposable{

    private World world;
    private TiledMap map;
    private Rectangle bounds;
    private Body body;
    private Sound victory;
    private static boolean levelFinished;
    private Preferences prefs;

    public Finish(World world, TiledMap map, Rectangle bounds, Sound victory){
        this.world = world;
        this.map = map;
        this.bounds = bounds;
        levelFinished = false;

        prefs = Gdx.app.getPreferences("prefs");

        this.victory = victory;
        victory.setVolume(6, 6);

        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((bounds.getX() + bounds.getWidth() / 2) / Main.PPM, (bounds.getY() + bounds.getHeight() / 2) / Main.PPM);
        body = world.createBody(bdef);

        shape.setAsBox(bounds.getWidth() / 2 / Main.PPM, bounds.getHeight() / 2 / Main.PPM);
        fdef.shape = shape;
        fdef.isSensor = true;
        body.createFixture(fdef).setUserData(this);
    }

    public void hit(){
        prefs.putInteger("levelsUnlocked", level);
        prefs.flush();
        game.setVolume();
        victory.play(0.5f);
        TiledMapTileSet tileSet = map.getTileSets().getTileSet(0);
        TiledMapTileLayer layer = (TiledMapTileLayer)map.getLayers().get(1);

        TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
        cell.setTile(tileSet.getTile(3));
        layer.setCell((int)(body.getPosition().x * Main.PPM / 128), 2, cell);
        levelFinished = true;
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                game.setScreen(new PlayScreen(game, level + 1));
            }
        }, 1.5f);
    }

    public static boolean isLevelFinished(){
        return levelFinished;
    }

    @Override
    public void dispose() {
        victory.dispose();
        world.dispose();
        map.dispose();
    }
}
