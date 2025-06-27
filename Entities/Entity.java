package Entities;
import Engine.GameLib;
import utils.*;

// Entidades que interagem no jogo
public abstract class Entity extends GameElement {
    protected double explosionStart;			// instante do início da explosão
    protected double explosionEnd;				// instante do final da explosão
    protected int ShootingSpeed = 100;
    protected long nextShot;             		// instante a partir do qual pode haver um próximo tiro


    public Entity(double x, double y, double radius) {
        super(x,y,radius);
        explosionStart = 0;
        explosionEnd = 0;
    }

    public void explode(int explosionTime, long currentTime){
        setState(EntityState.EXPLODING);
        explosionStart = currentTime;
        explosionEnd = currentTime + 2000;
        double alpha = (System.currentTimeMillis() - explosionStart) / (explosionEnd - explosionStart);
        GameLib.drawExplosion(getX(), getY(), alpha);
    }

    public double collideTo(GameElement collider) {
        double dx = this.getX() - collider.getX();
        double dy = this.getY() - collider.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    public boolean canShoot(long currentTime){
        return currentTime > nextShot && getState() == EntityState.ACTIVE;
    }

    public abstract void update(long delta, long currentTime);

}