package com.gabriel.base;
import com.gabriel.base.service.impl.CartaServiceImpl;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static com.gabriel.util.Util.MAX_CARDS;
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

    public void addCartas(List<Carta> cartas) {
        for(Carta i: cartas) {
            adicionarCarta(i);
        }
    }
    public Carta comprar() {
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
        int sizeA = a.vetorCartas.size();
        int sizeB = b.vetorCartas.size();

        if(sizeA != sizeB) {
            return false;
        }

        else {
            for(int i = 0; i < sizeA; i++) {
                Carta c = a.vetorCartas.get(i);
                Carta d = b.vetorCartas.get(i);
                if(!c.equals(d)) {
                    return false;
                }
            }
        }
        return true;
    }

    public int hashCode() {
        int hash = 0;
        for(Carta k : vetorCartas) {
            hash += k.hashCode();
        }
        return hash;
    }

}
