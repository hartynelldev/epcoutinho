package Entities.EnemyModels;

import Engine.GameLib;
import Entities.Enemy;
import Entities.GameElement;
import Entities.Projectile;
import utils.EntityState;

import java.awt.*;
import java.util.ArrayList;

public class Enemy2 extends Enemy {
    protected static long same;

    public Enemy2(double x, double y, long when, long currentTime){
        super(x,y,when,12.0);
        same = when;
        VX = 0.42;
        angle = (3 * Math.PI) / 2;
        RV = 0;
        setState(EntityState.INACTIVE);
        //teoricamente -- para um mesmo inimigo 2 só que vai spownar depois
        if(when == same){
            spawTime = when + 120;
        }
    }


    public void update(long delta, long currentTime) {
        if(getState() == EntityState.EXPLODING){

            if(currentTime > explosionEnd){
                setState(EntityState.DESTROY);
            }
        }
        if(getState() == EntityState.ACTIVE){
            if (getY() > GameLib.HEIGHT + 10) {
                setState(EntityState.DESTROY);
            } else {

                double previousY = getY();

                setX(getX() + VX * Math.cos(angle) * delta);
                setY(getY() + VY * Math.sin(angle) * delta * (-1.0));
                angle += RV * delta;

                double threshold = GameLib.HEIGHT * 0.30;

                if(previousY < threshold && getY() >= threshold) {

                    if(getX() < GameLib.WIDTH / 2) RV = 0.003;
                    else RV = -0.003;
                }
            }
        }

    }

    public boolean canShoot(long currentTime, GameElement ent){

        if(RV > 0 && Math.abs(angle - 3 * Math.PI) < 0.05){

            RV = 0.0;
            angle = 3 * Math.PI;
            return true;
        }

        if(RV < 0 && Math.abs(angle) < 0.05){

            RV = 0.0;
            angle = 0.0;
            return true;
        }

        return false;
        // return currentTime > nextShot && getY() < ent.getY() && getState() == EntityState.ACTIVE;
    }

    public Projectile shot(long currentTime, GameElement ent,double angles){

        double a = angles + Math.random() * Math.PI/6 - Math.PI/12;
        double vx = Math.cos(a) * 0.3;
        double vy = Math.sin(a) * 0.3;

        return new Projectile(getX(),getY(),2.0,vx,vy, Color.RED);
    }

}

