package Entities;
import Engine.GameLib;
import utils.*;

// Entidades que interagem no jogo
public abstract class Entity extends GameElement {
    protected double explosionStart;			// instante do início da explosão
    protected double explosionEnd;				// instante do final da explosão
    protected long nextShot;             		// instante a partir do qual pode haver um próximo tiro


    public Entity(double x, double y, double radius) {
        super(x,y,radius);
        explosionStart = 0;
        explosionEnd = 0;
        nextShot = now;
    }

    public void explode(int explosionTime){
        setState(EntityState.EXPLODING);
        explosionStart = now;
        explosionEnd = now + 2000;
        double alpha = (System.currentTimeMillis() - explosionStart) / (explosionEnd - explosionStart);
        GameLib.drawExplosion(getX(), getY(), alpha);
    }

    // Checa se há colisão com outro elemento do jogo
    public double collideTo(GameElement collider) {
        double dx = this.getX() - collider.getX();
        double dy = this.getY() - collider.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }
    public abstract void update(long delta);

    //Somente uma ideia de como vai ficar
    public void collide(){

    }

    private void collideShot(){

    }

    private void  collideEnemy(){

    }

    //talvez eu coloque o shot aqui

}