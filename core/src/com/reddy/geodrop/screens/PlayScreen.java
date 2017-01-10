package com.reddy.geodrop.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.reddy.geodrop.Main;
import com.reddy.geodrop.World.CreateWorld;
import com.reddy.geodrop.World.GameContactListener;
import com.reddy.geodrop.World.Hud;
import com.reddy.geodrop.actors.Coin;
import com.reddy.geodrop.actors.Finish;
import com.reddy.geodrop.actors.Player;
import com.reddy.geodrop.actors.GameRectangle;
import com.reddy.geodrop.actors.GameSquare;

import java.util.ArrayList;
import java.util.logging.Level;

/**
 * Created by Hayden on 2016-12-20.
 */

public class PlayScreen implements Screen {

    public static Main game;
    public static int level;
    private OrthographicCamera gameCam;
    private Viewport viewPort;
    private World world;
    private Box2DDebugRenderer debug;
    private GameContactListener gcl;
    private Sound death;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private Player player;
    private Hud hud;
    private ArrayList<GameRectangle> rectangles;
    private ArrayList<GameSquare> squares;
    private Stage stage;

    //ui stage
    private ImageButton buySq, buyRect, jump;
    private Texture sqText, rectText, jumpText;
    private Drawable drawSq, drawRect, drawJump;

    public PlayScreen(Main game, int level) {
        this.game = game;
        this.level = level;
        gameCam = new OrthographicCamera();
        viewPort = new StretchViewport(Main.WIDTH / Main.PPM, Main.HEIGHT / Main.PPM, gameCam);
        gameCam.setToOrtho(false, viewPort.getWorldWidth(), viewPort.getWorldHeight());
        gameCam.position.set(viewPort.getWorldWidth() / 2, viewPort.getWorldHeight() / 2, 0);
        stage = new Stage(new StretchViewport(Main.WIDTH, Main.HEIGHT));
        map = game.manager.get("levels/level" + level + ".tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / Main.PPM);
        world = new World(new Vector2(0, -10), true);
        gcl = new GameContactListener();
        world.setContactListener(gcl);
        debug = new Box2DDebugRenderer();
        new CreateWorld(world, map, game);
        player = new Player(world, game.manager.get("actors/player.png", Texture.class));
        death = game.manager.get("audio/death.ogg");
        hud = new Hud(game.batch, level);

        //arraylists
        rectangles = new ArrayList<GameRectangle>();
        squares = new ArrayList<GameSquare>();

        //stage buttons
        sqText = game.manager.get("ui/buySquare.png");
        rectText = game.manager.get("ui/buyRect.png");
        jumpText = game.manager.get("ui/jump.png");
        drawSq = new TextureRegionDrawable(new TextureRegion(sqText));
        drawRect = new TextureRegionDrawable(new TextureRegion(rectText));
        drawJump = new TextureRegionDrawable(new TextureRegion(jumpText));
        //buttons
        buySq = new ImageButton(drawSq);
        buyRect = new ImageButton(drawRect);
        jump = new ImageButton(drawJump);

    }

    private void update(float dt) {

        if (player.body.getLinearVelocity().x <= 3f)
            player.body.applyLinearImpulse(new Vector2(0.1f, 0), player.body.getWorldCenter(), true);
        if (player.body.getLinearVelocity().x > 3.1f)
            player.body.applyLinearImpulse(new Vector2(-0.1f, 0), player.body.getWorldCenter(), true);

        //move with player while they're moving
        if (player.getX() + 650 / Main.PPM >= gameCam.position.x) {
            if (Math.round(player.body.getLinearVelocity().x) > 0f && !Finish.isLevelFinished())
                gameCam.position.x = gameCam.position.x + (player.body.getLinearVelocity().x) / Main.PPM * 1.666f;
        }

        //keep camera moving
        if (Math.round(player.body.getLinearVelocity().x) <= 0f && !Finish.isLevelFinished())
            gameCam.position.x = gameCam.position.x + 5f / Main.PPM;

        world.step(1 / 60f, 6, 2);

        player.update();
        for(GameRectangle rect : rectangles){
            rect.update();
        }
        for(GameSquare sq : squares){
            sq.update();
        }
        gameCam.update();
        renderer.setView(gameCam);

        //death
        if (player.getY() + (160 / Main.PPM) < gameCam.position.y - (Main.HEIGHT / 2 / Main.PPM)) {
            death.play(0.8f);
            game.setScreen(new PlayScreen(game, level));
        }
        //player off camera
        if (player.getX() + (160 / Main.PPM) < gameCam.position.x - (Main.WIDTH / 2 / Main.PPM)) {
            death.play(0.8f);
            game.setScreen(new PlayScreen(game, level));
        }
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        buySq.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (hud.getScore() >= 100) {
                    squares.add(new GameSquare(world, gameCam.position.x, 600 / Main.PPM, game.manager.get("actors/square.png", Texture.class)));
                    hud.addScore(-100);

                }
            }
        });

        buyRect.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (hud.getScore() >= 300) {
                    rectangles.add(new GameRectangle(world, gameCam.position.x, 600 / Main.PPM, game.manager.get("actors/rectangle.png", Texture.class)));
                    hud.addScore(-300);
                }
            }
        });

        jump.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (gcl.isPlayerOnGround() )
                    player.body.applyLinearImpulse(new Vector2(0, 3.75f), player.body.getWorldCenter(), true);
            }
        });

        buySq.setPosition(Main.WIDTH - 400, Main.HEIGHT - 1200);
        buyRect.setPosition(Main.WIDTH - 800, Main.HEIGHT - 1200);
        jump.setPosition(800, Main.HEIGHT -1200);
        stage.addActor(buyRect);
        stage.addActor(buySq);
        stage.addActor(jump);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        renderer.render();

        debug.render(world, gameCam.combined);

        game.batch.setProjectionMatrix(gameCam.combined);

        game.batch.begin();
        game.batch.draw(game.manager.get("actors/player.png", Texture.class), player.getX() - 80 / Main.PPM, player.getY() - 80 / Main.PPM, 160 / Main.PPM, 160 / Main.PPM);
        for(GameRectangle rect : rectangles){
            game.batch.draw(game.manager.get("actors/rectangle.png", Texture.class),
                    rect.getX(), rect.getY(),
                    rect.getOriginX(), rect.getOriginY(),
                    602 / Main.PPM, 42 / Main.PPM,
                    1f, 1f,
                    rect.getRotation(), 0, 0,
                    300, 20, false, false);
        }

        for(GameSquare sq : squares){
            game.batch.draw(game.manager.get("actors/square.png", Texture.class),
                    sq.getX(), sq.getY(),
                    sq.getOriginX(), sq.getOriginY(),
                    152 / Main.PPM, 82 / Main.PPM,
                    1f, 1f,
                    sq.getRotation(), 0, 0,
                    75, 40, false, false);
        }

        game.batch.end();

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
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
        map.dispose();
        game.dispose();
        debug.dispose();
        world.dispose();
        renderer.dispose();
        game.batch.dispose();
        debug.dispose();
        death.dispose();
        hud.dispose();
        player.dispose();
    }
}
