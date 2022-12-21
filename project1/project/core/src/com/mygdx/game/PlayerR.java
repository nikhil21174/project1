package com.mygdx.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import static com.badlogic.gdx.Input.Keys.J;
import static com.badlogic.gdx.Input.Keys.L;

public class PlayerR extends Sprite implements InputProcessor{
    private Vector2 velocity = new Vector2();
    private World world;
    private TextureRegion rtank;
    Body b2body;
    private float speed = 1000*2, gravity = 60*1.8f;

    public void playerr(World world){
        this.world=world;
        defineplayerr();
    }

    public PlayerR(Sprite sprite,World world) {
        super(sprite);
        this.world=world;
        defineplayerr();
    }

    public void tankr(World world, PlayScreen screen){
        this.world=world;
        defineplayerr();
//      rtank=screen.;
        setBounds(0,0,16,16);
        setRegion(rtank);
    }

    public void defineplayerr(){
        BodyDef bdef=new BodyDef();
        bdef.position.set(20000,6500);
        bdef.type=BodyDef.BodyType.DynamicBody;
        b2body=world.createBody(bdef);

        FixtureDef fdef=new FixtureDef();
        CircleShape shape=new CircleShape();
        shape.setRadius(500);

        fdef.shape=shape;
        b2body.createFixture(fdef);
    }

    public void update(float deltaTime) {
        velocity.x = gravity*deltaTime;
        setPosition(b2body.getPosition().x-getWidth()/2,b2body.getPosition().y-getHeight()/2);
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode){
            case J:
                float oldx = getX(), oldy = getY();
                velocity.x =-speed;
                setPosition(oldx+500 , oldy);
                b2body.setLinearVelocity(velocity);
                break;
            case L:
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