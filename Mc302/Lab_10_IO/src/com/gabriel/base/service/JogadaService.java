package com.gabriel.base.service;

import com.gabriel.base.Jogada;
import com.gabriel.base.Mesa;

import java.util.List;

public interface JogadaService {
    List<Jogada> criaJogada(Mesa mesa, char jogador);
}
