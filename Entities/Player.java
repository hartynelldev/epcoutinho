package Entities;

import Engine.GameLib;
import utils.EntityState;

public class Player extends Entity {
    // Fields (attributes)
	double explosion_start = 0;					// instante do início da explosão
	double explosion_end = 0;					// instante do final da explosão
	long nextShot;					// instante a partir do qual pode haver um próximo tir

    long currentTime = System.currentTimeMillis();	// instante atual do sistema

    // Constructors
    public Player() {
        super(
            GameLib.WIDTH / 2,                // coordenada x inicial
            GameLib.HEIGHT * 0.90,            // coordenada y inicial
            12.0,                         // raio (tamanho aproximado do player)
            EntityState.ACTIVE,            // estado inicial
            0.25,                          // velocidade no eixo x
            0.25,                          // velocidade no eixo y
            0.0,                           // ângulo inicial
            0.0                            // velocidade de rotação inicial
        );

		this.nextShot = this.currentTime;					// instante a partir do qual pode haver um próximo tir
    }
    
    // Methods
    public void moveUp(double delta) {
        if (state == EntityState.ACTIVE) {
            double newY = getY() - delta * getVy();
            setY(newY);
        }
    }
    
    public void moveDown(double delta) {
        if (state == EntityState.ACTIVE) {
            double newY = getY() + delta * getVy();
            setY(newY);
        }
    }
    
    public void moveLeft(double delta) {
        if (state == EntityState.ACTIVE) {
            double newY = getX() - delta * getVx();
            setX(newY);
        }
    }
    
    public void moveRight(double delta) {
        if (state == EntityState.ACTIVE) {
            double newY = getX() + delta * getVx();
            setX(newY);
        }
    }

    public void tryShoot(long currentTime) {
    if (state == EntityState.ACTIVE && currentTime > nextShot) {
        // implementar lógica de tiro
        nextShot = currentTime + 100; // Cooldown de 100ms
    }
    }
}