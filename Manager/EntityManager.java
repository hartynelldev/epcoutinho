package Manager;

import Entities.Enemy;
import Entities.Entity;

import Entities.*;

import java.awt.*;
import java.util.ArrayList;
import Entities.GameElement;
import Entities.Player;
import Entities.ProjectileModels.Projectile;
import Entities.Powerup;
import java.util.List;

import Entities.PowerUps.Powerup1;
import Entities.ProjectileModels.ProjectileEnemy;
import Entities.ProjectileModels.ProjectilePlayer;
import Entities.EnemyModels.Bosses.Boss1;

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

		// colisões player - Boss1
		if ((ent1 instanceof Player && ent2 instanceof Boss) || (ent2 instanceof Player && ent1 instanceof Boss)) {
			Player player = (ent1 instanceof Player) ? (Player) ent1 : (Player) ent2;
			Boss enemy = (ent1 instanceof Boss) ? (Boss) ent1 : (Boss) ent2;

			if (dist < (player.getRadius() + (enemy.getRadius())) * 0.8) {
				player.hit(1, currentTime);
				enemy.hit(1, currentTime);
				// player.explode(currentTime);
				return true;
			}
			return false;
		}

		// colisões player - inimigos
		if ((ent1 instanceof Player && ent2 instanceof Enemy) || (ent2 instanceof Player && ent1 instanceof Enemy)) {
			Player player = (ent1 instanceof Player) ? (Player) ent1 : (Player) ent2;
			Enemy enemy = (ent1 instanceof Enemy) ? (Enemy) ent1 : (Enemy) ent2;
	
			if (dist < (player.getRadius() + enemy.getRadius()) * 0.8) {
				player.hit(1, currentTime);
				enemy.hit(1, currentTime);
				//player.explode(currentTime);
				//enemy.explode(currentTime);
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
				player.hit(1, currentTime);

				//player.explode(currentTime);
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
				enemy.hit(1, currentTime);
				//enemy.explode(currentTime);
				projec.setState(EntityState.INACTIVE);
				return true;
			}
			return false;
		}

		if ((ent1 instanceof Player && ent2 instanceof Powerup) || (ent2 instanceof Player && ent1 instanceof Powerup)) {
			Player player = (ent1 instanceof Player) ? (Player) ent1 : (Player) ent2;
			Powerup powerup = (ent1 instanceof Powerup) ? (Powerup) ent1 : (Powerup) ent2;
	
			if (dist < (player.getRadius() + powerup.getRadius()) * 0.8) {
				powerup.setState(EntityState.INACTIVE);
				powerup.runPowerUp(player, currentTime);
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
    
	public static boolean updateEntities(long delta, long currentTime,
										 Player player,
										 ArrayList<ProjectilePlayer> playerProjectiles,
										 ArrayList<Enemy> enemies,
										 Boss1 boss1,
										 ArrayList<ProjectileEnemy> enemyProjectiles,
										 ArrayList<ProjectileEnemy> enemy_ProjectilesBoss,
										 ArrayList<Powerup> powerups
										 ) {


		// Verifica colisões
		processCollisions(currentTime, player, boss1,playerProjectiles,  enemies, enemyProjectiles, enemy_ProjectilesBoss, powerups);

		//player
		boolean running = true;
		if(player.update(delta, currentTime,playerProjectiles)) running = false;

    	// Atualiza projéteis do player
		for (Projectile p : playerProjectiles) {
			p.update(delta, currentTime);
		}

		// Atualiza projéteis dos inimigos
		for (Projectile p : enemyProjectiles) {
			p.update(delta, currentTime);
		}
		for (Projectile p : enemy_ProjectilesBoss) {
			p.update(delta, currentTime);
		}

		// Atualiza inimigos
		for (Enemy e : enemies) {
			e.update(delta, player, enemyProjectiles, currentTime);
		}

		// Atualiza powerups
		for (Powerup p : powerups) {
			p.update(delta, player, currentTime);
		}


		//Atualiza Boss1
		boss1.update(delta, player, enemy_ProjectilesBoss, currentTime);

		return running;
	}

	public static void processCollisions(long currentTime, Player player, Boss1 boss1,
										 ArrayList<ProjectilePlayer> playerProjectiles,
										 ArrayList<Enemy> enemies,
										 ArrayList<ProjectileEnemy> enemy_Projectiles,
										 ArrayList<ProjectileEnemy> enemy_ProjectilesBoss,
										 ArrayList<Powerup> powerups) {

		if(player.isActive()){
			// Colisões player - projéteis (inimigo)
			for(ProjectileEnemy p : enemy_Projectiles){
				EntityManager.checkThenCollide(p, player, currentTime);
			}
			for(ProjectileEnemy p : enemy_ProjectilesBoss) {
				EntityManager.checkThenCollide(p, player, currentTime);
			}

				// Colisões player - inimigos
			for(Enemy e : enemies){
				EntityManager.checkThenCollide(e, player, currentTime);
			}
			EntityManager.checkThenCollide(boss1,player,currentTime);

			// Colisões player - powerups
			for(Powerup e : powerups){
				checkThenCollide(e, player, currentTime);
			}
		}

		// Colisões projéteis (player) - inimigos
		for(ProjectilePlayer p : playerProjectiles){
			for(Enemy e1 : enemies){
				EntityManager.checkThenCollide(p, e1, currentTime);
			}
		}
	}
}