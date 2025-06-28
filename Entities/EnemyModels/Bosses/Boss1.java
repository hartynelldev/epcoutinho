package Entities.EnemyModels.Bosses;

import Engine.GameLib;
import Entities.Boss;
import Entities.Player;
import Entities.ProjectileModels.ProjectileEnemy;
import Manager.EntityState;

import java.awt.*;
import java.util.ArrayList;

public class Boss1 extends Boss {
    protected boolean shild = false;
    protected long shildRecall;
    protected long shildTime;

    public Boss1(double x, double y, long when, long now, int hp) {
        super(x, y, when, now, hp);
        shildRecall = now + 800;
        shildTime = 800;
        angle = (3 * Math.PI) / 2;

        color = Color.ORANGE;
        VY = 0.25;
        VX = 0.25;

        setState(EntityState.ACTIVE);

        radius = 50;
    }

    public void update(long delta, Player player, ArrayList<ProjectileEnemy> enemy_Projectiles, long currentTime) {

        if(handleExploding(currentTime)) return;

        if(!isActive()) return;

        // Verifica se saiu da tela
        if(handleSaiuDaTela()){
            VX = VX * (-1);
        }


        shoot(enemy_Projectiles, player, delta, currentTime);
        updatePosition(delta, currentTime);
    }

    public void updatePosition(long delta, long now){
        if(handlePocicionado()){
            setX(getX() + getVX() * Math.cos(0) * delta);
        } else {
            setY(getY() + delta * VY);
        }
    }

    public boolean handlePocicionado(){
        if(getY() > 150) return true;
        return false;
    }

    public void shoot(ArrayList<ProjectileEnemy> enemy_Projectiles, Player player, long delta, long now) {
        if (now > getNextShot() && getY() < player.getY()) {
            for (ProjectileEnemy proj : enemy_Projectiles) {
                if (!proj.isActive()) {
                    proj.setX(getX());
                    proj.setY(getY());
                    proj.setVX(Math.cos(getAngle()) * 0.45);
                    proj.setVY(Math.sin(getAngle()) * 0.70 * (-1.0));
                    proj.setState(EntityState.ACTIVE);
                    setNextShot((long) (now + 250 + Math.random()*10));
                    proj.setRadius(5);
                    break;
                }
            }
        }
    }

    //O Boss1 se movera no Eixo X e não no Y;
    public boolean handleSaiuDaTela(){

        if(getX() > GameLib.WIDTH + 10) {
            //setState(EntityState.INACTIVE);
            return true;
        }
        if(getX() < 10){
            return true;
        }
        return false;
    }

    public void draw(long now){
        if(getState() == EntityState.EXPLODING){
            double alpha = (now - explosionStart) / (explosionEnd - explosionStart);
            GameLib.drawExplosion(getX(), getY(), alpha);
        }
        if(getState() == EntityState.ACTIVE){
            GameLib.setColor(color);
            GameLib.drawPlayer(getX(), getY(), (-1) * radius );
        }
    }
}