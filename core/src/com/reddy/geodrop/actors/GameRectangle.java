package com.reddy.geodrop.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.reddy.geodrop.Main;

/**
 * Created by Hayden on 2016-12-31.
 */

public class GameRectangle extends Sprite implements Disposable{

    public World world;
    public Body body;
    private Sprite rect;
    private Texture tex;
    private float posX, posY;

    public GameRectangle(World world, float posX, float posY, Texture tex){
        this.world = world;
        this.posX = posX;
        this.posY = posY;
        this.tex = tex;
        rect = new Sprite(tex);
        defineRecangle();
        body.applyForceToCenter(new Vector2(0, -10f), true);
    }

    public void defineRecangle(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(posX, posY);
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);
        body.setUserData(rect);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(300 / Main.PPM, 20 / Main.PPM);
        fdef.shape = shape;
        fdef.friction = 1f;
        fdef.restitution = 0f;
        fdef.density = 0.05f;
        body.createFixture(fdef).setUserData("ground");
    }

    public void update(){
        this.setPosition(body.getPosition().x - 300 / Main.PPM, body.getPosition().y - 20 / Main.PPM);
        this.setOrigin(rect.getX() + rect.getWidth()/ Main.PPM, rect.getY() + rect.getHeight()/ Main.PPM);
        this.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
    }

    @Override
    public void dispose() {
        tex.dispose();
        world.dispose();
    }
}
