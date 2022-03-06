package com.dinoRun.screens;

import static com.dinoRun.extra.Utils.SCREEN_HEIGHT;
import static com.dinoRun.extra.Utils.SCREEN_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.dinoRun.MainGame;

public class GetReadyScreen extends BaseScreen{

    private Stage stage;
    private Skin skin;
    private TextButton retry;
    private Image getReady;
    private Image background;
    private Music musicGetReady;

    public GetReadyScreen(MainGame mainGame) {
        super(mainGame);
        FitViewport fitViewport = new FitViewport(SCREEN_WIDTH,SCREEN_HEIGHT);
        this.stage = new Stage(fitViewport);
        // Skin para el botón de jugar
        skin = mainGame.assetManager.getSkin();
        retry = new TextButton("JUGAR", skin);
        // Captura el evento al tocar el botón de jugar
        retry.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                mainGame.setScreen(mainGame.gameScreen);
            }
        });
        retry.setSize(300,60);
        retry.setPosition(SCREEN_WIDTH / 2 - 300 / 2,200);
        this.musicGetReady = this.mainGame.assetManager.getMusicGetReady();
    }

    // Añade fondo de pantalla
    public void addBackground(){
        this.background = new Image(mainGame.assetManager.getBackground());
        this.background.setPosition(0,0);
        this.background.setSize(SCREEN_WIDTH,SCREEN_WIDTH);
        this.stage.addActor(this.background);
    }

    // Añade imágen get ready
    public void addGetReady(){
        this.getReady = new Image(mainGame.assetManager.getReady());
        this.getReady.setPosition(SCREEN_WIDTH / 2 - 400 / 2,370);
        this.getReady.setSize(400,80);
        this.stage.addActor(this.getReady);
    }

    @Override
    public void show() {
        addBackground();
        addGetReady();
        stage.addActor(retry);
        Gdx.input.setInputProcessor(stage);
        // Sonido en bucle de cancións
        this.musicGetReady.setLooping(true);
        this.musicGetReady.play();
    }

    // Elimina y deja de capturar el detector de los botones
    @Override
    public void hide() {
        this.background.remove();
        this.getReady.remove();
        this.retry.remove();
        Gdx.input.setInputProcessor(null);
        this.musicGetReady.stop();
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
