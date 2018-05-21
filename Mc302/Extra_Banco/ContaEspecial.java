public class ContaEspecial extends ContaBancaria {

    private double limite;
    
    public ContaEspecial() {
        super();
        limite = 0;
    } 
    public ContaEspecial(String nome, double saldo, double limite) {
        super(nome, saldo + limite);
        this.limite = limite;
    }
    public double getSaldo() {
       return super.getSaldo() - limite; 
    } 
}
