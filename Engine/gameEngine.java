package Engine;

import java.awt.Color;

import Entities.*;
import Entities.EnemyModels.*;
import Entities.PowerUps.Powerup1;
import Entities.PowerUps.Powerup2;
import Entities.EnemyModels.Bosses.Boss1;
import Entities.ProjectileModels.ProjectileEnemy;
import Entities.ProjectileModels.ProjectilePlayer;
import Manager.EntityState;

import static Engine.SceneRenderer.render;
import Manager.EntityManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class gameEngine {

	// Variáveis do player e entidades do jogo (agora como atributos da classe)
    private Player player;
    private ArrayList<ProjectilePlayer> playerProjectiles;
    private ArrayList<Enemy1> enemy1List;
    private ArrayList<Enemy2> enemy2List;
    private ArrayList<ProjectileEnemy> enemy_Projectiles;
	private ArrayList<Powerup1> powerups1;
	private ArrayList<Powerup2> powerups2;
	private ArrayList<ProjectileEnemy> enemy_ProjectilesBoss;
    private BackGround backGround;
	//teste de boss
	private Boss1 boss1;

    // Variáveis auxiliares
    private long firstEnemy1;
    private long firstEnemy2;
	private long firstPowerup1;
	private long firstPowerup2;
    private double enemy2_spawnX;
    private int enemy2_count;

	/* Espera, sem fazer nada, até que o instante de tempo atual seja */
	/* maior ou igual ao instante especificado no parâmetro "time.    */
    public static void busyWait(long time){
		
		while(System.currentTimeMillis() < time) Thread.yield();
	}

	private void processCollisions(long currentTime) {
		if(player.isActive()){
			// Colisões player - projéteis (inimigo)
			for(ProjectileEnemy p : enemy_Projectiles){
				EntityManager.checkThenCollide(p, player, currentTime);
			}
			// Colisões player - inimigos
			for(Enemy e : enemy1List){
				EntityManager.checkThenCollide(e, player, currentTime);
			}
			for(Enemy e : enemy2List){
				EntityManager.checkThenCollide(e, player, currentTime);
			}
			// Colisões player - powerups
			for(Powerup1 e : powerups1){
				EntityManager.checkThenCollide(e, player, currentTime);
			}
			for(Powerup2 e : powerups2){
				EntityManager.checkThenCollide(e, player, currentTime);
			}
		}
		// Colisões projéteis (player) - inimigos
		for(ProjectilePlayer p : playerProjectiles){
			for(Enemy e1 : enemy1List){
				EntityManager.checkThenCollide(p, e1, currentTime);
			}
			for(Enemy e2: enemy2List){
				EntityManager.checkThenCollide(p, e2, currentTime);
			}
		}
	}

	private void updateEntities(long delta, long currentTime) {
    // Atualiza projéteis do player
		for (ProjectilePlayer p : playerProjectiles) {
			p.update(delta, currentTime);
		}

		// Atualiza projéteis dos inimigos
		for (ProjectileEnemy p : enemy_Projectiles) {
			p.update(delta, currentTime);
		}

		for (ProjectileEnemy p : enemy_ProjectilesBoss) {
			p.update(delta, currentTime);
		}

		// Atualiza inimigos tipo 1
		for (Enemy1 e1 : enemy1List) {
			e1.update(delta, player, enemy_Projectiles, currentTime);
		}

		// Atualiza inimigos tipo 2
		for (Enemy2 e2 : enemy2List) {
			e2.update(delta, player, enemy_Projectiles, currentTime);
		}

		// Atualiza powerups tipo 1
		for (Powerup1 p1 : powerups1) {
			p1.update(delta, player, currentTime);
		}
		// Atualiza powerups tipo 2
		for (Powerup2 p2 : powerups2) {
			p2.update(delta, player, currentTime);
		}

		//Atualiza Boss1
		boss1.update(delta, player, enemy_ProjectilesBoss, currentTime);
	}

	void inicializate(){

		long startTime = System.currentTimeMillis(); 

        /* variáveis do player */
        player = new Player();

        /* variáveis dos projéteis disparados pelo player */
        playerProjectiles = new ArrayList<>(10);

        /* variáveis dos inimigos tipo 1 */
        enemy1List = new ArrayList<>(10);
        firstEnemy1 = startTime + 2000; // Use o tempo atual aqui --Lembre que no arquivo os tempos de spaw são fixos  (definido em cada entrada)

        /* variáveis dos inimigos tipo 2 */
        enemy2List = new ArrayList<>(10);
        firstEnemy2 = startTime + 7000; //--Lembre que no arquivo os tempos de spaw são fixos
        enemy2_spawnX = GameLib.WIDTH * 0.20;
        enemy2_count = 5;

        /* variáveis dos projéteis lançados pelos inimigos */
        enemy_Projectiles = new ArrayList<>(200);

		/* variáveis dos powerups */
		powerups1 = new ArrayList<>(2);
		firstPowerup1 = startTime + 4000;
		powerups2 = new ArrayList<>(2);
		firstPowerup2 = startTime + 8000;
		enemy_ProjectilesBoss = new ArrayList<>(200);

        /* estrelas que formam o fundo de primeiro plano */
        backGround = new BackGround(20,50);

		/*Tesste de boss*/
		boss1 = new Boss1(GameLib.WIDTH/2,0,firstEnemy1, startTime, 50);

        // Inicializações
        for(int i = 0; i < 10; i++) playerProjectiles.add(new ProjectilePlayer(0,0,0,0,0));
        for(int i = 0; i < 200; i++) enemy_Projectiles.add(new ProjectileEnemy(0,0,0,0,0));
		for(int i = 0; i < 200; i++) enemy_ProjectilesBoss.add(new ProjectileEnemy(0,0,0,0,0));
        for(int i = 0; i < 10; i++) enemy1List.add(new Enemy1(0,0,firstEnemy1, startTime));
        for(int i = 0; i < 10; i++) enemy2List.add(new Enemy2(enemy2_spawnX,0,firstEnemy2, startTime));
        for(int i = 0; i < 2; i++) powerups1.add(new Powerup1((Math.random()*GameLib.WIDTH-20)+10,0,firstPowerup1, startTime));
        for(int i = 0; i < 2; i++) powerups2.add(new Powerup2((Math.random()*GameLib.WIDTH-20)+10,0,firstPowerup1, startTime));
    }


    public void run() {

		/* Indica que o jogo está em execução */

		boolean running = true;

		/* variáveis usadas no controle de tempo efetuado no main loop */
		
		long delta;
		// as definições utilizam o currentTime, mas apenas quando inicia o jogo. Para não confundir, troquei o nome inicial

		inicializate();

		List<List<? extends Entity>> entities = Arrays.asList(
            playerProjectiles,
            enemy_Projectiles,
			enemy_ProjectilesBoss,
            enemy1List,
            enemy2List,
			powerups1,
			powerups2
        );

						
		/* iniciado interface gráfica */
		
		GameLib.initGraphics();
		//GameLib.initGraphics_SAFE_MODE();  // chame esta versão do método caso nada seja desenhado na janela do jogo.
		
		/*************************************************************************************************/
		/*                                                                                               */
		/* Main loop do jogo                                                                             */
		/* -----------------                                                                             */
		/*                                                                                               */
		/* O main loop do jogo executa as seguintes operações:                                           */
		/*                                                                                               */
		/* 1) Verifica se há colisões e atualiza estados dos elementos conforme a necessidade.           */
		/*                                                                                               */
		/* 2) Atualiza estados dos elementos baseados no tempo que correu entre a última atualização     */
		/*    e o timestamp atual: posição e orientação, execução de disparos de projéteis, etc.         */
		/*                                                                                               */
		/* 3) Processa entrada do usuário (teclado) e atualiza estados do player conforme a necessidade. */
		/*                                                                                               */
		/* 4) Desenha a cena, a partir dos estados dos elementos.                                        */
		/*                                                                                               */
		/* 5) Espera um período de tempo (de modo que delta seja aproximadamente sempre constante).      */
		/*                                                                                               */
		/*************************************************************************************************/
		
		long currentTime = System.currentTimeMillis(); // o timestamp atual, usado para calcular o delta

		while(running){
		
			/* Usada para atualizar o estado dos elementos do jogo    */
			/* (player, projéteis e inimigos) "delta" indica quantos  */
			/* ms se passaram desde a última atualização.             */
			delta = System.currentTimeMillis() - currentTime;
			
			/* Já a variável "currentTime" nos dá o timestamp atual.  */
			currentTime = System.currentTimeMillis();
			
			
			//
				
			/***************************/
			/* Atualizações de estados */
			/***************************/
			
			processCollisions(currentTime);

			updateEntities(delta, currentTime);
			
			/* verificando se novos inimigos (tipo 1) devem ser "lançados" */
			//spaw() faz isso
			if (currentTime > firstEnemy1) {
				for (Enemy1 e1 : enemy1List) {
					if (e1.getState() == EntityState.INACTIVE) {
						e1.setX(Math.random() * (GameLib.WIDTH - 20.0) + 10.0);
						e1.setY(-10.0);
						e1.setVX(0.0); // Set as needed
						e1.setVY(0.20 + Math.random() * 0.15);
						e1.setAngle((3 * Math.PI) / 2);
						e1.setRV(0.0);
						e1.setState(EntityState.ACTIVE);
						e1.setNextShot(currentTime + 500);
						firstEnemy1 = currentTime + 500;
						break; // Only activate one per tick
					}
				}
			}
			
			/* verificando se novos inimigos (tipo 2) devem ser "lançados" */
			if (currentTime > firstEnemy2) {
				// Procura um inimigo inativo na lista
				for (Enemy2 e2 : enemy2List) {
					if (e2.getState() == EntityState.INACTIVE) {
						e2.setX(enemy2_spawnX);
						e2.setY(-10.0);
						e2.setVY(0.42);
						e2.setAngle((3 * Math.PI) / 2);
						e2.setRV(0.0);
						e2.setState(EntityState.ACTIVE);
						e2.setExplosionEnd(0); // ajuste se necessário

						enemy2_count++;

						if (enemy2_count < 10) {
							firstEnemy2 = currentTime + 120;
						} else {
							enemy2_count = 0;
							enemy2_spawnX = Math.random() > 0.5 ? GameLib.WIDTH * 0.2 : GameLib.WIDTH * 0.8;
							firstEnemy2 = (long) (currentTime + 3000 + Math.random() * 3000);
						}
						break; // só ativa um por vez
					}
				}
			}

			/* verificando se novos powerups devem ser lançados */
			
			if (currentTime > firstPowerup1) {
				for (Powerup1 e1 : powerups1) {
					if (e1.getState() == EntityState.INACTIVE) {
						e1.setX(Math.random() * (GameLib.WIDTH - 20.0) + 10.0);
						e1.setY(-10.0);
						e1.setVX(0.0); // Set as needed
						e1.setVY(0.10 + Math.random() * 0.15);
						e1.setAngle((3 * Math.PI) / 2);
						e1.setRV(0.0);
						e1.spawn(currentTime+500, currentTime);
						firstPowerup1 = currentTime + 500;
						break; // Only activate one per tick
					}
				}
			}

			if (currentTime > firstPowerup2) {
				for (Powerup2 e1 : powerups2) {
					if (e1.getState() == EntityState.INACTIVE) {
						e1.setX(Math.random() * (GameLib.WIDTH - 20.0) + 10.0);
						e1.setY(-10.0);
						e1.setVX(0.0); // Set as needed
						e1.setVY(0.05 + Math.random() * 0.10);
						e1.setAngle((3 * Math.PI) / 2);
						e1.setRV(0.0);
						e1.spawn(currentTime+500, currentTime);
						firstPowerup1 = currentTime + 500;
						break; // Only activate one per tick
					}
				}
			}
	
			/********************************************/
			/* Verificando entrada do usuário (teclado) */
			/********************************************/
			if(player.update(delta, currentTime,playerProjectiles)) running = false;


			/*******************/
			/* Desenho da cena */
			/*******************/
			render(
                backGround,
                player,
				boss1,
                entities,
                currentTime,
                delta
            );
			
			/* chamada a display() da classe GameLib atualiza o desenho exibido pela interface do jogo. */
			GameLib.display();
			
			/* faz uma pausa de modo que cada execução do laço do main loop demore aproximadamente 3 ms. */
			busyWait(currentTime + 3);
		}
		
		System.exit(0);
	
    }
}