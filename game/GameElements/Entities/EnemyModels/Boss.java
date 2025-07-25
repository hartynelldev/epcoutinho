package GameElements.Entities.EnemyModels;

import GameElements.LifeBar;
import GameElements.Entities.Enemy;
import Engine.ConfigReaders.GameConfig;
import Exceptions.HpExcption;

// do enunciado:
/* 
- Os chefes não podem sair da tela uma vez que tenham entrado na área de jogo (diferentemente dos
inimigos comuns que em algum momento acabam saindo da tela caso não sejam abatidos pelo
jogador). 

- Os chefes deve apresentar comportamentos de ataque e movimento diferentes daqueles apresentados
pelos inimigos comuns (e diferentes entre si também). Devem apresentar visual próprio também
    
- Os chefes também devem possuir pontos de vida. Quando entrar na área de jogo uma barra de vida
também deve ser exibida para o chefe.

- Deve-se assumir que um chefe aparece uma única vez durante uma fase. Além disso, a derrota de um
chefe implica no avanço para a próxima fase (ver mais detalhes sobre as fases adiante)
*/

public abstract class Boss extends Enemy {

    //  ATRIBUTOS 
    
    protected long nextSuperAtack;
    protected long superAtackDuration;
    protected LifeBar lifeBar;

    //  CONSTRUTOR 
    
    public Boss(double x, double y, long when, long now, int hp) {
        super(x, y, when, now, GameConfig.getBossDefaultRadius());
        try {
            if (hp <= 0) throw new HpExcption();
            nextSuperAtack = now + GameConfig.getBossNextSuperAttackDelay();
            this.HP = hp;
            this.superAtackDuration = GameConfig.getBossSuperAttackDuration();
            lifeBar = new LifeBar(hp);
        } catch (Exception e) {
            // System.out.println(e.getMessage());
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    //  MÉTODOS ABSTRATOS 
    
    // public abstract update
}
