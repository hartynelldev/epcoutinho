package Manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Engine.GameLib;
import GameElements.Entity;
import GameElements.Entities.EnemyModels.Enemy1;
import GameElements.Entities.EnemyModels.Enemy2;
import GameElements.Entities.EnemyModels.Bosses.Boss2;
import GameElements.Entities.PowerUps.Powerup1;
import GameElements.Entities.PowerUps.Powerup2;

public class SpawnManager {
    
    // SpawnTimes
    Map<String, Long> spawnTimes = new HashMap<>();

    // Dict de counts de elementos
    Map<String, Integer> count = new HashMap<>();

    private double enemy2_spawnX;
    private int enemy2_count;

    // construtor - tempo de spawn inicial são fixos
    public SpawnManager(long startTime){

        spawnTimes.put("Enemy1", startTime + 2000); // Use o tempo atual aqui --Lembre que no arquivo os tempos de spaw são fixos  (definido em cada entrada)
        spawnTimes.put("Enemy2", startTime + 7000); //--Lembre que no arquivo os tempos de spaw são fixos
        spawnTimes.put("firstPowerup1", startTime + 4000);
        spawnTimes.put("firstPowerup2", startTime + 8000);

        // contador de ativos no jogo
        count.put("Enemy1", 0);
        count.put("Enemy2", 0);
        count.put("Boss1", 0);
        count.put("Boss2", 0);
        count.put("Powerup1", 0);
        count.put("Powerup2", 0);

    }

    // verificando se novos inimigos devem ser "lançados"
    public void shouldSpawn(long currentTime, List<List<? extends Entity>> entities){

        /* verificando se novos inimigos (tipo 1) devem ser "lançados" */
			//spaw() faz isso
			if (currentTime > spawnTimes.get("Enemy1")) {
                for (Entity entity : entities.get(4)) {
                    Enemy1 e1 = (Enemy1) entity;
                    if (e1.getState() == EntityState.INACTIVE) {
                        long newSpawnTime = e1.spawn(currentTime);
                        spawnTimes.put("Enemy1", newSpawnTime);
                        break; // Only activate one per tick
                    }
                }
			}
			
			/* verificando se novos inimigos (tipo 2) devem ser "lançados" */
			if (currentTime > spawnTimes.get("Enemy2")) {
                if (count.get("Enemy2") < 10) {
                    for (Entity entity : entities.get(5)) {
                        Enemy2 e2 = (Enemy2) e2;
                        if (e2.getState() == EntityState.INACTIVE) {
                            e2.spawn(enemy2_spawnX, -10.0);
                            enemy2_count++;
                            spawnTimes.put("Enemy2", currentTime + 120);
                            break;
                        }
                    }
                } else {
                    enemy2_count = 0;
                    enemy2_spawnX = Math.random() > 0.5 ? GameLib.WIDTH * 0.2 : GameLib.WIDTH * 0.8;
                    spawnTimes.put("Enemy2", currentTime + 3000 + (long)(Math.random() * 3000));
                }
            }

			/* verificando se novos powerups devem ser lançados */
			
			if (currentTime > spawnTimes.get("Powerup1")) {
				for (Powerup1 e1 : powerups1) {
					if (e1.getState() == EntityState.INACTIVE) {
						e1.setX(Math.random() * (GameLib.WIDTH - 20.0) + 10.0);
						e1.setY(-10.0);
						e1.setVX(0.0); // Set as needed
						e1.setVY(0.10 + Math.random() * 0.15);
						e1.setAngle((3 * Math.PI) / 2);
						e1.setRV(0.0);
						e1.spawn(currentTime+500, currentTime);
						firstPowerup1 = currentTime + 500;
						break; // Only activate one per tick
					}
				}
			}

			if (currentTime > firstPowerup2) {
				for (Powerup2 e1 : powerups2) {
					if (e1.getState() == EntityState.INACTIVE) {
						e1.setX(Math.random() * (GameLib.WIDTH - 20.0) + 10.0);
						e1.setY(-10.0);
						e1.setVX(0.0); // Set as needed
						e1.setVY(0.05 + Math.random() * 0.10);
						e1.setAngle((3 * Math.PI) / 2);
						e1.setRV(0.0);
						e1.spawn(currentTime+500, currentTime);
						firstPowerup1 = currentTime + 500;
						break; // Only activate one per tick
					}
				}
        }
    }

    public int getElementCount(String type){

        return count.get(type);

    }
}