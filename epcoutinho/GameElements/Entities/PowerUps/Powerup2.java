package GameElements.Entities.PowerUps;

import Engine.GameLib;
import GameElements.Entities.Player;
import GameElements.Entities.Powerup;
import Manager.EntityState;
import Config.GameConfig;
import java.awt.*;

public class Powerup2 extends Powerup {

    //  CONSTRUTOR 
    
    public Powerup2(double x, double y, long when, long now) {
        super(x, y, when, GameConfig.getColorPowerup2Yellow(), GameConfig.getColorPowerup2Magenta(), GameConfig.getPowerup2ColorSteps());
        setState(EntityState.INACTIVE);
        VY = GameConfig.getPowerup2VY() + Math.random() * GameConfig.getPowerup2VYRandom();
        duration = GameConfig.getPowerup2Duration();
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
            GameLib.drawCircle(getX(), getY(), radius);
        }
    }

    //  MÉTODOS PROTEGIDOS 
    
    protected long powerUpFunctionality(Player player) {
        player.setIvulnerability(true);
        player.setShield();
        if (player.getVX() > GameConfig.getPowerup2MaxSpeed()) player.setVX(VX - GameConfig.getPowerup2SpeedDecrement());
        return duration;
    }
}

