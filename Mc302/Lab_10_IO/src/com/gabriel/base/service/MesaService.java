package com.gabriel.base.service;

import com.gabriel.base.Baralho;
import com.gabriel.base.Mesa;
import com.gabriel.base.cartas.TipoCarta;
import com.gabriel.base.exception.MesaNulaException;
import com.gabriel.base.exception.ValorInvalidoException;

import java.util.Random;

public interface MesaService {
    Mesa adicionaLacaios(Mesa mesa, Random random, TipoCarta tipoCarta) throws MesaNulaException, ValorInvalidoException;
    Mesa addMaoInicial(Mesa mesa, Baralho baralhoP, Baralho baralhoS, int quantidade);
}