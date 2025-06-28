package Entities;
import Engine.GameLib;
import java.awt.Color;
import Manager.EntityState;

// Entidades que interagem no jogo
public abstract class Entity extends GameElement {
    protected double explosionStart;			// instante do início da explosão
    protected double explosionEnd;				// instante do final da explosão
    protected double explosionTime = 500;         	// tempo de explosão (padrão, player tem 2000 e muda no construtor)


    public Entity(double x, double y, double radius) {
        super(x,y,radius);
    }

    public void explode(long now){
        setState(EntityState.EXPLODING);
        explosionStart = now;
        this.explosionEnd = now + explosionTime;
    }


    abstract public void draw(long now);

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