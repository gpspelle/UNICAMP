import java.util.Scanner;
public class Inverte { 

    public static void main(String[] args) { 
        Scanner entrada = new Scanner(System.in);
        String buff;

        buff = entrada.nextLine();    
        StringBuffer sb = new StringBuffer(buff);
        
        sb.reverse();

        System.out.println(sb); 
    }
}
