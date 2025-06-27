package Engine;
import utils.EntityState;
import Entities.Entity;

public class stateManager {

    



    /* projeteis (player) */
    public void update(Entity proj){
        proj.update();
    }




    // FUNCOES ANTIGAS

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
			
			if(stateArray[i] == EntityState.INACTIVE) break;
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
}
