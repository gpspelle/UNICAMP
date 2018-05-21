import java.util.ArrayList;

public class RUIMJogadorRA172358 extends Jogador {
    private ArrayList<CartaLacaio> lacaios;
    private ArrayList<CartaLacaio> lacaiosOponente;

    public RUIMJogadorRA172358(ArrayList<Carta> maoInicial, boolean primeiro) {
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

        for(int i = 0; i < mao.size(); i++) {
            Carta a = mao.get(i);
            if(minhaMana > a.getMana()) {
                if (a instanceof CartaLacaio) {
                    minhaMana -= a.getMana();
                    Jogada jog = new Jogada(TipoJogada.LACAIO, a, null);
                    minhasJogadas.add(jog);
                    if(((CartaLacaio) a).getEfeito() == TipoEfeito.INVESTIDA) {
                        minhasJogadas.add(new Jogada(TipoJogada.ATAQUE, a, null));
                    }
                    mao.remove(a);
                } else if (a instanceof CartaMagia && ((CartaMagia) a).getMagiaTipo() != TipoMagia.BUFF) {
                    minhaMana -= a.getMana();
                    Jogada jog = new Jogada(TipoJogada.MAGIA, a, null);
                    minhasJogadas.add(jog);
                    mao.remove(a);
                }
            }
        }
        for(int i = 0; i < lacaios.size(); i++) {
            CartaLacaio a = lacaios.get(i);
                Jogada jog = new Jogada(TipoJogada.ATAQUE, a, null);
                //Jogada jog1 = new Jogada(TipoJogada.ATAQUE, a, null);
                minhasJogadas.add(jog);
                //minhasJogadas.add(jog1);
        }
        return minhasJogadas;
    }
}
