package Entities;
import Engine.GameLib;
import java.awt.Color;
import Manager.EntityState;

// Entidades que interagem no jogo
public abstract class Powerup extends Entity {
    protected Color initialColor;
    protected Color finalColor;
    protected long nextSpawn;

    protected int colorSteps;
    protected int colorGradientTick = 0;
    protected int currentColorStep;
    protected boolean forwardColorSteps = true;

    protected long duration = 500;

    public Powerup(double x, double y, long nextSpawn, Color initialColor, Color finalColor, int colorSteps) {
        super(x,y,15);
        if (colorSteps < 2) {
            throw new IllegalArgumentException("There must be at least 2 color Steps");
        }
        this.initialColor = initialColor;
        this.finalColor = finalColor;
        this.nextSpawn = nextSpawn;
        this.colorSteps = colorSteps;
        this.currentColorStep = 0;
        VX = 0;
        VY = 0.10 + Math.random() * 0.15;
        angle = (3 * Math.PI) / 2;
        RV = 0;
    }

    protected Color getNextColor(){
        float t = currentColorStep / (float)(colorSteps - 1);
        
        int r = (int)(initialColor.getRed() + t * (finalColor.getRed() - initialColor.getRed()));
        int g = (int)(initialColor.getGreen() + t * (finalColor.getGreen() - initialColor.getGreen()));
        int b = (int)(initialColor.getBlue() + t * (finalColor.getBlue() - initialColor.getBlue()));

        Color result = new Color(r, g, b);
        if(colorGradientTick == 10){
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

    public void spawn(long startTime, long now){
        if(startTime > this.nextSpawn){
            setState(EntityState.ACTIVE);
        }
    }

    public final void runPowerUp(Player player, long now){
        player.setPowerUpDuration(now, powerUpFunctionality(player));
    }

    // Returns its duration
    protected abstract long powerUpFunctionality(Player player);
}