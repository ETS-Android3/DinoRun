package com.dinoRun;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dinoRun.extra.AssetMan;
import com.dinoRun.screens.GameOverScreen;
import com.dinoRun.screens.GameScreen;
import com.dinoRun.screens.GetReadyScreen;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class MainGame extends Game {
	public AssetMan assetManager;
	private SpriteBatch batch;
	private Texture image;

	public GameScreen gameScreen;
	public GetReadyScreen getReadyScreen;
	public GameOverScreen gameOverScreen;

	@Override
	public void create() {
		this.assetManager = new AssetMan();
		this.gameScreen = new GameScreen(this);
		this.getReadyScreen  = new GetReadyScreen(this);
		this.gameOverScreen = new GameOverScreen(this);

		//Scene2d nos ayuda a manejar las diferentes instancias de las diferentes pantallas que
		//compondrá nuestro juego.
		setScreen(this.getReadyScreen);
	}

}