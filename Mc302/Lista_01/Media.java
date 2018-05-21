public class Media { 

    public static int media(int a, int b) { 
        return (a+b)/2;
    }
    public static double media(double a, double b) { 
        return (a+b)/2;
    }
    public static void main(String[] args) { 
        int a = 3;
        int b = 3;

        double c = 1;
        double d = 2;
        
        int e = media(a, b);
        double f = media(c, d); 

        System.out.println(e);
        System.out.println(f);
    }
}
