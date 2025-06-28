package Entities;
import Engine.GameLib;
import java.awt.Color;
import Manager.EntityState;

// Entidades que interagem no jogo
public abstract class Entity extends GameElement {
    protected double explosionStart;			// instante do início da explosão
    protected double explosionEnd;				// instante do final da explosão
    protected double explosionTime = 500;         	// tempo de explosão (padrão, player tem 2000 e muda no construtor)

    protected boolean isIvulnerable = false;

    public Entity(double x, double y, double radius) {
        super(x,y,radius);
    }

    public void explode(long now){
        if(!isIvulnerable){
            setState(EntityState.EXPLODING);
            explosionStart = now;
            this.explosionEnd = now + explosionTime;
        }
    }

    public boolean handleExploding(long now){
        return false;
        //not implemented
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

    public void setIvulnerability(boolean isIvulnerable){
        this.isIvulnerable = isIvulnerable;
    }

    public boolean getIvulnerability(){
        return isIvulnerable;
    }

    // verificando se inimigo saiu da tela, se sim desativa
    public boolean handleSaiuDaTela(){
        
        if(getY() > GameLib.HEIGHT + 10 || getY() < -10) {
            setState(EntityState.INACTIVE);
            return true;
        }
        return false;
    }

    // atualiza a posição (é padrão)
    public void updatePosition(long delta, long now){
        if(!isActive()) return;

        // Atualiza posição e angulo
        setX(getX() + getVX() * Math.cos(getAngle()) * delta);

        setY(getY() + getVY() * Math.sin(getAngle()) * delta * (-1.0));
        setAngle(getAngle() + getRV() * delta);
    }
}