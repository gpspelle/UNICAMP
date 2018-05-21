public class ContaBancaria {

    private String nome;
    private double saldo;
    private int numero;
    private static int numContas = 0;

    public ContaBancaria() {
        saldo = 0;
        setNumero(++numContas); 
    }
    public ContaBancaria(String nome, double saldo) {
        this.nome = nome;
        this.saldo = saldo;
        setNumero(++numContas); 
    }
    public void setNumero(int numero) {
        this.numero = numero;
    }
    public int getNumero() {
        return this.numero;
    }
    public boolean sacar(double quantia) {
        if(quantia <= saldo) {
            saldo-=quantia;
            return true;
        } else {
            return false;
        }
    }
    public boolean depositar(double quantia) {
        if(quantia >= 0) {
            saldo+=quantia;
            return true;
        } else {
            return false;
        }
    }
    public double getSaldo() {
        return saldo;
    }
}
