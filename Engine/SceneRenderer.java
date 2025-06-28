package Engine;

import java.awt.Color;
import java.util.List;
import Entities.*;
import Entities.EnemyModels.*;
import Entities.ProjectileModels.ProjectileEnemy;
import Entities.ProjectileModels.ProjectilePlayer;
import Manager.EntityState;

public class SceneRenderer {

    public SceneRenderer() {
    }

    public static void render(
        BackGround backGround,
        Player player,
        List<ProjectilePlayer> playerProjectiles,
        List<ProjectileEnemy> enemy_Projectiles,
        List<Enemy1> enemy1List,
        List<Enemy2> enemy2List,
        long currentTime,
        long delta
    ) {
        // Desenhar fundo
        backGround.draw(delta);

        // Desenhar player
        player.draw(currentTime);

        // Desenhar projéteis do player
        for(ProjectilePlayer proj : playerProjectiles){
            proj.draw(currentTime);
        }

        // Desenhar projéteis dos inimigos
        for(ProjectileEnemy proj : enemy_Projectiles){
            proj.draw(currentTime);
        }

        // Desenhar inimigos tipo 1
        for(Enemy1 e1 : enemy1List){
            e1.draw(currentTime);
        }

        // Desenhar inimigos tipo 2
        for(Enemy2 e2 : enemy2List){
            if(e2.getState() == EntityState.EXPLODING){
                double alpha = (currentTime - e2.getExplosionStart()) / (double)(e2.getExplosionEnd() - e2.getExplosionStart());
                GameLib.drawExplosion(e2.getX(), e2.getY(), alpha);
            }
            if(e2.getState() == EntityState.ACTIVE){
                GameLib.setColor(Color.MAGENTA);
                GameLib.drawDiamond(e2.getX(), e2.getY(), e2.getRadius());
            }
        }
    }
}