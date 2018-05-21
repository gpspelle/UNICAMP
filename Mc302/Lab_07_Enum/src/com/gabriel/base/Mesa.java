package com.gabriel.base;

import com.gabriel.util.Util;

import java.util.ArrayList;
import java.util.Random;

public class Mesa {

    ArrayList<Carta> maoP;
    ArrayList<Carta> maoS;
    ArrayList<Carta> lacaiosP;
    ArrayList<Carta> lacaiosS;
    int poderHeroiP;
    int poderHeroiS;
    int manaP;
    int manaS;

    public Mesa() {
        maoP = null;
        maoS = null;
        lacaiosP = null;
        lacaiosS = null;
        manaP = 1;
        manaS = 1;
        poderHeroiP = Util.PODER_HEROI;
        poderHeroiS = Util.PODER_HEROI;
    }

    public Mesa(ArrayList<Carta> maoP, ArrayList<Carta> maoS, ArrayList<Carta> lacaiosP, ArrayList<Carta> lacaiosS, int poderHeroiP, int poderHeroiS, int manaP, int manaS) {
        this.maoP = maoP;
        this.maoS = maoS;
        this.lacaiosP = lacaiosP;
        this.lacaiosS = lacaiosS;
        this.poderHeroiP = poderHeroiP;
        this.poderHeroiS = poderHeroiS;
        this.manaP = manaP;
        this.manaS = manaS;
    }

    public ArrayList<Carta> getMaoP() {
        return maoP;
    }

    public void setMaoP(ArrayList<Carta> maoP) {
        this.maoP = maoP;
    }

    public ArrayList<Carta> getMaoS() {
        return maoS;
    }

    public void setMaoS(ArrayList<Carta> maoS) {
        this.maoS = maoS;
    }

    public ArrayList<Carta> getLacaiosP() {
        return lacaiosP;
    }

    public void setLacaiosP(ArrayList<Carta> lacaiosP) {
        this.lacaiosP = lacaiosP;
    }

    public ArrayList<Carta> getLacaiosS() {
        return lacaiosS;
    }

    public void setLacaiosS(ArrayList<Carta> lacaiosS) {
        this.lacaiosS = lacaiosS;
    }

    public int getPoderHeroiS() {
        return poderHeroiS;
    }

    public void setPoderHeroiS(int poderHeroiS) {
        this.poderHeroiS = poderHeroiS;
    }

    public int getPoderHeroiP() {
        return poderHeroiP;
    }

    public void setPoderHeroiP(int poderHeroiP) {
        this.poderHeroiP = poderHeroiP;
    }

    public int getManaP() {
        return manaP;
    }

    public void setManaP(int manaP) {
        this.manaP = manaP;
    }

    public int getManaS() {
        return manaS;
    }

    public void setManaS(int manaS) {
        this.manaS = manaS;
    }

    public void decPoderHeroi(int dec, char autor) {
        if(autor == 'P') {
            setPoderHeroiP(getPoderHeroiP() - dec);
        } else {
            setPoderHeroiS(getPoderHeroiS() - dec);
        }
    }

    public void decMana(int dec, char autor) {
        if(autor == 'P') {
            setManaP(getManaP() - dec);
        } else {
            setManaS(getManaS() - dec);
        }
    }

    public Carta sacarCarta(char autor) {
        Random gerador = new Random();
        if(autor == 'P' && lacaiosP.size() > 1) {
            return lacaiosP.remove(Util.randInt(gerador, 0, lacaiosP.size()-1) );
        } else if(lacaiosS.size() > 1) {
            return lacaiosS.remove(Util.randInt(gerador, 0, lacaiosS.size()-1));
        }
        return null;
    }
}
