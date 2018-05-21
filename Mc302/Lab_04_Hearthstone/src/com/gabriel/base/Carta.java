package com.gabriel.base;
import java.util.UUID;

public class Carta { 
    
    private UUID id;
    private String nome;
    private int custoMana;

    //Metodo construtor de uma Carta 'super()';
    public Carta(UUID id, String nome, int custoMana) { 
        this(nome, custoMana);
        this.id = id;
    }
    public Carta(String nome, int custoMana) {
        this.nome = nome;
        this.custoMana = custoMana;
    }
    //Metodos comuns a uma Carta
    public String getNome() { 
        return nome;
    }
    /*Esse metodo foi criado para poder alterar o nome da carta
    no metodo buffar da classe Util*/
    public void setNome(String nome) {
       this.nome = nome; 
    }
    public int getCustoMana() { 
        return custoMana;
    }
    public UUID getId() { 
        return id;
    }
    public void setCustoMana(int mana) {
        this.custoMana = mana;
    }
    public String toString() {
        return getNome() + " (id: " + getId() + ")\n" + "Custo de Mana = "
        + getCustoMana() + "\n";
    }
    public void usar(Carta alvo) {

    }
    public static void meuMetodoEstatico() {
        System.out.println("Metodo Estatico de Carta");
    }
}
