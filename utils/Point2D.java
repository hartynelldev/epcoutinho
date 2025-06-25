package utils;
import java.awt.Color;

// Elementos do jogo (não se preocupar com interações)
public abstract class Point2D {
    // Coordenadas x e y
    private double x = 0.0;
    private double y = 0.0;

    // Raio do ponto
    private double raio = 12.0; // raio padrão, tamanho apropriado

    // Cor
    protected Color color;
    
    // Constructors
    public Point2D(double x, double y, double raio) {
        this.x = x;
        this.y = y;
        this.raio = raio;
    }
    public Point2D(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public Point2D() {
    }

    // Methods
    public abstract void update(long delta);
    public abstract void draw();

    // Getters e Setters

    public double getX() {
        return x;
    }

    protected void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    protected void setY(double y) {
        this.y = y;
    }

    public double getRaio() {
        return raio;
    }

    protected void setRaio(double raio) {
        this.raio = raio;
    }
}