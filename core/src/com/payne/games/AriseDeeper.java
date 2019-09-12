package com.payne.games;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.payne.games.screens.GameScreen;
import com.payne.games.screens.MainMenuScreen;
import com.payne.games.splashScreen.ISplashWorker;


/*
		arise create
			/* setScreen(MenuScreen)
		menu constructor
		menu show
		menu resize - width: 800 | height: 480
		menu resize - width: 800 | height: 480
			/* Clicked "new game" which calls `setScreen(GameScreen)`
		game constructor
		menu hide
		game show
		game resize - width: 800 | height: 480
		menu dispose
			/* Swapping back to "MenuScreen"
		menu constructor
		game hide
		menu show
		menu resize - width: 800 | height: 480
			/* Again pressing "New Game" which calls setScreen(GameScreen)
		game constructor
		menu hide
		game show
		game resize - width: 800 | height: 480
		menu dispose
			/* Closed the application while on GameScreen
		game pause
		arise dispose
 */
public class AriseDeeper extends Game {

	/* Loading. */
	private ISplashWorker splashWorker;
	private Assets assets;

	/* Display. */
	public SpriteBatch batch;
	public BitmapFont font;

	/* Flow management. */
	private Screen previousScreen;


	/**
	 * Allows swapping between Screens while retaining a reference to the previous Screen.<br>
	 * This eventually allows the player to return to the previous Screen without losing its state.
	 *
	 * @param newScreen the Screen to show to the player.
	 */
	public void setNewScreen(Screen newScreen) {
		previousScreen = getScreen();
		setScreen(newScreen); // automatically calls "show()" on the newScreen
	}

	/**
	 * Returns to the Screen that was presented to the player before the one that is currently displayed.
	 */
	public void returnToPreviousScreen() {
		setNewScreen(previousScreen);
	}

	@Override
	public void create () {
		splashWorker.closeSplashScreen();

		System.out.println("arise create");
		batch  = new SpriteBatch();
		font   = new BitmapFont();
		assets = new Assets();

		assets.loadLoadingScreen();
		assets.loadGameAssets();

		setInitialScreen();
	}

	private void setInitialScreen() {
//		setScreen(new MainMenuScreen(this, assets.manager));

		/* Purely for debugging: skips the MainMenuScreen. */
		previousScreen = new MainMenuScreen(this, assets.manager);
		setScreen(new GameScreen(this, assets.manager));
	}


	/**
	 * todo: figure out how to do this
	 * Asynchronous loading of the game assets. Showing progression within the Loading Screen.
	 */
	private void loadingScreen() {

		/* Waits for all the assets to be loaded before moving on. */
		if (assets.manager.update()) {
			setScreen(new MainMenuScreen(this, assets.manager));
		}


		/* Display loading information. */
		float progress = assets.manager.getProgress();
		System.out.println("Progress: " + (int)(progress*100) + "%");
		batch.draw(assets.manager.get(Assets.LOADING_IMAGE), 0, 0);
		// todo: ProgressBar
	}

	@Override
	public void render () {
		super.render();
//		batch.begin();
//		loadingScreen();
//		batch.end();
	}
	
	@Override
	public void dispose () {
		System.out.println("arise dispose");
		batch.dispose();
		font.dispose();
		assets.dispose();

		/* Cleaning the Screens as well. */
		getScreen().dispose();
		if(previousScreen != null)
			previousScreen.dispose();
	}

//	@Override
//	public void resume() {
//		/*
//		Resuming with a Loading Screen
//
//			On Android, your app can be paused and resumed. Managed OpenGL resources like Textures need to be reloaded in that case, which can take a bit of time. If you want to display a loading screen on resume, you can do the following after you created your AssetManager.
//
//				`Texture.setAssetManager(manager);`
//
//			In your ApplicationListener.resume() method you can then switch to your loading screen and call AssetManager.update() again until everything is back to normal.
//			If you don't set the AssetManager as shown in the last snippet, the usual managed texture mechanism will kick in, so you don't have to worry about anything.
//		 */
//	}


	public ISplashWorker getSplashWorker() {
		return splashWorker;
	}
	public void setSplashWorker(ISplashWorker splashWorker) {
		this.splashWorker = splashWorker;
	}
	public Screen getPreviousScreen() {
		return previousScreen;
	}
}
