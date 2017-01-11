package com.reddy.geodrop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Timer;
import com.reddy.geodrop.screens.PlayScreen;
import com.reddy.geodrop.screens.SplashScreen;

import static com.reddy.geodrop.screens.PlayScreen.game;
import static com.reddy.geodrop.screens.PlayScreen.level;

public class Main extends Game {

	public static final int WIDTH = 1920;
	public static final int HEIGHT = 1152;
	public static final	float PPM = 100;

	public BitmapFont font100, font80;

	public SpriteBatch batch;

	public Music gameSound;

	public OrthographicCamera camera;

	public AssetManager manager;


	@Override
	public void create () {
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, WIDTH, HEIGHT);
		manager = new AssetManager();

		//load textures
		manager.load("actors/player.png", Texture.class);
		manager.load("actors/square.png", Texture.class);
		manager.load("actors/rectangle.png", Texture.class);
		//ui
		manager.load("ui/logo.png", Texture.class);
		manager.load("ui/play.png", Texture.class);
		manager.load("ui/credits.png", Texture.class);
		manager.load("ui/menu.png", Texture.class);
		manager.load("ui/next.png", Texture.class);
		manager.load("ui/playup.png", Texture.class);
		manager.load("ui/creditsup.png", Texture.class);
		manager.load("ui/nextup.png", Texture.class);
		manager.load("ui/menuup.png", Texture.class);
		manager.load("ui/bg.png", Texture.class);
		manager.load("ui/buyRect.png", Texture.class);
		manager.load("ui/buySquare.png", Texture.class);
		manager.load("ui/jump.png", Texture.class);
		manager.load("ui/box.png", Texture.class);
		manager.load("ui/boxDown.png", Texture.class);
		manager.load("ui/grassBg.png", Texture.class);
		manager.load("ui/locked.png", Texture.class);
		//load sounds
		manager.load("audio/song.ogg", Music.class);
		manager.load("audio/pickup.ogg", Sound.class);
		manager.load("audio/death.ogg", Sound.class);
		manager.load("audio/victory.ogg", Sound.class);
		//load maps
		manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
		manager.load("levels/level1.tmx", TiledMap.class);
		manager.load("levels/level2.tmx", TiledMap.class);
		manager.finishLoading();

		gameSound = manager.get("audio/song.ogg", Music.class);
		gameSound.setLooping(true);
		gameSound.setVolume(0.5f);

		initFonts();

		setScreen(new SplashScreen(this));
	}
	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		gameSound.dispose();
		manager.dispose();
		font80.dispose();
		font100.dispose();
	}

	public void playMusic(){
		gameSound.play();
	}

	public void setVolume(){
		gameSound.setVolume(0.05f);
		Timer.schedule(new Timer.Task() {
			@Override
			public void run() {
				gameSound.setVolume(0.5f);
			}
		}, 2.25f);
	}

	private void initFonts() {
		FreeTypeFontGenerator g = new FreeTypeFontGenerator(Gdx.files.internal("fonts/soupofjustice.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();

		params.size = 100;
		params.minFilter = Texture.TextureFilter.Linear;
		params.magFilter = Texture.TextureFilter.Linear;
		font100 = g.generateFont(params);

		params.size = 80;
		params.minFilter = Texture.TextureFilter.Linear;
		params.magFilter = Texture.TextureFilter.Linear;
		font80 = g.generateFont(params);
	}
}
