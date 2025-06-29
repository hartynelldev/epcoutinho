package Entities;

import Engine.GameLib;

public class LifeBar extends GameElement{

    public LifeBar(int hp){
        super(GameLib.WIDTH/2,0,hp);
        VY = 0.05;
        angle = (3 * Math.PI) / 2;
    }

    public void update(long delta, long currentTime, int hp){
        radius = hp;
        if(!handlePocionado()){
            updatePosition(delta, currentTime);
        }
        //updatePosition(delta, currentTime);

    }

    public boolean handlePocionado(){
        return getY() > 80;
    }

    public void updatePosition(long delta, long now){

        // Atualiza posição e angulo
        //(getX() + getVX() * Math.cos(getAngle()) * delta);
        setY(getY() + getVY() * Math.sin(getAngle()) * delta * (-1.0));
        //setAngle(getAngle() + getRV() * delta);
    }

    public void draw(){
        GameLib.fillRect(getX(), getY(), 5000*getRadius()/GameLib.WIDTH, 40);
    }
}
