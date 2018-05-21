package com.gabriel.base;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import com.gabriel.util.Util;

import static com.gabriel.util.Util.MAX_CARDS;
import static com.gabriel.util.Util.geraCartaleatoria;
import static java.lang.Integer.min;

public class Baralho { 
   
    private ArrayList<Carta> vetorCartas;

    public Baralho() {
        vetorCartas = new ArrayList<>();
    }
    public void adicionarCarta(Carta carta) {
        if(vetorCartas.size() <= MAX_CARDS) {
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
    public void preencheAleatorio(Random gerador, int tamanho, int maxMana, int maxAtaque, int maxVida) {
       for (int i = 0; i < min(tamanho, MAX_CARDS); i++) {
           vetorCartas.add(geraCartaleatoria(gerador, maxMana, maxAtaque, maxVida, null));
       }
    }
}
