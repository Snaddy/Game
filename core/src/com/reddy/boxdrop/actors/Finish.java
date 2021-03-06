package com.reddy.boxdrop.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
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
import com.reddy.boxdrop.Main;
import com.reddy.boxdrop.World.Hud;
import com.reddy.boxdrop.screens.MenuScreen;
import com.reddy.boxdrop.screens.NextLevelScreen;
import com.reddy.boxdrop.screens.PlayScreen;

import static com.reddy.boxdrop.screens.PlayScreen.game;
import static com.reddy.boxdrop.screens.PlayScreen.level;

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
    private float time;

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

    public void hit() {
        PlayScreen.finished = true;

        if(prefs.getFloat("level" + level + "best") == 0f){
            prefs.putFloat("level" + level + "best", Hud.time);
        }

        if (Hud.time < prefs.getFloat("level" + level + "best")) {
            prefs.putFloat("level" + level + "best", Hud.time);
        }
        if (level != 999) {
            if (level > prefs.getInteger("levelsUnlocked")) {
                prefs.putInteger("levelsUnlocked", level);
            }
        }
            prefs.flush();
            game.setVolume();
            if (prefs.getBoolean("mute") == true) {
                victory.play(0.25f);
            }
            TiledMapTileSet tileSet = map.getTileSets().getTileSet(0);
            TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(1);

            TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
            cell.setTile(tileSet.getTile(3));
            layer.setCell((int) (body.getPosition().x * Main.PPM / 128) - 1, 2, cell);
            levelFinished = true;
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    if (level == 999) {
                        game.setScreen(new MenuScreen(game));
                    } else {
                        game.setScreen(new NextLevelScreen(game, level));
                    }
                }
            }, 2f);
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
