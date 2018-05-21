package com.gabriel.base.controle;
import com.gabriel.base.Baralho;
import com.gabriel.base.Jogada;
import com.gabriel.base.Mesa;
import com.gabriel.base.cartas.TipoCarta;
import com.gabriel.base.service.CartaService;
import com.gabriel.base.service.JogadaService;
import com.gabriel.base.service.impl.CartaServiceImpl;
import com.gabriel.base.service.impl.JogadaServiceAgressivaImpl;
import com.gabriel.base.service.impl.ProcessadorServiceImpl;

import java.util.List;
import java.util.Random;

import static com.gabriel.util.Util.MAO_INI;
import static com.gabriel.util.Util.MAX_CARDS;

public class Controle {

    private Mesa mesa;
    private Baralho baralhoP;
    private Baralho baralhoS;
    private Random gerador;
    private ProcessadorControle processadorControle;
    private CartaService cartaService;
    private JogadaService jogadaService;
    private int maxLacaios;
    private int maxMana;
    private int maxAtaque;
    private int maxVida;
    private int index;

    public Controle () {
        this.maxAtaque = 10;
        this.maxMana = 2;
        this.maxLacaios = 10;
        this.maxVida = 6;
        this.baralhoP = new Baralho();
        this.baralhoS = new Baralho();
        this.mesa = new Mesa();
        this.gerador = new Random();
        this.cartaService = new CartaServiceImpl() ;
        this.jogadaService = new JogadaServiceAgressivaImpl();
    }

    public void executa() {
        preencheBaralho();
        organizaMesa();

        processadorControle = new ProcessadorControle (new ProcessadorServiceImpl());

        List<Jogada> jogadas = jogadaService.criaJogada(mesa, 'P');
        for(Jogada jogada : jogadas) {
            if(!processadorControle.processar(jogada, mesa)) {
                System.out.println("###### " + jogada.getAutor() + " venceu!");
                break;
            }
        }
    }
    private void preencheBaralho() {
        baralhoP.preencheAleatorio(gerador, MAX_CARDS, maxMana, maxAtaque, maxVida);
        baralhoS.preencheAleatorio(gerador, MAX_CARDS, maxMana, maxAtaque, maxVida);

    }

    private void organizaMesa() {
        for(int i = 0; i < maxLacaios; i++) {
            mesa.getLacaiosP().
                    add(cartaService.
                            geraCartaAleatoria(gerador, maxMana, maxAtaque, maxVida, TipoCarta.LACAIO));
            mesa.getLacaiosS().
                    add(cartaService.
                            geraCartaAleatoria(gerador, maxMana, maxAtaque, maxVida, TipoCarta.LACAIO));
        }

        for(int i = 0; i < MAO_INI; i++) {
            mesa.getMaoP().add(baralhoP.comprar());
            mesa.getMaoS().add(baralhoS.comprar());
        }
        mesa.getMaoS().add(baralhoS.comprar());
    }
}
