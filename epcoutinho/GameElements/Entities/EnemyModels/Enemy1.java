package GameElements.Entities.EnemyModels;

import Engine.GameLib;
import Engine.ConfigReaders.GameConfig;
import GameElements.Entities.Enemy;
import GameElements.Entities.Player;
import GameElements.Entities.ProjectileModels.ProjectileEnemy;
import Manager.EntityState;

import java.util.ArrayList;
import java.awt.*;

public class Enemy1 extends Enemy {

    //  CONSTRUTOR 
    
    public Enemy1(double x, double y, long when, long now) {
        super(x, y, when, now + 500, GameConfig.getEnemy1Radius());
        VX = GameConfig.getEnemy1VX();
        VY = GameConfig.getEnemy1VY() + Math.random() * GameConfig.getEnemy1VYRandom();
        angle = GameConfig.getEnemy1Angle();
        RV = GameConfig.getEnemy1RV();
        color = GameConfig.getColorEnemy1();
        setState(EntityState.INACTIVE);
    }

    //  MÉTODOS PÚBLICOS 
    
    // update
    public void update(long delta, Player player, ArrayList<ProjectileEnemy> enemy_Projectiles, long currentTime) {
        hasLife(currentTime);
        if (hitTimeEnd(currentTime)) {
            color = GameConfig.getColorEnemy1Hit();
        }

        if (handleExploding(currentTime)) return;
        
        if (!isActive()) return;

        // Verifica se saiu da tela
        if (handleSaiuDaTela()) return;

        shoot(enemy_Projectiles, player, delta, currentTime);
        updatePosition(delta, currentTime);
    }

    // Mecânica de Spawn
    public void spawn(long currentTime){
        this.setX(Math.random() * (GameLib.WIDTH - 20.0) + 10.0);
        this.setY(-10.0);
        this.setVX(0.0); // Set as needed
        this.setVY(0.20 + Math.random() * 0.15);
        this.setAngle((3 * Math.PI) / 2);
        this.setRV(0.0);
        this.setState(EntityState.ACTIVE);
        this.setNextShot(currentTime + 500);

        return;
    }

    public void shoot(ArrayList<ProjectileEnemy> enemy_Projectiles, Player player, long delta, long now) {
        if (now > getNextShot() && getY() < player.getY()) {
            // Cria um novo projétil do inimigo
            double vx = Math.cos(getAngle()) * GameConfig.getEnemy1ProjectileSpeed();
            double vy = Math.sin(getAngle()) * GameConfig.getEnemy1ProjectileSpeed() * (-1.0);
            ProjectileEnemy newProj = new ProjectileEnemy(getX(), getY(), 2.0, vx, vy);
            newProj.setState(EntityState.ACTIVE);
            enemy_Projectiles.add(newProj);
            setNextShot((long) (now + GameConfig.getEnemy1ShotCooldown() + Math.random() * GameConfig.getEnemy1ShotRandom()));
        }
    }

    public void draw(long now) {
        if (getState() == EntityState.EXPLODING) {
            double alpha = (now - explosionStart) / (explosionEnd - explosionStart);
            GameLib.drawExplosion(getX(), getY(), alpha);
        }
        if (getState() == EntityState.ACTIVE) {
            GameLib.setColor(color);
            GameLib.drawCircle(getX(), getY(), radius);
        }
    }
}

