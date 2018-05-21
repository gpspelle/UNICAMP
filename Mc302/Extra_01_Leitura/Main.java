import java.util.Scanner;

class Main {

    public static void main(String[] args) {

        int i, leitura;
        Scanner entrada = new Scanner(System.in);
        
        System.out.println("Aguardando insercao");
        
        for(i=0; i<10; i++) {
            leitura = entrada.nextInt();
            System.out.println("O dado inserido eh " + leitura);
        }
    }
} 
