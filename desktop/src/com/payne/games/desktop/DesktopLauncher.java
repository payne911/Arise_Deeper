package com.payne.games.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.payne.games.AriseDeeper;
import com.payne.games.logic.GameLogic;


public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title  = GameLogic.GAME_NAME;
		config.width  = GameLogic.GAME_WIDTH;
		config.height = GameLogic.GAME_HEIGHT;

		AriseDeeper core = new AriseDeeper();
		core.setSplashWorker(new DesktopSplashWorker());
		new LwjglApplication(core, config);
	}
}
