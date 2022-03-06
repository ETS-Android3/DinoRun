package com.dinoRun.extra;

//import static com.dinoRun.extra.Utils.ATLAS_COIN;
import static com.dinoRun.extra.Utils.ATLAS_MAP;
import static com.dinoRun.extra.Utils.ATLAS_SKIN;
import static com.dinoRun.extra.Utils.BACKGROUND_IMAGE;
import static com.dinoRun.extra.Utils.FONT_FNT;
import static com.dinoRun.extra.Utils.FONT_PNG;
import static com.dinoRun.extra.Utils.GAME_OVER_IMAGE;
import static com.dinoRun.extra.Utils.GET_READY_IMAGE;
import static com.dinoRun.extra.Utils.MUSIC_BG_PLAY;
import static com.dinoRun.extra.Utils.MUSIC_GET_READY;
import static com.dinoRun.extra.Utils.SKIN_JSON;
import static com.dinoRun.extra.Utils.SOUND_COIN;
import static com.dinoRun.extra.Utils.SOUND_GAME_OVER;
import static com.dinoRun.extra.Utils.SOUND_JUMP;
import static com.dinoRun.extra.Utils.SOUND_LOST;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Json;

public class AssetMan {
    private AssetManager assetManager;
    private TextureAtlas textureAtlas;

    public AssetMan() {
        this.assetManager = new AssetManager();

        //assetManager.load(ATLAS_COIN,TextureAtlas.class);
        assetManager.load(ATLAS_MAP,TextureAtlas.class);
        assetManager.load(ATLAS_SKIN,TextureAtlas.class);
        assetManager.load(SOUND_JUMP, Sound.class);
        assetManager.load(SOUND_GAME_OVER, Sound.class);
        assetManager.load(SOUND_COIN, Sound.class);
        assetManager.load(SOUND_LOST, Sound.class);
        assetManager.load(SOUND_GAME_OVER, Sound.class);
        assetManager.load(MUSIC_BG_PLAY, Music.class);
        assetManager.load(MUSIC_GET_READY, Music.class);
        assetManager.finishLoading();
        textureAtlas = assetManager.get(ATLAS_MAP);
    }

    // Textura fondo de pantalla
    public TextureRegion getBackground() {
        return this.textureAtlas.findRegion(BACKGROUND_IMAGE);
    }
    // Textura imágen Get Ready
    public TextureRegion getReady() {
        return this.textureAtlas.findRegion(GET_READY_IMAGE);
    }
    // Textura imágen Game Over
    public TextureRegion getGameOver() {
        return this.textureAtlas.findRegion(GAME_OVER_IMAGE);
    }

    // Animación dino
    public Animation<TextureRegion> getDinoAnimation(){
        return new Animation<TextureRegion>(0.125f,
                textureAtlas.findRegion("Dino1"),
                textureAtlas.findRegion("Dino2"),
                textureAtlas.findRegion("Dino3"),
                textureAtlas.findRegion("Dino4"),
                textureAtlas.findRegion("Dino5"),
                textureAtlas.findRegion("Dino6"),
                textureAtlas.findRegion("Dino7"),
                textureAtlas.findRegion("Dino8"));
    }

    // Animación salto dino
    public Animation<TextureRegion> getDinoJumpAnimation(){
        return new Animation<TextureRegion>(0.083f,
                textureAtlas.findRegion("DinoJump1"),
                textureAtlas.findRegion("DinoJump2"),
                textureAtlas.findRegion("DinoJump3"),
                textureAtlas.findRegion("DinoJump4"),
                textureAtlas.findRegion("DinoJump5"),
                textureAtlas.findRegion("DinoJump6"),
                textureAtlas.findRegion("DinoJump7"),
                textureAtlas.findRegion("DinoJump8"),
                textureAtlas.findRegion("DinoJump9"),
                textureAtlas.findRegion("DinoJump10"),
                textureAtlas.findRegion("DinoJump11"),
                textureAtlas.findRegion("DinoJump12"));
    }

    // Animación al choca el dino
    public Animation<TextureRegion> getDinoDiedAnimation(){
        return new Animation<TextureRegion>(0.125f,
                textureAtlas.findRegion("DinoDead1"),
                textureAtlas.findRegion("DinoDead2"),
                textureAtlas.findRegion("DinoDead3"),
                textureAtlas.findRegion("DinoDead4"),
                textureAtlas.findRegion("DinoDead5"),
                textureAtlas.findRegion("DinoDead6"),
                textureAtlas.findRegion("DinoDead7"),
                textureAtlas.findRegion("DinoDead8"));
    }

    // Animación moneda oro
    public Animation<TextureRegion> getCoinAnimationGold(){
        return new Animation<TextureRegion>(0.1f,
                textureAtlas.findRegion("Gold1"),
                textureAtlas.findRegion("Gold2"),
                textureAtlas.findRegion("Gold3"),
                textureAtlas.findRegion("Gold4"),
                textureAtlas.findRegion("Gold5"),
                textureAtlas.findRegion("Gold6"),
                textureAtlas.findRegion("Gold7"),
                textureAtlas.findRegion("Gold8"),
                textureAtlas.findRegion("Gold9"),
                textureAtlas.findRegion("Gold10"));
    }

    // Animación monedas plata
    public Animation<TextureRegion> getCoinAnimationSilver(){
        return new Animation<TextureRegion>(0.1f,
                textureAtlas.findRegion("Silver1"),
                textureAtlas.findRegion("Silver2"),
                textureAtlas.findRegion("Silver3"),
                textureAtlas.findRegion("Silver4"),
                textureAtlas.findRegion("Silver5"),
                textureAtlas.findRegion("Silver6"),
                textureAtlas.findRegion("Silver7"),
                textureAtlas.findRegion("Silver8"),
                textureAtlas.findRegion("Silver9"),
                textureAtlas.findRegion("Silver10"));
    }

    // Animación moneda bronce
    public Animation<TextureRegion> getCoinAnimationBronze(){
        return new Animation<TextureRegion>(0.1f,
                textureAtlas.findRegion("Bronze1"),
                textureAtlas.findRegion("Bronze2"),
                textureAtlas.findRegion("Bronze3"),
                textureAtlas.findRegion("Bronze4"),
                textureAtlas.findRegion("Bronze5"),
                textureAtlas.findRegion("Bronze6"),
                textureAtlas.findRegion("Bronze7"),
                textureAtlas.findRegion("Bronze8"),
                textureAtlas.findRegion("Bronze9"),
                textureAtlas.findRegion("Bronze10"));
    }

    //Textura obstáculo
    public TextureRegion getObstacle(){
        return this.textureAtlas.findRegion("obstacle");
    }

    // Fuente de puntuación
    public BitmapFont getFont(){
        return new BitmapFont(Gdx.files.internal(FONT_FNT),Gdx.files.internal(FONT_PNG), false);
    }

    // Skin botones
    public Skin getSkin(){
        return new Skin(Gdx.files.internal(SKIN_JSON));
    }

    // Sonido monedas
    public Sound getCoinSound(){
        return this.assetManager.get(SOUND_COIN);
    }

    // Sonido salto
    public Sound getJumpSound(){
        return this.assetManager.get(SOUND_JUMP);
    }

    // Sonido al chocar
    public Sound getLostSound(){
        return this.assetManager.get(SOUND_LOST);
    }

    // Sonido pantalla game over
    public Sound getGameOverSound(){
        return this.assetManager.get(SOUND_GAME_OVER);
    }

    // Música al estar jugando
    public Music getMusicPLay(){
        return this.assetManager.get(MUSIC_BG_PLAY);
    }

    // Música pantalla get ready
    public Music getMusicGetReady(){
        return this.assetManager.get(MUSIC_GET_READY);
    }
}

