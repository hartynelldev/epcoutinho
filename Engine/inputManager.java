package Engine;

import Entities.Player;
import utils.EntityState;
//Talvez não será usada
/*public class inputManager {
    
    public static final int ACTIVE = 1;
    public static final int INACTIVE = 0;
    
    public inputManager() {
        // Constructor
    }
    
    // Método para processar input do jogador
    public void handlePlayerInput(Player player, long currentTime, double delta) {
        
        if (player.getState() == EntityState.ACTIVE) {
            
            // Movimento
            if (GameLib.iskeyPressed(GameLib.KEY_UP)) {
                player.moveUp(delta);
            }
            if (GameLib.iskeyPressed(GameLib.KEY_DOWN)) {
                player.moveDown(delta);
            }
            if (GameLib.iskeyPressed(GameLib.KEY_LEFT)) {
                player.moveLeft(delta);
            }
            if (GameLib.iskeyPressed(GameLib.KEY_RIGHT)) {
                player.moveRight(delta);
            }
            
            // Ação de tiro
            if (GameLib.iskeyPressed(GameLib.KEY_CONTROL)) {
                player.tryShoot(currentTime);
            }
        }
    }
    
    public boolean shouldExit() {
        return GameLib.iskeyPressed(GameLib.KEY_ESCAPE);
    }
}*/