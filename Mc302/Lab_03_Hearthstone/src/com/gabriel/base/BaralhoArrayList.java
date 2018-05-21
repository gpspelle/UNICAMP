package com.gabriel.base;
import java.util.ArrayList;
import java.util.Collections;
import com.gabriel.util.Util;

public class BaralhoArrayList { 
   
    private ArrayList<CartaLacaio> vetorCartas;

    public BaralhoArrayList() {
        vetorCartas = new ArrayList<CartaLacaio>();
    }
    public void adicionarCarta(CartaLacaio carta) {
        if(vetorCartas.size() <= Util.MAX_CARDS) {
            vetorCartas.add(carta);
        } else {
            System.out.println("Baralho lotado!");
        }
    }
    public CartaLacaio comprarCarta() {
        return vetorCartas.remove(vetorCartas.size() - 1);
    } 
    public void embaralhar() {
        int i;
        Collections.shuffle(vetorCartas);
        /*Por questoes de eficiencia de codigo, em tempo de execucao, foi imple
        mentado o for abaixo, ao inves de inverter e desenverter o vetor com o
        metodo proposto pela classe Collections*/
        for(i = vetorCartas.size() - 1 ; i >= 0; i--) {
            System.out.println(vetorCartas.get(i));
        }
    }
}
