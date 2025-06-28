package Entities.ProjectileModels;
import java.awt.Color;

import Engine.GameLib;
import Entities.Entity;
import Manager.EntityState;

public class ProjectileEnemy extends Projectile{
    //disparos (player ou inimigos)

    public ProjectileEnemy(double x, double y, double radius, double vx, double vy) {
        super(x, y, radius, vx, vy);
    }

    public void draw(long now) {
        if (this.isActive()) {
            GameLib.setColor(Color.RED);
            GameLib.drawCircle(getX(), getY(), radius); // Use o raio correto se necessário
        }
    }
}