package com.gabriel.base.controle;

import com.gabriel.base.Jogada;
import com.gabriel.base.Mesa;
import com.gabriel.base.service.ProcessadorService;

public class ProcessadorControle {

    ProcessadorService processadorService;

    public ProcessadorControle(ProcessadorService processadorService) {
        this.processadorService = processadorService;
    }

    public boolean processar(Jogada jogada, Mesa mesa) {
        return processadorService.processar(jogada, mesa);
    }
}
