package Engine.ConfigReaders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PhaseConfig {
    
    private int playerHP;
    private int numberOfPhases;
    private List<String> phaseFiles;
    private static boolean initialized = false;
    
    public static class SpawnEvent {
        public String type;
        public int enemyType;
        public int bossHP;
        public long when;
        public double x;
        public double y;
        
        public SpawnEvent(String type, int enemyType, int bossHP, long when, double x, double y) {
            this.type = type;
            this.enemyType = enemyType;
            this.bossHP = bossHP;
            this.when = when;
            this.x = x;
            this.y = y;
        }
    }
    
    public PhaseConfig() {
        this.phaseFiles = new ArrayList<>();
        loadPhaseConfig();
    }
    
    private void loadPhaseConfig() {
        if (initialized) return;
        
        try (BufferedReader reader = new BufferedReader(new FileReader("epcoutinho/Config/phaseConfig.txt"))) {
            this.playerHP = Integer.parseInt(reader.readLine().trim());
            this.numberOfPhases = Integer.parseInt(reader.readLine().trim());
            
            for (int i = 0; i < numberOfPhases; i++) {
                this.phaseFiles.add(reader.readLine().trim());
            }
            initialized = true;
        } catch (IOException e) {
            System.err.println("Erro ao carregar configuração de fases: " + e.getMessage());
            // Configuração padrão em caso de erro
            this.playerHP = 10;
            this.numberOfPhases = 2;
            this.phaseFiles.add("epcoutinho/Config/levels/fase1.txt");
            this.phaseFiles.add("epcoutinho/Config/levels/fase2.txt");
            initialized = true;
        }
    }
    
    public List<SpawnEvent> loadPhaseEvents(int phaseIndex) {
        if (phaseIndex >= phaseFiles.size()) {
            return new ArrayList<>();
        }
        
        List<SpawnEvent> spawnEvents = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(phaseFiles.get(phaseIndex)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;   // ignora linhas vazias e comentários com '#'
                
                String[] parts = line.split(" ");
                if (parts.length >= 5) {
                    String type = parts[0];
                    int enemyType = Integer.parseInt(parts[1]);
                    
                    if (type.equals("INIMIGO")) {
                        long when = Long.parseLong(parts[2]);
                        double x = Double.parseDouble(parts[3]);
                        double y = Double.parseDouble(parts[4]);
                        spawnEvents.add(new SpawnEvent("INIMIGO", enemyType, 0, when, x, y));
                    } else if (type.equals("CHEFE")) {
                        int bossHP = Integer.parseInt(parts[2]);
                        long when = Long.parseLong(parts[3]);
                        double x = Double.parseDouble(parts[4]);
                        double y = Double.parseDouble(parts[5]);
                        spawnEvents.add(new SpawnEvent("CHEFE", enemyType, bossHP, when, x, y));
                    } else if (type.equals("POWERUP")) {
                        long when = Long.parseLong(parts[2]);
                        double x = Double.parseDouble(parts[3]);
                        double y = Double.parseDouble(parts[4]);
                        spawnEvents.add(new SpawnEvent("POWERUP", enemyType, 0, when, x, y));
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar fase " + (phaseIndex + 1) + ": " + e.getMessage());
        }
        
        return spawnEvents;
    }
    
    public int getPlayerHP() {
        return playerHP;
    }
    
    public int getNumberOfPhases() {
        return numberOfPhases;
    }
    
    public List<String> getPhaseFiles() {
        return new ArrayList<>(phaseFiles);
    }
}
