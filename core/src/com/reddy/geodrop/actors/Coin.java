package com.reddy.geodrop.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.reddy.geodrop.Main;
import com.reddy.geodrop.World.Hud;
import com.reddy.geodrop.screens.PlayScreen;

import java.security.interfaces.DSAKey;

/**
 * Created by Hayden on 2017-01-03.
 */

public class Coin implements Disposable{
    private World world;
    private TiledMap map;
    private Ellipse bounds;
    private Body body;
    private Sound pickup;
    private Preferences prefs;


    public Coin(World world, TiledMap map, Ellipse bounds, Sound pickup){
        this.world = world;
        this.map = map;
        this.bounds = bounds;

        this.pickup = pickup;

        prefs = Gdx.app.getPreferences("prefs");

        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();

        bdef.position.set((bounds.x + bounds.width / 2) / Main.PPM, (bounds.y + bounds.height / 2) / Main.PPM);
        bdef.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(bdef);

        shape.setRadius(32 / Main.PPM);
        fdef.shape = shape;
        fdef.isSensor = true;
        body.createFixture(fdef).setUserData(this);

        TiledMapTileSet tileSet = map.getTileSets().getTileSet(0);
        TiledMapTileLayer layer = (TiledMapTileLayer)map.getLayers().get(1);

        TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
        cell.setTile(tileSet.getTile(30));
        layer.setCell((int)(body.getPosition().x * Main.PPM / 128), (int)(body.getPosition().y * Main.PPM / 128), cell);
    }

    public void hit(){
        if(prefs.getBoolean("mute") == true) {
            pickup.play(0.25f);
        }
        TiledMapTileSet tileSet = map.getTileSets().getTileSet(0);
        TiledMapTileLayer layer = (TiledMapTileLayer)map.getLayers().get(1);

        TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
        cell.setTile(tileSet.getTile(0));
        layer.setCell((int)(body.getPosition().x * Main.PPM / 128), (int)(body.getPosition().y * Main.PPM / 128), cell);
        if(PlayScreen.level != 999) {
            Hud.addScore(100);
        }
    }

    @Override
    public void dispose() {
        pickup.dispose();
        world.dispose();
        map.dispose();
    }
}
