package com.gabriel.base.service.impl;

import com.gabriel.base.Carta;
import com.gabriel.base.Jogada;
import com.gabriel.base.Mesa;
import com.gabriel.base.cartas.Lacaio;
import com.gabriel.base.cartas.magias.Dano;
import com.gabriel.base.cartas.magias.DanoArea;
import com.gabriel.base.service.JogadaService;

import java.util.*;
import java.util.stream.Collectors;

public class JogadaServiceAgressivaImpl implements JogadaService {

    public JogadaServiceAgressivaImpl() {

    }

    public List<Jogada> criaJogada(Mesa mesa, char jogador) {

        List<Jogada> jogadas = new ArrayList<>();

        if(jogador == 'P') {

            Collection<Carta> cartasLacaio = mesa.getMaoP().
                                           stream().
                                           filter(p -> p instanceof Lacaio).
                                           collect(Collectors.toList());
            Collection<Carta> cartasDano = mesa.getMaoP().
                                           stream().
                                           filter(p -> p instanceof Dano).
                                           collect(Collectors.toList());

            Collections.sort((ArrayList<Carta>) cartasLacaio, new LacaioComparator());
            Collections.sort((ArrayList<Carta>) cartasDano, new DanoComparator());

            for(int i = 0; i < mesa.getLacaiosP().size(); i++) {
                Jogada jog = new Jogada(mesa.getLacaiosP().get(i), null, 'P');
                jogadas.add(jog);
            }

            for(int i = 0; i < cartasDano.size(); i++) {
                if(((ArrayList<Carta>) cartasDano).get(i).getCustoMana() <= mesa.getManaP()) {
                    Jogada jog = new Jogada(((ArrayList<Carta>) cartasDano).get(i), null, 'P');
                    jogadas.add(jog);
                    mesa.setManaP(mesa.getManaP() - ((ArrayList<Carta>) cartasDano).get(i).getCustoMana());
                }
            }

            for(int i = 0; i < cartasLacaio.size(); i++) {
                if(((ArrayList<Carta>) cartasLacaio).get(i).getCustoMana() <= mesa.getManaP()) {
                    Jogada jog = new Jogada(((ArrayList<Carta>) cartasLacaio).get(i), null, 'P');
                    jogadas.add(jog);
                    mesa.setManaP(mesa.getManaP() - ((ArrayList<Carta>) cartasLacaio).get(i).getCustoMana());
                }
            }

        } else {

            Collection<Carta> cartasLacaio = mesa.getMaoS().
                    stream().
                    filter(p -> p instanceof Lacaio).
                    collect(Collectors.toList());
            Collection<Carta> cartasDano = mesa.getMaoS().
                    stream().
                    filter(p -> p instanceof Dano).
                    collect(Collectors.toList());

            for(int i = 0; i < mesa.getLacaiosS().size(); i++) {
                Jogada jog = new Jogada(mesa.getLacaiosS().get(i), null, 'S');
                jogadas.add(jog);
            }

            for(int i = 0; i < cartasDano.size(); i++) {
                Jogada jog = new Jogada(((ArrayList<Carta>) cartasDano).get(i), null, 'S');
                jogadas.add(jog);
            }

            for(int i = 0; i < cartasLacaio.size(); i++) {
                Jogada jog = new Jogada(((ArrayList<Carta>) cartasLacaio).get(i), null, 'S');
                jogadas.add(jog);
            }
        }

        return jogadas;
    }
    class DanoComparator implements Comparator<Carta> {

        public DanoComparator() {

        }

        @Override
        public int compare(Carta o1, Carta o2) {
            if(o1 instanceof Dano && o2 instanceof Dano)
                return ((Dano) o2).getDano() - ((Dano) o1).getDano();

            return 0;
        }
    }

    class LacaioComparator implements Comparator<Carta> {

        public LacaioComparator() {

        }
        @Override
        public int compare(Carta o1, Carta o2) {
            if(o1 instanceof Lacaio && o2 instanceof Lacaio)
                return ((Lacaio) o2).getAtaque() - ((Lacaio) o1).getAtaque();

            return 0;
        }
    }
}
