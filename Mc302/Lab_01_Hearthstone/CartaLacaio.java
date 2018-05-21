public class CartaLacaio extends Carta { 

    private int ataque;
    private int vidaAtual;
    private int vidaMaxima;

    //Metodo construtor da Carta Lacaio 
    public CartaLacaio(int ID, String nome, int ataque,
                           int vida, int mana) {
        super(ID, nome, mana);
        setAtaque(ataque);
        setVidaAtual(vida);
        setVidaMaxima(vida);
        /*      this.ataque = ataque;
                this.vidaAtual = vida;
                this.vidaMaxima = vida;
        */
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
