package Engine;

import java.util.List;

import GameElements.Entity;
import GameElements.Entities.*;
import GameElements.Entities.EnemyModels.Boss;
import GameElements.Entities.EnemyModels.Bosses.Boss1;


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

        boss.draw(currentTime);

        // Desenhar todas as entidades das listas
        for (List<? extends Entity> list : entityLists) {
            for (Entity e : list) {
                e.draw(currentTime);
            }
        }
    }
}