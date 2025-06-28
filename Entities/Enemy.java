package Entities;

import java.util.ArrayList;

import Engine.GameLib;
import Entities.ProjectileModels.ProjectileEnemy;
import Entities.ProjectileModels.Projectile;
import Manager.EntityState;

// Entidade única de inimigo
public abstract class Enemy extends Entity{

    private long nextSpawn;
    private long nextShot;             		// instante a partir do qual pode haver um próximo tiro

    public Enemy(double x, double y, long nextSpawn, long nextShot, double radius){
        super(x, y, radius);
        this.nextSpawn = nextSpawn;
        this.nextShot = nextShot;
    }

    public boolean canShoot(long currentTime, GameElement ent){
        return currentTime > nextShot && getY() < ent.getY() && getState() == EntityState.ACTIVE;
    }


    public void spawn(long currentTime){
        if(currentTime > this.nextSpawn){
            setState(EntityState.ACTIVE);
        }
    }

    // HANDLERS dos updates
    public abstract void update(long delta, Player player, ArrayList<ProjectileEnemy> enemy_Projectiles, long currentTime);

    // atualiza a posição (é padrão)
    public void updatePosition(long delta, long now){
        if(!isActive()) return;

        // Atualiza posição e angulo
        setX(getX() + getVX() * Math.cos(getAngle()) * delta);
        double previousY = getY();
        setY(getY() + getVY() * Math.sin(getAngle()) * delta * (-1.0));
        setAngle(getAngle() + getRV() * delta);
    }

    // checa se ainda esta explodindo(retorna), se sim desativa
    @Override
    public boolean handleExploding(long currentTime){
        if(getState() == EntityState.EXPLODING){
            if (currentTime > getExplosionEnd()) {
                    setState(EntityState.INACTIVE);
            }
            
            return true;
        }
        return false;
    }

    // verificando se inimigo saiu da tela, se sim desativa
    public boolean handleSaiuDaTela(){
        
        if(getY() > GameLib.HEIGHT + 10 || getY() < -10) {
            setState(EntityState.INACTIVE);
            return true;
        }
        return false;
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

    // getter para o próximo tiro
    public long getNextShot() {
        return nextShot;
    }
    public void setNextShot(long newS) {
        this.nextShot = newS;
    }

}