package Entities.PowerUps;

import Engine.GameLib;
import Entities.Player;
import Entities.Powerup;
import Entities.ProjectileModels.ProjectileEnemy;
import Manager.EntityState;
import java.util.ArrayList;

import java.awt.*;

public class Powerup1 extends Powerup {

    public Powerup1(double x, double y, long when, long now){
        super(x, y, when, Color.GREEN, Color.YELLOW, 10);
        setState(EntityState.INACTIVE);
        duration = 3000;
    }

    public void update(long delta, Player player, long currentTime) {
        
        if(handleExploding(currentTime)) return;
        
        if(!isActive()) return;

        // Verifica se saiu da tela
        if(handleSaiuDaTela()) return;

        updatePosition(delta, currentTime);
    }

    public void draw(long now){
        if(getState() == EntityState.ACTIVE){
            GameLib.setColor(getNextColor());
            GameLib.drawDiamond(getX(), getY(), radius );
            GameLib.drawCircle(getX(), getY(), radius-7 );
        }
    }

    protected long powerUpFunctionality(Player player){
        player.setColor(Color.GREEN);
        player.setVY(VY+0.2);
        return duration;
    }

    
}

