import java.util.ArrayList;

public class JogadorRA172358 extends Jogador {
    private ArrayList<CartaLacaio> lacaios;
    private ArrayList<CartaLacaio> lacaiosOponente;

    public JogadorRA172358(ArrayList<Carta> maoInicial, boolean primeiro) {
        this.primeiroJogador = primeiro;
        this.mao = maoInicial;
        this.lacaios = new ArrayList();
        this.lacaiosOponente = new ArrayList();
        System.out.println("Sou o " + (primeiro?"primeiro":"segundo") + " jogador (classe: JogadorAleatorio)");
        System.out.println("Mao inicial:");

        for(int i = 0; i < this.mao.size(); ++i) {
            System.out.println("ID " + ((Carta)this.mao.get(i)).getID() + ": " + this.mao.get(i));
        }

        System.out.println("obs: nenhuma funcionalidade ativa para a classe JogadorAleatorio.");
    }

    public ArrayList<Jogada> processarTurno(Mesa mesa, Carta cartaComprada, ArrayList<Jogada> jogadasOponente) {

        if(cartaComprada != null) {
            this.mao.add(cartaComprada);
        }

        int minhaMana;
        int minhaVida;
        if(this.primeiroJogador) {
            minhaMana = mesa.getManaJog1();
            minhaVida = mesa.getVidaHeroi1();
            this.lacaios = mesa.getLacaiosJog1();
            this.lacaiosOponente = mesa.getLacaiosJog2();
        } else {
            minhaMana = mesa.getManaJog2();
            minhaVida = mesa.getVidaHeroi2();
            this.lacaios = mesa.getLacaiosJog2();
            this.lacaiosOponente = mesa.getLacaiosJog1();
        }

        ArrayList minhasJogadas = new ArrayList();
        ArrayList possiveisDrops = new ArrayList();
        ArrayList possiveisDrops_CartasIdx = new ArrayList();
        int numMeusLacaiosLimite = this.lacaios.size();
        int k;
        int alvoRand;
        Jogada var17;
        Jogada var18;

        if(minhaMana >= 2) {
            minhasJogadas.add(new Jogada(TipoJogada.PODER, null, mao.get(0)));
        }
        return minhasJogadas;
    }
}
