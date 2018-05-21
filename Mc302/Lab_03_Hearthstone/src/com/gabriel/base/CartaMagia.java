package com.gabriel.base;

public class CartaMagia extends Carta { 

    private int dano;
    private boolean area;

    //Metodo construtor da Carta de Magia 
    public CartaMagia(int ID, String nome, int dano, boolean area,
                          int mana) {
        super(ID, nome, mana);
        setDano(dano);
        setArea(area);
    }
    //Metodos comuns a uma Carta de Magia 
    public int getDano() { 
        return dano;
    }    
    public boolean getArea() { 
        return area;
    }
    public void setDano(int dano) {
        this.dano = dano;
    }
    public void setArea(boolean area) {
       this.area = area; 
    }
    public String toString() { 
        String out = super.toString();
        out = out + "Dano = " + getDano() + "\n";
        out = out + "Area = " + getArea() + "\n";
        
        return out;
    }
}
