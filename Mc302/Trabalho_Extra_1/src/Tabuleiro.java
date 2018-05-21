import java.util.ArrayList;

/**
 * Created by gabriel on 13/05/17.
 */
public class Tabuleiro {

    private int numJogadores;
    private ProcessadorJogada processador;

    public Tabuleiro(int n) {
        numJogadores = n;
        processador = new ProcessadorJogada(numJogadores);
    }
}
