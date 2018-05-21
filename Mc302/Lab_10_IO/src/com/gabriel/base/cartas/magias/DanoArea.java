package com.gabriel.base.cartas.magias;
import java.io.IOException;
import java.util.ArrayList;
import com.gabriel.base.Carta;
import java.util.UUID;
import com.gabriel.base.cartas.Lacaio;
import com.gabriel.io.Escritor;
import com.gabriel.io.ILaMaSerializable;

public class DanoArea extends Dano implements ILaMaSerializable{

    public DanoArea(UUID id, String nome, int custoMana, int dano) {
        super(id, nome, custoMana, dano);
    } 
    public DanoArea(String nome, int custoMana, int dano) {
        super(nome, custoMana, dano);
    }
    public String toString() { 
        return super.toString();
    }
    public void usar(ArrayList<Carta> alvos) {
        for(Carta alvo : alvos) {
            if (alvo instanceof Lacaio) {
                Lacaio f = (Lacaio) alvo;
                f.setVidaAtual(f.getVidaAtual() - getDano());
            }
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
