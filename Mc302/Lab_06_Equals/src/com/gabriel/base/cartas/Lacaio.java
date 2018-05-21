package com.gabriel.base.cartas;
import java.util.UUID;
import com.gabriel.base.Carta;

public class Lacaio extends Carta { 

    private int ataque;
    private int vidaAtual;
    private int vidaMaxima;

    //Metodo construtor da Carta Lacaio 
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
    //Metodos comuns a uma Carta Lacaio 
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
    public String toString() { 
        return super.toString() + "Ataque = " + getAtaque() + "\n" +
        "Vida Atual = "+ getVidaAtual() + "\n" + "Vida Maxima = " + 
        getVidaMaxima() + "\n";
    } 
    public void usar(Carta alvo) {
        /*Preciso ter essa preocupacao?*/
        if(alvo instanceof Lacaio) {
            Lacaio f = (Lacaio) alvo;
            f.setVidaAtual(f.getVidaAtual() - ataque); 
        } 
    }
    public static void meuMetodoEstatico() {
        //System.out.println("Meu metodo da carta Lacaio");
    }
}
    
