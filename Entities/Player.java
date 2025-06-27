package Entities;

import Engine.GameLib;
import utils.EntityState;

import java.awt.*;
import java.lang.annotation.ElementType;

public class Player extends Entity{


    public Player(double x, double y, double radius){
        super(x, y, radius);
        VX = 0.25;						// velocidade no eixo x
        VY = 0.25;						// velocidade no eixo y
        color = Color.BLUE;
    }

    public void setShootingSpeed(int shootingSpeed){
        ShootingSpeed = shootingSpeed;
    }

    public void update(long delta, long currentTime) {
        if (getState() != EntityState.EXPLODING) {
            if (GameLib.iskeyPressed(GameLib.KEY_UP)) setY(getY() - delta * VY);
            if (GameLib.iskeyPressed(GameLib.KEY_DOWN)) setY(getY() + delta * VY);
            if (GameLib.iskeyPressed(GameLib.KEY_LEFT)) setX(getX() - delta * VX);
            if (GameLib.iskeyPressed(GameLib.KEY_RIGHT)) setX(getX()+- delta * VX);

            if (GameLib.iskeyPressed(GameLib.KEY_CONTROL)) {
                if (currentTime > nextShot) {
                    // DAR TIRO
                    nextShot = currentTime + ShootingSpeed;
                }
            }
        } else {
            if (currentTime > explosionEnd) {
                setState(EntityState.ACTIVE);
            }
        }
    }

    public Projectile shoot(long currentTime){
        if (GameLib.iskeyPressed(GameLib.KEY_CONTROL) && canShoot(currentTime)){
            nextShot = currentTime + ShootingSpeed;
            //não sei se esta certo os valores
            return new ProjectilePlayer(getX(),getY() - 2 * radius, radius, VX, VY, Color.GREEN);
        }
        return null;

        //Player: super(player.getX(), player.getY() - 2 * player.getRadius(), radius);
        // Player: VX = vx; VY = vy;
    }

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

    public void draw(long currentTime) {
        if (getState() == EntityState.EXPLODING) {
            explode(2000, currentTime);
        } else {
            GameLib.setColor(color);
            GameLib.drawPlayer(getX(), getY(), radius);
        }
    }
}