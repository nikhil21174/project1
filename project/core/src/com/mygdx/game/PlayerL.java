package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapImageLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import static com.badlogic.gdx.Input.Keys.A;
import static com.badlogic.gdx.Input.Keys.D;

public class PlayerL extends Sprite implements InputProcessor {
    private Vector2 velocity = new Vector2();
    public World world;
    public Body b2body;
    private TextureRegion ltank;
    private float speed = 1000*2, gravity = 60*1.8f;

    public void playerl(World world){
        this.world=world;
        defineplayerl();
    }

    public PlayerL(Sprite sprite,World world) {
        super(sprite);
        this.world=world;
        defineplayerl();
    }

    public void ltank(World world, PlayScreen screen){
        this.world=world;
        defineplayerl();
//      ltank=screen.;
        setBounds(0,0,16,16);
        setRegion(ltank);
    }

    public void defineplayerl(){
        BodyDef bdef=new BodyDef();
        bdef.position.set(2000,6500);
        bdef.type=BodyDef.BodyType.DynamicBody;
        b2body=world.createBody(bdef);

        FixtureDef fdef=new FixtureDef();
        CircleShape shape=new CircleShape();
        shape.setRadius(500);

        fdef.shape=shape;
        b2body.createFixture(fdef);
    }

    public void draw(Batch spriteBatch){
        update(Gdx.graphics.getDeltaTime());
        super.draw(spriteBatch);
    }
    public void update(float deltaTime) {
        velocity.x = gravity*deltaTime;
        setPosition(b2body.getPosition().x-getWidth()/2,b2body.getPosition().y-getHeight()/2);
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode){
            case A:
                float oldx = getX(), oldy = getY();
                velocity.x =-speed;
                setPosition(oldx+500 , oldy);
                b2body.setLinearVelocity(velocity);
                break;
            case D:
                float oldX = getX(), oldY = getY();
                velocity.x = +speed;
                setPosition(oldX-500,oldY);
                b2body.setLinearVelocity(velocity);
                break;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode){
            case Input.Keys.HOME:
            case Input.Keys.END:
                velocity.x=0;
        }
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}