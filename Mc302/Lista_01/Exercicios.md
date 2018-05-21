Exercicios da Lista 1 - Esther Luna Colombini

    1. Uma classe possui um conjunto de atributos e metodos que definem as pro
    priedades relacionadas a um objeto. Ou seja, um objeto eh um propriamente
    um objeto de uma classe
    Ademais, podemos instanciar um objeto de uma classe.

    Exemplo: 

    public class Aluno { 

        public int nota;

        public Aluno() {
            nota = 0;
        }
        public Aluno(int n) {
            nota = n;
        }
        public void setNota(int n) {
            nota = n;
        }
        public int getNota() {
            return nota;
        }

        public static void main(String[] args) {
            Aluno a1 = new Aluno();
            Aluno a2 = new Aluno(2);
        }
    }
    
    No codigo acima, a1 e a2 sao objetos da classe aluno, inicializados com
    construtores diferentes, no caso.

    2. Programacao orientada a objetos eh uma das logicas de programacao exis-
    tentes, sendo java e c++ as principais linguagens que a utilizam. 
    Um dos beneficios da programacao a objeto eh a facilidade de manutencao de 
    codigo, aproveitamento de codigo de terceiros ou reutilizacao de proprio co
    digo, escalabilidade do codigo e encapsulamento. 
    Pois os atributos sao acessados atraves de metodos, e uma vez mantendo as 
    chamadas dos metodos podemos alterar a logica interna, adicionar eventos, 
    mudar chamadas internas da classe, sem fazer diferenca para o usuario.

    3. Lampada, atributos:preco, codigo de barras, quantidade em estoque.
    Metodos: get/set, vender lampada, estocar lampada.

    4. 
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
            + "Altura: " + getAltura()";
        }
    }

    5.
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
            andar = n;
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
    }

    6. Variaveis de classe sao variaveis marcadas por 'static' e nao necessitam
    de um objeto estanciado para ser utilizada. Por exemplo, a variavel
    numereroDeContas nao precisa de uma classe Conta para ser utilizada. Alem
    disso, essa variavel se relaciona a classe Conta, e pode ser incrementada
    toda vez que um objeto da classe Conta for instanciado. 

    Um atributo de instancia relaciona-se a um objeto em especifico, sendo
    pertinente apenas a ele. Um exemplo eh numeroDaConta, uma variavel que iden
    tifica a conta e nao tem ligacao a classe como um todo, mas sim a um objeto.
    
    Variaveis locais sao areas de memoria capazes de armazenar valores utiliza
    dos em operacoes durante o processamento do codigo, por exemplo guardar uma
    saida a ser impressa, ou uma auxiliar em um algoritmo de ordenacao. 

    Parametros sao as variaveis que um metodo espera que voce passe quando voce
    for invoca-lo, um exemplo sao os parametros que um construtor espera para
    inicializar as variaveis de um objeto. 

 
