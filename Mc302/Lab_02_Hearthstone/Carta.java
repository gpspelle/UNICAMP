public class Carta { 
    
    private int ID;
    private String nome;
    private int custoMana;

    //Metodo construtor de uma Carta 'super()';
    public Carta(int ID, String nome, int mana) { 
        setID(ID);
        setCustoMana(mana);
        setNome(nome);
    }
    public Carta(CartaLacaio origem) {
        setID(origem.getID());
        setCustoMana(origem.getCustoMana());
        setNome(origem.getNome());
    }
    //Metodos comuns a uma Carta
    public String getNome() { 
        return nome;
    }
    public int getCustoMana() { 
        return custoMana;
    }
    public int getID() { 
        return ID;
    }
    public void setID(int ID) {
        this.ID = ID;
    }
    public void setCustoMana(int mana) {
        this.custoMana = mana;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String toString() { 
        String out = getNome() + " (ID: " + getID() + ")\n";
        out = out + "Custo de Mana = "+ getCustoMana() + "\n";
        
        return out;
    }
}
