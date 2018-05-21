package com.gabriel.base;

import java.util.UUID;

public class Carta {
    
    private UUID id;
    private String nome;
    private int custoMana;

    public Carta(UUID id, String nome, int custoMana) {
        this(nome, custoMana);
        this.id = id;
    }
    public Carta(String nome, int custoMana) {
        this.nome = nome;
        this.custoMana = custoMana;
        this.id = UUID.randomUUID();
    }

    public String getNome() {
        return nome;
    }
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

    @Override
    public boolean equals(Object o) {
        return this == o || o instanceof Carta && id.equals(((Carta) o).id);
    }

    public int hashCode() {
        int hash = 3;
        hash = 67 * (id != null ? id.hashCode() : 0);
        hash = 13 * hash + nome.hashCode();
        hash = 29 * hash + custoMana;

        return hash;
    }
}
