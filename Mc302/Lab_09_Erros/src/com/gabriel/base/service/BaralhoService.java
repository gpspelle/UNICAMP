package com.gabriel.base.service;

import com.gabriel.base.Carta;

import java.util.List;
import java.util.Random;

public interface BaralhoService {

   List<Carta> preencheAleatorio(Random random, int tamanho, int maxMana, int maxAtaque, int maxVida);
}
