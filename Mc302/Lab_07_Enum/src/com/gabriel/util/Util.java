package com.gabriel.util;
import com.gabriel.base.Carta;
import com.gabriel.base.TipoCarta;
import com.gabriel.base.cartas.HabilidadesLacaio;
import com.gabriel.base.cartas.Lacaio;
import com.gabriel.base.cartas.magias.Buff;
import com.gabriel.base.cartas.magias.Dano;
import com.gabriel.base.cartas.magias.DanoArea;

import java.util.Random;

public abstract class Util {

    public static final int PODER_HEROI = 0;
    public static final int MAX_CARDS = 30;
    public static final int MAO_INI = 3;
    public static void buffar(Lacaio carta, int a) {
        buffar(carta, a, a);
    }
    public static void buffar(Lacaio carta, int a, int v) {
        if(a > 0 && v > 0) {
            carta.setVidaAtual(carta.getVidaAtual() + a);
            carta.setVidaMaxima(carta.getVidaMaxima() + v);
            alteraNomeFortalecido(carta);
        }
    }
    public static void alteraNomeFortalecido(Lacaio carta) {
        carta.setNome(carta.getNome() + " Buffed");
    }
    public static Carta geraCartaleatoria(Random gerador, int maxMana, int maxAtaque, int maxVida, TipoCarta tc) {
        RandomString stringGerador = new RandomString(gerador, 10);
        if (tc == TipoCarta.LACAIO) {
            int vida = randInt(gerador, 1, maxVida);
            int pos = randInt(gerador, 0, HabilidadesLacaio.values().length - 1);
            HabilidadesLacaio minhaHabilidade = HabilidadesLacaio.values()[pos];
            return new Lacaio(stringGerador.nextString(), randInt(gerador, 1, maxMana), randInt(gerador, 1, maxAtaque), vida, vida, minhaHabilidade);
        } else if (tc == TipoCarta.DANO) {
            return new Dano(stringGerador.nextString(), randInt(gerador, 1, maxMana), randInt(gerador, 1, maxAtaque));
        } else if (tc == TipoCarta.BUFF) {
            return new Buff(stringGerador.nextString(), randInt(gerador, 1, maxMana), randInt(gerador, 1, maxAtaque), randInt(gerador, 1, maxVida));
        } else if(tc == TipoCarta.DANO_AREA) {
            return new DanoArea(stringGerador.nextString(), randInt(gerador, 1, maxMana), randInt(gerador, 1, maxAtaque));
        } else {
            /*loop danger*/
            int pos = randInt(gerador, 0, TipoCarta.values().length - 1);
            TipoCarta tipo = TipoCarta.values()[pos];
            geraCartaleatoria(gerador, maxMana, maxAtaque, maxVida, tipo);
        }
        return null;
    }
    public static int randInt(Random gerador, int min, int max) {
        return gerador.nextInt((max-min) + 1) + min;
    }
}
