package com.gabriel.base.cartas.magias;
import com.gabriel.base.Carta;
import com.gabriel.base.cartas.Lacaio;
import java.util.UUID;
public class Dano extends Magia { 

    private int dano;

    public Dano(UUID id, String nome, int custoMana, int dano) {
        super(id, nome, custoMana);
        this.dano = dano;
    }
    public Dano(String nome, int custoMana, int dano) {
        super(nome, custoMana);
        this.dano = dano;
    }
    public int getDano() {
        return dano;
    }
    public void setDano(int dano) {
        this.dano = dano;
    }
    public String toString() {
        return super.toString() + "Dano = " + getDano() + "\n";
    }
    public void usar(Carta alvo) {
        if(alvo instanceof Lacaio) {
            Lacaio f = (Lacaio) alvo;
            f.setVidaAtual(f.getVidaAtual() - getDano()); 
        }
    }
}
