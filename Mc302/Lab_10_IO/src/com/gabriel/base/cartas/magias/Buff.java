package com.gabriel.base.cartas.magias;
import com.gabriel.base.Carta;
import com.gabriel.base.cartas.Lacaio;
import com.gabriel.io.Escritor;
import com.gabriel.io.ILaMaSerializable;

import java.io.IOException;
import java.util.UUID;
public class Buff extends Magia implements ILaMaSerializable{
    
    private int aumentoEmAtaque;
    private int aumentoEmVida;
    
    public Buff(UUID id, String nome, int custoMana, int aumentoEmAtaque,
                int aumentoEmVida) {
        super(id, nome, custoMana);
        this.aumentoEmAtaque = aumentoEmAtaque;
        this.aumentoEmVida = aumentoEmVida; 
    }   
    public Buff(String nome, int custoMana, int aumentoEmAtaque,
                int aumentoEmVida) {
        super(nome, custoMana);
        this.aumentoEmAtaque = aumentoEmAtaque;
        this.aumentoEmVida = aumentoEmVida; 
    }
    public int getAumentoEmAtaque() {
        return aumentoEmAtaque;
    }
    public void setAumentoEmAtaque(int aumentoEmAtaque) {
        this.aumentoEmAtaque = aumentoEmAtaque;
    }
    public int getAumentoEmVida() {
        return aumentoEmVida;
    }
    public void setAumentoEmVida(int aumentoEmVida) {
        this.aumentoEmVida = aumentoEmVida;
    }
    public String toString() {
        return super.toString() + "Aumento em Ataque = " 
        + getAumentoEmAtaque() + "\n" + "Aumento em Vida = " 
        + getAumentoEmVida() + "\n";
    }
    public void usar(Carta alvo) {
        if(alvo instanceof Lacaio) {
            Lacaio f = (Lacaio) alvo;
            f.setVidaAtual(f.getVidaAtual() + aumentoEmVida);
            f.setVidaMaxima(f.getVidaMaxima() + aumentoEmVida);
            f.setAtaque(f.getAtaque() + aumentoEmAtaque);
        }
    }
    public void escreveAtributos(Escritor fw) throws IOException {
        fw.escreveDelimObj("obj " + getClass().getSimpleName());
        fw.escreveAtributo("nome", getNome());
        String s = String.valueOf(getId());
        fw.escreveAtributo("id", s);
        s = String.valueOf(getCustoMana());
        fw.escreveAtributo("custoMana", s);
        s = String.valueOf(getAumentoEmAtaque());
        fw.escreveAtributo("aumentoEmAtaque", s);
        s = String.valueOf(getAumentoEmVida());
        fw.escreveAtributo("aumentoEmVida", s);
        fw.escreveDelimObj("obj " + getClass().getSimpleName());
    }
}
