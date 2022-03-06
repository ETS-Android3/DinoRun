package com.dinoRun.actors;

import com.badlogic.gdx.Gdx;
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

public class Coin extends Actor {

    private Animation<TextureRegion> coinAnimation;
    private Vector2 position;
    private World world;
    private Body body;
    private int score;
    private Fixture fixture;
    private float stateTime;
    private boolean isContact;
    private float speed;

    // Constructor coin, pasando como parámtros la animacion, posición, valor de puntos y velocidad
    public Coin(World world, Animation<TextureRegion> coinAnimation, Vector2 position, int score, float speed) {
        this.coinAnimation = coinAnimation;
        this.position = position;
        this.world = world;
        this.stateTime = 0f;
        this.isContact = false;
        this.score = score;
        this.speed = speed;
        createBody();
        createFixture();
    }

    @Override
    public void act(float delta) {

    }

    //Dibujar la moneda en pantalla
    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition(body.getPosition().x - 0.2f,body.getPosition().y- 0.2f);
        batch.draw(this.coinAnimation.getKeyFrame(stateTime,true),getX(),getY(),0.4f,0.4f);

        stateTime += Gdx.graphics.getDeltaTime();
    }

    // Body coin
    private void createBody() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(position);
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        this.body = this.world.createBody(bodyDef);
        this.body.setLinearVelocity(this.speed,0);
    }
    // Fixture coin
    private void createFixture() {
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(0.2f,0.2f);
        this.fixture = this.body.createFixture(polygonShape,8);
        this.fixture.setSensor(true);
        // le paso el propio objeto para identificar que moneda es al colisionar
        this.fixture.setUserData(this);
        polygonShape.dispose();
    }

    // En caso de salir la moneda de la pantalla devuelve true para ser eliminada
    public boolean isOutOfScreen(){
        return this.body.getPosition().x <= -2f;
    }

    // Eliminar fixture y body de la moneda
    public void detach(){
        this.body.destroyFixture(this.fixture);
        this.world.destroyBody(this.body);
    }

    // Para la velocidad de la moneda
    public void stopSpeed(float speed) {
        this.body.setLinearVelocity(speed,0);
    }

    // Devuelve true si la moneda es tocada
    public boolean isContact() {
        return isContact;
    }

    // Método set para cambiar el valor del booleano del contactos
    public void setContact(boolean contact) {
        isContact = contact;
    }

    // Devuelve el valor de la moneda
    public int getScore() {
        return score;
    }
}
