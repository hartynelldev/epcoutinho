package Entities;

import Engine.GameLib;
import utils.EntityState;

// Entidade única de inimigo
public abstract class Enemy extends Entity{

    //talvez seja em outro lugar
    protected long nextSpawn;

    public Enemy(double x, double y, long when, double radius){
        super(x, y, radius);
        nextSpawn = when;
    }

    public void spawn(long currentTime){
        if(currentTime > this.nextSpawn){
            setState(EntityState.ACTIVE);
        }
    }


    public void collide(GameElement element, long currentTime){
        if(getState() == EntityState.ACTIVE){
            double dist = collideTo(element);
            if(dist < radius){
                setState(EntityState.EXPLODING);
                explosionStart = currentTime;
                explosionEnd = currentTime + 2000;
            }
        }
    }

    public void draw(){
        if(getState() == EntityState.EXPLODING){
            explode(10);
        }
        if(getState() == EntityState.ACTIVE){
            GameLib.setColor(color);
            GameLib.drawCircle(getX(), getY(), radius );
        }
    }


}