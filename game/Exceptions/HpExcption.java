package Exceptions;

import GameElements.Entity;

public class HpExcption extends Exception {
    public HpExcption() {
        super("Erro: A vida do boss deve ser maior que 0.");
    }
}
