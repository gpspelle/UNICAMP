public  class A {
    int val = 1; 

    public  void m1() {  }
    public  void m1( int h ) { }
    public  static void m3( ) { System.out.println("Oi gut em A"); }

    public static void main(String[] args) {
        A a = new A();
        B b = new B();
        A c = new B();
        
        if(c instanceof A && c instanceof B) {
            System.out.println(c.getClass());
        }
        c.m3();
        a.m3();
        b.m3(); 
    }
}

