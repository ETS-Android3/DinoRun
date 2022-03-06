package com.dinoRun.screens;

import static com.dinoRun.extra.Utils.SCREEN_HEIGHT;
import static com.dinoRun.extra.Utils.SCREEN_WIDTH;
import static com.dinoRun.extra.Utils.SKIN_JSON;
import static com.dinoRun.extra.Utils.WORLD_HEIGTH;
import static com.dinoRun.extra.Utils.WORLD_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.dinoRun.MainGame;

public class GameOverScreen extends BaseScreen{

    private Stage stage;
    private Skin skin;
    private TextButton retry;
    private TextButton menu;
    private Image gameOver;
    private Image background;
    private Sound gameOverSound;

    public GameOverScreen(MainGame mainGame) {
        super(mainGame);

        FitViewport fitViewport = new FitViewport(SCREEN_WIDTH,SCREEN_HEIGHT);
        this.stage = new Stage(fitViewport);
        skin = mainGame.assetManager.getSkin();
        // Añadir botones volver a intentar y menú
        retry = new TextButton("VOLVER A INTENTAR", skin);
        menu = new TextButton("VOLVER AL MENU", skin);
        // sonido game over, esta puesto como sonido y no como música porque dura menos de 10 segundos
        this.gameOverSound = this.mainGame.assetManager.getGameOverSound();
        // Capturar evento botón volver a intentar
        retry.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                mainGame.setScreen(mainGame.gameScreen);
            }
        });
        // Capturar evento menú
        menu.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                mainGame.setScreen(mainGame.getReadyScreen);
            }
        });
        retry.setSize(300,60);
        retry.setPosition(SCREEN_WIDTH / 2 - 300 / 2,200);
        menu.setSize(300,60);
        menu.setPosition(SCREEN_WIDTH / 2 - 300 / 2,120);
    }

    // Añadir fondo de pantalla
    public void addBackground(){
        this.background = new Image(mainGame.assetManager.getBackground());
        this.background.setPosition(0,0);
        this.background.setSize(SCREEN_WIDTH,SCREEN_WIDTH);
        this.stage.addActor(this.background);
    }

    // Añadir imagén Get Ready
    public void addGetReady(){
        this.gameOver = new Image(mainGame.assetManager.getGameOver());
        this.gameOver.setPosition(SCREEN_WIDTH / 2 - 400 / 2,370);
        this.gameOver.setSize(400,80);
        this.stage.addActor(this.gameOver);
    }

    // Añadir actores
    @Override
    public void show() {
        addBackground();
        addGetReady();
        stage.addActor(menu);
        stage.addActor(retry);
        // Detectar al pulsar el boton
        Gdx.input.setInputProcessor(stage);
        this.gameOverSound.play();
    }

    // Elinar y desactivar el detectar los botones al ocultr la pantalla
    @Override
    public void hide() {
        this.background.remove();
        this.gameOver.remove();
        this.retry.remove();
        this.menu.remove();
        Gdx.input.setInputProcessor(null);
        this.gameOverSound.stop();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public void render(float delta) {
        // Con esto pantalla borra lo que había y dibuja el fondo con un color
        Gdx.gl.glClearColor(0.4f, 0.5f, 0.8f,1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }
}
