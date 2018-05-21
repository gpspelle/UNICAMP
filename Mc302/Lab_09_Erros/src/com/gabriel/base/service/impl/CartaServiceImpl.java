package com.gabriel.base.service.impl;

import com.gabriel.base.Carta;
import com.gabriel.base.cartas.HabilidadesLacaio;
import com.gabriel.base.cartas.Lacaio;
import com.gabriel.base.cartas.TipoCarta;
import com.gabriel.base.cartas.magias.Buff;
import com.gabriel.base.cartas.magias.Dano;
import com.gabriel.base.cartas.magias.DanoArea;
import com.gabriel.base.service.CartaService;
import com.gabriel.util.RandomString;

import java.util.Random;

public class CartaServiceImpl implements CartaService {
    private RandomString stringGerador;
    private HabilidadesLacaio habilidade;
    private TipoCarta escolhido;

    public CartaServiceImpl() {

    }

    public Carta geraCartaAleatoria(Random gerador, int maxMana, int maxAtaque, int maxVida, TipoCarta tc) {

        stringGerador = new RandomString(gerador, 10);

        if(tc == null) {
            int pos = randInt(gerador, 0, TipoCarta.values().length - 1);
            tc = TipoCarta.values()[pos];
        }
        Carta carta = null;
        if (tc == TipoCarta.LACAIO) {

            int vida = randInt(gerador, 1, maxVida);
            int pos = randInt(gerador, 0, HabilidadesLacaio.values().length - 1);
            HabilidadesLacaio minhaHabilidade = HabilidadesLacaio.values()[pos];
            carta = new Lacaio(stringGerador.nextString(), randInt(gerador, 1, maxMana), randInt(gerador, 1, maxAtaque), vida, vida, minhaHabilidade);
        } else if (tc == TipoCarta.DANO) {

            carta = new Dano(stringGerador.nextString(), randInt(gerador, 1, maxMana), randInt(gerador, 1, maxAtaque));
        } else if (tc == TipoCarta.BUFF) {
            return new Buff(stringGerador.nextString(), randInt(gerador, 1, maxMana), randInt(gerador, 1, maxAtaque), randInt(gerador, 1, maxVida));
        } else if (tc == TipoCarta.DANO_AREA) {
            carta = new DanoArea(stringGerador.nextString(), randInt(gerador, 1, maxMana), randInt(gerador, 1, maxAtaque));
        }

        return carta;
    }

    public int randInt(Random gerador, int min, int max) {
        return gerador.nextInt((max-min) + 1) + min;
    }
}
