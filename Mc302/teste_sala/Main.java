import java.lang.reflect.Constructor;
import java.lang.Integer;

public class Main {
    public static void main(String[] args) {
        
        try {
            Constructor c = String.class.getConstructor(Integer.class);
            String s = (String)c.newInstance("Oi");
            System.out.println("Nova String: " + s);
        } catch(Exception e) {
            System.out.println(e.getCause());
            System.out.println(e.getClass());
            System.out.println("Dei errinho!");
        }
    }
}
