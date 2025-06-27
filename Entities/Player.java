package Entities;

import Engine.GameLib;
import utils.EntityState;

import java.awt.*;
import java.lang.annotation.ElementType;

public class Player extends Entity{

    protected int playerShootingSpeed = 100;
    protected long nextShot; 

    public Player(){
        super(GameLib.WIDTH / 2, GameLib.HEIGHT * 0.90, 12);
        VX = 0.25;						// velocidade no eixo x
        VY = 0.25;						// velocidade no eixo y
        color = Color.BLUE;

        super.explosionTime = 2000; // tempo especifico para player

        // update para setar nextShot
        //update(0);
    }

    public void setShootingSpeed(int shootingSpeed){
        playerShootingSpeed = shootingSpeed;
    }

    public void update(long delta) {
        if (getState() != EntityState.EXPLODING) {
            //if (GameLib.iskeyPressed(GameLib.KEY_UP)) setY(getY() - delta * VY);
            //if (GameLib.iskeyPressed(GameLib.KEY_DOWN)) setY(getY() + delta * VY);
            //if (GameLib.iskeyPressed(GameLib.KEY_LEFT)) setX(getX() - delta * VX);
            //if (GameLib.iskeyPressed(GameLib.KEY_RIGHT)) setX(getX()+- delta * VX);

/*             if (GameLib.iskeyPressed(GameLib.KEY_CONTROL)) {
                if (now > nextShot) {
                    // DAR TIRO
                    nextShot = now + playerShootingSpeed;
                }
            } */
        } else {
            if (now > explosionEnd) {
                setState(EntityState.ACTIVE);
            }
        }
    }

    public void draw() {
        if (getState() == EntityState.EXPLODING) {
            super.explode();
        } else {
            GameLib.setColor(color);
            GameLib.drawPlayer(getX(), getY(), radius);
        }
    }
    public long getNextShot() {
        return nextShot;
    }
    public void setNextShot(long nextShot) {
        this.nextShot = nextShot;
    }
}