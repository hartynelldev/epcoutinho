package GameElements.Entities.EnemyModels;

import Engine.GameLib;
import GameElements.Entities.Enemy;
import GameElements.Entities.Player;
import GameElements.Entities.ProjectileModels.ProjectileEnemy;
import Manager.EntityState;
import java.util.ArrayList;
import java.awt.*;

public class Enemy2 extends Enemy {

    public Enemy2(double x, double y, long when, long now){
        super(x, y, when,now + 500, 12.0);
        VX = 0.42;
        VY = 0.42;
        angle = (3 * Math.PI) / 2;
        RV = 0;
        color= Color.CYAN;
        setState(EntityState.INACTIVE);
    }



    public void update(long delta, Player player, ArrayList<ProjectileEnemy> enemy_Projectiles, long currentTime) {

        hasLife(currentTime);
        if(hitTimeEnd(currentTime)){
            color = Color.BLUE;
        }

        // checar se ainda esta explodindo

        if(handleExploding(currentTime)) return;

        if(!isActive()) return;
        if(handleSaiuDaTela()) return;

        double previousY = getY();

        updatePosition(delta, currentTime);
        
        double threshold = GameLib.HEIGHT * 0.30;
        
        if(previousY < threshold && getY() >= threshold) {
            
            if(getX() < GameLib.WIDTH / 2) setRV(0.003);
            else setRV(-0.003);
        }

        shoot(enemy_Projectiles);
    
    }

    public void shoot(ArrayList<ProjectileEnemy> enemy_Projectiles){
        // "IA" de disparo do inimigo
        boolean shootNow = false;
    
        if(getRV() > 0 && Math.abs(getAngle() - 3 * Math.PI) < 0.05){
            setRV(0.0);
            setAngle(3 * Math.PI);
            shootNow = true;
        }
    
        if(getRV() < 0 && Math.abs(getAngle()) < 0.05){
            setRV(0.0);
            setAngle(0.0);
            shootNow = true;
        }
    
        if (shootNow) {
            double[] angles = { Math.PI/2 + Math.PI/8, Math.PI/2, Math.PI/2 - Math.PI/8 };
            int shots = 0;
            for (ProjectileEnemy proj : enemy_Projectiles) {
                if (!proj.isActive() && shots < angles.length) {
                    double a = angles[shots] + Math.random() * Math.PI/6 - Math.PI/12;
                    double vx = Math.cos(a);
                    double vy = Math.sin(a);
    
                    proj.setX(getX());
                    proj.setY(getY());
                    proj.setVX(vx * 0.30);
                    proj.setVY(vy * 0.30);
                    proj.setState(EntityState.ACTIVE);
                    shots++;
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
            GameLib.setColor(Color.MAGENTA);
            GameLib.drawDiamond(getX(), getY(), getRadius());
        }
    }
    

}

