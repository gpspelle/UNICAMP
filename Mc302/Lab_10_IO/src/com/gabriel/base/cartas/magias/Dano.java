package com.gabriel.base.cartas.magias;
import com.gabriel.base.Carta;
import com.gabriel.base.cartas.Lacaio;
import com.gabriel.io.Escritor;
import com.gabriel.io.ILaMaSerializable;

import java.io.IOException;
import java.util.UUID;
public class Dano extends Magia implements ILaMaSerializable{

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
    public void escreveAtributos(Escritor fw) throws IOException {
        fw.escreveDelimObj("obj " + getClass().getSimpleName());
        fw.escreveAtributo("nome", getNome());
        String s = String.valueOf(getId());
        fw.escreveAtributo("id", s);
        s = String.valueOf(getCustoMana());
        fw.escreveAtributo("custoMana", s);
        s = String.valueOf(getDano());
        fw.escreveAtributo("dano", s);
        fw.escreveDelimObj("obj " + getClass().getSimpleName());

    }
}
