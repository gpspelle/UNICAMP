public class Gamma extends Beta{
    int over = 3;

    public static void main(String[] args) {
        new Gamma().go();
    }    
    public void go() {
            Alpha b = new Beta();
            Alpha a = new Beta();
            Gamma c = new Gamma();

        ((Alpha)b).go();
            System.out.println(c.over + " " + b.over + " " + a.over);
    }
}
