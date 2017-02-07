package com.reddy.boxdrop.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.reddy.geodrop.Main;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Geo Drop";
		config.width = com.reddy.boxdrop.Main.WIDTH;
		config.height = com.reddy.boxdrop.Main.HEIGHT;
		new LwjglApplication(new com.reddy.boxdrop.Main(), config);
	}
}
