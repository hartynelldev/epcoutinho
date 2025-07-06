package GameElements.Entities;

import java.awt.Color;

import Engine.ConfigReaders.GameConfig;
import GameElements.Entity;
import Manager.EntityState;

// Entidades que interagem no jogo
public abstract class Powerup extends Entity {
    
    //  ATRIBUTOS 
    
    // Cores e efeitos visuais
    protected Color initialColor;
    protected Color finalColor;
    protected int colorSteps;
    protected int colorGradientTick;
    protected int currentColorStep;
    protected boolean forwardColorSteps = true;
    
    // Tempo e spawn
    protected long nextSpawn;
    protected long duration;

    //  CONSTRUTOR 
    
    public Powerup(double x, double y, long nextSpawn, Color initialColor, Color finalColor, int colorSteps) {
        super(x, y, GameConfig.getPowerupDefaultRadius());
        if (colorSteps < 2) {
            throw new IllegalArgumentException("There must be at least 2 color Steps");
        }
        this.initialColor = initialColor;
        this.finalColor = finalColor;
        this.nextSpawn = nextSpawn;
        this.colorSteps = colorSteps;
        this.currentColorStep = 0;
        this.colorGradientTick = GameConfig.getPowerupColorGradientTick();
        this.duration = GameConfig.getPowerupDefaultDuration();
        VX = GameConfig.getPowerupDefaultVX();
        VY = GameConfig.getPowerupDefaultVY() + Math.random() * GameConfig.getPowerupVYRandom();
        angle = GameConfig.getPowerupAngle();
        RV = GameConfig.getPowerupRV();
    }

    //  MÉTODOS PÚBLICOS 
    
    public void spawn(long startTime, long now) {
        if (startTime > this.nextSpawn) {
            setState(EntityState.ACTIVE);
        }
    }

    public final void runPowerUp(Player player, long now) {
        player.setPowerUpDuration(now, powerUpFunctionality(player));
    }

    //  MÉTODOS ABSTRATOS 
    
    abstract public void update(long delta, Player player, long currentTime);

    // Returns its duration
    protected abstract long powerUpFunctionality(Player player);

    //  MÉTODOS PROTEGIDOS 
    
    protected Color getNextColor() {
        float t = currentColorStep / (float) (colorSteps - 1);
        
        int r = (int) (initialColor.getRed() + t * (finalColor.getRed() - initialColor.getRed()));
        int g = (int) (initialColor.getGreen() + t * (finalColor.getGreen() - initialColor.getGreen()));
        int b = (int) (initialColor.getBlue() + t * (finalColor.getBlue() - initialColor.getBlue()));

        Color result = new Color(r, g, b);
        if (colorGradientTick == GameConfig.getPowerupColorGradientTick()) {
            colorGradientTick = 0;
            if (forwardColorSteps) {
                currentColorStep++;
                if (currentColorStep >= colorSteps - 1) {
                    forwardColorSteps = false;
                }
            } else {
                currentColorStep--;
                if (currentColorStep <= 0) {
                    forwardColorSteps = true;
                }
            }
        }
        colorGradientTick++;
        return result;
    }
}