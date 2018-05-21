package com.gabriel.base.service;

import com.gabriel.base.Carta;
import com.gabriel.base.cartas.TipoCarta;

import java.util.Random;

public interface CartaService {
     Carta geraCartaAleatoria(Random gerador, int maxMana, int maxAtaque, int maxVida, TipoCarta tc);
     int randInt(Random gerador, int min, int max);

}


