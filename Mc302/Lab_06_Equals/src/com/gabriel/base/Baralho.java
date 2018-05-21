package com.gabriel.base;
import java.util.ArrayList;
import java.util.Collections;
import com.gabriel.util.Util;

public class Baralho { 
   
    private ArrayList<Carta> vetorCartas;

    public Baralho() {
        vetorCartas = new ArrayList<Carta>();
    }
    public void adicionarCarta(Carta carta) {
        if(vetorCartas.size() <= Util.MAX_CARDS) {
            vetorCartas.add(carta);
        } else {
            System.out.println("Baralho lotado!");
        }
    }
    public Carta comprarCarta() {
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
    public String toString() {
        String out = "";
        for(int i = 0 ; i < vetorCartas.size(); i++) {
           out += vetorCartas.get(i);
        }
        return out;
    }
    public boolean equals(Baralho a, Baralho b) {
        boolean answer = true;
        int sizeA = a.vetorCartas.size();
        int sizeB = b.vetorCartas.size();

        if(sizeA != sizeB) {
            answer = false;
        }
        else {
            for(int i = 0; i < sizeA; i++) {
                Carta c = a.vetorCartas.get(i);
                Carta d = b.vetorCartas.get(i);
                if(c.getNome().equals(d.getNome()) && c.getCustoMana() == d.getCustoMana()) {
                    answer = true;
                } else {
                    answer = false;
                }
            }
        }
        return answer;
    }
    public int hashCode() {
        int hash = 0;
        for(Carta k : vetorCartas) {
            hash += k.hashCode();
        }
        return hash;
    }

}
