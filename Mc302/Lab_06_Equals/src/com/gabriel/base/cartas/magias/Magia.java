package com.gabriel.base.cartas.magias;
import java.util.UUID;
import com.gabriel.base.Carta;

public class Magia extends Carta {

    public Magia(UUID id, String nome, int custoMana) {
        super(id, nome, custoMana);
    }
    public Magia(String nome, int custoMana) {
        super(nome, custoMana);
    }
    /*Declarar esse metodo eh desncessario?*/
    public String toString() {
        return super.toString();    
    }
}
