package Entities.EnemyModels;

import Engine.GameLib;
import Entities.Enemy;
import Entities.Player;
import Entities.Projectile;
import utils.EntityState;

import java.awt.*;

public class Enemy2 extends Enemy {

    public Enemy2(double x, double y, long when){
        super(x, y, when, 12.0);
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

        if(!super.isActive()) return;

            /* verificando se inimigo saiu da tela */
            if(getY() > GameLib.HEIGHT + 10 || getY() < -10) {
                
                setState(EntityState.INACTIVE);
                return;
            }

        // Atualiza posição e angulo
        double x_ant = getX();
        setX(x_ant + (VX * Math.cos(angle) * delta));

        double y_ant = getY();
        super.setY(y_ant + (VY * Math.sin(angle) * delta * (-1.0)));

        angle += RV * delta;
        

        // "IA" de disparo do inimigo
        boolean shootNow = false;

        if(RV > 0 && Math.abs(angle - 3 * Math.PI) < 0.05){
            
            setRV(0.0);
            setAngle(3 * Math.PI);
            shootNow = true;
        }
        
        if(RV < 0 && Math.abs(angle) < 0.05){
            
            setRV(0.0);
            setAngle(0.0);;
            shootNow = true;
        }
                                                        
        if (shootNow) {
            // ATAQUE EM LEQUE
            java.util.ArrayList<Double> angles = new java.util.ArrayList<>();
            angles.add(Math.PI/2 + Math.PI/8);
            angles.add(Math.PI/2);
            angles.add(Math.PI/2 - Math.PI/8);

            for(int k = 0; k < angles.size(); k++){
                double a = angles.get(k) + Math.random() * Math.PI/6 - Math.PI/12;
                double vx = Math.cos(a);
                double vy = Math.sin(a);
                Projectile proj = new Projectile(getX(), getY(), radius, vx * 0.30, vy * 0.30);
                
                proj.setState(EntityState.ACTIVE);
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

