package GameElements;

import Manager.EntityState;
import Config.GameConfig;
import java.awt.Color;
import java.util.ArrayList;

public abstract class GameElement {

    //  ATRIBUTOS 
    
    // Estado e propriedades básicas
    protected EntityState state = EntityState.INACTIVE;
    protected double radius;
    protected Color color;
    
    // Posição
    private double x;
    private double y;
    
    // Movimento e orientação
    protected double angle;
    protected double RV;                            // velocidades de rotação
    protected double VX;                            // velocidade no eixo x
    protected double VY;                            // velocidade no eixo y

    //  CONSTRUTOR 
    
    public GameElement(double x, double y, double radius) {
        // construtor deixará elemento INATIVO!
        this.x = x;
        this.y = y;
        this.radius = radius;
        
        // Inicializa com valores padrão da configuração
        this.VX = GameConfig.getGameElementDefaultVX();
        this.VY = GameConfig.getGameElementDefaultVY();
    }

    //  MÉTODOS PÚBLICOS 
    
    public void destroy(ArrayList<GameElement> object, int i) {
        // futuras instalações do método que vai destruir um inimigo
        object.remove(i);
    }

    public boolean isActive() {
        return state == EntityState.ACTIVE;
    }

    //  GETTERS E SETTERS 
    
    // Estado
    public EntityState getState() {
        return state;
    }
    
    public void setState(EntityState state) {
        this.state = state;
    }

    // Posição
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

    // Raio
    public double getRadius() {
        return radius;
    }
    
    public void setRadius(double radius) {
        this.radius = radius;
    }

    // Ângulo
    public double getAngle() {
        return angle;
    }
    
    public void setAngle(double angle) {
        this.angle = angle;
    }

    // Velocidades
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

    // Cor
    public void setColor(Color color) {
        this.color = color;
    }
    
    public Color getColor() {
        return color;
    }
}
