package Entities;
import Engine.GameLib;
import utils.*;

// Entidades que interagem no jogo
public abstract class Entity extends GameElement {
    protected double explosionStart;			// instante do início da explosão
    protected double explosionEnd;				// instante do final da explosão
    protected double explosionTime = 500;         	// tempo de explosão (padrão, player tem 2000 e muda no construtor)


    public Entity(double x, double y, double radius) {
        super(x,y,radius);
    }


    public void explode(){
        setState(EntityState.EXPLODING);
        explosionStart = now;
        double explosionEnd = now + explosionTime;
        double alpha = (System.currentTimeMillis() - explosionStart) / (explosionEnd - explosionStart);
        GameLib.drawExplosion(getX(), getY(), alpha);
    }

    //talvez eu coloque o shot aqui

    // getters e setters


    public double getExplosionStart() {
        return this.explosionStart;
    }

    public void setExplosionStart(double explosionStart) {
        this.explosionStart = explosionStart;
    }

    public double getExplosionEnd() {
        return this.explosionEnd;
    } 

    public void setExplosionEnd(double explosionEnd) {
        this.explosionEnd = explosionEnd;
    }

}