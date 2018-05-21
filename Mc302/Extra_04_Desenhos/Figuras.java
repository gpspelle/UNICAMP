public class Figuras {
    
    public static void retangulo(int m, int n) {
        int i;
        int j;
        int k;
        for(i=0; i<=m; i++) {
            System.out.print("*");
        }
        System.out.print("\n");
        for(i=1; i<n-1; i++) {
            System.out.print("*");
            for(j=0; j<m-1; j++) {
                System.out.print(" ");
            }
            System.out.println("*");
        }
        for(i=0; i<=m; i++) {
            System.out.print("*");
        }
        System.out.println();
    }
    public static void circulo(int r) { 
        String[] out = new String[2*r+1];
        boolean entrei = false;
        int i;
        int j; 
        int k = r;
        int l=0;
        for(i=0; i<=2*r; i++) {
            for(j=0; j<=2*r; j++) {
                if(j==k-l || j==k+l) {
                    System.out.print("*");
                } else {
                    System.out.print(" ");
                }
            }
            if (l<r && !entrei)
                l++;
            else { 
                l--;
                entrei = true;
            }
            System.out.println();
        }
    }
    public static void main(String[] args) { 
        int a = 10;
        int b = 10;
        retangulo(a, b); 
      
        circulo(10);
    }
}
