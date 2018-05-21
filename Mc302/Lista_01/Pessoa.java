public class Pessoa { 
    private String nome;
    private int idade;
    private int altura;

    public Pessoa(String n, int i, int a) {
        nome = n;
        idade = i;
        altura = a;
    }
    public String getNome() {
        return nome;
    }
    public int getIdade() {
        return idade;
    }
    public int getAltura() {
        return altura;
    }
    public void setNome(String n) {
        nome = n;
    }
    public void setIdade(int i) {
        idade = i;
    }
    public void setAltura(int a) {
        altura = a;
    }
    public String toString() {
        return "Nome: " + getNome() + "\n"  + "Idade: " + getIdade() + "\n"
        + "Altura: " + getAltura();
    }
    public static void main(String[] args) { 
        int i = 0;

        for(; i < 10; i++) {
            System.out.println(i);
        }
        System.out.println(i);
    }
}
