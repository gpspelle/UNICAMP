package com.gabriel.base;

import java.util.Comparator;

public class CartaComparator implements Comparator<Carta> {
    @Override
    public int compare(Carta carta, Carta t1) {
        if(Carta.equals(carta, t1)) {
            return 0;
        } else {
            return 1;
        }
    }
}
