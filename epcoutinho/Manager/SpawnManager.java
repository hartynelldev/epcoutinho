package Manager;

import java.util.ArrayList;
import java.util.List;

import Engine.ConfigReaders.PhaseConfig;
import GameElements.Entity;
import GameElements.Entities.EnemyModels.Enemy1;
import GameElements.Entities.EnemyModels.Enemy2;
import GameElements.Entities.EnemyModels.Boss;
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
    private Boss currentBoss;
    private boolean bossActive;
    
    // Controle específico para Enemy2 (cobrinha)
    private double enemy2_spawnX;
    private double enemy2_spawnY;
    private int enemy2_count;
    private long nextEnemy2Spawn;
    private boolean enemy2SnakeActive;

    private boolean boss1 = false;
    private boolean boss2 = false;
    
    public SpawnManager(long startTime) {
        this.phaseConfig = new PhaseConfig();
        this.phaseStartTime = startTime;
        this.currentPhase = 0;
        this.currentEventIndex = 0;
        this.phaseCompleted = false;
        this.spawnEvents = new ArrayList<>();
        this.currentBoss = null;
        this.bossActive = false;
        
        // Inicialização do controle Enemy2
        this.enemy2_spawnX = 100.0; // Posição inicial X
        this.enemy2_spawnY = -10.0; // Posição inicial Y
        this.enemy2_count = 0;
        this.nextEnemy2Spawn = startTime;
        this.enemy2SnakeActive = false;
        
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
                spawnEntity(event, entities, currentTime);
                currentEventIndex++;
            } else {
                break;
            }
        }
        
        // Verifica se a fase foi completada
        if (currentEventIndex >= spawnEvents.size()) {
            // Só passa para a próxima fase se não há boss ativo
            if (!bossActive) {
                // Aguarda um tempo antes de passar para a próxima fase
                if (phaseTime > 1000) { // 1 segundos de transição
                    nextPhase(currentTime);
                }
            }else{
                System.out.println("Aguardando o boss ser derrotado para passar para a próxima fase.");
            }
        }else{
            // Se ainda há eventos pendentes, não passa para a próxima fase
            System.out.println("Ainda há eventos pendentes na fase " + currentPhase + ", aguardando...");
        }
        
        // Processa spawns de Enemy2 (cobrinha) independentemente dos eventos
        // Só processa se a cobrinha estiver ativa
        processEnemy2Spawn(currentTime, entities);
    }
    
    private void spawnEntity(PhaseConfig.SpawnEvent event, List<List<? extends Entity>> entities, long currentTime) {
        System.out.println("Spawning: " + event.type + " " + event.enemyType + " na fase " + currentPhase);
        
        if (event.type.equals("INIMIGO")) {
            // Inimigos podem aparecer mesmo com boss ativo

            if (event.enemyType == 1) {
                // Cria um novo Enemy1
                Enemy1 newEnemy1 = new Enemy1(event.x, event.y, currentTime, currentTime);
                newEnemy1.spawn(currentTime);
                ((ArrayList<Enemy1>) entities.get(3)).add(newEnemy1);
                System.out.println("Enemy1 spawnado na posição (" + event.x + ", " + event.y + ")");
            } else if (event.enemyType == 2) {
                // Para Enemy2, ativa a cobrinha no tempo especificado pelo evento
                enemy2_spawnX = event.x; // Usa a posição X do evento como ponto inicial
                enemy2_spawnY = event.y; // Usa a posição Y do evento como ponto inicial
                nextEnemy2Spawn = currentTime; // Começa imediatamente
                enemy2SnakeActive = true;
                enemy2_count = 0; // Reset do contador para nova cobrinha
                System.out.println("Cobrinha Enemy2 ativada na posição (" + event.x + ", " + event.y + ")");
            }
        } else if (event.type.equals("CHEFE")) {
            // Spawn de Boss
            if (bossActive) {
                System.out.println("Boss já ativo, ignorando spawn de novo boss");
                return; // Já há um boss ativo
            }
            
            if (event.enemyType == 1) {
                currentBoss = new Boss1(event.x, event.y, currentTime, currentTime, event.bossHP);
                boss1=true;
                System.out.println("Boss1 spawnado com HP: " + event.bossHP);
            } else if (event.enemyType == 2) {
                currentBoss = new Boss2(event.x, event.y, currentTime, currentTime, event.bossHP);
                boss2=true;
                System.out.println("Boss2 spawnado com HP: " + event.bossHP);
            }
            
            if (currentBoss != null) {
                bossActive = true;
            }
        } else if (event.type.equals("POWERUP")) {
            // Spawn de Powerup
            if (event.enemyType == 1) {
                // Cria um novo Powerup1
                Powerup1 newPowerup1 = new Powerup1(event.x, event.y, currentTime, currentTime);
                newPowerup1.spawn(currentTime);
                ((ArrayList<Powerup1>) entities.get(5)).add(newPowerup1);
                System.out.println("Powerup1 spawnado na posição (" + event.x + ", " + event.y + ")");
            } else if (event.enemyType == 2) {
                // Cria um novo Powerup2
                Powerup2 newPowerup2 = new Powerup2(event.x, event.y, currentTime, currentTime);
                newPowerup2.spawn(currentTime);
                ((ArrayList<Powerup2>) entities.get(6)).add(newPowerup2);
                System.out.println("Powerup2 spawnado na posição (" + event.x + ", " + event.y + ")");
            }
        }
    }
    
    private void nextPhase(long currentTime) {
        currentPhase++;
        System.out.println("Passando para fase: " + currentPhase);
        if (currentPhase < phaseConfig.getNumberOfPhases()) {
            phaseStartTime = currentTime;
            loadCurrentPhase();
            System.out.println("Fase " + currentPhase + " carregada com " + spawnEvents.size() + " eventos");
        } else {
            phaseCompleted = true;
            System.out.println("Todas as fases completadas!");
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
    
    public Boss getCurrentBoss() {
        return currentBoss;
    }
    
    public boolean isBossActive() {
        return bossActive && currentBoss != null && currentBoss.getState() == EntityState.ACTIVE;
    }
    
    public void checkBossState() {

        if (bossActive && currentBoss != null && currentBoss.getState() == EntityState.EXPLODING) {

            if(boss1) boss1 = false;
            if(boss2) boss2 = false;

            bossActive = false;
            currentBoss = null;
            
        }

    }
    
        private void processEnemy2Spawn(long currentTime, List<List<? extends Entity>> entities) {
        if (!enemy2SnakeActive) return; // Só processa se a cobrinha estiver ativa
        
        if (currentTime > nextEnemy2Spawn) {
            // Cria um novo Enemy2
            Enemy2 newEnemy2 = new Enemy2(enemy2_spawnX, enemy2_spawnY, currentTime, currentTime);
            newEnemy2.spawn(currentTime);
            
            ((ArrayList<Enemy2>) entities.get(4)).add(newEnemy2);

            enemy2_count++;

            if (enemy2_count < 10) {
                nextEnemy2Spawn = currentTime + 120;
            } else {
                // Cobrinha terminou, desativa até o próximo evento
                enemy2SnakeActive = false;
                enemy2_count = 0;
            }
        }
    }
}