import java.util.Scanner;

class Main { 

    public static void main (String[] args) {
        
        System.out.println("Insira um inteiro 'n'");
        Scanner entrada = new Scanner(System.in);
    
        int i=2, j, n, teste, encontrados=0;
        n = entrada.nextInt();
            
        while(encontrados < n) {
            teste=0; 
            for(j=1; j<=i; j++) {
                if(i%j==0)
                   teste++; 
            }
            if(teste==2) {
                encontrados++;
                System.out.println("O numero " +i + " eh primo");    
            }
            i++;
        }
    }
}
