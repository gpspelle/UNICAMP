package com.gabriel.base;

public class CartaLacaio extends Carta { 

    private int ataque;
    private int vidaAtual;
    private int vidaMaxima;

    //Metodo construtor da Carta Lacaio 
    public CartaLacaio(int ID, String nome, int ataque, int vidaAtual, 
                       int vidaMaxima, int mana) {
        super(ID, nome, mana);
        setAtaque(ataque);
        setVidaAtual(vidaAtual);
        setVidaMaxima(vidaMaxima);
    }
    public CartaLacaio(int ID, String nome, int ataque, int vida, 
                       int mana) {
        this(ID, nome, ataque, vida, vida, mana);
    }
    public CartaLacaio(int ID, String nome, int mana) { 
        super(ID, nome, mana);
    }
    public CartaLacaio(CartaLacaio origem) {
        this(origem.getID(), origem.getNome(), origem.getAtaque(),
        origem.getVidaAtual(), origem.getVidaMaxima(), origem.getCustoMana());
    }
    //Metodos comuns a uma Carta Lacaio 
    public int getAtaque() { 
        return ataque;
    }    
    public int getVidaAtual() { 
        return vidaAtual;
    }
    public int getVidaMaxima() { 
        return vidaMaxima;
    }
    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }
    public void setVidaAtual(int vidaAtual) {
        this.vidaAtual = vidaAtual;
    }
    public void setVidaMaxima(int vidaMaxima) {
        this.vidaMaxima = vidaMaxima;
    }
    public String toString() { 
        String out = super.toString();
        out = out + "Ataque = "+ getAtaque() + "\n";
        out = out + "Vida Atual = "+ getVidaAtual() + "\n";
        out = out + "Vida Maxima = "+ getVidaMaxima() + "\n";
        
        return out;
    } 
}
