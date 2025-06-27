package Entities;
import Engine.GameLib;

import java.awt.*;
import java.util.ArrayList;

//ArrayList<Projectile> projectileList = new ArrayList<>(10);


//Futuras instalações do Background
public class BackGround{
    private ArrayList<Star> star1;
    private ArrayList<Star> star2;

    public BackGround(int firstBackgroundAmount, int secondBackgroundAmount){
        star1 = new ArrayList<>(0);
        star2 = new ArrayList<>(0);

        for(int i = 0; i < firstBackgroundAmount; i++){
            star1.add(new Star(0.070, Color.GRAY));
        }
        for(int i = 0; i < secondBackgroundAmount; i++){
            star2.add(new Star(0.045, Color.DARK_GRAY));
        }
        //star1 = new Star(0.070);
        //star2 = new Star(0.045);
    }
    public void draw(long delta){
        GameLib.setColor(star2.getFirst().getColor());
        star2.getFirst().count += star2.getFirst().VY * delta;
        for(int i = 0; i < star2.size(); i++){
            star2.get(i).count += star2.get(i).VY * delta;
            star2.get(i).draw(0);
        }
        GameLib.setColor(star1.getFirst().getColor());
        for(int i = 0; i < star1.size(); i++){
            star1.get(i).count += star1.get(i).VY * delta;
            star1.get(i).draw(0);
        }
    }
}
/*
		for(int i = 0; i < background1_X.length; i++){

			background1_X[i] = Math.random() * GameLib.WIDTH;
			background1_Y[i] = Math.random() * GameLib.HEIGHT;
		}

		for(int i = 0; i < background2_X.length; i++){

			background2_X[i] = Math.random() * GameLib.WIDTH;
			background2_Y[i] = Math.random() * GameLib.HEIGHT;
		}
 */
