package com.reddy.boxdrop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.reddy.boxdrop.screens.SplashScreen;

public class Main extends Game {

	public static final int WIDTH = 1920;
	public static final int HEIGHT = 1152;
	public static final	float PPM = 100;

	public BitmapFont font100, font80, font180, fontBerlin;

	public SpriteBatch batch;

	public OrthographicCamera camera;

	public AssetManager manager;

	private Music gameSound;

	public Viewport viewPort;

	public AdHandler ah;

	public Main(AdHandler ah){
		this.ah = ah;
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, WIDTH, HEIGHT);
		manager = new AssetManager();

		viewPort = new StretchViewport(WIDTH, HEIGHT, camera);

		gameSound = Gdx.audio.newMusic(Gdx.files.internal("audio/song.ogg"));
		gameSound.setVolume(0.5f);
		gameSound.setLooping(true);

		initFonts();

		setScreen(new SplashScreen(this));
	}
	@Override
	public void render () {
		super.render();
	}

	public void playMusic(){
		gameSound.play();
	}

	public void stopMusic(){
		gameSound.pause();
	}

	public void setVolume(){
		gameSound.setVolume(0.05f);
		Timer.schedule(new Timer.Task() {
			@Override
			public void run() {
				gameSound.setVolume(0.5f);
			}
		}, 2.5f);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		manager.dispose();
		gameSound.dispose();
		font80.dispose();
		font100.dispose();
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

		params.size = 180;
		params.minFilter = Texture.TextureFilter.Linear;
		params.magFilter = Texture.TextureFilter.Linear;
		font180 = g.generateFont(params);

		FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("fonts/berlin.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameters = new FreeTypeFontGenerator.FreeTypeFontParameter();

		parameters.size = 180;
		parameters.minFilter = Texture.TextureFilter.Linear;
		parameters.magFilter = Texture.TextureFilter.Linear;
		fontBerlin = gen.generateFont(params);

	}
}
