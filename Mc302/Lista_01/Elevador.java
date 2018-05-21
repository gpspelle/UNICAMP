public class Elevador { 
    private int andar = 0;
    private int quantidadeAndares;
    private int capacidade;
    private int pessoas = 0;
    
    public int getAndar() {
        return andar;
    }
    public int getQuantidadeAndares() {
        return quantidadeAndares;
    }
    public int getCapacidade() {
        return capacidade;
    }
    public int getPessoas() {
        return pessoas;
    }
    public void setAndar(int a) {
        andar = a;
    }
    public void setQuantidadeAndares(int q) {
        quantidadeAndares = q;
    }
    public void setCapacidade(int c) {
        capacidade = c;
    }
    public void setPessoas(int p) {
        pessoas = p;
    }
    public Elevador(int q, int c) { 
        quantidadeAndares = q;
        capacidade = c;  
    }
    public boolean entrar() {
       if(getPessoas() < getCapacidade()) {
            setPessoas(getPessoas() + 1);
            return true;
        } else { 
            return false;
        } 
    }
    public boolean sair() {
       if(getPessoas() > 0) {
            setPessoas(getPessoas() - 1);
            return true;
        } else { 
            return false;
        } 
    }
    public boolean subir() {
        if(getAndar() < getQuantidadeAndares()) {
            setAndar(getAndar() + 1);
            return true;
        } else {
            return false;
        }
    }
    public boolean descer() {
        if(getAndar() > 0) {
            setAndar(getAndar() - 1);
            return true;
        } else {
            return false;
        }
    } 
    
    public static void main(String[] args) { 
        Elevador e = new Elevador(5, 6);
    
        e.subir();

        if(e.descer()) {
            System.out.println("Desci");
        } else {
            System.out.println("Nao desci");
        }
    } 
} 
