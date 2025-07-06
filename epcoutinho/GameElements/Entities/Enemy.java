package GameElements.Entities;

import java.util.ArrayList;
import Engine.GameLib;
import Exceptions.SpawExcption;
import GameElements.Entity;
import GameElements.GameElement;
import GameElements.Entities.ProjectileModels.ProjectileEnemy;
import Manager.EntityState;
import Exceptions.*;

// Entidade única de inimigo
public abstract class Enemy extends Entity {

    //  ATRIBUTOS 
    
    // Spawn e tiro
    private long spawn;
    private long nextShot;                     // instante a partir do qual pode haver um próximo tiro

    //  CONSTRUTOR 
    
    public Enemy(double x, double y, long spawn, long nextShot, double radius) {
        super(x, y, radius);
        try {
            if (spawn < 0) throw new SpawExcption();
            this.spawn = spawn;
            this.nextShot = nextShot;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    //  MÉTODOS PÚBLICOS 
    
    public boolean canShoot(long currentTime, GameElement ent) {
        return currentTime > nextShot && getY() < ent.getY() && getState() == EntityState.ACTIVE;
    }

    // Deve retornar novo spawnTime para próximo inimigo
    public abstract long spawn(long currentTime);
/*     {
        if (currentTime > this.spawn) {
            setState(EntityState.ACTIVE);
        }
    } */

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

    //  MÉTODOS ABSTRATOS 
    
    // HANDLERS dos updates
    public abstract void update(long delta, Player player, ArrayList<ProjectileEnemy> enemy_Projectiles, long currentTime);

    //  MÉTODOS PROTEGIDOS 
    
    // atualiza a posição (é padrão)
    public void updatePosition(long delta, long now) {
        if (!isActive()) return;

        // Atualiza posição e angulo
        setX(getX() + getVX() * Math.cos(getAngle()) * delta);
        setY(getY() + getVY() * Math.sin(getAngle()) * delta * (-1.0));
        setAngle(getAngle() + getRV() * delta);
    }

    // checa se ainda esta explodindo(retorna), se sim desativa
    @Override
    public boolean handleExploding(long currentTime) {
        if (getState() == EntityState.EXPLODING) {
            if (currentTime > getExplosionEnd()) {
                setState(EntityState.INACTIVE);
            }
            return true;
        }
        return false;
    }

    //  GETTERS E SETTERS 
    
    // Tiro
    public long getNextShot() {
        return nextShot;
    }
    
    public void setNextShot(long newS) {
        this.nextShot = newS;
    }
}