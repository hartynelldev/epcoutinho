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
    private ArrayList<Projectile> playerProjectiles;
    private ArrayList<Enemy1> enemy1List;
    private ArrayList<Enemy2> enemy2List;
    private ArrayList<Projectile> enemy_Projectiles;
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

    /* Encontra e devolve o primeiro índice do  */
	/* array referente a uma posição "inativa". */
    public static int findFreeIndex(int [] stateArray){
		
		int i;
		
		for(i = 0; i < stateArray.length; i++){
			
			if(stateArray[i] == INACTIVE) break;
		}
		
		return i;
	}

    /* Encontra e devolve o conjunto de índices (a quantidade */
	/* de índices é defnida através do parâmetro "amount") do */
	/* array referente a posições "inativas".                 */ 
    public static int [] findFreeIndex(int [] stateArray, int amount){

		int i, k;
		int [] freeArray = new int[amount];

		for(i = 0; i < freeArray.length; i++) freeArray[i] = stateArray.length; 
		
		for(i = 0, k = 0; i < stateArray.length && k < amount; i++){
				
			if(stateArray[i] == INACTIVE) { 
				
				freeArray[k] = i; 
				k++;
			}
		}
		
		return freeArray;
	}

	// retorna distancia entre dois elementos
    private double distance(GameElement e1, GameElement e2) {
        double dx = e1.getX() - e2.getX();
        double dy = e1.getY() - e2.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

	private boolean checkThenCollide(Entity proj, Entity ent, long currentTime) {

		// checar se os elementos estão ativos
		if(!(proj.isActive() && ent.isActive())) {
			return false;
		}

		// pegar distancia
		double dist = distance(proj, ent);

		/* colisões player - projeteis (inimigo). Checa se o projetil existe e se há colisões, depois explode */
		if(ent instanceof Player && proj instanceof Projectile) {
			Player player = (Player) ent;
			Projectile projec = (Projectile) proj;

			if (dist < (player.getRadius() + projec.getRadius()) * 0.8) {
				player.explode();
				projec.setState(EntityState.INACTIVE); // projétil é inativado após a colisão instantaneamente
				return true;
			}
		}

		/* colisões player - inimigos. */
		if(ent instanceof Player && proj instanceof Enemy) {
			Player player = (Player) ent;
			Enemy enemy = (Enemy) proj;

			if (dist < enemy.getRadius()) {
				player.explode();
				enemy.explode();
				return true;
			}
		}

		/* colisões projeteis (player) - inimigos */
		if(proj instanceof Projectile && ent instanceof Enemy) {
			Projectile projec = (Projectile) proj;
			Enemy enemy = (Enemy) ent;

			if(dist < enemy.getRadius()) {
				enemy.explode();
				projec.setState(EntityState.INACTIVE); // projétil é inativado após a colisão instantaneamente
				return true;
			}
		}

		return false;
	}

	void inicializate(){

        /* variáveis do player */
        player = new Player();

        /* variáveis dos projéteis disparados pelo player */
        playerProjectiles = new ArrayList<>(10);

        /* variáveis dos inimigos tipo 1 */
        enemy1List = new ArrayList<>(10);
        firstEnemy1 = System.currentTimeMillis() + 2000; // Use o tempo atual aqui

        /* variáveis dos inimigos tipo 2 */
        enemy2List = new ArrayList<>(10);
        firstEnemy2 = System.currentTimeMillis() + 7000;
        enemy2_spawnX = GameLib.WIDTH * 0.20;
        enemy2_count = 5;

        /* variáveis dos projéteis lançados pelos inimigos */
        enemy_Projectiles = new ArrayList<>(200);

        /* estrelas que formam o fundo de primeiro plano */
        backGround = new BackGround(20,50);

        // Inicializações
        for(int i = 0; i < 10; i++) playerProjectiles.add(new Projectile(0,0,0,0,0));
        for(int i = 0; i < 200; i++) enemy_Projectiles.add(new Projectile(0,0,0,0,0));
        for(int i = 0; i < 10; i++) enemy1List.add(new Enemy1(0,0,firstEnemy1));
        for(int i = 0; i < 10; i++) enemy2List.add(new Enemy2(enemy2_spawnX,0,firstEnemy2));
    }

    public void run() {

		/* Indica que o jogo está em execução */

		boolean running = true;

		/* variáveis usadas no controle de tempo efetuado no main loop */
		
		long delta;
		// as definições utilizam o currentTime, mas apenas quando inicia o jogo. Para não confundir, troquei o nome inicial
		long startTime = System.currentTimeMillis(); 

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
		
		long currentTime = startTime; // o timestamp atual, usado para calcular o delta

		while(running){
		
			/* Usada para atualizar o estado dos elementos do jogo    */
			/* (player, projéteis e inimigos) "delta" indica quantos  */
			/* ms se passaram desde a última atualização.             */
			
			delta = System.currentTimeMillis() - currentTime;
			
			/* Já a variável "currentTime" nos dá o timestamp atual.  */
			
			currentTime = System.currentTimeMillis();
			
			/***************************/
			/* Verificação de colisões */
			/***************************/


			if(player.isActive()){
				
				/* colisões player - projeteis (inimigo) */

				for(Projectile p : enemy_Projectiles){
					checkThenCollide(p, player, currentTime);
				}
			
				/* colisões player - inimigos */ //colide faz isso
							
				for(Enemy e : enemy1List){
					checkThenCollide(e, player, currentTime);
				}
				
				for(Enemy e : enemy2List){
					checkThenCollide(e, player, currentTime);
				}
			}
			
			/* colisões projeteis (player) - inimigos */
			
			for(Projectile p : playerProjectiles){
				
				for(Enemy e1 : enemy1List){
					checkThenCollide(p, e1, currentTime);
				}
				
				for(Enemy e2: enemy2List){
					checkThenCollide(p, e2, currentTime);
				}
			}
				
			/***************************/
			/* Atualizações de estados */
			/***************************/
			
			/* projeteis (player) */ //update faz isso
			
			for(Projectile p : playerProjectiles){
				p.update(delta);
			}
			
			/* projeteis (inimigos) */
			
			for(Projectile p : enemy_Projectiles){
				p.update(delta);
			}
			
			/* inimigos tipo 1 */
			for (Enemy1 e1 : enemy1List) {
				if (e1.getState() == EntityState.EXPLODING) {
					if (currentTime > e1.getExplosionEnd()) {
						e1.setState(EntityState.INACTIVE);
					}
				}

				if (e1.getState() == EntityState.ACTIVE) {
					// Verifica se saiu da tela
					if (e1.getY() > GameLib.HEIGHT + 10) {
						e1.setState(EntityState.INACTIVE);
					} else {
						// Atualiza posição e ângulo
						e1.setX(e1.getX() + e1.getVX() * Math.cos(e1.getAngle()) * delta);
						e1.setY(e1.getY() + e1.getVY() * Math.sin(e1.getAngle()) * delta * (-1.0));
						e1.setAngle(e1.getAngle() + e1.getRV() * delta);

						// Disparo
						if (currentTime > e1.getNextShot() && e1.getY() < player.getY()) {
							for (Projectile proj : enemy_Projectiles) {
								if (!proj.isActive()) {
									proj.setX(e1.getX());
									proj.setY(e1.getY());
									proj.setVX(Math.cos(e1.getAngle()) * 0.45);
									proj.setVY(Math.sin(e1.getAngle()) * 0.45 * (-1.0));
									proj.setState(EntityState.ACTIVE);
									e1.setNextShot((long) (currentTime + 200 + Math.random() * 500));
									break;
								}
							}
						}
					}
				}
			}
						
						/* inimigos tipo 2 */
						
						for (Enemy2 e2 : enemy2List) {
				if (e2.getState() == EntityState.EXPLODING) {
					if (currentTime > e2.getExplosionEnd()) {
						e2.setState(EntityState.INACTIVE);
					}
				}

				if (e2.getState() == EntityState.ACTIVE) {
					// Verifica se saiu da tela
					if (e2.getX() < -10 || e2.getX() > GameLib.WIDTH + 10) {
						e2.setState(EntityState.INACTIVE);
					} else {
						boolean shootNow = false;
						double previousY = e2.getY();

						e2.setX(e2.getX() + e2.getVX() * Math.cos(e2.getAngle()) * delta);
						e2.setY(e2.getY() + e2.getVY() * Math.sin(e2.getAngle()) * delta * (-1.0));
						e2.setAngle(e2.getAngle() + e2.getRV() * delta);

						double threshold = GameLib.HEIGHT * 0.30;

						if (previousY < threshold && e2.getY() >= threshold) {
							if (e2.getX() < GameLib.WIDTH / 2) e2.setRV(0.003);
							else e2.setRV(-0.003);
						}

						if (e2.getRV() > 0 && Math.abs(e2.getAngle() - 3 * Math.PI) < 0.05) {
							e2.setRV(0.0);
							e2.setAngle(3 * Math.PI);
							shootNow = true;
						}

						if (e2.getRV() < 0 && Math.abs(e2.getAngle()) < 0.05) {
							e2.setRV(0.0);
							e2.setAngle(0.0);
							shootNow = true;
						}

						if (shootNow) {
							double[] angles = { Math.PI / 2 + Math.PI / 8, Math.PI / 2, Math.PI / 2 - Math.PI / 8 };
							int shots = 0;
							for (Projectile proj : enemy_Projectiles) {
								if (!proj.isActive() && shots < angles.length) {
									double a = angles[shots] + Math.random() * Math.PI / 6 - Math.PI / 12;
									double vx = Math.cos(a);
									double vy = Math.sin(a);

									proj.setX(e2.getX());
									proj.setY(e2.getY());
									proj.setVX(vx * 0.30);
									proj.setVY(vy * 0.30);
									proj.setState(EntityState.ACTIVE);
									shots++;
								}
							}
						}
					}
				}
			}
			
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
			if(player.getState() == EntityState.EXPLODING){
				
				if(currentTime > player.getExplosionEnd()){
					
					player.setState(EntityState.ACTIVE);
				}
			}
			
			/********************************************/
			/* Verificando entrada do usuário (teclado) */
			/********************************************/
			
			if(player.getState() == EntityState.ACTIVE){

				// Assume Player has get/setX, get/setY, getVX, getVY, getRadius, getNextShot, setNextShot
				if(GameLib.iskeyPressed(GameLib.KEY_UP)) player.setY(player.getY() - delta * player.getVY());
				if(GameLib.iskeyPressed(GameLib.KEY_DOWN)) player.setY(player.getY() + delta * player.getVY());
				if(GameLib.iskeyPressed(GameLib.KEY_LEFT)) player.setX(player.getX() - delta * player.getVX());
				if(GameLib.iskeyPressed(GameLib.KEY_RIGHT)) player.setX(player.getX() + delta * player.getVX());

				if(GameLib.iskeyPressed(GameLib.KEY_CONTROL)) {
					if(currentTime > player.getNextShot()){
						for(Projectile proj : playerProjectiles){
							if(!proj.isActive()){
								proj.setX(player.getX());
								proj.setY(player.getY() - 2 * player.getRadius());
								proj.setVX(0.0);
								proj.setVY(-1.0);
								proj.setState(EntityState.ACTIVE);
								player.setNextShot(currentTime + 100);
								break;
							}
						}
					}
				}
			}

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
			
			/* desenhando plano fundo distante */

			//GameLib.setColor(Color.DARK_GRAY);
			//background2_count +=  background2_speed* delta;
			
			//for(int i = 0; i < background2_X.length; i++){
				backGround.draw(delta);
				//GameLib.fillRect(background2_X[i], (background2_Y[i] + background2_count) % GameLib.HEIGHT, 2, 2);
			//}
			
			/* desenhando plano de fundo próximo */
			
			/*GameLib.setColor(Color.GRAY);
			background1_count += background1_speed * delta;
			
			for(int i = 0; i < background1_X.length; i++){
				
				GameLib.fillRect(background1_X[i], (background1_Y[i] + background1_count) % GameLib.HEIGHT, 3, 3);
			}*/
						
			/* desenhando player */
			
			// Desenhar player
			if(player.getState() == EntityState.EXPLODING){
				double alpha = (currentTime - player.getExplosionStart()) / (double)(player.getExplosionEnd() - player.getExplosionStart());
				GameLib.drawExplosion(player.getX(), player.getY(), alpha);
			}
			else{
				GameLib.setColor(Color.BLUE);
				GameLib.drawPlayer(player.getX(), player.getY(), player.getRadius());
			}

			// Desenhar projéteis do player
			for(Projectile proj : playerProjectiles){
				if(proj.isActive()){
					GameLib.setColor(Color.GREEN);
					GameLib.drawLine(proj.getX(), proj.getY() - 5, proj.getX(), proj.getY() + 5);
					GameLib.drawLine(proj.getX() - 1, proj.getY() - 3, proj.getX() - 1, proj.getY() + 3);
					GameLib.drawLine(proj.getX() + 1, proj.getY() - 3, proj.getX() + 1, proj.getY() + 3);
				}
			}

			// Desenhar projéteis dos inimigos
			for(Projectile proj : enemy_Projectiles){
				if(proj.isActive()){
					GameLib.setColor(Color.RED);
					GameLib.drawCircle(proj.getX(), proj.getY(), 2); // Use o raio correto se necessário
				}
			}

			// Desenhar inimigos tipo 1
			for(Enemy1 e1 : enemy1List){
				if(e1.getState() == EntityState.EXPLODING){
					double alpha = (currentTime - e1.getExplosionStart()) / (double)(e1.getExplosionEnd() - e1.getExplosionStart());
					GameLib.drawExplosion(e1.getX(), e1.getY(), alpha);
				}
				if(e1.getState() == EntityState.ACTIVE){
					GameLib.setColor(Color.CYAN);
					GameLib.drawCircle(e1.getX(), e1.getY(), e1.getRadius());
				}
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