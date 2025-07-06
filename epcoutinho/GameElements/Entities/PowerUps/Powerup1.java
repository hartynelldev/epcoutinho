package GameElements.Entities.PowerUps;

import Engine.GameLib;
import GameElements.Entities.Player;
import GameElements.Entities.Powerup;
import Manager.EntityState;
import Config.GameConfig;
import java.awt.*;

public class Powerup1 extends Powerup {

    //  CONSTRUTOR 
    
    public Powerup1(double x, double y, long when, long now) {
        super(x, y, when, GameConfig.getColorPowerup1Green(), GameConfig.getColorPowerup1Yellow(), GameConfig.getPowerup1ColorSteps());
        setState(EntityState.INACTIVE);
        duration = GameConfig.getPowerup1Duration();
    }

    //  MÉTODOS PÚBLICOS 

    public void update(long delta, Player player, long currentTime) {
        if (handleExploding(currentTime)) return;
        
        if (!isActive()) return;

        // Verifica se saiu da tela
        if (handleSaiuDaTela()) return;

        updatePosition(delta, currentTime);
    }

    public void draw(long now) {
        if (getState() == EntityState.ACTIVE) {
            GameLib.setColor(getNextColor());
            GameLib.drawDiamond(getX(), getY(), radius);
            GameLib.drawCircle(getX(), getY(), radius - 7);
        }
    }

    //  MÉTODOS PROTEGIDOS 
    
    protected long powerUpFunctionality(Player player) {
        player.setColor(GameConfig.getColorPlayerPowerup());
        player.setVY(VY + GameConfig.getPowerup1SpeedIncrement());
        return duration;
    }
}

