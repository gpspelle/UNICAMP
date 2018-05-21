/*  Gabriel Pellegrino da Silva /172358
    Laboratório 2
*/

public class Main { 

    public static void main(String[] args) { 
        CartaLacaio lac1 = new CartaLacaio(1, "Frodo Bolseiro", 2, 1, 1); 
        CartaLacaio lac2 = new CartaLacaio(2, "Aragorn", 5, 7, 6);
        CartaLacaio lac3 = new CartaLacaio(3, "Legolas", 8, 4, 6);
        CartaMagia mag1 = new CartaMagia(1, "You shall not pass", 4, true, 7);
        CartaMagia mag2 = new CartaMagia(2, "Telecinese", 3, false, 2);

        CartaLacaio lac4 = new CartaLacaio(5, "Paulo", 10);
        
        /*Tarefa 1, por default, os atribuitos nao definidos, sao inicializados
        com valor 0 pelo construtor de Object*/ 
        System.out.println(lac4); 

        /*Tarefa 2, alterando os dados de um lacaio*/
        System.out.println(lac1); 
        lac1.setAtaque(lac3.getAtaque());
        System.out.println(lac1); 
        
        /*Tarefa 3, ao comentar o metodo toString da classe CartaMagia, sao 
        impressos apenas os atributos da superclasse relacionada, Carta*/
        /*No arquivo Respostas esta detalhada o que aconteceria se nao fosse
        utilizado herenca*/
        System.out.println(mag1);
      
        /*Tarefa 4, nao ha diferenca entre as impressoes abaixo*/ 
        CartaLacaio lac5 = new CartaLacaio(lac2); 
        System.out.println(lac5); 
        System.out.println(lac2);
   
        /*Tarefa 5, nao podemos imprimir mag1.nome quando o atributo
        nome eh declarado private, porque apenas dentro da classe temos
        acesso a esse atributo, fora dela nao. Para acessar esse atributo
        temos que criar metodos na classe que fazem isso*/
    
        /*A intencao de criar metodos get e set com atributos privados
        e evitar que o usuario possa danificar as informacoes de um sistema.
        Pois, ele apenas teria acesso aquilo que lhe fosse permitido, nos
        moldes e maneiras que lhe fosse permitido utilizar*/ 
        //System.out.println(mag1.nome); 
        System.out.println(mag1.getNome());

        /*Tarefa 6, impressao das cartas antes do buff*/ 
        System.out.println(lac1); 
        System.out.println(lac2); 
        
        /*Buff das cartas*/
        lac1.buffar(3);
        lac2.buffar(10, 20); 
       
        /*Impressao das cartas apos o buff*/ 
        System.out.println(lac1); 
        System.out.println(lac2); 
    }
}
