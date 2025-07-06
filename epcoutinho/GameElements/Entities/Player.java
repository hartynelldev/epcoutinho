package GameElements.Entities;

import Engine.GameLib;
import GameElements.Entity;
import GameElements.Entities.ProjectileModels.ProjectilePlayer;
import Manager.EntityState;
import Config.GameConfig;
import java.util.ArrayList;
import java.awt.*;

public class Player extends Entity {

    //  ATRIBUTOS 
    
    // Posição inicial
    private final double initialX;
    private final double initialY;
    
    // Tiro
    protected int playerShootingSpeed;
    protected long nextShot;
    
    // Power-up
    protected long powerUpEnd = -1;
    protected int shieldState = 0;
    protected double baseRadius;

    //  CONSTRUTOR 
    
    public Player(int life) {
        super(GameConfig.getPlayerInitialX(), GameConfig.getPlayerInitialY(), GameConfig.getPlayerBaseRadius());
        this.initialX = GameConfig.getPlayerInitialX();
        this.initialY = GameConfig.getPlayerInitialY();
        this.playerShootingSpeed = GameConfig.getPlayerShootingSpeed();
        this.baseRadius = GameConfig.getPlayerBaseRadius();
        
        VX = GameConfig.getGameElementDefaultVX();
        VY = GameConfig.getGameElementDefaultVY();
        color = GameConfig.getColorPlayer();
        HP = life;
        super.setState(EntityState.ACTIVE);
        super.explosionTime = GameConfig.getEntityPlayerExplosionTime();
    }

    //  MÉTODOS PÚBLICOS 
    
    public boolean update(long delta, long now, ArrayList<ProjectilePlayer> playerProjectiles) {
        hasLife(now);
        if (hitTimeEnd(now)) {
            if (powerUpEnd == -1) color = GameConfig.getColorPlayer();
            else color = GameConfig.getColorPlayerPowerup();
        }

        if (powerUpEnd >= 0 && now > powerUpEnd) {
            powerUpEnd = -1;
            VX = GameConfig.getGameElementDefaultVX();
            VY = GameConfig.getGameElementDefaultVY();
            color = GameConfig.getColorPlayer();
            isIvulnerable = false;
            shieldState = 0;
            radius = baseRadius;
        }
        
        if (getState() != EntityState.EXPLODING) {
            if (GameLib.iskeyPressed(GameLib.KEY_UP)) setY(getY() - delta * VY);
            if (GameLib.iskeyPressed(GameLib.KEY_DOWN)) setY(getY() + delta * VY);
            if (GameLib.iskeyPressed(GameLib.KEY_LEFT)) setX(getX() - delta * VX);
            if (GameLib.iskeyPressed(GameLib.KEY_RIGHT)) setX(getX() + delta * VX);

            if (GameLib.iskeyPressed(GameLib.KEY_CONTROL)) {
                if (now > nextShot && getState() != EntityState.INACTIVE) {
                    for (ProjectilePlayer proj : playerProjectiles) {
                        if (!proj.isActive()) {
                            proj.setX(getX());
                            proj.setY(getY() - 2 * getRadius());
                            proj.setVX(0.0);
                            proj.setVY(-1.0);
                            proj.setState(EntityState.ACTIVE);
                            nextShot = now + playerShootingSpeed;
                            break;
                        }
                    }
                }
            }

            if (GameLib.iskeyPressed(GameLib.KEY_ESCAPE)) {
                return true;
            }
        } else {
            if (now > explosionEnd) {
                setState(EntityState.INACTIVE);
                setX(initialX);
                setY(initialY);
            }
        }

        /* Verificando se coordenadas do player ainda estão dentro */
        /* da tela de jogo após processar entrada do usuário.      */
        if (getX() < GameConfig.getPlayerMinX()) setX(GameConfig.getPlayerMinX());
        if (getX() >= GameConfig.getPlayerMaxX()) setX(GameConfig.getPlayerMaxX());
        if (getY() < GameConfig.getPlayerMinY()) setY(GameConfig.getPlayerMinY());
        if (getY() >= GameConfig.getPlayerMaxY()) setY(GameConfig.getPlayerMaxY());

        return false;
    }

    public void draw(long now) {
        if (getState() == EntityState.EXPLODING) {
            double alpha = (now - explosionStart) / (explosionEnd - explosionStart);
            GameLib.drawExplosion(getX(), getY(), alpha);
        }
        if (getState() == EntityState.ACTIVE) {
            if (shieldState > 0) {
                for (int i = shieldState; i >= 0; i--) {
                    GameLib.drawPlayer(getX(), getY(), radius - 5 * (i));
                }
            }
            GameLib.setColor(color);
            GameLib.drawPlayer(getX(), getY(), radius - 5 * shieldState);
        }
    }

    public void hit(int damage, long currentTime) {
        if (shieldState == 0) {
            super.hit(damage, currentTime);
            isIvulnerable = false;
        }
        if (shieldState > 0) {
            shieldState--;
            radius -= GameConfig.getPlayerShieldRadiusIncrement();
        }
    }

    //  GETTERS E SETTERS 
    
    // Tiro
    public long getNextShot() {
        return nextShot;
    }
    
    public void setNextShot(long nextShot) {
        this.nextShot = nextShot;
    }

    public void setShootingSpeed(int shootingSpeed) {
        playerShootingSpeed = shootingSpeed;
    }

    // Power-up
    public void setPowerUpDuration(long now, long duration) {
        powerUpEnd = now + duration;
    }

    public void setShield() {
        if (shieldState < 4) {
            shieldState++;
            radius += GameConfig.getPlayerShieldRadiusIncrement();
        }
    }
}