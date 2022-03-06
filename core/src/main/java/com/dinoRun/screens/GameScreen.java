package com.dinoRun.screens;

import static com.dinoRun.extra.Utils.SCREEN_HEIGHT;
import static com.dinoRun.extra.Utils.SCREEN_WIDTH;
import static com.dinoRun.extra.Utils.DINO;
import static com.dinoRun.extra.Utils.FLOOR;
import static com.dinoRun.extra.Utils.OBSTACLE;
import static com.dinoRun.extra.Utils.WORLD_HEIGTH;
import static com.dinoRun.extra.Utils.WORLD_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.dinoRun.MainGame;
import com.dinoRun.actors.Coin;
import com.dinoRun.actors.Dino;
import com.dinoRun.actors.Obstacle;

public class GameScreen extends BaseScreen implements ContactListener {

    private static final float TIME_TO_SPAWN_COIN =  2.5f;
    private static final float TIME_TO_SPAWN_OBSTACLE =  2.5f;
    private float timeToCreateCoin;
    private float timeToCreateObstacle;
    private Stage stage;
    private Image background;
    private Dino dino;
    private World world;
    private Array<Coin> arrayCoin;
    private Array<Obstacle> arrayObstacle;
    private int score;
    private OrthographicCamera scoreCamera;
    private BitmapFont scoreFont;
    private float speed = -2f;
    private Sound coinSound;
    private Sound lostSound;
    private Sound jumpSound;
    private Music musicPlay;

    public GameScreen(MainGame mainGame){
        super(mainGame);
        this.world = new World(new Vector2(0,-10),true);
        // Activa el listener de las colisiones
        this.world.setContactListener(this);

        FitViewport fitViewport = new FitViewport(WORLD_WIDTH,WORLD_HEIGTH);
        this.stage = new Stage(fitViewport);
        this.arrayCoin = new Array<>();
        this.arrayObstacle = new Array<>();
        this.timeToCreateCoin = 0f;
        this.timeToCreateObstacle = 0f;
        // Asigno los sonidos a las variables
        this.jumpSound = this.mainGame.assetManager.getJumpSound();
        this.musicPlay = this.mainGame.assetManager.getMusicPLay();
        this.coinSound = this.mainGame.assetManager.getCoinSound();
        this.lostSound = this.mainGame.assetManager.getLostSound();
        // Camara puntuación
        cameraScore();
    }

    // Método para obtener la fuente del contador y asignarla
    private void cameraScore(){
        this.score = 0;
        this.scoreFont = this.mainGame.assetManager.getFont();
        this.scoreFont.getData().scale(0.8f);
        this.scoreCamera = new OrthographicCamera();
        this.scoreCamera.setToOrtho(false, SCREEN_WIDTH,SCREEN_HEIGHT);
        this.scoreCamera.update();

    }

    // Método para añadir el fondo de panataña
    public void addBackground(){
        this.background = new Image(mainGame.assetManager.getBackground());
        this.background.setPosition(0,0);
        this.background.setSize(WORLD_WIDTH,WORLD_HEIGTH);
        this.stage.addActor(this.background);
    }

    // Método para añadir el suelo
    public void addFloor(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(WORLD_WIDTH/2,0.35f);
        bodyDef.type = BodyDef.BodyType.StaticBody;
        Body body = this.world.createBody(bodyDef);

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(WORLD_WIDTH/2f,0.3f);
        Fixture fixture = body.createFixture(polygonShape,8);
        fixture.setUserData(FLOOR);
        polygonShape.dispose();
    }

    // Si el dinosaurio sigue vivo, dependiendo del tiempo puesto para que salga la moneda,
    // se creará una nueva moneda y será añadido al array de monedas.
    public void addCoin(float delta){
        int numberRamdon = MathUtils.random(1,3);
        Animation<TextureRegion> coinSprite;
        Coin coin = null;

        if(dino.isAlive()){
            this.timeToCreateCoin += delta;
            if(this.timeToCreateCoin >= TIME_TO_SPAWN_COIN){
                this.timeToCreateCoin-=TIME_TO_SPAWN_COIN;
                // Crea un número aleatorio entre 1 y 3 para añadir una moneda de oro, plata o bronce con sus
                // correspondientes valores y animaciones
                float posRandomY = MathUtils.random(3f, 4f);
                switch (numberRamdon){
                    case 1:
                        coinSprite = mainGame.assetManager.getCoinAnimationBronze();
                        coin = new Coin(this.world,coinSprite,new Vector2(WORLD_WIDTH+0.4f,posRandomY),numberRamdon,this.speed);
                        break;
                    case 2:
                        coinSprite = mainGame.assetManager.getCoinAnimationSilver();
                        coin = new Coin(this.world,coinSprite,new Vector2(WORLD_WIDTH+0.4f,posRandomY),numberRamdon, this.speed);
                        break;
                    case 3:
                        coinSprite = mainGame.assetManager.getCoinAnimationGold();
                        coin = new Coin(this.world,coinSprite,new Vector2(WORLD_WIDTH+0.4f,posRandomY),numberRamdon, this.speed);
                        break;
                }
                arrayCoin.add(coin);
                this.stage.addActor(coin);
            }
        }
    }

    // En caso de que el valor de isContact() de la moneda sea true o la moneda esté fuera de la pantalla,
    // la moneda sera eliminada
    public void removeCoin(){
        for (Coin coin : this.arrayCoin) {
            if(!world.isLocked()) {
                if(coin.isContact() || coin.isOutOfScreen() || !this.dino.isAlive()) {
                    coin.detach();
                    coin.remove();
                    arrayCoin.removeValue(coin,false);
                }
            }
        }
    }

    // Si el dinosaurio sigue vivo, dependiendo del tiempo puesto para que salga el obstáculo,
    // se creará un nuevo obstáculo y será añadido al array de obstáculos.
    public void addObstacle(float delta){
        TextureRegion obstacleRegion =  mainGame.assetManager.getObstacle();
        if(dino.isAlive()){
            this.timeToCreateObstacle += delta;
            if(this.timeToCreateObstacle >= TIME_TO_SPAWN_OBSTACLE){
                this.timeToCreateObstacle-=TIME_TO_SPAWN_OBSTACLE;
                Obstacle obstacle = new Obstacle(this.world,obstacleRegion,new Vector2(WORLD_WIDTH+0.4f,1.1f), this.speed);
                arrayObstacle.add(obstacle);
                this.stage.addActor(obstacle);
            }
        }
    }

    // Recorre el array de obstáculos y si estos obstáculos se salen de la pantalla, son eliminados
    public void removeObstacle(){
        for (Obstacle obstacle : this.arrayObstacle) {
            if(!world.isLocked()) {
                if(obstacle.isOutOfScreen() || !this.dino.isAlive()) {
                    obstacle.detach();
                    obstacle.remove();
                    arrayObstacle.removeValue(obstacle,false);
                }
            }
        }
    }

    // Añade el dinosaurio al escenario
    private void addDino() {
        Animation<TextureRegion> dinoSprite = mainGame.assetManager.getDinoAnimation();

        this.dino = new Dino(this.world,dinoSprite, new Vector2(1.1f ,1.25f), this.jumpSound);
        this.stage.addActor(this.dino);
    }

    @Override
    public void show() {
        addBackground();
        addFloor();
        addDino();

        // Hace que la canción suene en buble
        this.musicPlay.setLooping(true);
        this.musicPlay.play();
    }

    @Override
    public void render(float delta) {
        // Con esto pantalla borra lo que había y dibuja el fondo con un color
        Gdx.gl.glClearColor(0.4f, 0.5f, 0.8f,1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        addObstacle(delta);
        addCoin(delta);
        this.stage.act();
        this.world.step(delta,6,2);
        this.stage.draw();

        removeCoin();
        removeObstacle();
        // Segunda cámara de la puntuación
        this.stage.getBatch().setProjectionMatrix(this.scoreCamera.combined);
        this.stage.getBatch().begin();
        this.scoreFont.draw(this.stage.getBatch(), ""+this.score,SCREEN_WIDTH/2 - 0.8f/2, 470);
        this.stage.getBatch().end();
    }

    // Al ocultarse elimina el dino y pone la velocidad y puntuación a la inicial
    @Override
    public void hide() {
        speed = -2f;
        score = 0;
        this.dino.detach();
        this.dino.remove();
        this.musicPlay.stop();
    }

    @Override
    public void dispose() {
        this.stage.dispose();
        this.world.dispose();
    }

    // Al colisionar devuelve true si el objeto A a colisionado con el objeto B o al revés,
    // porque no sabemos cual de los dos objetos tomará como que colisiona primero
    public boolean areColider(Contact contact, Object objA, Object objB){
        return (contact.getFixtureA().getUserData().equals(objA) && contact.getFixtureB().getUserData().equals(objB)) ||
                (contact.getFixtureA().getUserData().equals(objB) && contact.getFixtureB().getUserData().equals(objA));
    }

    public void addScore(Contact contact){
        // Comprueba si los contactos es una instancia de moneda
        Coin coin = null;
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        // Si es una instancia de moneda, pongo en true que la moneda ha contactado con el dinosaurio,
        // para posteriormente borrarla y suma uno a la puntuación
        if(fixtureA.getUserData() instanceof Coin){
            coin = (Coin) fixtureA.getUserData();
            coin.setContact(true);
            score += coin.getScore();
            this.coinSound.play();

        }else if(fixtureB.getUserData() instanceof Coin){
            coin = (Coin) fixtureB.getUserData();
            coin.setContact(true);
            score += coin.getScore();
            this.coinSound.play();

        }
        // Cada 5 puntos la velocidad aumenta
        if(this.score % 5 == 0){
            this.speed += -0.3f;
        }
    }


    @Override
    public void beginContact(Contact contact) {
        // Metodo que suma la puntuacion de la moneda
        addScore(contact);
        // Si colisiona el dinosaurio con el suelo pone la variable de esta saltando a true y activa la
        // animación de correr y si esta saltando activa la animación de saltar
        if(areColider(contact, DINO, FLOOR)){
            this.dino.setJumping(false);
            if(this.dino.isAlive())
                this.dino.setDinoAnimation(this.mainGame.assetManager.getDinoAnimation());
        }

        // Si el objeto colisionado es el dinosaruio y el obstáculo
        // Activa la animación del dino al chocar, suena el sonido, cambia el valor de dino vivo y para las monedas
        if(areColider(contact, DINO, OBSTACLE)){
            Animation<TextureRegion> dinoSprite = mainGame.assetManager.getDinoDiedAnimation();
            this.dino.setDinoAnimation(dinoSprite);
            this.lostSound.play();
            this.dino.setAlive(false);
            for(Coin coin:this.arrayCoin){
                coin.stopSpeed(0f);
            }

            for(Obstacle obstacle:this.arrayObstacle){
                obstacle.stopSpeed(0f);
            }

            // delay de 0.9f al chocar y cambio de pantalla a game Over
            this.stage.addAction(Actions.sequence(
                    Actions.delay(0.9f),
                    Actions.run(new Runnable() {
                        @Override
                        public void run() {
                            mainGame.setScreen(mainGame.gameOverScreen);
                        }
                    })
            ));
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
