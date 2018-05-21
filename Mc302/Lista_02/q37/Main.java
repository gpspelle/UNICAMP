import java.io.File;

public class Main { 

    public static void main(String[] args) {
        
        File f = new File("teste2");

        if(f.exists()) {
            System.out.println("Existe");
        } else {
            System.out.println("Nao Existe");
        }
        
    }
}
