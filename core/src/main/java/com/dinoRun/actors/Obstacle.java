package com.dinoRun.actors;

import static com.dinoRun.extra.Utils.OBSTACLE;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Obstacle extends Actor {
    private TextureRegion obstacle;
    private Body body;
    private Fixture fixture;
    private World world;
    private Vector2 position;
    private float speed;

    // Constructor del obstáculo pasando el mundo, la textura, posición y velocidad
    public Obstacle(World world, TextureRegion obstacle, Vector2 position, float speed) {
        this.obstacle = obstacle;
        this.world = world;
        this.position = position;
        this.speed = speed;
        createBody();
        createFixture();
    }

    // Dibuja el obstáculo
    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition(this.body.getPosition().x - 0.35f,this.body.getPosition().y - 0.45f);
        batch.draw(this.obstacle,getX(),getY(),0.7f,0.9f);
    }

    @Override
    public void act(float delta) {

    }

    // Body obstáculo
    private void createBody() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(this.position);
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        this.body = this.world.createBody(bodyDef);
        this.body.setLinearVelocity(this.speed,0);
    }

    // Fixture obstáculo
    private void createFixture() {
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(0.35f,0.45f);
        this.fixture = this.body.createFixture(polygonShape,8);
        this.fixture.setUserData(OBSTACLE);
        polygonShape.dispose();
    }

    // Elimina fixture y body
    public void detach(){
        this.body.destroyFixture(this.fixture);
        this.world.destroyBody(this.body);
    }

    // Si esta fuera de la pantalla devuelve true para ser eliminado
    public boolean isOutOfScreen(){
        return this.body.getPosition().x <= -2f;
    }

    // Cambia la velocidad
    public void stopSpeed(float speed) {
        this.body.setLinearVelocity(speed,0);
    }
}
