package Entities;

import Engine.GameLib;
import utils.EntityState;

import java.awt.*;
import java.lang.annotation.ElementType;

public class Player extends Entity{

    protected int playerShootingSpeed = 100;

    public Player(){
        super(GameLib.WIDTH / 2, GameLib.HEIGHT * 0.90, 12);
        VX = 0.25;						// velocidade no eixo x
        VY = 0.25;						// velocidade no eixo y
        color = Color.BLUE;

        // update para setar nextShot
        update(0);
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

    /* colisões player - projeteis (inimigo). Checa se o projetil existe e se há colisões, depois explode */
    public void collide(GameElement element, long currentTime){
        if(getState() == EntityState.ACTIVE){
            double dist = collideTo(element);
            if(dist < (radius + element.radius)*0.8){
                setState(EntityState.EXPLODING);
                explosionStart = currentTime;
                explosionEnd = currentTime + 2000;
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