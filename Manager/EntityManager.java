package Manager;

import Entities.Enemy;
import Entities.Entity;
import java.util.ArrayList;
import java.util.List;
import Entities.GameElement;
import Entities.Player;
import Entities.Powerup;
import Entities.PowerUps.Powerup1;
import Entities.ProjectileModels.ProjectileEnemy;
import Entities.ProjectileModels.ProjectilePlayer;
import Manager.EntityState;

public class EntityManager {

    public static double distance(GameElement e1, GameElement e2) {
        double dx = e1.getX() - e2.getX();
        double dy = e1.getY() - e2.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

	public static boolean checkThenCollide(Entity ent1, Entity ent2, long currentTime) {
	
		// checar se os elementos estão ativos
		if (!(ent1.isActive() && ent2.isActive())) {
			return false;
		}
	
		double dist = distance(ent1, ent2);
	
		// colisões player - inimigos
		if ((ent1 instanceof Player && ent2 instanceof Enemy) || (ent2 instanceof Player && ent1 instanceof Enemy)) {
			Player player = (ent1 instanceof Player) ? (Player) ent1 : (Player) ent2;
			Enemy enemy = (ent1 instanceof Enemy) ? (Enemy) ent1 : (Enemy) ent2;
	
			if (dist < enemy.getRadius()) {
				player.explode(currentTime);
				enemy.explode(currentTime);
				return true;
			}
			return false;
		}
	
		// colisões inimigos - inimigos
		if (ent1 instanceof Enemy && ent2 instanceof Enemy) {
			// se quiser, implementamos
			return false;
		}
	
		// colisões player - projeteis (inimigo)
		if ((ent1 instanceof Player && ent2 instanceof ProjectileEnemy) || (ent2 instanceof Player && ent1 instanceof ProjectileEnemy)) {
			Player player = (ent1 instanceof Player) ? (Player) ent1 : (Player) ent2;
			ProjectileEnemy projec = (ent1 instanceof ProjectileEnemy) ? (ProjectileEnemy) ent1 : (ProjectileEnemy) ent2;
	
			if (dist < (player.getRadius() + projec.getRadius()) * 0.8) {
				player.explode(currentTime);
				projec.setState(EntityState.INACTIVE);
				return true;
			}
			return false;
		}
	
		// colisões projeteis (player) - inimigos
		if ((ent1 instanceof ProjectilePlayer && ent2 instanceof Enemy) || (ent2 instanceof ProjectilePlayer && ent1 instanceof Enemy)) {
			ProjectilePlayer projec = (ent1 instanceof ProjectilePlayer) ? (ProjectilePlayer) ent1 : (ProjectilePlayer) ent2;
			Enemy enemy = (ent1 instanceof Enemy) ? (Enemy) ent1 : (Enemy) ent2;
	
			if (dist < enemy.getRadius()) {
				enemy.explode(currentTime);
				projec.setState(EntityState.INACTIVE);
				return true;
			}
			return false;
		}

		if ((ent1 instanceof Player && ent2 instanceof Powerup) || (ent2 instanceof Player && ent1 instanceof Powerup)) {
			Player player = (ent1 instanceof Player) ? (Player) ent1 : (Player) ent2;
			Powerup powerup = (ent1 instanceof Powerup) ? (Powerup) ent1 : (Powerup) ent2;
	
			if (dist < powerup.getRadius()) {
				powerup.setState(EntityState.INACTIVE);
				return true;
			}
			return false;
		}
	
		// colisões projeteis (player) - projeteis (inimigos)
		if ((ent1 instanceof ProjectilePlayer && ent2 instanceof ProjectileEnemy) || (ent2 instanceof ProjectilePlayer && ent1 instanceof ProjectileEnemy)) {
			// se quiser, implementamos
			return false;
		}
	
		return false;
	}
    
}