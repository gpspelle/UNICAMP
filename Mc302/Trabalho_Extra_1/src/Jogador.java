import java.util.ArrayList;

/**
 * Created by gabriel on 13/05/17.
 */
public class Jogador {

    private String nome;
    private int numero;
    private int dinheiro;
    private ArrayList<Locais> propriedades;
    private int posicaoNoTabuleiro;
    public Jogador(String nome, int n) {
        this.nome = nome;
        numero = n;
        dinheiro = 2458;
        propriedades = new ArrayList<>();
        posicaoNoTabuleiro = 0;
    }
}
