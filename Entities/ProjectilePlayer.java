package Entities;

import Engine.GameLib;
import utils.EntityState;

import java.awt.*;

public class ProjectilePlayer extends Projectile{

    public ProjectilePlayer(double x, double y, double radius, double vx, double vy, Color cor) {
        super(x, y, radius, vx ,vy, cor);
    }

    //sobreescrita do draw de Projectile
    public void draw(long currentTime) {
        if(getState() == EntityState.ACTIVE){

            GameLib.setColor(color);
            GameLib.drawLine(getX(), getY() - 5, getX(), getY() + 5);
            GameLib.drawLine(getX() - 1, getY() - 3, getX()- 1, getY() + 3);
            GameLib.drawLine(getX() + 1, getY() - 3, getX() + 1, getY() + 3);
        }
    }
}
