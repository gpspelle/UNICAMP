package com.gabriel.base;
import java.util.Random;
import com.gabriel.util.Util;

public class Baralho {
    private CartaLacaio[] vetorCartas;
    private static int nCartas = 0; 
    private static Random gerador = new Random();

    public Baralho() { 
        vetorCartas = new CartaLacaio[10];
    }
    public void adicionarCarta(CartaLacaio card) {
        vetorCartas[nCartas++] = card;
    } 
    public CartaLacaio comprarCarta() {
        return vetorCartas[--nCartas];
    }
    public void embaralhar() {
        int i;
        int j;
        CartaLacaio a;
        
        for(i = 1; i < nCartas; i++) {
            j = gerador.nextInt(i+1);
            if(j != i) {
                a = vetorCartas[i];
                vetorCartas[i] = vetorCartas[j]; 
                vetorCartas[j] = a;       
            } 
        }
        for(i = nCartas-1; i >= 0; i--) {
            System.out.println(vetorCartas[i]);
        } 
    }
}
