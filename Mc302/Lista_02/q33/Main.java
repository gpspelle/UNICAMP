public class Main { 
    public static void main(String[] args) {

       for(int i = 0; i < 4; i++) {
            try {
                methodA(i);         
            } catch(ExceptionA a) {
                if(a instanceof ExceptionB) {
                    System.out.println("Eh b");
                } else if(a instanceof ExceptionC) {
                    System.out.println("Eh c");
                } else {
                    System.out.println("Eh a");
                }
                System.out.println(a);
            }
        }
    }
    public static void methodA(int x) throws ExceptionA {
       
        if(x == 1) {
            throw new ExceptionB();
        } else if(x == 2) {
            throw new ExceptionC();
        } else {
            throw new ExceptionA();
        } 
    }
}
