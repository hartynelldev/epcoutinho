package Entities.EnemyModels;

import Engine.GameLib;
import Entities.Enemy;
import Entities.Player;
import Entities.ProjectileModels.ProjectileEnemy;
import Manager.EntityState;
import java.util.ArrayList;

import java.awt.*;

public class Enemy1 extends Enemy {

    public Enemy1(double x, double y, long when, long now){
        super(x, y, when, now + 500, 9.0);
        VX = 0;
        VY = 0.20 + Math.random() * 0.15;
        angle = (3 * Math.PI) / 2;
        RV = 0;
        color= Color.CYAN;
        setState(EntityState.INACTIVE);
    }

    // update
    public void update(long delta, Player player, ArrayList<ProjectileEnemy> enemy_Projectiles, long currentTime) {

        hasLife(currentTime);
        if(hitTimeEnd(currentTime)){
            color = Color.GREEN;
        }

        if(handleExploding(currentTime)) return;
        
        if(!isActive()) return;

        // Verifica se saiu da tela
        if(handleSaiuDaTela()) return;

        shoot(enemy_Projectiles, player, delta, currentTime);
        updatePosition(delta, currentTime);
    }

    public void shoot(ArrayList<ProjectileEnemy> enemy_Projectiles, Player player, long delta, long now) {
        if (now > getNextShot() && getY() < player.getY()) {
            for (ProjectileEnemy proj : enemy_Projectiles) {
                if (!proj.isActive()) {
                    proj.setX(getX());
                    proj.setY(getY());
                    proj.setVX(Math.cos(getAngle()) * 0.45);
                    proj.setVY(Math.sin(getAngle()) * 0.45 * (-1.0));
                    proj.setState(EntityState.ACTIVE);
                    setNextShot((long) (now + 200 + Math.random() * 500));
                    break;
                }
            }
        }
    }

    public void draw(long now){
        if(getState() == EntityState.EXPLODING){
            double alpha = (now - explosionStart) / (explosionEnd - explosionStart);
            GameLib.drawExplosion(getX(), getY(), alpha);
        }
        if(getState() == EntityState.ACTIVE){
            GameLib.setColor(color);
            GameLib.drawCircle(getX(), getY(), radius );
        }
    }
}

