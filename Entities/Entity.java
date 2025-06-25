package Entities;
import utils.*;

// Entidades que interagem no jogo
public class Entity extends Point2D{
    
    // Estado atual da entidade
    protected EntityState state = EntityState.ACTIVE;

    // MOVIMENTO

    // Velocidades
    private double vx = 0.25;
    private double vy = 0.25;

    protected double angle;     				    // ângulos ()
	protected double RV;                            // velocidades de rotação

    
    protected long now = System.currentTimeMillis();
    
    // Constructors
    public Entity() {
        super(); // Posição inicial (0,0) e raio padrão de 12.0
    }
    public Entity(double x, double y, double raio) {

        super(x, y, raio);
    }
    public Entity(double x, double y, double raio, EntityState state, double vx, double vy, double angle, double RV) {

        super(x, y, raio);
        this.state = state;
        this.vx = vx;
        this.vy = vy;
        this.angle = angle;
        this.RV = RV;
    }

    // Methods

    // Checa se duas entidades colidem
    public boolean collidesWith(Entity collider) {
        // Distancia entre os centros das entidades
        double dx = super.getX() - collider.getX();
        double dy = super.getY() - collider.getY();
        double dist = Math.sqrt(dx * dx + dy * dy);
        return dist < super.getRaio() + collider.getRaio();
    }


    // Atualiza a entidade
    public void update(long delta) {
        // Atualiza a posição com base na velocidade
        
        // Aqui poderia ser adicionado lógica para atualizar o estado da entidade
        // por exemplo, se ela está ativa, inativa ou em explosão.
    }
    // Desenha a entidade
    public void draw() {
        // Aqui poderia ser adicionado lógica para desenhar a entidade na tela
        // por exemplo, usando uma biblioteca gráfica.
    }

    // Getters e Setters

    public EntityState getState() {
        return this.state;
    }
    public void setState(EntityState state) {
        this.state = state;
    }
    public double getVx() {
        return vx;
    }
    public void setVx(double vx) {
        this.vx = vx;
    }
    public double getVy() {
        return vy;
    }
    public void setVy(double vy) {
        this.vy = vy;
    }
    public double getAngle() {
        return angle;
    }
    public void setAngle(double angle) {
        this.angle = angle;
    }
    // (RV) é a velocidade angular da entidade, que pode ser usada para girar
    public double getRV() {
        return RV;
    }
    public void setRV(double RV) {
        this.RV = RV;
    }
}