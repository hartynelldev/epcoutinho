package Entities.EnemyModels;

import Engine.GameLib;
import Entities.Enemy;
import Entities.Player;
import Entities.Projectile;
import utils.EntityState;

import java.awt.*;

public class Enemy1 extends Enemy {

    public Enemy1(double x, double y, long when){
        super(x, y, when, 9.0);
        VX = 0;
        VY = 0.20 + Math.random() * 0.15;
        angle = (3 * Math.PI) / 2;
        RV = 0;
        nextShot = now + 500;
        color= Color.CYAN;
        setState(EntityState.INACTIVE);
    }

    public void update(long delta, Player player){

        // checar se esta explodindo
        if(getState() == EntityState.EXPLODING){
					
            if(now > super.explosionEnd){
                
                super.setState(EntityState.INACTIVE);
            }
        }

        if(super.isActive()){
    
            /* verificando se inimigo saiu da tela */
            if(getY() > GameLib.HEIGHT + 10) {
                
                setState(EntityState.INACTIVE);
            }
            else {
                // Atualiza posição
                double x_ant = getX();
                setX(x_ant + (VX * Math.cos(angle) * delta));

                double y_ant = getY();
                super.setY(y_ant + (VY * Math.sin(angle) * delta * (-1.0)));

                // Atualiza ângulo
                angle += RV * delta;
                
                // "IA" de disparo do inimigo
                if(now > nextShot && getY() < player.getY()){
                    
                    // criar um novo projetil
                    //Inimigo1 e Inimigo2: super(enemy.getX(), enemy.getY(), radius);
                    // Inimigo1: VX = Math.cos(enemy.getAngle()) * 0.45; VY = Math.sin(enemy.getAngle()) * 0.45 * (-1.0);
                    Projectile proj = new Projectile(getX(), getY(), radius, Math.cos(angle) * 0.45, Math.sin(angle) * 0.45 * (-1.0));
                    proj.setState(EntityState.ACTIVE);
                    
                    this.nextShot = (long) (now + 200 + Math.random() * 500);
                }
            }
        }
        
    }

    //Provavlemente esse metodo retornara alguma coisa
    public void shot(){

        Projectile project = new Projectile(getX(),getY(), radius, Math.cos(angle) * 0.45,Math.sin(angle) * 0.45 * (-1.0) );

        //Inimigo1 e Inimigo2: super(enemy.getX(), enemy.getY(), radius);
        // Inimigo1: VX = Math.cos(enemy.getAngle()) * 0.45; VY = Math.sin(enemy.getAngle()) * 0.45 * (-1.0);
        // o tiro vai ser parte do inimigo, mas o inimigo vai chamar/criar uma classe de projétil c o construtor de projetil.
        // Se vc quiser, pode criar 2 subclasses de projetil, uma o projetil base e uma outra q quando chega em um pedaco da tela 
        // (o fim se pa) explode em outros projeteis (pro boss e pros powerup w vo usa). mas ai e opcional sepa, tu escolhe, 
        //so acho q vale a pena ter mais q 1 tipo de projetil
        //teoricamente quem vai controlar porjetil seria GameEnge
        //mas a arrayList ficaria aonde ?
        //no codigo original ele não controla isso, pra ele todos os projeteis são a mesma coisa


    }

}

