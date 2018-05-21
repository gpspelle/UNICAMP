public class ExceptionA extends Throwable{

    public ExceptionA(String a) {
        super(a);
    }
    public ExceptionA() {
        super("A");
    }
}
