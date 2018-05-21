public class Aluno { 

    private String nome;
    private int matricula; 
    private int idade;
    private double altura;
 

    public Aluno() {
    
    }       
    public Aluno(String nome) { 
        this.nome = nome;
    }
    public String getNome() {
        return nome;
    } 
    public int getMatricula() {
        return matricula;
    } 
    public int getIdade() {
        return idade;
    } 
    public double getAltura() {
        return altura;
    } 
    public void setNome(String nome) {
        this.nome = nome;
    } 
    public void setMatricula(int matricula) {
        this.matricula = matricula;
    } 
    public void setIdade(int idade) {
        this.idade = idade;
    } 
    public void setAltura(double altura) {
        this.altura = altura;
    }
    public String toString() { 
        String out = getNome() + "\n" + getMatricula() + "\n"
        + getIdade() + "\n" + getAltura() + "\n";
        
        return out; 
    } 
}
