package Entities.EnemyModels;

import Entities.Enemy;

public class Enemy1 extends Enemy{
    
    private long currentTime = System.currentTimeMillis(); // tempo atual do sistema
    public long nextEnemy = currentTime + 2000;				// instante em que um novo inimigo deve aparecer		
}
