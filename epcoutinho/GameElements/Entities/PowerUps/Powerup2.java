package GameElements.Entities.PowerUps;

import Engine.GameLib;
import GameElements.Entities.Player;
import GameElements.Entities.Powerup;
import Manager.EntityState;
import Engine.ConfigReaders.GameConfig;
import java.awt.*;

// Powerup 2: cria um escudo temporário que deixa entidade INVULNERÁVEL
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

    public long spawn(long currentTime){
        this.setX(Math.random() * (GameLib.WIDTH - 20.0) + 10.0);
		this.setY(-10.0);
		this.setVX(0.0); // Set as needed
		this.setVY(0.05 + Math.random() * 0.10);
		this.setAngle((3 * Math.PI) / 2);
		this.setRV(0.0);
		this.spawn(currentTime+500, currentTime);

        // retorna novo spawnTime para próximo powerUp
        return currentTime + 500;
    }
}

