package game.gameObjects.entities;
import java.awt.Color;

import game.GameLib;
import game.gameObjects.Entity;

public class Projectile  extends Entity{
    //disparo do player

    public Projectile(double radius, Entity entity, double angle, double vx, double vy){
        super(coiso)
        vx
    }
    projectile1{
        atirar()
    }

    public  Projectile(double radius, Player player){
        super(player.getX(), player.getY() - 2 * player.getRadius(),radius );
        VX = 0.0;
        VY = -1.0;
        setState(ACTIVE);
    }

    //disparo do inimigo1
    public Projectile(double radius, Enemy enemy){
        super(enemy.getX(), enemy.getY(), radius);
        VX = Math.cos(enemy.getAngle()) * 0.45;
        VY = Math.sin(enemy.getAngle()) * 0.45 * (-1.0);
        setState(ACTIVE);
    }

    //disparo do inimigo2
    public Projectile(double radius, Enemy enemy, double angle){
        super(enemy.getX(), enemy.getY(), radius);

        double a = angle + Math.random() * Math.PI/6 - Math.PI/12;

        VX = Math.cos(a) * 0.30;
        VY = Math.sin(a) * 0.30;
        setState(ACTIVE);
    }

    public void update(long delta) {
        if(state == ACTIVE){

            /* verificando se projétil saiu da tela */
            if(Y < 0) {

                //projectile_states[i] = INACTIVE;
            }
            else {

                X += VX * delta;
                Y += VY * delta;
            }
        }
    } //tentantdo comitar/
    public void draw() {

    }
}


