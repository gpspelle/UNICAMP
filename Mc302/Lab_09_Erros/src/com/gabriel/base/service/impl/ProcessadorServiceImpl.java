package com.gabriel.base.service.impl;

import com.gabriel.base.Carta;
import com.gabriel.base.Jogada;
import com.gabriel.base.Mesa;
import com.gabriel.base.cartas.HabilidadesLacaio;
import com.gabriel.base.cartas.Lacaio;
import com.gabriel.base.cartas.magias.Buff;
import com.gabriel.base.cartas.magias.Dano;
import com.gabriel.base.cartas.magias.DanoArea;
import com.gabriel.base.service.ProcessadorService;

public class ProcessadorServiceImpl implements ProcessadorService {

    public ProcessadorServiceImpl() {

    }

    public boolean processar(Jogada jogada, Mesa mesa) {
        if(jogada.getAutor() == 'P') {

            System.out.println("Vida inimigo " + mesa.getPoderHeroiS());
            System.out.println(jogada);

            if(jogada.getJogada() instanceof DanoArea) {
                ((DanoArea) jogada.getJogada()).usar(mesa.getLacaiosS());
                mesa.decPoderHeroi(((DanoArea) jogada.getJogada()).getDano(), 'S');
                System.out.println("Vida inimigo " + mesa.getPoderHeroiS());
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

                if(jogada.getAlvo() == null) {
                    mesa.decPoderHeroi(((Dano) jogada.getJogada()).getDano(), 'S');
                    System.out.println("Vida inimigo " + mesa.getPoderHeroiS());
                } else {
                    jogada.getJogada().usar(jogada.getAlvo());
                }
            } else if(jogada.getJogada() instanceof Buff) {
               if (jogada.getAlvo() instanceof Lacaio) {
                   jogada.getJogada().usar(jogada.getAlvo());
               }
            } else if(jogada.getJogada() instanceof Lacaio) {
                if(((Lacaio) jogada.getJogada()).getHabilidadeLacaio() == HabilidadesLacaio.EXAUSTAO) {
                    ((Lacaio) jogada.getJogada()).setHabilidadeLacaio(HabilidadesLacaio.INVESTIDA);
                } else {
                    if(jogada.getAlvo() == null) {
                        mesa.decPoderHeroi(((Lacaio) jogada.getJogada()).getAtaque(), 'S');
                    } else {
                        jogada.getJogada().usar(jogada.getAlvo());
                    }
                }
            }
            return mesa.getPoderHeroiS() > 0;
        } else {

            System.out.println(jogada);
            System.out.println(mesa.getLacaiosP());
            System.out.println(mesa.getPoderHeroiP());
            mesa.decMana(jogada.getJogada().getCustoMana(), 'S');

            if(jogada.getJogada() instanceof DanoArea) {
                ((DanoArea) jogada.getJogada()).usar(mesa.getLacaiosS());
                mesa.decPoderHeroi(((DanoArea) jogada.getJogada()).getDano(), 'S');
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

                if(jogada.getAlvo() == null) {
                    mesa.decPoderHeroi(((Dano) jogada.getJogada()).getDano(), 'P');
                } else {
                    jogada.getJogada().usar(jogada.getAlvo());
                }
            } else if(jogada.getJogada() instanceof Buff) {
               if (jogada.getAlvo() instanceof Lacaio) {
                   jogada.getJogada().usar(jogada.getAlvo());
               }
            } else if(jogada.getJogada() instanceof Lacaio) {
                if(((Lacaio) jogada.getJogada()).getHabilidadeLacaio() == HabilidadesLacaio.EXAUSTAO) {
                    ((Lacaio) jogada.getJogada()).setHabilidadeLacaio(HabilidadesLacaio.INVESTIDA);
                } else {
                    if(jogada.getAlvo() == null) {
                        mesa.decPoderHeroi(((Lacaio) jogada.getJogada()).getAtaque(), 'P');
                    } else {
                        jogada.getJogada().usar(jogada.getAlvo());
                    }
                }
            }
            return mesa.getPoderHeroiP() > 0;
        }
    }
}
