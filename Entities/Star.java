package Entities;
import Engine.GameLib;

import java.awt.*;


public class Star extends GameElement{
    protected double count = 0.0;

    public Star(double speed, Color cor ){
        super(Math.random() * GameLib.WIDTH,Math.random() * GameLib.HEIGHT, 0);
        VX = 0;
        VY = speed;
        color = cor;
    }

    public void draw(){
        GameLib.fillRect(getX(), (getY() + count) % GameLib.HEIGHT, 2, 2);
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

