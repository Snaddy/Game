package com.reddy.boxdrop.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Box Drop";
		config.width = com.reddy.boxdrop.Main.WIDTH;
		config.height = com.reddy.boxdrop.Main.HEIGHT;
		new LwjglApplication(new com.reddy.boxdrop.Main(), config);
	}
}
