import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        ArrayList<Animal> bixos = new ArrayList<>();        
        bixos.add(new Cachorro());
        bixos.add(new Gato());
        bixos.add(new Vaca());
        bixos.add(new Vaca());
        bixos.add(new Vaca());
        bixos.add(new Vaca());
        bixos.add(new Vaca());
        bixos.add(new Vaca());
        bixos.add(new Vaca());

        for(Animal a: bixos) {
            a.emitirSom();
        }
    }
}
