package Manager;

import java.util.ArrayList;
import java.util.List;

import Engine.ConfigReaders.PhaseConfig;
import GameElements.Entity;
import GameElements.Entities.EnemyModels.Enemy1;
import GameElements.Entities.EnemyModels.Enemy2;
import GameElements.Entities.EnemyModels.Bosses.Boss1;
import GameElements.Entities.EnemyModels.Bosses.Boss2;
import GameElements.Entities.PowerUps.Powerup1;
import GameElements.Entities.PowerUps.Powerup2;

public class SpawnManager {
    
    private PhaseConfig phaseConfig;
    private int currentPhase;
    private long phaseStartTime;
    private List<PhaseConfig.SpawnEvent> spawnEvents;
    private int currentEventIndex;
    private boolean phaseCompleted;
    
    public SpawnManager(long startTime) {
        this.phaseConfig = new PhaseConfig();
        this.phaseStartTime = startTime;
        this.currentPhase = 0;
        this.currentEventIndex = 0;
        this.phaseCompleted = false;
        this.spawnEvents = new ArrayList<>();
        
        loadCurrentPhase();
    }
    
    private void loadCurrentPhase() {
        if (currentPhase >= phaseConfig.getNumberOfPhases()) {
            phaseCompleted = true;
            return;
        }
        
        spawnEvents = phaseConfig.loadPhaseEvents(currentPhase);
        currentEventIndex = 0;
    }
    
    public void shouldSpawn(long currentTime, List<List<? extends Entity>> entities) {
        if (phaseCompleted) return;
        
        long phaseTime = currentTime - phaseStartTime;
        
        // Verifica se há eventos para spawnar
        while (currentEventIndex < spawnEvents.size()) {
            PhaseConfig.SpawnEvent event = spawnEvents.get(currentEventIndex);
            
            if (phaseTime >= event.when) {
                spawnEntity(event, entities);
                currentEventIndex++;
            } else {
                break;
            }
        }
        
        // Verifica se a fase foi completada
        if (currentEventIndex >= spawnEvents.size()) {
            // Aguarda um tempo antes de passar para a próxima fase
            if (phaseTime > 10000) { // 10 segundos de transição
                nextPhase(currentTime);
            }
        }
    }
    
    private void spawnEntity(PhaseConfig.SpawnEvent event, List<List<? extends Entity>> entities) {
        if (event.type.equals("INIMIGO")) {
            if (event.enemyType == 1) {
                for (Entity entity : entities.get(3)) { // enemy1List
                    Enemy1 e1 = (Enemy1) entity;
                    if (e1.getState() == EntityState.INACTIVE) {
                        e1.setX(event.x);
                        e1.setY(event.y);
                        e1.spawn(System.currentTimeMillis());
                        break;
                    }
                }
            } else if (event.enemyType == 2) {
                for (Entity entity : entities.get(4)) { // enemy2List
                    Enemy2 e2 = (Enemy2) entity;
                    if (e2.getState() == EntityState.INACTIVE) {
                        e2.setX(event.x);
                        e2.setY(event.y);
                        e2.spawn(System.currentTimeMillis());
                        break;
                    }
                }
            }
        } else if (event.type.equals("CHEFE")) {
            // Boss será tratado no gameEngine
            // Aqui apenas marcamos que um boss deve aparecer
        }
    }
    
    private void nextPhase(long currentTime) {
        currentPhase++;
        if (currentPhase < phaseConfig.getNumberOfPhases()) {
            phaseStartTime = currentTime;
            loadCurrentPhase();
        } else {
            phaseCompleted = true;
        }
    }
    
    public boolean isPhaseCompleted() {
        return phaseCompleted;
    }
    
    public int getCurrentPhase() {
        return currentPhase;
    }
    
    public int getPlayerHP() {
        return phaseConfig.getPlayerHP();
    }
    
    public PhaseConfig.SpawnEvent getCurrentBossEvent() {
        if (currentEventIndex < spawnEvents.size()) {
            PhaseConfig.SpawnEvent event = spawnEvents.get(currentEventIndex);
            if (event.type.equals("CHEFE")) {
                return event;
            }
        }
        return null;
    }
    
    public void advanceBossEvent() {
        if (currentEventIndex < spawnEvents.size()) {
            PhaseConfig.SpawnEvent event = spawnEvents.get(currentEventIndex);
            if (event.type.equals("CHEFE")) {
                currentEventIndex++;
            }
        }
    }
}