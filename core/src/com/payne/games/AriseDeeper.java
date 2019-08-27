package com.payne.games;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.payne.games.screens.GameScreen;
import com.payne.games.screens.MainMenuScreen;

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
	public SpriteBatch batch;
	public BitmapFont font;


	@Override
	public void create () {
		System.out.println("arise create");
		batch = new SpriteBatch();
		font = new BitmapFont();
		this.setScreen(new MainMenuScreen(this));
//		this.setScreen(new GameScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		System.out.println("arise dispose");
		batch.dispose();
		font.dispose();
	}
}
