package com.gabriel.base.service.impl;

import com.gabriel.base.Carta;
import com.gabriel.base.exception.BaralhoVazioException;
import com.gabriel.base.service.BaralhoService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import static java.lang.Integer.min;

import static com.gabriel.util.Util.MAX_CARDS;

public class BaralhoServiceImpl implements BaralhoService{

    public List<Carta> preencheAleatorio(Random gerador, int tamanho, int maxMana, int maxAtaque, int maxVida) {
        List<Carta> vetorCartas = new ArrayList<>();

        CartaServiceImpl carta = new CartaServiceImpl();
        try {
            if(tamanho == 0) {
                throw new BaralhoVazioException("Baralho vazio");
            }
            for (int i = 0; i < min(tamanho, MAX_CARDS); i++) {
                vetorCartas.add(carta.geraCartaAleatoria(gerador, maxMana, maxAtaque, maxVida, null));
            }
        } catch(BaralhoVazioException baralho) {
            //toDo: nao entendi o tratamento, eh isso que eh pra fazer?
            vetorCartas.add(carta.geraCartaAleatoria(gerador, maxMana, maxAtaque, maxVida, null));
        }
        return vetorCartas;
    }
}
