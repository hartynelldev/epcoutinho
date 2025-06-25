package game.gameObjects.entities;
import java.awt.Color;
import java.util.ArrayList;


import game.GameLib;
import game.gameObjects.Entity;

public abstract class Enemy extends Entity{

    //talvez seja em outro lugar
    private long spawTime;

    public Enemy(double x, double y, long when, double radius){
        super(x, y, radius);
        spawTime = when;
    }

    public void draw(){
        if(getState() == EXPLODING){
            explode(10);
        }
        if(getState() == ACTIVE){
            GameLib.setColor(color);
            GameLib.drawCircle(X, Y, radius );
        }
    }


}
