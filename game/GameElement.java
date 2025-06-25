package game;

import game.gameObjects.entities.Enemy;

import java.awt.Color;
import java.util.ArrayList;


public abstract class GameElement {
    public static final int INACTIVE = 0;
	public static final int ACTIVE = 1;
	public static final int EXPLODING = 2;
    public static final int DESTROY = 3; //define se um objeto deve ser destruido

	protected int state = ACTIVE;				    // estado
	protected double X = 0;							// coordenada x
	protected double Y = 0;							// coordenada y
	protected double VX = 0.25;						// velocidade no eixo x
	protected double VY = 0.25;						// velocidade no eixo y
	protected double radius = 12.0;					// raio (tamanho aproximado)

    protected double angle;     				    // ângulos (indicam direção do movimento)
	protected double RV;                            // velocidades de rotação

    protected long now = System.currentTimeMillis();
    protected Color color;

    public GameElement(double x, double y, double radius){
        X = x;
        Y = y;
        this.radius = radius;
    }

	public boolean collidesWith(GameElement collider) {
        double dx = this.X - collider.X;
        double dy = this.Y - collider.Y;
        double dist = Math.sqrt(dx * dx + dy * dy);
        return dist < this.radius + collider.radius;
    }

    public void destroy(ArrayList<GameElement> object, int i){
        //futuras isntalaões do metodo que vai destruir um inimigo
        object.remove(i);
    }

	public int getState() {
        return state;
    }
    public double getY(){return Y; }
    public double getX(){return X; }
    public double getRadius(){return radius;}
    public  double getAngle(){return angle;}

    public void setState(int state) {
        this.state = state;
    }
    
    public void setColor(Color color){
        this.color = color;
    }

    public abstract void update(long delta);
    public abstract void draw();
}
