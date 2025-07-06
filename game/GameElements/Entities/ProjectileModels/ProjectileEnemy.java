package GameElements.Entities.ProjectileModels;

import java.awt.Color;
import Engine.GameLib;
import Engine.ConfigReaders.GameConfig;
import GameElements.Entities.Projectile;

public class ProjectileEnemy extends Projectile {
    // disparos (player ou inimigos)

    //  CONSTRUTOR 
    
    public ProjectileEnemy(double x, double y, double radius, double vx, double vy) {
        super(x, y, radius, vx, vy);
    }

    //  MÉTODOS PÚBLICOS 
    
    public void draw(long now) {
        if (this.isActive()) {
            GameLib.setColor(GameConfig.getColorProjectileEnemy());
            GameLib.drawCircle(getX(), getY(), radius); // Use o raio correto se necessário
        }
    }
}