import Engine.gameEngine;
import Engine.ConfigReaders.*;

/***********************************************************************/
/*                                                                     */
/* Para jogar:                                                         */
/*                                                                     */
/*    - cima, baixo, esquerda, direita: movimentação do player.        */
/*    - control: disparo de projéteis.                                 */
/*    - ESC: para sair do jogo.                                        */
/*                                                                     */
/* O jogo agora usa o levelManager para controlar quais inimigos,      */
/* bosses e powerups aparecem baseado em arquivos de configuração.     */
/*                                                                     */
/***********************************************************************/

public class Main {
		public static void main(String [] args){
		// Inicializa as configurações do jogo
		GameConfig.initialize();
		
		// Cria uma instância do jogo
		gameEngine game = new gameEngine();
		
		// Executa o jogo
		game.run();
		
		System.exit(0);
	}
}
