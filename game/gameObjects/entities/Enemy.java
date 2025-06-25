package game.gameObjects.entities;
import java.awt.Color;
import java.util.ArrayList;


import game.GameLib;
import game.gameObjects.Entity;

public class Enemy extends Entity{

    //inimigo1 e inimigo2 tem somente uma varaivale diferentente,
    // aonde eu implemento ele? super classe inimigo ? if ? sla

    //talvez seja em outro lugar
    private long spawTime;

    public Enemy(double x, double y, long when){
        super(x, y, 9.0);
        spawTime = when;
        VY = 0;
        VX = 0.20 + Math.random() * 0.15;;
        angle = (3 * Math.PI) / 2;
        RV = 0;
        nextShot = now + 500;
        setState(INACTIVE);

    }
    public void update(long delta/*, Player player*/){
        if(getState() == EXPLODING){

            if(now > explosionEnd){
                //Excluir o inimigo tipo: Inimigo1.remove(i);
            }
        }

        if(getState() == ACTIVE){

            /* verificando se inimigo saiu da tela */
            if(Y > GameLib.HEIGHT + 10) {
                //Excluir o inimigo
            }
            else {

                X += VX * Math.cos(angle) * delta;
                Y += VX * Math.sin(angle) * delta * (-1.0);
                angle += RV * delta;

                if(now > nextShot/* && Y < player.getY()*/){

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
            }
        }
    }

    public void draw(){
        if(getState() == EXPLODING){
            explode(10);
        }
        if(getState() == ACTIVE){
            GameLib.setColor(Color.CYAN);
            GameLib.drawCircle(X, Y, radius );
        }
    }

}
