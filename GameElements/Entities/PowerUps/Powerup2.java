package GameElements.Entities.PowerUps;

import Engine.GameLib;
import GameElements.Entities.Player;
import GameElements.Entities.Powerup;
import Manager.EntityState;

import java.awt.*;

public class Powerup2 extends Powerup {

    public Powerup2(double x, double y, long when, long now){
        super(x, y, when, Color.YELLOW, Color.MAGENTA, 10);
        setState(EntityState.INACTIVE);
        VY = (0.05 + Math.random() * 0.10);
        duration = 10000;
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
            GameLib.drawCircle(getX(), getY(), radius );
        }
    }

    protected long powerUpFunctionality(Player player){
        player.setIvulnerability(true);
        player.setShield();
        if(player.getVX()>0.4) player.setVX(VX-0.2);
        return duration;
    }
}

