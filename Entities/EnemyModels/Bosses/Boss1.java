package Entities.EnemyModels.Bosses;

import Engine.GameLib;
import Entities.Boss;
import Entities.Player;
import Entities.ProjectileModels.ProjectileEnemy;
import Manager.EntityState;

import java.awt.*;
import java.util.ArrayList;

public class Boss1 extends Boss {
    //protected boolean shild = false;
    protected long shildRecall;
    protected long shildTime;
    protected long shildDuration;

    public Boss1(double x, double y, long when, long now, int hp) {
        super(x, y, when, now, hp);
        shildTime = now + 10000;
        angle = (3 * Math.PI) / 2;
        color = Color.ORANGE;
        VY = 0.05;
        VX = 0.25;
        setState(EntityState.ACTIVE);

        radius = 50;
    }

    public void update(long delta, Player player, ArrayList<ProjectileEnemy> enemy_Projectiles, long currentTime) {

        hasLife(currentTime);
        if(hitTimeEnd(currentTime)){
            if(isSuperAttack(currentTime)){
                setColor(Color.CYAN);
            } else setColor(Color.ORANGE);
        }

        if(handleExploding(currentTime)) return;

        if(!isActive()) return;

        // Verifica se saiu da tela
        if(handleSaiuDaTela()){
            VX = VX * (-1);
        }

        shild(currentTime);
        lifeBar.update(delta,currentTime,HP);
        shoot(enemy_Projectiles, player, delta, currentTime);
        updatePosition(delta, currentTime);
    }

    public void shild(long currentTime){
       if(shildTime < currentTime && !isIvulnerable){
           //shild = true;
           shildDuration = currentTime + 10000;
           shildTime = currentTime + 20000;
           isIvulnerable = true;
       }
       if(isIvulnerable && shildDuration < currentTime){
           isIvulnerable = false;
       }
    }

    public void updatePosition(long delta, long now){
        if(handlePocicionado()){
            setX(getX() + getVX() * Math.cos(0) * delta);
        } else {
            setY(getY() + delta * VY);
        }
    }

    public boolean handlePocicionado(){
        if(getY() > 170) return true;
        return false;
    }

    public boolean isSuperAttack(long now){
        return now >= nextSuperAtack && now <= (nextSuperAtack + superAtackDuration);
    }

    public void shoot(ArrayList<ProjectileEnemy> enemy_Projectiles, Player player, long delta, long now) {
        // Se o super ataque anterior terminou, agenda o próximo
        if (now > nextSuperAtack + superAtackDuration) {
            // Próximo super ataque entre 5 e 10 segundos a partir de agora
            long intervalo = 8000 + (long)(Math.random() * 5000); // 5000ms a 10000ms
            nextSuperAtack = now + intervalo;
        }

        // Só dispara se for o momento certo e o Boss estiver acima do jogador
        if (now > getNextShot() && getY() < player.getY() && handlePocicionado()) {
            // Corrige movimentação se estiver parada ou errada

            if (isSuperAttack(now)) {
                // SUPER DISPARO: múltiplos projéteis em leque
                if(VX == 0.25 || VX == -0.25){
                    VX = VX * 2;
                }

                    for (ProjectileEnemy proj : enemy_Projectiles) {
                        if (!proj.isActive()) {
                            proj.setX(getX());
                            proj.setY(getY());
                            proj.setVX(Math.cos(getAngle()) * 0.8);
                            proj.setVY(Math.sin(getAngle()) * 0.8 * (-1.0));
                            proj.setRadius(20);
                            proj.setState(EntityState.ACTIVE);
                            break;
                        }
                    }

                setNextShot(now + 300); // cooldown do super disparo
            } else {
                // DISPARO NORMAL
                if ((VX != 0.25 && VX != -0.25)) {
                    VX = VX/2;
                }
                for (ProjectileEnemy proj : enemy_Projectiles) {
                    if (!proj.isActive()) {
                        proj.setX(getX());
                        proj.setY(getY());
                        proj.setVX(Math.cos(getAngle()) * 0.45);
                        proj.setVY(Math.sin(getAngle()) * 0.70 * (-1.0));
                        proj.setRadius(5);
                        proj.setState(EntityState.ACTIVE);
                        setNextShot((long) (now + 250 + Math.random() * 10));
                        break;
                    }
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
            if(isSuperAttack(now) && hitTimeEnd(now)) GameLib.setColor(Color.ORANGE);
            lifeBar.draw();
            if(isIvulnerable){
                GameLib.setColor(Color.WHITE);
                GameLib.drawPlayer(getX(), getY(),  (-1.5) * radius );
            }

        }
    }
}