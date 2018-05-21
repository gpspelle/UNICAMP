package com.gabriel.base;

public class Jogada {
    private Carta jogada;
    private Carta alvo;
    private char autor;

    public Jogada(Carta jogada, Carta alvo, char autor) {
        this.jogada = jogada;
        this.alvo = alvo;
        this.autor = autor;
    }

    public Carta getJogada() {
        return jogada;
    }

    public void setJogada(Carta jogada) {
        this.jogada = jogada;
    }

    public Carta getAlvo() {
        return alvo;
    }

    public void setAlvo(Carta alvo) {
        this.alvo = alvo;
    }

    public char getAutor() {
        return autor;
    }

    public void setAutor(char autor) {
        this.autor = autor;
    }

    public String toString() {
        return "Jogada " + jogada + " Alvo " + alvo + " Autor " + autor + "\n";
    }
}
