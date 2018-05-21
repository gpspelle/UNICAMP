package com.gabriel.base;

import com.gabriel.base.cartas.HabilidadesLacaio;
import com.gabriel.base.cartas.Lacaio;
import com.gabriel.base.cartas.magias.Buff;
import com.gabriel.base.cartas.magias.Dano;
import com.gabriel.base.cartas.magias.DanoArea;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class ProcessadorJogada {

    public static void processa(Jogada jogada, Mesa mesa) {
        if(jogada.getAutor() == 'P') {
            System.out.println(jogada);
            System.out.println(mesa.getLacaiosS());
            System.out.println(mesa.getPoderHeroiS());
            mesa.setManaP(mesa.getManaP() - jogada.getJogada().getCustoMana());
            if(jogada.getJogada() instanceof DanoArea) {
                for(Carta lacaio : mesa.getLacaiosS()) {
                    Lacaio lac = (Lacaio)lacaio;
                    lac.setVidaAtual(lac.getVidaAtual() - ((DanoArea) jogada.getJogada()).getDano());
                }
            } else if(jogada.getJogada() instanceof Dano) {
                Lacaio alvo = null;
                for(Carta lacaio : mesa.getLacaiosS()) {
                    Lacaio lac = (Lacaio)lacaio;
                    if(lac.getHabilidadeLacaio() == HabilidadesLacaio.PROVOCAR) {
                       alvo = lac;
                    }
                }
                if(alvo != null) {
                    jogada.setAlvo(alvo);
                }
                jogada.getJogada().usar(jogada.getAlvo());
            } else if(jogada.getJogada() instanceof Buff) {
               if (jogada.getAlvo() instanceof Lacaio) {
                   jogada.getJogada().usar(jogada.getAlvo());
               }
            } else if(jogada.getJogada() instanceof Lacaio) {
                if(((Lacaio) jogada.getJogada()).getHabilidadeLacaio() == HabilidadesLacaio.EXAUSTAO) {
                    ((Lacaio) jogada.getJogada()).setHabilidadeLacaio(HabilidadesLacaio.INVESTIDA);
                } else {
                    jogada.getJogada().usar(jogada.getAlvo());
                }
            }
        } else {
            System.out.println(jogada);
            System.out.println(mesa.getLacaiosP());
            System.out.println(mesa.getPoderHeroiP());
            mesa.setManaS(mesa.getManaS() - jogada.getJogada().getCustoMana());
            if(jogada.getJogada() instanceof DanoArea) {
                for(Carta lacaio : mesa.getLacaiosP()) {
                    Lacaio lac = (Lacaio)lacaio;
                    lac.setVidaAtual(lac.getVidaAtual() - ((DanoArea) jogada.getJogada()).getDano());
                }
            } else if(jogada.getJogada() instanceof Dano) {
                Lacaio alvo = null;
                for(Carta lacaio : mesa.getLacaiosP()) {
                    Lacaio lac = (Lacaio)lacaio;
                    if(lac.getHabilidadeLacaio() == HabilidadesLacaio.PROVOCAR) {
                       alvo = lac;
                    }
                }
                if(alvo != null) {
                    jogada.setAlvo(alvo);
                }
                jogada.getJogada().usar(jogada.getAlvo());
            } else if(jogada.getJogada() instanceof Buff) {
               if (jogada.getAlvo() instanceof Lacaio) {
                   jogada.getJogada().usar(jogada.getAlvo());
               }
            } else if(jogada.getJogada() instanceof Lacaio) {
                if(((Lacaio) jogada.getJogada()).getHabilidadeLacaio() == HabilidadesLacaio.EXAUSTAO) {
                    ((Lacaio) jogada.getJogada()).setHabilidadeLacaio(HabilidadesLacaio.INVESTIDA);
                } else {
                    jogada.getJogada().usar(jogada.getAlvo());
                }
            }
        }

        Collection<Carta> collectionP = mesa.getLacaiosP();
        ArrayList<Carta> aux = collectionP.stream().filter(o1 -> o1 instanceof Lacaio).filter(o1 -> ((Lacaio) o1).getVidaAtual() > 0).collect(Collectors.toCollection((ArrayList::new)));
        mesa.setLacaiosP(aux);
        Collection<Carta> collectionS = mesa.getLacaiosS();
        aux = collectionS.stream().filter(o1 -> o1 instanceof Lacaio).filter(o1 -> ((Lacaio) o1).getVidaAtual() > 0).collect(Collectors.toCollection((ArrayList::new)));
        mesa.setLacaiosS(aux);

        System.out.println(mesa.getLacaiosP());
        System.out.println(mesa.getLacaiosS());
        System.out.println(mesa.getPoderHeroiP());
        System.out.println(mesa.getPoderHeroiS());
    }
}
