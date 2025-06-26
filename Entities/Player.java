package Entities;

import Engine.GameLib;
import utils.EntityState;

import java.awt.*;
import java.lang.annotation.ElementType;

public class Player extends Entity{

    protected int playerShootingSpeed = 100;

    public Player(double x, double y, double radius){
        super(x, y, radius);
        VX = 0.25;						// velocidade no eixo x
        VY = 0.25;						// velocidade no eixo y
        color = Color.BLUE;
    }

    public void setShootingSpeed(int shootingSpeed){
        playerShootingSpeed = shootingSpeed;
    }

    public void update(long delta) {
        if (getState() != EntityState.EXPLODING) {
            if (GameLib.iskeyPressed(GameLib.KEY_UP)) setY(getY() - delta * VY);
            if (GameLib.iskeyPressed(GameLib.KEY_DOWN)) setY(getY() + delta * VY);
            if (GameLib.iskeyPressed(GameLib.KEY_LEFT)) setX(getX() - delta * VX);
            if (GameLib.iskeyPressed(GameLib.KEY_RIGHT)) setX(getX()+- delta * VX);

            if (GameLib.iskeyPressed(GameLib.KEY_CONTROL)) {
                if (now > nextShot) {
                    // DAR TIRO
                    nextShot = now + playerShootingSpeed;
                }
            }
        } else {
            if (now > explosionEnd) {
                setState(EntityState.ACTIVE);
            }
        }
    }

    public void draw() {
        if (getState() == EntityState.EXPLODING) {
            explode(2000);
        } else {
            GameLib.setColor(color);
            GameLib.drawPlayer(getX(), getY(), radius);
        }
    }
}