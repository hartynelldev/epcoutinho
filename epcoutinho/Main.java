import Engine.gameEngine;

/***********************************************************************/
/*                                                                     */
/* Para jogar:                                                         */
/*                                                                     */
/*    - cima, baixo, esquerda, direita: movimentação do player.        */
/*    - control: disparo de projéteis.                                 */
/*    - ESC: para sair do jogo.                                        */
/*                                                                     */
/***********************************************************************/

public class Main {
	// COPIAMOS TODO O CONTEUDO DA MAIN anterior PARA A CLASSE gameEngine
	public static void main(String [] args){
		// Cria uma instância do jogo
		gameEngine game = new gameEngine();
		game.run();
		
		System.exit(0);
	}
}
