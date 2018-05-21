public class Test{
    void thrower() throws A {
        throw new A();
    }
    public static void main(String[] args) {
        Test t = new Test();
        try {
            t.thrower ();
        }
        catch(B e) {

        }
    }
}
