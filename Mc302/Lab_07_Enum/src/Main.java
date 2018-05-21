/*  Gabriel Pellegrino da Silva /17258
    Laboratório 6 :
*/

import com.gabriel.base.*;
import com.gabriel.base.cartas.*;
import com.gabriel.base.cartas.magias.*;
import com.gabriel.util.Util;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        Random gerador = new Random();

        Baralho baralhoA = new Baralho();
        Baralho baralhoB = new Baralho();

        baralhoA.preencheAleatorio(gerador, 10, 10, 20, 30);
        baralhoB.preencheAleatorio(gerador, 10, 12, 23, 34);
        Mesa mesa = new Mesa();

        Lacaio a = new Lacaio("A", 3, 13, 6, 3, HabilidadesLacaio.INVESTIDA);
        Lacaio b = new Lacaio("B", 4, 17, 5, 4, HabilidadesLacaio.EXAUSTAO);
        Lacaio c = new Lacaio("C", 1, 23, 3, 5, HabilidadesLacaio.PROVOCAR);
        Lacaio d = new Lacaio("D", 7, 18, 5, 7, HabilidadesLacaio.EXAUSTAO);
        Lacaio e = new Lacaio("E", 3, 19, 6, 9, HabilidadesLacaio.PROVOCAR);
        Lacaio f = new Lacaio("F", 5, 17, 9, 11, HabilidadesLacaio.INVESTIDA);
        Lacaio g = new Lacaio("G", 1, 25, 11, 12, HabilidadesLacaio.INVESTIDA);

        ArrayList<Carta> meuArrayP = new ArrayList<>();
        meuArrayP.add(a);
        meuArrayP.add(b);
        meuArrayP.add(c);
        mesa.setLacaiosP(meuArrayP);
        ArrayList<Carta> meuArrayS = new ArrayList<>();
        meuArrayS.add(d);
        meuArrayS.add(e);
        meuArrayS.add(f);
        meuArrayS.add(g);
        mesa.setLacaiosS(meuArrayS);

        ArrayList<Carta> arrayMaoP = new ArrayList<>();
        ArrayList<Carta> arrayMaoS = new ArrayList<>();
        for(int i = 0; i < Util.MAO_INI; i++) {
            arrayMaoP.add(baralhoA.comprarCarta());
            arrayMaoS.add(baralhoB.comprarCarta());
        }
        arrayMaoS.add(baralhoB.comprarCarta());
        mesa.setMaoP(arrayMaoP);
        mesa.setMaoS(arrayMaoS);


        Carta ataqueP = mesa.sacarCarta('P');
        Carta ataqueS = mesa.sacarCarta('S');
        Jogada jogP = new Jogada(ataqueP, null, 'P');
        Jogada jogS = new Jogada(ataqueS, null, 'S');
        ProcessadorJogada.processa(jogP, mesa);
        ProcessadorJogada.processa(jogS, mesa);

        ataqueP = mesa.sacarCarta('P');
        ataqueS = mesa.sacarCarta('S');

        if(mesa.getLacaiosS().size() > 0)
            jogP = new Jogada(ataqueP, mesa.getLacaiosS().get(mesa.getLacaiosS().size()-1), 'P');
        if(mesa.getLacaiosP().size() > 0)
            jogS = new Jogada(ataqueS, mesa.getLacaiosP().get(mesa.getLacaiosP().size()-1), 'S');

        ProcessadorJogada.processa(jogP, mesa);
        ProcessadorJogada.processa(jogS, mesa);
    }
}
