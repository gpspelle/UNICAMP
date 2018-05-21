/**
 * Created by gabriel on 13/05/17.
 */
public class Terreno extends Locais {

    private int aluguelAtual;
    private int precoDeCompra;
    private int precoConstruirCasa;
    private int precoConstruirHotel;
    private Jogador dono;
    private int hipoteca;
    private int cor;
    public Terreno(int aluguelAtual, int precoDeCompra, int precoConstruirCasa, int precoConstruirHotel, int hipoteca, int cor, int n) {
        super(n);

    }
    public void acao() {
    }
}
