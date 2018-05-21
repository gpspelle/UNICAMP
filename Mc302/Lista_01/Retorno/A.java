public class A {
    public void a1() {
        System.out.println("Metodo a1");
    }
    public float a2() { 
        System.out.println("Metodo a2");
    
        return 1.0f;
    }

    public static void main(String[] args) { 
        A a = new A();
        B b = new B();
        
        a.a1();
        a.a2();
        b.a1();
        b.a2();
    }
}
