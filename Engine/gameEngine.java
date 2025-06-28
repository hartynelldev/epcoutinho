package Engine;

import java.awt.Color;

import Entities.*;
import Entities.EnemyModels.*;
import utils.EntityState;

import java.util.ArrayList;

public class gameEngine {

    public static final int INACTIVE = 0;
	public static final int ACTIVE = 1;
	public static final int EXPLODING = 2;

	// Variáveis do player e entidades do jogo (agora como atributos da classe)
    private Player player;
    private ArrayList<ProjectilePlayer> playerProjectiles;
    private ArrayList<Enemy1> enemy1List;
    private ArrayList<Enemy2> enemy2List;
    private ArrayList<ProjectileEnemy> enemy_Projectiles;
    private BackGround backGround;

    // Variáveis auxiliares
    private long firstEnemy1;
    private long firstEnemy2;
    private double enemy2_spawnX;
    private int enemy2_count;

	/* Espera, sem fazer nada, até que o instante de tempo atual seja */
	/* maior ou igual ao instante especificado no parâmetro "time.    */
    public static void busyWait(long time){
		
		while(System.currentTimeMillis() < time) Thread.yield();
	}


	// retorna distancia entre dois elementos
    private double distance(GameElement e1, GameElement e2) {
        double dx = e1.getX() - e2.getX();
        double dy = e1.getY() - e2.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

	private boolean checkThenCollide(Entity ent1, Entity ent2, long currentTime) {

		// checar se os elementos estão ativos
		if(!(ent1.isActive() && ent2.isActive())) {
			return false;
		}

		// pegar distancia
		double dist = distance(ent1, ent2);

		/* colisões player - projeteis (inimigo). Checa se o projetil existe e se há colisões, depois explode */
		if(ent2 instanceof Player && ent1 instanceof ProjectileEnemy) {
			Player player = (Player) ent2;
			ProjectileEnemy projec = (ProjectileEnemy) ent1;

			if (dist < (player.getRadius() + projec.getRadius()) * 0.8) {
				player.explode(currentTime);
				projec.setState(EntityState.INACTIVE); // projétil é inativado após a colisão instantaneamente
				return true;
			}
		}

		/* colisões player - inimigos. */
		if(ent2 instanceof Player && ent1 instanceof Enemy) {
			Player player = (Player) ent2;
			Enemy enemy = (Enemy) ent1;

			if (dist < enemy.getRadius()) {
				player.explode(currentTime);
				enemy.explode(currentTime);
				return true;
			}
		}

		/* colisões projeteis (player) - inimigos */
		if(ent1 instanceof ProjectilePlayer && ent2 instanceof Enemy) {
			ProjectilePlayer projec = (ProjectilePlayer) ent1;
			Enemy enemy = (Enemy) ent2;

			if(dist < enemy.getRadius()) {
				enemy.explode(currentTime);
				projec.setState(EntityState.INACTIVE); // projétil é inativado após a colisão instantaneamente
				return true;
			}
		}

		return false;
	}


	private void processCollisions(long currentTime) {
		if(player.isActive()){
			// Colisões player - projéteis (inimigo)
			for(ProjectileEnemy p : enemy_Projectiles){
				checkThenCollide(p, player, currentTime);
			}
			// Colisões player - inimigos
			for(Enemy e : enemy1List){
				checkThenCollide(e, player, currentTime);
			}
			for(Enemy e : enemy2List){
				checkThenCollide(e, player, currentTime);
			}
		}
		// Colisões projéteis (player) - inimigos
		for(ProjectilePlayer p : playerProjectiles){
			for(Enemy e1 : enemy1List){
				checkThenCollide(p, e1, currentTime);
			}
			for(Enemy e2: enemy2List){
				checkThenCollide(p, e2, currentTime);
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

		// Atualiza inimigos tipo 1
		for (Enemy1 e1 : enemy1List) {
			e1.update(delta, player, enemy_Projectiles, currentTime);
		}

		// Atualiza inimigos tipo 2
		for (Enemy2 e2 : enemy2List) {
			e2.update(delta, player, enemy_Projectiles, currentTime);
		}
	}

	void inicializate(){

		long startTime = System.currentTimeMillis(); 

        /* variáveis do player */
        player = new Player();

        /* variáveis dos projéteis disparados pelo player */
        playerProjectiles = new ArrayList<>(10);

        /* variáveis dos inimigos tipo 1 */
        enemy1List = new ArrayList<>(10);
        firstEnemy1 = startTime + 2000; // Use o tempo atual aqui

        /* variáveis dos inimigos tipo 2 */
        enemy2List = new ArrayList<>(10);
        firstEnemy2 = startTime + 7000;
        enemy2_spawnX = GameLib.WIDTH * 0.20;
        enemy2_count = 5;

        /* variáveis dos projéteis lançados pelos inimigos */
        enemy_Projectiles = new ArrayList<>(200);

        /* estrelas que formam o fundo de primeiro plano */
        backGround = new BackGround(20,50);

        // Inicializações
        for(int i = 0; i < 10; i++) playerProjectiles.add(new ProjectilePlayer(0,0,0,0,0));
        for(int i = 0; i < 200; i++) enemy_Projectiles.add(new ProjectileEnemy(0,0,0,0,0));
        for(int i = 0; i < 10; i++) enemy1List.add(new Enemy1(0,0,firstEnemy1, startTime));
        for(int i = 0; i < 10; i++) enemy2List.add(new Enemy2(enemy2_spawnX,0,firstEnemy2, startTime));
    }


    public void run() {

		/* Indica que o jogo está em execução */

		boolean running = true;

		/* variáveis usadas no controle de tempo efetuado no main loop */
		
		long delta;
		// as definições utilizam o currentTime, mas apenas quando inicia o jogo. Para não confundir, troquei o nome inicial

		inicializate();
						
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
			
			// processar possiveis colisoes
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
			
			/* Verificando se a explosão do player já acabou.         */
			/* Ao final da explosão, o player volta a ser controlável */
			
			/********************************************/
			/* Verificando entrada do usuário (teclado) */
			/********************************************/
			
			player.update(delta, currentTime,playerProjectiles);

			// SAIR DO JOGO :3 
			if(GameLib.iskeyPressed(GameLib.KEY_ESCAPE)) running = false;

			/* Verificando se coordenadas do player ainda estão dentro */
			/* da tela de jogo após processar entrada do usuário.      */

			if(player.getX() < 0.0) player.setX(0.0);
			if(player.getX() >= GameLib.WIDTH) player.setX(GameLib.WIDTH - 1);
			if(player.getY() < 25.0) player.setY(25.0);
			if(player.getY() >= GameLib.HEIGHT) player.setY(GameLib.HEIGHT - 1);

			/*******************/
			/* Desenho da cena */
			/*******************/
			backGround.draw(delta);

			/* desenhando player */
			player.draw(currentTime);

			// Desenhar projéteis do player
			for(ProjectilePlayer proj : playerProjectiles){
				proj.draw();
			}

			// Desenhar projéteis dos inimigos
			for(ProjectileEnemy proj : enemy_Projectiles){
				proj.draw();
			}

			// Desenhar inimigos tipo 1
			for(Enemy1 e1 : enemy1List){
				e1.draw(currentTime);
			}

			// Desenhar inimigos tipo 2
			for(Enemy2 e2 : enemy2List){
				if(e2.getState() == EntityState.EXPLODING){
					double alpha = (currentTime - e2.getExplosionStart()) / (double)(e2.getExplosionEnd() - e2.getExplosionStart());
					GameLib.drawExplosion(e2.getX(), e2.getY(), alpha);
				}
				if(e2.getState() == EntityState.ACTIVE){
					GameLib.setColor(Color.MAGENTA);
					GameLib.drawDiamond(e2.getX(), e2.getY(), e2.getRadius());
				}
			}
			
			/* chamada a display() da classe GameLib atualiza o desenho exibido pela interface do jogo. */
			
			GameLib.display();
			
			/* faz uma pausa de modo que cada execução do laço do main loop demore aproximadamente 3 ms. */
			
			busyWait(currentTime + 3);
		}
		
		System.exit(0);
	
    }
}