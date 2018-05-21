public class A {
    int a = 1;
    int c = 1; 
    public void metodo() {
        System.out.println("Metodo de A");
    }
    public static void main(String[] args) {
        
        A b = new B();
        System.out.println(((B)b).c);
    }
}
