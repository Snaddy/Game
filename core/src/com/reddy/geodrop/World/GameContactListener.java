package com.reddy.geodrop.World;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.reddy.geodrop.actors.Coin;
import com.reddy.geodrop.actors.Finish;
import com.reddy.geodrop.screens.PlayScreen;

/**
 * Created by Hayden on 2017-01-02.
 */

public class GameContactListener implements ContactListener{

    private boolean playerOnGround, crateHit;

    @Override
    public void beginContact(Contact contact) {


        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        if((b.getUserData() == "ground" && a.getUserData().equals("sensor"))){
            playerOnGround = true;
        }
        if(a.getUserData() == "ground" && b.getUserData().equals("sensor")){
            playerOnGround = true;
        }


        //coin collision
        if(a.getUserData() == "body" || b.getUserData() == "body"){
            Fixture body = a.getUserData() == "body" ? a : b;
            Fixture coin = body == a ? b : a;

            if(coin.getUserData() instanceof Coin){
                ((Coin) coin.getUserData()).hit();
            }
        }

        //finish collision
        if(a.getUserData() == "body" || b.getUserData() == "body"){
            Fixture body = a.getUserData() == "body" ? a : b;
            Fixture finish = body == a ? b : a;

            if(finish.getUserData() instanceof Finish){
                ((Finish) finish.getUserData()).hit();
            }
        }

    }

    @Override
    public void endContact(Contact contact) {

        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        if(b.getUserData() == "ground" && a.getUserData().equals("sensor")){
            playerOnGround = false;
        }
        if(a.getUserData() == "ground" && b.getUserData().equals("sensor")){
            playerOnGround = false;
        }
    }
    public boolean isPlayerOnGround(){
        return playerOnGround;
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
