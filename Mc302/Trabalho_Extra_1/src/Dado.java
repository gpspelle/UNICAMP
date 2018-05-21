/**
 * Created by gabriel on 13/05/17.
 */
import java.util.Random;

public class Dado {

    public int ultimoValor;
    private Random gerador;

    public Dado() {
        gerador = new Random();
    }

    public int duasJogadasDado() {
        ultimoValor =  (gerador.nextInt() %6) + 1;
        ultimoValor += (gerador.nextInt() %6) + 1;

        return ultimoValor;
    }
}
