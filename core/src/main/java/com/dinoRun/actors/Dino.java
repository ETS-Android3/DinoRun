package com.dinoRun.actors;

import static com.dinoRun.extra.Utils.DINO;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.dinoRun.extra.AssetMan;

public class Dino extends Actor {
    private Animation<TextureRegion> dinoAnimation;
    private Vector2 position;
    private World world;
    private Body body;
    private Fixture fixture;
    private static final float JUMP_SPEED = 5.9f;

    private boolean isAlive;
    private float stateTime;
    private AssetMan assetManager;
    private Sound jumpSound;
    private boolean isJumping;


    // Constructor que se le pasa la animación. posición y sonido del salto
    public Dino(World world, Animation<TextureRegion> dinoAnimation, Vector2 position,Sound jumpSound) {
        this.world = world;
        this.assetManager = new AssetMan();
        this.dinoAnimation = dinoAnimation;
        this.position = position;
        this.stateTime = 0f;
        this.isAlive = true;
        this.jumpSound = jumpSound;
        this.isJumping = false;
        createBody();
        createFixture();
    }

    @Override
    public void act(float delta) {
        // Salta al tocar la pantalla
        boolean jump = Gdx.input.justTouched();
        // Si es tocada la pantalla, sta vivo y no esta saltandoentra en el if
        if(jump && this.isAlive && !this.isJumping) {
            // pone a true el valor de esta saltando, cambia la animación, aunmenta el valor de y del dinosaurio y
            // activa el sonido del salto
            this.isJumping = true;
            this.dinoAnimation = this.assetManager.getDinoJumpAnimation();
            this.jumpSound.play();
            this.body.setLinearVelocity(0, JUMP_SPEED);
        }
        // Si está saltando se le aplica una fuerza en proporcion de la velocidad del salto
        if(this.isJumping){
            this.body.applyForceToCenter(0,JUMP_SPEED * 1.25f,true);
        }
    }

    // Dibuja el dinosaurio con la animación
    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition(body.getPosition().x-0.4f,body.getPosition().y-0.75f);
        batch.draw(this.dinoAnimation.getKeyFrame(stateTime,true),getX(),getY(), 0.8f,1.5f);

        stateTime += Gdx.graphics.getDeltaTime();

    }

    //Body dino
    public void createBody(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(position);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        this.body = this.world.createBody(bodyDef);
    }

    // Fixture dino
    public void createFixture(){
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(0.4f,0.75f);
        this.fixture = this.body.createFixture(polygonShape,8);
        this.fixture.setUserData(DINO);
        polygonShape.dispose();
    }

    // Elimina body y fixture dino
    public void detach(){
        this.body.destroyFixture(this.fixture);
        this.world.destroyBody(this.body);
    }

    // Devuelve si el dino esta vivo o no
    public boolean isAlive() {
        return this.isAlive;
    }

    // Si no esta vivo cambia la velocidad del dino a 0 y cambiael estado de isAlive
    public void setAlive(boolean alive) {
        if(!alive){
          this.stateTime = 0f;
        }
        this.isAlive = alive;
    }

    //Cambia la animación del dino
    public void setDinoAnimation(Animation<TextureRegion> dinoAnimation) {
        this.dinoAnimation = dinoAnimation;
    }

    // cambia la variable de esta saltando
    public void setJumping(boolean jumping) {
        isJumping = jumping;
    }
}
