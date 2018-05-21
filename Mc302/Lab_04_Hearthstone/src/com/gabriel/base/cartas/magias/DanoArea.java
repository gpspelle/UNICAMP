package com.gabriel.base.cartas.magias;
import java.util.ArrayList;
import com.gabriel.base.Carta;
import java.util.UUID;
import com.gabriel.base.cartas.Lacaio;
public class DanoArea extends Dano {

    public DanoArea(UUID id, String nome, int custoMana, int dano) {
        super(id, nome, custoMana, dano);
    } 
    public DanoArea(String nome, int custoMana, int dano) {
        super(nome, custoMana, dano);
    }
    public String toString() { 
        return super.toString();
    }
    public void usar(ArrayList<Carta> alvos) {
        int i;
        for(i = 0; i < alvos.size(); i++) {
            Lacaio f = (Lacaio) alvos.get(i);
            f.setVidaAtual(f.getVidaAtual() - getDano());
        } 
    }
} 
