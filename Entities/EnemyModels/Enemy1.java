package Entities.EnemyModels;

import Engine.GameLib;
import Entities.Enemy;
import Entities.GameElement;
import Entities.Projectile;
import utils.EntityState;

import javax.lang.model.type.NullType;
import java.awt.*;

public class Enemy1 extends Enemy {



    public Enemy1(double x, double y, long when, long currentTime){
        super(x, y, when, 9.0);
        VX = 0;
        VY = 0.20 + Math.random() * 0.15;
        angle = (3 * Math.PI) / 2;
        RV = 0;
        ShootingSpeed = 500;
        nextShot = currentTime + ShootingSpeed;
        color= Color.CYAN;
        setState(EntityState.ACTIVE);
    }

    public void update(long delta, long currentTime){
        if(getState() == EntityState.EXPLODING){

            if(currentTime > explosionEnd){
                setState(EntityState.DESTROY);
            }
        }

        if(getState() == EntityState.ACTIVE) {

            /* verificando se inimigo saiu da tela */
            if (getY() > GameLib.HEIGHT + 10) {
                setState(EntityState.DESTROY);
            } else {
                setX(getX() + VX * Math.cos(angle) * delta);
                setY(getY() + VY * Math.sin(angle) * delta * (-1.0));
                angle += RV * delta;
            }
        }
                //if(now > nextShot/* && Y < player.getY()*/){


                    //efetua o proximo disparo
                    /*int free = findFreeIndex(e_projectile_states);

                    if(free < e_projectile_states.length){

                        e_projectile_X[free] = enemy1_X[i];
                        e_projectile_Y[free] = enemy1_Y[i];
                        e_projectile_VX[free] = Math.cos(enemy1_angle[i]) * 0.45;
                        e_projectile_VY[free] = Math.sin(enemy1_angle[i]) * 0.45 * (-1.0);
                        e_projectile_states[free] = ACTIVE;

                        enemy1_nextShoot[i] = (long) (currentTime + 200 + Math.random() * 500);
                    }
                     */



    }

    //Provavlemente esse metodo retornara alguma coisa
    public Projectile shot(long currentTime, GameElement ent){

        if(canShoot(currentTime, ent)){
            nextShot = currentTime + ShootingSpeed;
            return new Projectile(getX(),getY(), 2.0, Math.cos(angle) * 0.45,Math.sin(angle) * 0.45 * (-1.0), Color.RED );
        }
        return null;
        //Inimigo1 e Inimigo2: super(enemy.getX(), enemy.getY(), radius);
        // Inimigo1: VX = Math.cos(enemy.getAngle()) * 0.45; VY = Math.sin(enemy.getAngle()) * 0.45 * (-1.0);
        // o tiro vai ser parte do inimigo, mas o inimigo vai chamar/criar uma classe de projétil c o construtor de projetil. Se vc quiser, pode criar 2 subclasses de projetil, uma o projetil base e uma outra q quando chega em um pedaco da tela (o fim se pa) explode em outros projeteis (pro boss e pros powerup w vo usa). mas ai e opcional sepa, tu escolhe, so acho q vale a pena ter mais q 1 tipo de projetil
        //teoricamente quem vai controlar porjetil seria GameEnge
        //mas a arrayList ficaria aonde ?
        //no codigo original ele não controla isso, pra ele todos os projeteis são a mesma coisa
    }

}

