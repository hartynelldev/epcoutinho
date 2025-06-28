package Engine;

import java.util.List;
import Entities.*;

public class SceneRenderer {

    public SceneRenderer() {
    }

    public static void render(
        BackGround backGround,
        Player player,
        List<List<? extends Entity>> entityLists,
        long currentTime,
        long delta
    ) {
        // Desenhar fundo
        backGround.draw(delta);

        // Desenhar player
        player.draw(currentTime);

        // Desenhar todas as entidades das listas
        for (List<? extends Entity> list : entityLists) {
            for (Entity e : list) {
                e.draw(currentTime);
            }
        }
    }
}