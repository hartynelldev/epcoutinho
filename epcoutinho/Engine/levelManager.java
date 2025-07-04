package Engine;

import Entities.EnemyModels.Enemy1;
import Entities.EnemyModels.Enemy2;
import Entities.EnemyModels.Bosses.Boss1;
import Entities.EnemyModels.Bosses.Boss2;
import Entities.PowerUps.Powerup1;
import Entities.PowerUps.Powerup2;
import Entities.Player;
import Entities.ProjectileModels.ProjectileEnemy;
import java.util.ArrayList;
import java.util.List;

public class LevelManager {

    private List<Enemy> enemies;
    private List<Powerup> powerups;
    private Boss boss;
    private Player player;
    private List<ProjectileEnemy> projectiles;

    public LevelManager(Player player) {
        this.player = player;
        this.enemies = new ArrayList<>();
        this.powerups = new ArrayList<>();
        this.projectiles = new ArrayList<>();
    }

    public void loadLevel(String levelData) {
        // Parse o arquivo de dados do nível (ex: fase1.txt)
        // Para cada linha, crie os objetos correspondentes
        String[] lines = levelData.split("\n");
        for (String line : lines) {
            String[] parts = line.split(" ");
            String type = parts[0];
            int id = Integer.parseInt(parts[1]);
            double x = Double.parseDouble(parts[2]);
            double y = Double.parseDouble(parts[3]);
            long when = Long.parseLong(parts[4]);

            switch (type) {
                case "INIMIGO":
                    if (id == 1) {
                        enemies.add(new Enemy1(x, y, when, System.currentTimeMillis()));
                    } else if (id == 2) {
                        enemies.add(new Enemy2(x, y, when, System.currentTimeMillis()));
                    }
                    break;
                case "CHEFE":
                    if (id == 1) {
                        boss = new Boss1(x, y, when, System.currentTimeMillis(), 100);
                    } else if (id == 2) {
                        boss = new Boss2(x, y, when, System.currentTimeMillis(), 200);
                    }
                    break;
                case "POWERUP":
                    if (id == 1) {
                        powerups.add(new Powerup1(x, y, when, System.currentTimeMillis()));
                    } else if (id == 2) {
                        powerups.add(new Powerup2(x, y, when, System.currentTimeMillis()));
                    }
                    break;
                default:
                    System.out.println("Tipo desconhecido: " + type);
            }
        }
    }

    public void update(long delta) {
        for (Enemy enemy : enemies) {
            enemy.update(delta, player, projectiles, System.currentTimeMillis());
        }
        if (boss != null) {
            boss.update(delta, player, projectiles, System.currentTimeMillis());
        }
        for (Powerup powerup : powerups) {
            powerup.update(delta, player, System.currentTimeMillis());
        }
    }

    public void draw() {
        for (Enemy enemy : enemies) {
            enemy.draw(System.currentTimeMillis());
        }
        if (boss != null) {
            boss.draw(System.currentTimeMillis());
        }
        for (Powerup powerup : owerups) {
            powerup.draw(System.currentTimeMillis());
        }
    }

}
