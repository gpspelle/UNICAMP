package com.gabriel.util;
import com.gabriel.base.*;

public abstract class Util {

    public static final int MAX_CARDS = 30;
 
    public static void buffar(CartaLacaio carta, int a) {
        buffar(carta, a, a);
    }
    public static void buffar(CartaLacaio carta, int a, int v) {
        if(a > 0 && v > 0) {
            carta.setVidaAtual(carta.getVidaAtual() + a);
            carta.setVidaMaxima(carta.getVidaMaxima() + v);
            alteraNomeFortalecido(carta);
        }
    }
    public static void alteraNomeFortalecido(CartaLacaio carta) {
        carta.setNome(carta.getNome() + " Buffed");
    }
}
