package game;

import java.awt.Color;

public abstract class GameElement {
    public static final int INACTIVE = 0;
	public static final int ACTIVE = 1;
	public static final int EXPLODING = 2;

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

	public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
    
    public void setColor(Color color){
        this.color = color;
    }

    public abstract void update(long delta);
    public abstract void draw();
}
