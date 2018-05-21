import java.util.Scanner;

public class Main { 

    public static void main(String[] args) {
        
        int leitura; 
        ContaBancaria c;
            
        Scanner scanner = new Scanner(System.in); 

        leitura = scanner.nextInt();

        if(leitura == 0) {
            c = new ContaBancaria("Paulo", 1);
        } else {
            c = new ContaEspecial("Paulo", 1, 2);
        }
        
        System.out.println(c);
    }
}
