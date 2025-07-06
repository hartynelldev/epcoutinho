package GameElements;

import Engine.GameLib;
import Manager.EntityState;
import Config.GameConfig;
import java.awt.Color;

// Entidades que interagem no jogo
public abstract class Entity extends GameElement {
    
    //  ATRIBUTOS 
    
    // Vida e dano
    protected int HP;
    protected boolean isIvulnerable = false;
    protected long hitTime;
    
    // Explosão
    protected double explosionStart;            // instante do início da explosão
    protected double explosionEnd;              // instante do final da explosão
    protected double explosionTime;             // tempo de explosão (padrão, player tem 2000 e muda no construtor)

    //  CONSTRUTOR 
    
    public Entity(double x, double y, double radius) {
        super(x, y, radius);
        this.HP = GameConfig.getEntityDefaultHP();
        this.explosionTime = GameConfig.getEntityDefaultExplosionTime();
    }

    //  MÉTODOS PÚBLICOS 
    
    public void explode(long now) {
        if (!isIvulnerable) {
            setState(EntityState.EXPLODING);
            explosionStart = now;
            this.explosionEnd = now + explosionTime;
        }
    }

    public void hit(int damage, long currentTime) {
        if (!isIvulnerable) {
            HP = HP - 1;
            setColor(GameConfig.getColorHit());
            hitTime = currentTime + 100;
        }
    }

    public boolean hasLife(long currentTime) {
        if (HP <= 0 && getState() == EntityState.ACTIVE) {
            explode(currentTime);
            return false;
        }
        return true;
    }

    //  MÉTODOS PROTEGIDOS 
    
    public boolean handleExploding(long now) {
        return false;
        // not implemented
    }

    public boolean hitTimeEnd(long currentTime) {
        return hitTime < currentTime;
    }

    // verificando se inimigo saiu da tela, se sim desativa
    public boolean handleSaiuDaTela() {
        if (getY() > GameLib.HEIGHT + 10 || getY() < -10) {
            setState(EntityState.INACTIVE);
            return true;
        }
        return false;
    }

    // atualiza a posição (é padrão)
    public void updatePosition(long delta, long now) {
        if (!isActive()) return;

        // Atualiza posição e angulo
        setX(getX() + getVX() * Math.cos(getAngle()) * delta);
        setY(getY() + getVY() * Math.sin(getAngle()) * delta * (-1.0));
        setAngle(getAngle() + getRV() * delta);
    }

    //  MÉTODOS ABSTRATOS 
    
    abstract public void draw(long now);

    //  GETTERS E SETTERS 
    
    // Explosão
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

    // Invulnerabilidade
    public void setIvulnerability(boolean isIvulnerable) {
        this.isIvulnerable = isIvulnerable;
    }

    public boolean getIvulnerability() {
        return isIvulnerable;
    }
}