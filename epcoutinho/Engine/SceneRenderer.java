package Engine;

import java.util.List;

import GameElements.Entity;
import GameElements.Entities.*;
import GameElements.Entities.EnemyModels.Boss;
import GameElements.Entities.EnemyModels.Bosses.Boss1;
import Manager.EntityState;


public class SceneRenderer {

    public SceneRenderer() {
    }

    public static void render(
        BackGround backGround,
        Player player,
        Boss boss,
        List<List<? extends Entity>> entityLists,
        long currentTime,
        long delta
    ) {
        // Desenhar fundo
        backGround.draw(delta);

        // Desenhar player
        player.draw(currentTime);

        // Desenhar boss se estiver ativo
        if (boss != null && boss.getState() == EntityState.ACTIVE) {
            boss.draw(currentTime);
        }

        // Desenhar todas as entidades das listas
        for (List<? extends Entity> list : entityLists) {
            for (Entity e : list) {
                e.draw(currentTime);
            }
        }
    }
}