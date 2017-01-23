package com.reddy.geodrop.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.reddy.geodrop.Main;

/**
 * Created by Hayden on 2017-01-02.
 */

public class GameSquare extends Sprite implements Disposable{

    public World world;
    public Body body;
    private Sprite sq;
    private Texture tex;
    private float posX, posY;

    public GameSquare(World world, float posX, float posY, Texture tex){
        this.world = world;
        this.posX = posX;
        this.posY = posY;
        this.tex = tex;
        sq = new Sprite(tex);
        defineRecangle();
    }

    public void defineRecangle(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(posX, posY);
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);
        body.setUserData(sq);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(68 / Main.PPM, 35 / Main.PPM);
        fdef.shape = shape;
        fdef.friction = 1f;
        fdef.restitution = 0f;
        fdef.density = 0.05f;
        body.createFixture(fdef).setUserData("ground");
    }

    public void update(){
        this.setPosition(body.getPosition().x - 70 / Main.PPM, body.getPosition().y - 35 / Main.PPM);
        this.setOrigin(sq.getX() + sq.getWidth() / Main.PPM, sq.getY() + sq.getHeight() / Main.PPM);
        this.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
    }

    @Override
    public void dispose() {
        tex.dispose();
        world.dispose();
    }
}

