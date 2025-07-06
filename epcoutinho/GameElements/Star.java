package GameElements;

import Engine.GameLib;
import Engine.ConfigReaders.GameConfig;

import java.awt.*;

public class Star extends GameElement {
    
    //  ATRIBUTOS 
    
    protected double count;

    //  CONSTRUTOR 
    
    public Star(double speed, Color cor) {
        super(Math.random() * GameLib.WIDTH, Math.random() * GameLib.HEIGHT, 0);
        VX = GameConfig.getStarVX();
        VY = speed;
        color = cor;
        count = GameConfig.getStarCount();
    }

    // MÉTODOS PÚBLICOS 
    
    public void draw() {
        GameLib.fillRect(getX(), (getY() + count) % GameLib.HEIGHT, GameConfig.getStarSize(), GameConfig.getStarSize());
    }

    // GETTERS E SETTERS 
    
    public void setCount(double value) {
        count = value;
    }
    
    public double getCount() {
        return count;
    }
}

/* estrelas que formam o fundo de primeiro plano

double [] background1_X = new double[20];
double [] background1_Y = new double[20];
double background1_speed = 0.070;
double background1_count = 0.0;

/* estrelas que formam o fundo de segundo plano

double [] background2_X = new double[50];
double [] background2_Y = new double[50];
double background2_speed = 0.045;
double background2_count = 0.0;

 */

