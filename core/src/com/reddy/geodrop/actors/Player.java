package com.reddy.geodrop.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.reddy.geodrop.Main;

/**
 * Created by Hayden on 2016-12-24.
 */

public class Player extends Sprite implements Disposable {

    public World world;
    public Body body;
    private Sprite player;
    private Texture tex;

    public Player(World world, Texture tex){
        this.world = world;
        this.tex = tex;
        tex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        player = new Sprite(tex);
        definePlayer();
    }

    public void definePlayer(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(player.getWidth() / 2 / Main.PPM, 384/ Main.PPM + player.getWidth() / 2 / Main.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);
        body.setUserData(player);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius((player.getWidth() - 5) / 2 / Main.PPM);

        fdef.shape = shape;
        fdef.friction = 0f;
        fdef.restitution = 0f;
        body.createFixture(fdef).setUserData("body");

        PolygonShape jumpSensor = new PolygonShape();
        jumpSensor.setAsBox(player.getWidth() / 4 / Main.PPM, 54 / Main.PPM, new Vector2(0, -29 / Main.PPM), 0);
        fdef.shape = jumpSensor;
        fdef.isSensor = true;
        body.createFixture(fdef).setUserData("sensor");
    }

    public void update(){
        this.setPosition(body.getPosition().x, body.getPosition().y);
    }

    @Override
    public void dispose() {
        world.dispose();
        tex.dispose();
    }
}
