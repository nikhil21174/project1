package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

public class PlayScreen implements Screen {
    private final MyGdxGame game;
    private World world;
    private Box2DDebugRenderer b2dr;
    private TiledMap map;
    private OrthogonalTiledMapRenderer pam;
    private OrthographicCamera camera;
    private PlayerL playerl;
    private Viewport gamePort;
    private PlayerR playerR;
    final ArrayList<String> dict = new ArrayList<String>();
    final ArrayList<String> dict1 = new ArrayList<String>();
    private int p, q;


    public PlayScreen(final MyGdxGame game, int i, int j) {
        this.game = game;
        gamePort = new FitViewport(1100.0F, 620.0F, camera);
        if (i < 0) {
            this.p = 8 + i;
        } else {
            this.p = i;
        }
        if (j < 0) {
            this.q = 8 + j;
        } else {
            this.q = j;
        }
        dict.add(0, "tank/Abrams.png"); //800
        dict.add(1, "tank/Frost.png"); //800
        dict.add(2, "tank/Buratino.png"); //750
        dict.add(3, "tank/Coalition.png"); //800
        dict.add(4, "tank/Dubstep_edited_29.png"); //800
        dict.add(5, "tank/Atomic.png");  //800
        dict.add(6, "tank/Mark_I.png"); //800
        dict.add(7, "tank/Pinky_edited.png");  //850

        dict1.add(0, "Rtank/AbramsR.png");
        dict1.add(1, "Rtank/FrostR.png");
        dict1.add(2, "Rtank/BuratinoR.png");
        dict1.add(3, "Rtank/CoalitionR.png");
        dict1.add(4, "Rtank/Dubstep_edited_29R.png");
        dict1.add(5, "Rtank/AtomicR.png");
        dict1.add(6, "Rtank/Mark_IR.png");
        dict1.add(7, "Rtank/Pinky_editedR.png");

        map = new TmxMapLoader().load("Maps/fb1.tmx");

        world = new World(new Vector2(0, 0),true);
        b2dr = new Box2DDebugRenderer();

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        BodyDef bdef2 = new BodyDef();
        PolygonShape shape2 = new PolygonShape();
        FixtureDef fdef2 = new FixtureDef();
        Body body2;

        for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(rect.getX() + rect.getWidth()/ 2, rect.getY() + rect.getHeight() / 2);
            body = world.createBody(bdef);
            shape.setAsBox(rect.getX() / 2, rect.getY() / 2);
            fdef.shape = shape;
            body.createFixture(fdef);
        }
    }

    @Override
    public void show() {
        map = new TmxMapLoader().load("Maps/fb1.tmx");
        pam = new OrthogonalTiledMapRenderer(map);
        camera = new OrthographicCamera();
        camera.position.set(gamePort.getScreenWidth() / 2, gamePort.getWorldHeight() / 2, 0);
        playerl = new PlayerL(new Sprite(new Texture(dict.get(p))), world);
        playerl.setSize(2000, 2000);
        playerR = new PlayerR(new Sprite(new Texture(dict1.get(q))), world);
        playerR.setSize(2000, 2000);
        playerR.setPosition(1200, 7550);
        playerR.setPosition(18200, 7450);
    }

    public void handleInput(float dt) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.J) && playerR.b2body.getLinearVelocity().x <= 2)
            playerR.b2body.applyLinearImpulse(new Vector2(0.2f, 0), playerR.b2body.getWorldCenter(), true);
        if (Gdx.input.isKeyPressed(Input.Keys.L) && playerR.b2body.getLinearVelocity().x >= -2)
            playerR.b2body.applyLinearImpulse(new Vector2(-0.2f, 0), playerR.b2body.getWorldCenter(), true);
        if (Gdx.input.isKeyJustPressed(Input.Keys.D) && playerR.b2body.getLinearVelocity().x <= 2)
            playerR.b2body.applyLinearImpulse(new Vector2(0.2f, 0), playerR.b2body.getWorldCenter(), true);
        if (Gdx.input.isKeyPressed(Input.Keys.A) && playerR.b2body.getLinearVelocity().x >= -2)
            playerR.b2body.applyLinearImpulse(new Vector2(-0.2f, 0), playerR.b2body.getWorldCenter(), true);
    }


    public void update(float dt){
        handleInput(dt);
        playerR.update(dt);
        playerl.update(dt);
        world.step(1/60f,6,2);
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_STENCIL_VALUE_MASK);
        b2dr.render(world,camera.combined);
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin(); //texture file load pic of mario/tank
        playerR.draw(game.batch);
        playerl.draw(game.batch);
        game.batch.end();
        camera.setToOrtho(false,22000,22000);
        pam.setView(camera);
        pam.render();


        pam.getBatch().begin();
        b2dr.render(world,camera.combined);
        playerl.draw(pam.getBatch());
        playerR.draw(pam.getBatch());
        Gdx.input.setInputProcessor(playerl);
        Gdx.input.setInputProcessor(playerR);
        pam.getBatch().end();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        pam.dispose();
        map.dispose();
        playerl.getTexture().dispose();
        playerR.getTexture().dispose();
        b2dr.dispose();
        world.dispose();
    }
}
