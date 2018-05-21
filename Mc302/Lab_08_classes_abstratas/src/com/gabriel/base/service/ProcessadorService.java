package com.gabriel.base.service;

import com.gabriel.base.Jogada;
import com.gabriel.base.Mesa;

public interface ProcessadorService {
    boolean processar(Jogada jogada, Mesa mesa);
}
