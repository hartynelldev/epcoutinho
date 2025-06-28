package Entities.EnemyModels;

import Engine.GameLib;
import Entities.Enemy;
import Entities.Player;
import Entities.ProjectileModels.ProjectileEnemy;
import Manager.EntityState;
import java.util.ArrayList;

import java.awt.*;

public class Enemy1 extends Enemy {

    public Enemy1(double x, double y, long when, long now){
        super(x, y, when, 9.0);
        VX = 0;
        VY = 0.20 + Math.random() * 0.15;
        angle = (3 * Math.PI) / 2;
        RV = 0;
        nextShot = now + 500;
        color= Color.CYAN;
        setState(EntityState.INACTIVE);
    }

    public void update(long delta, Player player, ArrayList<ProjectileEnemy> enemy_Projectiles, long currentTime) {
    
        if (getState() == EntityState.EXPLODING) {
            if (currentTime > getExplosionEnd()) {
                setState(EntityState.INACTIVE);
            }
        }
    
        if (getState() == EntityState.ACTIVE) {
            // Verifica se saiu da tela
            if (getY() > GameLib.HEIGHT + 10) {
                setState(EntityState.INACTIVE);
            } else {
                // Atualiza posição e ângulo
                setX(getX() + getVX() * Math.cos(getAngle()) * delta);
                setY(getY() + getVY() * Math.sin(getAngle()) * delta * (-1.0));
                setAngle(getAngle() + getRV() * delta);
    
                // Disparo
                if (currentTime > getNextShot() && getY() < player.getY()) {
                    for (ProjectileEnemy proj : enemy_Projectiles) {
                        if (!proj.isActive()) {
                            proj.setX(getX());
                            proj.setY(getY());
                            proj.setVX(Math.cos(getAngle()) * 0.45);
                            proj.setVY(Math.sin(getAngle()) * 0.45 * (-1.0));
                            proj.setState(EntityState.ACTIVE);
                            setNextShot((long) (currentTime + 200 + Math.random() * 500));
                            break;
                        }
                    }
                }
            }
        }
    }
    

    //Provavlemente esse metodo retornara alguma coisa
    public void shot(){

        ProjectileEnemy project = new ProjectileEnemy(getX(),getY(), radius, Math.cos(angle) * 0.45,Math.sin(angle) * 0.45 * (-1.0) );

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

