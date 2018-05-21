package com.gabriel.base.cartas;
import java.util.UUID;
import com.gabriel.base.Carta;

public class Lacaio extends Carta { 

    private int ataque;
    private int vidaAtual;
    private int vidaMaxima;
    private HabilidadesLacaio habilidadeLacaio;

    public Lacaio(UUID id, String nome, int custoMana, int ataque,
                  int vidaAtual, int vidaMaxima) {
            super(id, nome, custoMana);
            this.ataque = ataque;
            this.vidaAtual = vidaAtual;
            this.vidaMaxima = vidaMaxima;
    }

    public Lacaio(String nome, int custoMana, int ataque, int vidaAtual,
                  int vidaMaxima) {
            super(nome, custoMana);
            this.ataque = ataque;
            this.vidaAtual = vidaAtual;
            this.vidaMaxima = vidaMaxima;
    }

    public Lacaio(String nome, int custoMana, int ataque, int vidaAtual, int vidaMaxima, HabilidadesLacaio habilidadeLacaio) {
       this(nome, custoMana, ataque, vidaAtual, vidaMaxima);
       this.habilidadeLacaio = habilidadeLacaio;
    }

    public int getAtaque() {
        return ataque;
    }    
    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }
    public int getVidaAtual() { 
        return vidaAtual;
    }
    public void setVidaAtual(int vidaAtual) {
        this.vidaAtual = vidaAtual;
    }
    public int getVidaMaxima() { 
        return vidaMaxima;
    }
    public void setVidaMaxima(int vidaMaxima) {
        this.vidaMaxima = vidaMaxima;
    }
    public HabilidadesLacaio getHabilidadeLacaio() {
        return habilidadeLacaio;
    }
    public void setHabilidadeLacaio(HabilidadesLacaio habilidadeLacaio) {
        this.habilidadeLacaio = habilidadeLacaio;
    }
    public String toString() {
        return super.toString() + "Ataque = " + getAtaque() + "\n" +
        "Vida Atual = "+ getVidaAtual() + "\n" + "Vida Maxima = " + 
        getVidaMaxima() + "\n";
    }

    public void usar(Carta alvo) {
        if(alvo instanceof Lacaio) {
            Lacaio f = (Lacaio) alvo;
            f.setVidaAtual(f.getVidaAtual() - ataque);
            this.setVidaAtual(vidaAtual - f.getAtaque());
        } 
    }
}
    
