package Entities;


import utils.EntityState;

import java.awt.Color;
import java.util.ArrayList;
import Engine.GameLib;



public abstract class GameElement {

	protected EntityState state = EntityState.INACTIVE;				    // estado
	protected double radius = 12.0;					// raio (tamanho aproximado)
    private double x;
    private double y;

    protected double angle;     				    // ângulos (indicam direção do movimento)

    protected double RV;                            // velocidades de rotação
    protected double VX = 0.25;						// velocidade no eixo x
	protected double VY = 0.25;						// velocidade no eixo y


    protected long now = System.currentTimeMillis();
    protected Color color;

    public GameElement(double x, double y, double radius){
        // contrutor deixará elemento INATIVO!
        this.x = x;
        this.y = y;
        this.radius = radius;
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

    
    public void setColor(Color color){
        this.color = color;
    }
    public Color getColor(){return color;}

        public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    // getter de angulo
    public double getAngle() {
        return angle;
    }
    // setter de angulo
    public void setAngle(double angle) {
        this.angle = angle;
    }

    public double getVX() {
        return this.VX;
    }
    public double setVX(double vX) {
        this.VX = vX;
        return VX;
    }

    public double getVY() {
        return this.VY;
    }
    public double setVY(double vY) {
        this.VY = vY;
        return this.VY;
    }
    public double getRV() {
        return this.RV;
    }
    public void setRV(double rV) {
        this.RV = rV;
    }

    public abstract void draw();
}
