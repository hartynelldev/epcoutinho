package GameElements;

import Engine.GameLib;
import Engine.ConfigReaders.GameConfig;

public class LifeBar extends GameElement {

    //  CONSTRUTOR 
    
    public LifeBar(int hp) {
        super(GameLib.WIDTH / 2, 0, hp/4);
        VY = GameConfig.getLifeBarVY();
        angle = GameConfig.getLifeBarAngle();
    }

    //  MÉTODOS PÚBLICOS 
    
    public void update(long delta, long currentTime, int hp) {
        radius = hp;
        if (!handlePocionado()) {
            updatePosition(delta, currentTime);
        }
    }

    public boolean handlePocionado() {
        return getY() > GameConfig.getLifeBarMaxY();
    }

    public void updatePosition(long delta, long now) {
        // Atualiza posição e angulo
        // (getX() + getVX() * Math.cos(getAngle()) * delta);
        setY(getY() + getVY() * Math.sin(getAngle()) * delta * (-1.0));
        // setAngle(getAngle() + getRV() * delta);
    }

    public void draw() {
        GameLib.fillRect(getX(), getY(), GameConfig.getLifeBarWidth() * getRadius() / GameLib.WIDTH, GameConfig.getLifeBarHeight());
    }
}
