public  class  ClassA {
    public  void  methodOne(int i) {
        System.out.println("methodOne A");
    }
    public  void  methodTwo(int i) {
        System.out.println("methodTwo A");
    }
    public  static  void  methodThree(int i){
        System.out.println("methodThree A");
    }
    public  static  void  methodFour(int i) {
        System.out.println("methodFour A");
    }

    public static void main(String[] args) {
       
        ClassA a = new ClassA(); 
        ClassB b = new ClassB();

        a.methodOne(1);
        a.methodTwo(1);
        a.methodThree(1);
        a.methodFour(1);
        b.methodOne(1);
        b.methodTwo(1);
        b.methodThree(1);
        b.methodFour(1);
    }
}
