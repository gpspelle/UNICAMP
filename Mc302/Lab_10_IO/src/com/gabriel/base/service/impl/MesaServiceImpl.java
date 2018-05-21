package com.gabriel.base.service.impl;

import com.gabriel.base.Baralho;
import com.gabriel.base.Mesa;
import com.gabriel.base.cartas.TipoCarta;
import com.gabriel.base.exception.MesaNulaException;
import com.gabriel.base.exception.ValorInvalidoException;
import com.gabriel.base.service.CartaService;
import com.gabriel.base.service.MesaService;

import java.util.Random;

import static com.gabriel.util.Util.*;

public class MesaServiceImpl implements MesaService {

    private CartaService cartaService;

    public MesaServiceImpl() {
        this.cartaService = new CartaServiceImpl();
    }

    public Mesa adicionaLacaios(Mesa mesa, Random gerador, TipoCarta tipoCarta) throws MesaNulaException, ValorInvalidoException {

        MesaNulaException e = new MesaNulaException("Mesa Nula");
        ValorInvalidoException e2 = new ValorInvalidoException("Valor invalido");

        if(MAO_INI < 3) {
            throw e2;
        }
        if(mesa == null) {
            throw e;
        }
        for (int i = 0; i < MAX_LACAIOS; i++) {
            mesa.getLacaiosP().
                    add(cartaService.
                            geraCartaAleatoria(gerador, MAX_MANA, MAX_ATAQUE, MAX_VIDA, TipoCarta.LACAIO));
            mesa.getLacaiosS().
                    add(cartaService.
                            geraCartaAleatoria(gerador, MAX_MANA, MAX_ATAQUE, MAX_VIDA, TipoCarta.LACAIO));
        }
        return mesa;
    }
    public Mesa addMaoInicial(Mesa mesa, Baralho baralhoP, Baralho baralhoS, int quantidade) {

        for(int i = 0; i < quantidade; i++) {
            mesa.getMaoP().add(baralhoP.comprar());
            mesa.getMaoS().add(baralhoS.comprar());
        }

        return mesa;
    }
}
