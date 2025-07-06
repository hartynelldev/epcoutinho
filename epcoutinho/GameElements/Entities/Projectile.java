package GameElements.Entities;

import Engine.GameLib;
import GameElements.Entity;
import Manager.EntityState;

// Classe mãe dos projectiles	
public abstract class Projectile extends Entity {
    // disparos (player ou inimigos)

    //  CONSTRUTOR 
    
    public Projectile(double x, double y, double radius, double vx, double vy) {
        super(x, y, radius);
        // variações de super:
        // Player: super(player.getX(), player.getY() - 2 * player.getRadius(), radius);
        // Inimigo1 e Inimigo2: super(enemy.getX(), enemy.getY(), radius);
        VX = vx;
        VY = vy;
        // variações de VX e VY
        // Player: VX = vx; VY = vy;
        // Inimigo1: VX = Math.cos(enemy.getAngle()) * 0.45; VY = Math.sin(enemy.getAngle()) * 0.45 * (-1.0);
        // Inimigo2:  double a = angle + Math.random() * Math.PI/6 - Math.PI/12; VX = Math.cos(a) * 0.30; VY = Math.sin(a) * 0.30;
        setState(EntityState.INACTIVE);
    }

    //  MÉTODOS PÚBLICOS 
    
    public void update(long delta, long now) {
        if (state == EntityState.ACTIVE) {
            /* verificando se projétil saiu da tela */
            if (getY() < 0 || getY() > GameLib.HEIGHT) {
                setState(EntityState.INACTIVE);
            } else {
                setX(getX() + VX * delta);
                setY(getY() + VY * delta);
            }
        }
    }

    //  MÉTODOS ABSTRATOS 
    
    public abstract void draw(long now);
}