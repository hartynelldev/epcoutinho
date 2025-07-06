package GameElements.Entities.EnemyModels;

import Engine.GameLib;
import Engine.ConfigReaders.GameConfig;
import GameElements.Entities.Enemy;
import GameElements.Entities.Player;
import GameElements.Entities.ProjectileModels.ProjectileEnemy;
import Manager.EntityState;
import Manager.SpawnManager;


import java.util.ArrayList;

public class Enemy2 extends Enemy {

    //  CONSTRUTOR 
    
    public Enemy2(double x, double y, long when, long now) {
        super(x, y, when, now + 500, GameConfig.getEnemy2Radius());
        VX = GameConfig.getEnemy2VX();
        VY = GameConfig.getEnemy2VY();
        angle = GameConfig.getEnemy2Angle();
        RV = GameConfig.getEnemy2RV();
        color = GameConfig.getColorEnemy2();
        setState(EntityState.INACTIVE);
    }

    //  MÉTODOS PÚBLICOS

/*     @Override
    public long spawn(long currentTime){ 
        return currentTime + 120;
    } */

    public void spawn(long currentTime) {
        this.setVY(0.42);
        this.setAngle((3 * Math.PI) / 2);
        this.setRV(0.0);
        this.setState(EntityState.ACTIVE);
        this.setExplosionEnd(0);
        return;
    }
    
    public void update(long delta, Player player, ArrayList<ProjectileEnemy> enemy_Projectiles, long currentTime) {
        hasLife(currentTime);
        if (hitTimeEnd(currentTime)) {
            color = GameConfig.getColorEnemy2Hit();
        }

        // checar se ainda esta explodindo
        if (handleExploding(currentTime)) return;

        if (!isActive()) return;
        if (handleSaiuDaTela()) return;

        double previousY = getY();

        updatePosition(delta, currentTime);
        
        double threshold = GameConfig.getEnemy2ThresholdY();
        
        if (previousY < threshold && getY() >= threshold) {
            if (getX() < GameLib.WIDTH / 2) setRV(GameConfig.getEnemy2RVChange());
            else setRV(-GameConfig.getEnemy2RVChange());
        }

        shoot(enemy_Projectiles);
    }

    public void shoot(ArrayList<ProjectileEnemy> enemy_Projectiles) {
        // "IA" de disparo do inimigo
        boolean shootNow = false;
    
        if (getRV() > 0 && Math.abs(getAngle() - 3 * Math.PI) < 0.05) {
            setRV(0.0);
            setAngle(3 * Math.PI);
            shootNow = true;
        }
    
        if (getRV() < 0 && Math.abs(getAngle()) < 0.05) {
            setRV(0.0);
            setAngle(0.0);
            shootNow = true;
        }
    
        if (shootNow) {
            double[] angles = { Math.PI/2 + Math.PI/8, Math.PI/2, Math.PI/2 - Math.PI/8 };
            for (int i = 0; i < angles.length; i++) {
                double a = angles[i] + Math.random() * Math.PI/6 - Math.PI/12;
                double vx = Math.cos(a);
                double vy = Math.sin(a);
    
                // Cria um novo projétil do inimigo
                ProjectileEnemy newProj = new ProjectileEnemy(getX(), getY(), GameConfig.getProjectileDefaultRadius(), 
                    vx * GameConfig.getEnemy2ProjectileSpeed(), 
                    vy * GameConfig.getEnemy2ProjectileSpeed());
                newProj.setState(EntityState.ACTIVE);
                enemy_Projectiles.add(newProj);
            }
        }
    }

    public void draw(long now) {
        if (getState() == EntityState.EXPLODING) {
            double alpha = (now - explosionStart) / (explosionEnd - explosionStart);
            GameLib.drawExplosion(getX(), getY(), alpha);
        }
        if (getState() == EntityState.ACTIVE) {
            GameLib.setColor(GameConfig.getColorEnemy2Draw());
            GameLib.drawDiamond(getX(), getY(), getRadius());
        }
    }
}

