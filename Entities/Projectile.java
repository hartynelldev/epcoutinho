package Entities;
import java.awt.Color;
import Entities.Entity;
import utils.EntityState;

public class Projectile extends Entity{

    public Projectile(double x, double y) {
        super(x, y);
        
    }
    
    public Projectile(double x, double y, double raio, double vx, double vy, Color cor) {
        super(x, y, raio, EntityState.ACTIVE, vx, vy, 0.0, 0.0);
    }

    public void update(long delta) {
        if(super.getState() == EntityState.ACTIVE){

            /* verificando se projétil saiu da tela */
            if(super.getX() < 0) {

                //projectile_states[i] = INACTIVE;
            }
            else {

                double newX = super.getX() + super.getVx() * delta;
                super.setX(newX);

                double newY = super.getY() + super.getVy() * delta;
                super.setY(newY);
            }
        }
    } 
    public void draw() {

    }
}


