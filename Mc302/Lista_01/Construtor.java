public class Construtor { 

    private int a, b;

    public Erro() {
        this(0,0);
        System.out.println("Sem argumentos");
    }
    public Erro(int c, int d) {
        System.out.println("Com argumentos");
        a = c;
        b = d;
    }
    public static void main(String[] args) {
        Erro e = new Erro();
        Erro f = new Erro(1, 1);
    }
}
