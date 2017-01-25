package com.reddy.geodrop.World;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.reddy.geodrop.Main;
import com.reddy.geodrop.actors.Coin;
import com.reddy.geodrop.actors.Finish;

/**
 * Created by Hayden on 2016-12-25.
 */

public class CreateWorld{

    BodyDef bdef = new BodyDef();
    PolygonShape shape = new PolygonShape();
    FixtureDef fdef = new FixtureDef();
    Body body;
    private Main game;

    public CreateWorld(World world, TiledMap map, Main game) {
        this.game = game;
        //ground
        for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / Main.PPM, (rect.getY() + rect.getHeight() / 2) / Main.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / Main.PPM, rect.getHeight() / 2 / Main.PPM);
            fdef.shape = shape;
            body.createFixture(fdef).setUserData("ground");
        }

        //coins
        for (MapObject object : map.getLayers().get(3).getObjects().getByType(EllipseMapObject.class)) {
            Ellipse circle = ((EllipseMapObject) object).getEllipse();
            new Coin(world, map, circle, game.manager.get("audio/pickup.ogg", Sound.class));
        }

        //finish
        for (MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            new Finish(world, map, rect, game.manager.get("audio/victory.ogg", Sound.class));
        }
    }
}
