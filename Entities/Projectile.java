package Entities;
import java.awt.Color;

import Engine.GameLib;
import Entities.Entity;
import utils.EntityState;

public class Projectile  extends Entity{
    //disparo do player

    //Implementar
    public Projectile(double x, double y, double radius, double vx, double vy, Color cor) {
        super(x, y, radius);
        color = cor;
        //variações de super:
        //Player: super(player.getX(), player.getY() - 2 * player.getRadius(), radius);
        //Inimigo1 e Inimigo2: super(enemy.getX(), enemy.getY(), radius);

        VX = vx;
        VY = vy;

        //variações de VX e VY
        // Player: VX = vx; VY = vy;
        // Inimigo1: VX = Math.cos(enemy.getAngle()) * 0.45; VY = Math.sin(enemy.getAngle()) * 0.45 * (-1.0);
        // Inimigo2:  double a = angle + Math.random() * Math.PI/6 - Math.PI/12; VX = Math.cos(a) * 0.30; VY = Math.sin(a) * 0.30;
        setState(EntityState.ACTIVE);

    }

    public void update(long delta, long currentTime) {
        if(state == EntityState.ACTIVE){

            /* verificando se projétil saiu da tela */
            if(getY() < 0) {
                setState(EntityState.DESTROY);
            }
            else {
                setX(getX() + VX * delta);
                setY(getY() + VY * delta);
            }
        }
    }
    public void draw(long currentTime) {
        if(getState() == EntityState.ACTIVE){
            GameLib.setColor(color);
            GameLib.drawCircle(getX(), getY(), radius);
        }
    }
}