package Entities.ProjectileModels;
import java.awt.Color;

import Engine.GameLib;
import Entities.Entity;
import Manager.EntityState;

public class ProjectilePlayer extends Projectile{
    //disparos (player ou inimigos)

    public ProjectilePlayer(double x, double y, double radius, double vx, double vy) {
        super(x, y, radius, vx, vy);
    }
    
    public void draw(long now) {
        if (isActive()) {
            GameLib.setColor(Color.GREEN);
            GameLib.drawLine(getX(), getY() - 5, getX(), getY() + 5);
            GameLib.drawLine(getX() - 1, getY() - 3, getX() - 1, getY() + 3);
            GameLib.drawLine(getX() + 1, getY() - 3, getX() + 1, getY() + 3);
        }
    }
}