package Entities;

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

public class Boss extends Enemy{

    int HP;

    public Boss(double x, double y, long when, double radius, int hp){
        // super(x, y, when, ...)

        this.HP = hp;
    }

    // public abstract update
}
