package com.gabriel.base.controle;
import com.gabriel.base.Baralho;
import com.gabriel.base.Carta;
import com.gabriel.base.Jogada;
import com.gabriel.base.Mesa;
import com.gabriel.base.cartas.TipoCarta;
import com.gabriel.base.exception.BaralhoVazioException;
import com.gabriel.base.exception.MesaNulaException;
import com.gabriel.base.exception.ValorInvalidoException;
import com.gabriel.base.service.*;
import com.gabriel.base.service.impl.*;

import java.util.List;
import java.util.Random;

import static com.gabriel.util.Util.*;

public class Controle {

    private Mesa mesa;
    private Baralho baralhoP;
    private Baralho baralhoS;
    private Random gerador;
    private ProcessadorService processadorService;
    private JogadaService jogadaService;
    private BaralhoService baralhoService;
    private MesaService mesaService;

    public Controle () {
        this.baralhoP = new Baralho();
        this.baralhoS = new Baralho();
        this.mesa = new Mesa();
        this.gerador = new Random();
        this.jogadaService = new JogadaServiceAgressivaImpl();
        baralhoService = new BaralhoServiceImpl();
        mesaService = new MesaServiceImpl();
        processadorService = new ProcessadorServiceImpl();
    }

    public void executa() {

        try {
            baralhoP.addCartas(baralhoService.preencheAleatorio(gerador, MAX_CARDS, MAX_MANA, MAX_ATAQUE, MAX_VIDA));
            baralhoS.addCartas(baralhoService.preencheAleatorio(gerador, MAX_CARDS, MAX_MANA, MAX_ATAQUE, MAX_VIDA));

            try {
                mesa = mesaService.adicionaLacaios(null, gerador, TipoCarta.LACAIO);
            } catch (MesaNulaException mesaNulaException) {
                System.out.println(mesaNulaException);
            } catch (ValorInvalidoException valorInvalidoException) {
                System.out.println("Poucas cartas na mao " + MAO_INI);
            }
            mesa = mesaService.addMaoInicial(mesa, baralhoP, baralhoS, MAO_INI);

            List<Jogada> jogadas = jogadaService.criaJogada(mesa, 'P');

            for (Jogada jogada : jogadas) {
                if (!processadorService.processar(jogada, mesa)) {
                    System.out.println("###### " + jogada.getAutor() + " venceu!");
                    break;
                }
            }
        } finally {
            System.out.println("Partida Encerrada");
        }
    }
}
