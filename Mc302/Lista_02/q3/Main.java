import java.util.ArrayList;

public class Main {
    
    public static void main(String[] args) {
    
        ArrayList<Forma> formas = new ArrayList<>();               
        Forma circulo = new Circulo(1);
        Forma quadrado = new Quadrado(2);
        formas.add(circulo);
        formas.add(quadrado);

        for(Forma forma: formas) {
            System.out.println(forma.calculaArea());
            System.out.println(forma.calcularPerimetro());
        }
    }
}
