/**
 * Created by gabriel on 13/05/17.
 */
public class LocaisEspeciais extends Locais {

    private TipoJogadaEspecial jogada;
    public void acao() {
    }

    public LocaisEspeciais(TipoJogadaEspecial tipo, int n) {
        super(n);
        jogada = tipo;
    }
}
