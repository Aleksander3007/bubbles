package com.bubbles.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.bubbles.App;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Bubbles";
		config.width = 480;
		config.height = 640;
		LwjglApplicationConfiguration config2 = new LwjglApplicationConfiguration();
		config2.title = "Bubbles";
		config2.width = 640;
		config2.height = 1280;
		LwjglApplicationConfiguration config3 = new LwjglApplicationConfiguration();
		config2.title = "Bubbles";
		config2.width = 640;
		config2.height = 640;
		new LwjglApplication(new App(), config);
	}
}
