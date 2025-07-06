package Engine;

import Manager.EntityState;
import Manager.SpawnManager;

import static Engine.SceneRenderer.render;
import Manager.EntityManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import GameElements.Entity;
import GameElements.Entities.*;
import GameElements.Entities.EnemyModels.*;
import GameElements.Entities.EnemyModels.Bosses.Boss1;
import GameElements.Entities.EnemyModels.Bosses.Boss2;
import GameElements.Entities.PowerUps.Powerup1;
import GameElements.Entities.PowerUps.Powerup2;
import GameElements.Entities.ProjectileModels.ProjectileEnemy;
import GameElements.Entities.ProjectileModels.ProjectilePlayer;

import java.util.Arrays;
import java.util.HashMap;

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

	double enemy2_spawnX;
	int enemy2_count;
	SpawnManager spawnManager;

	//teste de boss
	private Boss2 boss2;

	boolean running;

	/* Espera, sem fazer nada, até que o instante de tempo atual seja */
	/* maior ou igual ao instante especificado no parâmetro "time.    */

	void inicializate(){

		long startTime = System.currentTimeMillis(); 

        /* variáveis do player */
        player = new Player(10);

        /* variáveis dos projéteis disparados pelo player */
        playerProjectiles = new ArrayList<>(10);

		// tempos de spawn iniciais
		this.spawnManager = new SpawnManager(startTime);

        /* variáveis dos inimigos tipo 1 */
        enemy1List = new ArrayList<>(10);

        /* variáveis dos inimigos tipo 2 */
        enemy2List = new ArrayList<>(10);
        enemy2_spawnX = GameLib.WIDTH * 0.20;
        enemy2_count = 5;

        /* variáveis dos projéteis lançados pelos inimigos */
        enemy_Projectiles = new ArrayList<>(200);

		/* variáveis dos powerups */
		powerups1 = new ArrayList<>(2);
		
		powerups2 = new ArrayList<>(2);
		enemy_ProjectilesBoss = new ArrayList<>(200);

        /* estrelas que formam o fundo de primeiro plano */
        backGround = new BackGround(20,50);

		/*Tesste de boss*/
		boss2 = new Boss2(GameLib.WIDTH/2,0,spawnManager.firstEnemy1, startTime, 5);

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

		running = true;

		/* variáveis usadas no controle de tempo efetuado no main loop */
		
		long delta;
		// as definições utilizam o currentTime, mas apenas quando inicia o jogo. Para não confundir, troquei o nome inicial

		inicializate();

		List<List<? extends Entity>> entities = Arrays.asList(
            playerProjectiles, // 0
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
				
			/***************************/
			/* Atualizações de estados */
			/***************************/

			ArrayList<Enemy> enemies = new ArrayList<>();
			enemies.addAll(enemy1List);
			enemies.addAll(enemy2List);

			ArrayList<Powerup> powerups = new ArrayList<>();
			powerups.addAll(powerups1);
			powerups.addAll(powerups2);

			running = EntityManager.updateEntities(delta, currentTime, player,playerProjectiles,enemies, boss2, enemy_Projectiles, enemy_ProjectilesBoss, powerups);

			
			/* verificando se novos inimigos (tipo 1) devem ser "lançados" */
			spawnManager.shouldSpawn(currentTime, entities);
	
			/********************************************/
			/* Verificando entrada do usuário (teclado) */
			/********************************************/


			/*******************/
			/* Desenho da cena */
			/*******************/
			render(
                backGround,
                player,
				boss2,
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

	public static void busyWait(long time){
		while(System.currentTimeMillis() < time) Thread.yield();
	}
}