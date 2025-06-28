package Entities;

import Engine.GameLib;
import Entities.ProjectileModels.ProjectilePlayer;
import Manager.EntityState;
import java.util.ArrayList;

import java.awt.*;

public class Player extends Entity{

    protected int playerShootingSpeed = 100;
    protected long nextShot;
    protected long powerUpEnd = -1;

    protected int shieldState = 0;
    protected double baseRadius = radius;

    private final double initialX = GameLib.WIDTH / 2;
    private final double initialY = GameLib.HEIGHT * 0.90;

    public Player(int life) {
        super(GameLib.WIDTH / 2, GameLib.HEIGHT * 0.90, 12);
        VX = 0.25;						// velocidade no eixo x
        VY = 0.25;						// velocidade no eixo y
        color = Color.BLUE;
        HP = life;
        super.setState(EntityState.ACTIVE);
        super.explosionTime = 2000; // tempo especifico para player

        // update para setar nextShot
        //update(0);
    }

    public void setShootingSpeed(int shootingSpeed){
        playerShootingSpeed = shootingSpeed;
    }

    public boolean update(long delta, long now, ArrayList<ProjectilePlayer> playerProjectiles) {
        hasLife(now);
        if(hitTimeEnd(now)){
            if(powerUpEnd == -1) color = Color.BLUE;
            else color = Color.GREEN;
        }

        if(powerUpEnd >= 0 && now > powerUpEnd){
            powerUpEnd = -1;
            VX = 0.25;
            VY = 0.25;
            color = Color.BLUE;
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
        }
        else {
            if (now > explosionEnd) {
                setState(EntityState.INACTIVE);
                setX(initialX);
                setY(initialY);
            }
        }

        /* Verificando se coordenadas do player ainda estão dentro */
        /* da tela de jogo após processar entrada do usuário.      */

        if(getX() < 0.0) setX(0.0);
        if(getX() >= GameLib.WIDTH) setX(GameLib.WIDTH - 1);
        if(getY() < 25.0) setY(25.0);
        if(getY() >= GameLib.HEIGHT) setY(GameLib.HEIGHT - 1);

        return false;
    }
    public void draw(long now){
        if(getState() == EntityState.EXPLODING){
            double alpha = (now - explosionStart) / (explosionEnd - explosionStart);
            GameLib.drawExplosion(getX(), getY(), alpha);
        }
        if(getState() == EntityState.ACTIVE){
            if(shieldState>0){
                for(int i = shieldState; i>= 0; i--){
                    GameLib.drawPlayer(getX(), getY(), radius-5*(i));
                }
            }
            GameLib.setColor(color);
            GameLib.drawPlayer(getX(), getY(), radius-5*shieldState);
        }
    }



    public void hit(int damage, long currentTime){
        if(shieldState==0){
            super.hit(damage, currentTime);
            isIvulnerable = false;
        }
        if(shieldState>0){
            shieldState--;
            radius -= 6;
        }
    }

    public long getNextShot() {
        return nextShot;
    }
    public void setNextShot(long nextShot) {
        this.nextShot = nextShot;
    }

    public void setPowerUpDuration(long now, long duration){
        powerUpEnd = now + duration;
    }

    public void setShield(){
        if(shieldState<4){
            shieldState++;
            radius +=6;
        }
    }
}