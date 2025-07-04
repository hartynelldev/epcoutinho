package Exceptions;

public class SpawExcption extends Exception {
    public SpawExcption() {
        super("Erro: O tempo de spaw do inimigo deve ser maior ou igual a  0");
    }
}