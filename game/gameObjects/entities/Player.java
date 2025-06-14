package game.gameObjects.entities;

import java.awt.Color;

import game.GameLib;
import game.gameObjects.Entity;

public class Player extends Entity{

    protected int playerShootingSpeed = 100;
    
    public Player(double x, double y, double radius){
        super(x, y, radius);
		VX = 0.25;						// velocidade no eixo x
		VY = 0.25;						// velocidade no eixo y
        color = Color.BLUE;
    }

    public void setShootingSpeed(int shootingSpeed){
        playerShootingSpeed = shootingSpeed;
    }

    public void update(long delta) {
        if (getState() != EXPLODING) {
            if (GameLib.iskeyPressed(GameLib.KEY_UP)) Y -= delta * VY;
            if (GameLib.iskeyPressed(GameLib.KEY_DOWN)) Y += delta * VY;
            if (GameLib.iskeyPressed(GameLib.KEY_LEFT)) X -= delta * VX;
            if (GameLib.iskeyPressed(GameLib.KEY_RIGHT)) X += delta * VX;

            if (GameLib.iskeyPressed(GameLib.KEY_CONTROL)) {
                if (now > nextShot) {
                    // DAR TIRO
                    nextShot = now + playerShootingSpeed;
                }
            }
        } else {
            if (now > explosionEnd) {
                 setState(ACTIVE);
            }
        }
    }

    public void draw() {
        if (getState() == EXPLODING) {
            explode(2000);
        } else {
            GameLib.setColor(color);
            GameLib.drawPlayer(X, Y, radius);
        }
    }
}
