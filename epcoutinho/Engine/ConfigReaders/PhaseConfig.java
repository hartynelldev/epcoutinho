package Engine.ConfigReaders;

import GameElements.Entities.Enemy;
import GameElements.Entities.Powerup;
import GameElements.Entities.EnemyModels.Boss;
import GameElements.Entities.EnemyModels.Enemy1;
import GameElements.Entities.EnemyModels.Enemy2;
import GameElements.Entities.EnemyModels.Bosses.Boss1;
import GameElements.Entities.EnemyModels.Bosses.Boss2;
import GameElements.Entities.PowerUps.Powerup1;
import GameElements.Entities.PowerUps.Powerup2;
import GameElements.Entities.Player;
import GameElements.Entities.ProjectileModels.ProjectileEnemy;
import java.util.ArrayList;

public class PhaseConfig {

    private ArrayList<Enemy> enemies;
    private ArrayList<Powerup> powerups;
    private ArrayList<ProjectileEnemy> projectiles;
    private Boss boss;
    private Player player;

    public PhaseConfig(Player player) {
        this.player = player;
        this.enemies = new ArrayList<>();
        this.powerups = new ArrayList<>();
        this.projectiles = new ArrayList<>();
    }

    // Leitor do level
    public void loadLevel(String levelData) {
        String[] lines = levelData.split("\n");
        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty() || line.startsWith("#")) continue; // pula linhas vazias ou comentários
            String[] parts = line.split(" ");
            String type = parts[0];
            int id = Integer.parseInt(parts[1]);
            double x = Double.parseDouble(parts[2]);
            double y = Double.parseDouble(parts[3]);
            long when = Long.parseLong(parts[3].trim());

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
        long now = System.currentTimeMillis();
        for (Enemy enemy : enemies) {
            enemy.update(delta, player, projectiles, now);
        }
        if (boss != null) {
            boss.spawn(now);
            boss.update(delta, player, projectiles, now);
        }
        for (Powerup powerup : powerups) {
            powerup.update(delta, player, now);
        }
    }

    public void draw() {
        long now = System.currentTimeMillis();
        for (Enemy enemy : enemies) {
            enemy.draw(now);
        }
        if (boss != null) {
            boss.spawn(now);
            boss.draw(now);
        }
        for (Powerup powerup : powerups) {
            powerup.draw(now);
        }
    }

}
