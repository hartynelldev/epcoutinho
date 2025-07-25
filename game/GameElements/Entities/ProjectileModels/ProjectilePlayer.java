package GameElements.Entities.ProjectileModels;

import java.awt.Color;
import Engine.GameLib;
import Engine.ConfigReaders.GameConfig;
import GameElements.Entities.Projectile;

public class ProjectilePlayer extends Projectile {
    // disparos (player ou inimigos)

    //  CONSTRUTOR 
    
    public ProjectilePlayer(double x, double y, double radius, double vx, double vy) {
        super(x, y, radius, vx, vy);
    }

    //  MÉTODOS PÚBLICOS 
    
    public void draw(long now) {
        if (isActive()) {
            GameLib.setColor(GameConfig.getColorProjectilePlayer());
            GameLib.drawLine(getX(), getY() - 5, getX(), getY() + 5);
            GameLib.drawLine(getX() - 1, getY() - 3, getX() - 1, getY() + 3);
            GameLib.drawLine(getX() + 1, getY() - 3, getX() + 1, getY() + 3);
        }
    }
}