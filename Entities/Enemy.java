package Entities;

import Engine.GameLib;
import utils.EntityState;

// Entidade única de inimigo
public abstract class Enemy extends Entity{

    //talvez seja em outro lugar
    private long spawTime;

    public Enemy(double x, double y, long when, double radius){
        super(x, y, radius);
        spawTime = when;
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