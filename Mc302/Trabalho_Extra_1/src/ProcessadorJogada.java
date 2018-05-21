import java.util.ArrayList;

/**
 * Created by gabriel on 14/05/17.
 */
public class ProcessadorJogada {

    private ArrayList<Jogador> jogadores;
    private int numJogadores;
    int[] sequencia;
    Banco banco;
    public ProcessadorJogada(int n) {
        numJogadores = n;
        sequencia = new int[n];
        for(int i = 0; i < numJogadores; i++) {
            Jogador jogador = new Jogador("Jogador " + i, i);
            jogadores.add(jogador);
        }
    }
    public void setSequencia() {

    }
    public void startGame() {
        banco = new Banco();
    }
}
