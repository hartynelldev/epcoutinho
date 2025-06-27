package Entities;
import Engine.GameLib;


import utils.EntityState;
import utils.Point2D;

import java.awt.Color;
import java.util.ArrayList;
import Engine.GameLib;



public abstract class GameElement extends Point2D {

	protected EntityState state = EntityState.INACTIVE;				    // estado
	protected double VX = 0.25;						// velocidade no eixo x
	protected double VY = 0.25;						// velocidade no eixo y
	protected double radius = 12.0;					// raio (tamanho aproximado)

    protected double angle;     				    // ângulos (indicam direção do movimento)
	protected double RV;                            // velocidades de rotação

    protected long now = System.currentTimeMillis();
    protected Color color;

    public GameElement(double x, double y, double radius){
        // contrutor deixará elemento INATIVO!
        super(x,y,radius);
    }


    public void destroy(ArrayList<GameElement> object, int i){
        //futuras isntalaões do metodo que vai destruir um inimigo
        object.remove(i);
    }

    public boolean isActive() {
        if(state == EntityState.ACTIVE) {
            return true;
        }
        return false;
    }

	public EntityState getState() {
        return state;
    }
        public void setState(EntityState state) {
        this.state = state;
    }

    public double getRadius(){return radius;}
    public  double getAngle(){return angle;}
    
    public void setColor(Color color){
        this.color = color;
    }
    public Color getColor(){return color;}

    public abstract void draw();
}
