package Entities;

import Engine.GameLib;
import utils.EntityState;

// Entidade única de inimigo
public abstract class Enemy extends Entity{

    //talvez seja em outro lugar
    protected long nextSpawn;
    protected long nextShot;             		// instante a partir do qual pode haver um próximo tiro

    public Enemy(double x, double y, long when, double radius){
        super(x, y, radius);
        nextSpawn = when;
    }

    public void spawn(long currentTime){
        if(currentTime > this.nextSpawn){
            setState(EntityState.ACTIVE);
        }
    }

    public void draw(){
        if(getState() == EntityState.EXPLODING){
            explode();
        }
        if(getState() == EntityState.ACTIVE){
            GameLib.setColor(color);
            GameLib.drawCircle(getX(), getY(), radius );
        }
    }

    // getter para o próximo tiro
    public long getNextShot() {
        return nextShot;
    }
    public void setNextShot(long newS) {
        this.nextShot = newS;
    }

}