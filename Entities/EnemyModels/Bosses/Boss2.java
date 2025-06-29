package Entities.EnemyModels.Bosses;
import Engine.GameLib;
import Entities.Boss;
import Entities.Player;
import Entities.ProjectileModels.ProjectileEnemy;
import Manager.EntityState;

import java.awt.*;
import java.util.ArrayList;

public class Boss2 extends Boss {
    //protected boolean shild = false;
//    protected long shildRecall;
//    protected long shildTime;
//    protected long shildDuration;

    public Boss2(double x, double y, long when, long now, int hp) {
        super(x, y, when, now, hp);
        //shildTime = now + 10000;
        angle = (3 * Math.PI) / 2;
        color = Color.YELLOW;
        VY = 0.25;
        VX = 0.25;
        setState(EntityState.ACTIVE);

        radius = 90;
    }

    public void update(long delta, Player player, ArrayList<ProjectileEnemy> enemy_Projectiles, long currentTime) {

        hasLife(currentTime);
        if(hitTimeEnd(currentTime)){
            if(isSuperAttack(currentTime)){
                setColor(Color.magenta);
            } else setColor(Color.YELLOW);
        }

        if(handleExploding(currentTime)) return;

        if(!isActive()) return;

        // Verifica se saiu da tela
        if(handleSaiuDaTelaX()){
            VX = VX * (-1);
        }
        if(handleSaiuDaTelaY()){
            //setY(0);
            VY = VY * (-1);
            //setX(Math.random()*1000/GameLib.WIDTH);
        }

        //shild(currentTime);
        lifeBar.update(delta, currentTime, HP);
        shoot(enemy_Projectiles, player, delta, currentTime);
        updatePosition(delta, currentTime);
    }

//    public void shild(long currentTime){
//        if(shildTime < currentTime && !isIvulnerable){
//            //shild = true;
//            shildDuration = currentTime + 10000;
//            shildTime = currentTime + 20000;
//            isIvulnerable = true;
//        }
//        if(isIvulnerable && shildDuration < currentTime){
//            isIvulnerable = false;
//        }
//    }

    public void updatePosition(long delta, long now) {
        double targetY = 215; // centro da faixa desejada (200–230)

        if (isSuperAttack(now)) {
            // Durante o super ataque: desce rápido
            setY(getY() + delta * VY * 5.0);
        } else if (getY() > 230) {
            // Após super ataque: volta para cima suavemente
            setY(getY() - delta * 0.15); // sobe suavemente
            if (getY() < targetY) setY(targetY); // trava no centro
        } else if (handlePocionado()) {
            // Já está posicionado: mover horizontalmente
            setX(getX() + getVX() * delta);
        } else {
            // Ainda se posicionando: descer lentamente até a faixa
            setY(getY() + delta * VY);
        }
    }



    public boolean handlePocionado(){
        if(getY() > 200 && getY() < 230) return true;
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
        if (now > getNextShot() && getY() < player.getY() && handlePocionado()) {
            // Corrige movimentação se estiver parada ou errada

            if (isSuperAttack(now)) {
                // SUPER DISPARO: múltiplos projéteis em leque

                setNextShot(now + 300); // cooldown do super disparo
            } else {

                // DISPARO NORMAL

                int disparos = 5;
                double anguloBase = getAngle();
                double spread = Math.toRadians(45); // ângulo total
                double passo = spread / (disparos - 1);

                for (int i = 0; i < disparos; i++) {
                    for (ProjectileEnemy proj : enemy_Projectiles) {
                        if (!proj.isActive()) {
                            double angulo = anguloBase - spread / 2 + i * passo;
                            proj.setX(getX());
                            proj.setY(getY());
                            proj.setVX(Math.cos(angulo) * 0.5);
                            proj.setVY(Math.sin(angulo) * 0.5 * (-1.0));
                            proj.setRadius(8);
                            proj.setState(EntityState.ACTIVE);
                            setNextShot((long) (now + 400 + Math.random() * 10));
                            break;
                        }
                    }
                }

            }
        }
    }


    //O Boss1 se movera no Eixo X e não no Y;
    public boolean handleSaiuDaTelaX(){

        if(getX() > GameLib.WIDTH + 10) {
            //setState(EntityState.INACTIVE);
            return true;
        }
        if(getX() < 10){
            return true;
        }
        return false;
    }

    public boolean handleSaiuDaTelaY(){
        if (getY() > GameLib.HEIGHT + 10 || getY() < -5 ){
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

            GameLib.drawDiamond(getX(), getY(), radius );
            GameLib.drawDiamond(getX(), getY(), radius * Math.sin(getY()) );
            GameLib.drawDiamond(getX(), getY(), radius * Math.sin(getX()) );

            if(isSuperAttack(now) && hitTimeEnd(now)) GameLib.setColor(Color.YELLOW);
            lifeBar.draw();
//            GameLib.fillRect(240, getY() - 150, 5000*HP/GameLib.WIDTH, 40);

        }
    }
}
/* ideoa de disparo
int disparos = 5;
                double anguloBase = getAngle();
                double spread = Math.toRadians(45); // ângulo total
                double passo = spread / (disparos - 1);

                for (int i = 0; i < disparos; i++) {
                    for (ProjectileEnemy proj : enemy_Projectiles) {
                        if (!proj.isActive()) {
                            double angulo = anguloBase - spread / 2 + i * passo;
                            proj.setX(getX());
                            proj.setY(getY());
                            proj.setVX(Math.cos(angulo) * 0.5);
                            proj.setVY(Math.sin(angulo) * 0.5 * (-1.0));
                            proj.setRadius(8);
                            proj.setState(EntityState.ACTIVE);
                            break;
                        }
                    }
                }
 */