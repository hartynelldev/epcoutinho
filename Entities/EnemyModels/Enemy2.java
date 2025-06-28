package Entities.EnemyModels;

import Engine.GameLib;
import Entities.Enemy;
import Entities.Player;
import Entities.ProjectilePlayer;
import utils.EntityState;
import java.util.ArrayList;
import Entities.ProjectileEnemy;

import java.awt.*;

public class Enemy2 extends Enemy {

    public Enemy2(double x, double y, long when, long now){
        super(x, y, when, 12.0);
        VX = 0.42;
        VY = 0.42;
        angle = (3 * Math.PI) / 2;
        RV = 0;
        nextShot = now + 500;
        color= Color.CYAN;
        setState(EntityState.INACTIVE);
    }



    public void update(long delta, Player player, ArrayList<ProjectileEnemy> enemy_Projectiles, long currentTime) {
    
        // checar se esta explodindo
        if(getState() == EntityState.EXPLODING){
            if(currentTime > getExplosionEnd()){
                setState(EntityState.INACTIVE);
            }
            return;
        }
    
        if(!isActive()) return;
    
        // verificando se inimigo saiu da tela
        if(getY() > GameLib.HEIGHT + 10 || getY() < -10) {
            setState(EntityState.INACTIVE);
            return;
        }
    
        // Atualiza posição e angulo
        setX(getX() + getVX() * Math.cos(getAngle()) * delta);
        double previousY = getY();
        setY(getY() + getVY() * Math.sin(getAngle()) * delta * (-1.0));
        setAngle(getAngle() + getRV() * delta);

        double threshold = GameLib.HEIGHT * 0.30;
        
        if(previousY < threshold && getY() >= threshold) {
            
            if(getX() < GameLib.WIDTH / 2) setRV(0.003);
            else setRV(-0.003);
        }
    
        // "IA" de disparo do inimigo
        boolean shootNow = false;
    
        if(getRV() > 0 && Math.abs(getAngle() - 3 * Math.PI) < 0.05){
            setRV(0.0);
            setAngle(3 * Math.PI);
            shootNow = true;
        }
    
        if(getRV() < 0 && Math.abs(getAngle()) < 0.05){
            setRV(0.0);
            setAngle(0.0);
            shootNow = true;
        }
    
        if (shootNow) {
            double[] angles = { Math.PI/2 + Math.PI/8, Math.PI/2, Math.PI/2 - Math.PI/8 };
            int shots = 0;
            for (ProjectileEnemy proj : enemy_Projectiles) {
                if (!proj.isActive() && shots < angles.length) {
                    double a = angles[shots] + Math.random() * Math.PI/6 - Math.PI/12;
                    double vx = Math.cos(a);
                    double vy = Math.sin(a);
    
                    proj.setX(getX());
                    proj.setY(getY());
                    proj.setVX(vx * 0.30);
                    proj.setVY(vy * 0.30);
                    proj.setState(EntityState.ACTIVE);
                    shots++;
                }
            }
        }
    }
    //Provavlemente esse metodo retornara alguma coisa
    public void shot(){

        ProjectilePlayer project = new ProjectilePlayer(getX(),getY(), radius, Math.cos(angle) * 0.45,Math.sin(angle) * 0.45 * (-1.0) );

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

