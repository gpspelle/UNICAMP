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
    }
    public CartaLacaio(int ID, String nome, int mana) { 
        super(ID, nome, mana);
    }
    public CartaLacaio(CartaLacaio origem) {
        this(origem.getID(), origem.getNome(), origem.getAtaque(),
        origem.getVidaAtual(), origem.getCustoMana());
    }
    //Metodos comuns a uma Carta Lacaio 
    public void alteraNomeFortalecido() {
        setNome(getNome() + " Buffed");
    }
    public void buffar(int a) {
        alteraNomeFortalecido();
        setVidaAtual(this.vidaAtual + a);
        setAtaque(this.ataque + a);
    }
    public void buffar(int a, int v) {
        alteraNomeFortalecido();
        setVidaAtual(this.vidaAtual + v);
        setAtaque(this.ataque + a);
    }
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
