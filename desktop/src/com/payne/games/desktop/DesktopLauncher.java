package com.payne.games.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.payne.games.AriseDeeper;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Arise Deeper";
		config.width = 800;
		config.height = 480;
		new LwjglApplication(new AriseDeeper(), config);
	}
}
