import java.util.ArrayList;

/**
 * Created by gabriel on 14/05/17.
 */
public class Banco {

    Dado dado;
    private int numCasa;
    private int numHoteis;
    private ArrayList<SorteReves> sR;
    private ArrayList<Locais> locais;

    public Banco() {
       sR = new ArrayList<>();
       locais = new ArrayList<>();
       dado = new Dado();
       numCasa = 32;
       numHoteis = 12;
    }
    public void setLocaisBanco() {

    }
    public void shuffleSR() {

    }
}
