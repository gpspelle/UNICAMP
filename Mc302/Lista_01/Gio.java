public class Gio {

    public float metodo() {
        System.out.println("Passar da compilacao");
        return 1.0f;
    }
    public static void main(String[] args) {
        
        Gio a = new Gio();
        Eu b = new Eu();
       
        b.metodo(1);
        a.metodo(); 
    }

}
