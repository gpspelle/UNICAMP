/*  Gabriel Pellegrino da Silva /17258
    Laboratório 6 :
*/

import com.gabriel.base.*;
import com.gabriel.base.cartas.*;
import com.gabriel.base.cartas.magias.*;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) { 
        ArrayList<Carta> cartas = new ArrayList<>();
        LinkedList<Carta> cartasLinked = new LinkedList<>();
        Vector<Carta> cartasVector = new Vector<Carta>();

        HashSet<Carta> cartasHash = new HashSet<Carta>();
        TreeSet<Carta> cartasTree = new TreeSet<Carta>(new CartaComparator());
        ArrayList<Carta> meuArray = new ArrayList<>();

        Baralho e = new Baralho();
        Baralho f = new Baralho();
        Carta c = new Carta("a", 1);
        Carta d = new Carta("a", 1);

        if(c.equals(c, d)) {
            System.out.println("Cartas sao iguais");
        }
        for(int i = 0; i < 10000; i++) {
            Carta carta = new Carta(UUID.randomUUID(),"a", i);
            cartas.add(carta);
            cartasLinked.add(carta);
            cartasVector.add(carta);
            cartasTree.add(carta);
            cartasHash.add(carta);
            meuArray.add(carta);
        }

        for(int i = 0; i < 11; i++) {
            Carta carta = new Carta("a", 1);
            e.adicionarCarta(carta);
        }
        for(int i = 0; i < 10; i++) {
            Carta carta = new Carta("a", 1);
            f.adicionarCarta(carta);
        }
        if(e.equals(e, f)) {
            System.out.println("Baralhos sao iguais");
        }
        long time = 0;
        long s;
        for(int i = 0; i < 10000; i++) {
            s = System.nanoTime();
            cartas.get(i);
            time += (System.nanoTime() - s)/ 1000000;
        }
        System.out.println("Tempo decorrido em ArrayList com get " + time + " ms");

        time = 0;
        for(int i = 0; i < 10000; i++) {
            s = System.nanoTime();
            cartasLinked.get(i);
            time += (System.nanoTime() - s)/ 1000000;
        }
        System.out.println("Tempo decorrido LinkedList com get " + time + " ms");

        time = 0;
        for(int i = 0; i < 10000; i++) {
            Carta a = cartas.get(i);
            s = System.nanoTime();
            cartas.contains(a);
            time += (System.nanoTime() - s)/ 1000000;
        }
        System.out.println("Tempo decorrido em ArrayList com contains " + time + " ms");

        time = 0;
        for(int i = 0; i < 10000; i++) {
            Carta a = cartasLinked.get(i);
            s = System.nanoTime();
            cartasLinked.contains(a);
            time += (System.nanoTime() - s)/ 1000000;
        }

        System.out.println("Tempo decorrido em LinkedList com contains " + time + " ms");
        time = 0;
        for(int i = 0; i < 10000; i++) {
            Carta a = meuArray.get(i);
            s = System.nanoTime();
            cartasHash.contains(a);
            time += (System.nanoTime() - s)/ 1000000;
        }
        System.out.println("Tempo decorrido em HashSet com contains " + time + " ms");

        time = 0;
        /*HashSet permite elementos iguais: o hash so fica muito ruim*/
        for(int i = 0; i < 10000; i++) {
            Carta a = meuArray.get(i);
            s = System.nanoTime();
            cartasTree.contains(a);
            time += (System.nanoTime() - s)/ 1000000;
        }
        System.out.println("Tempo decorrido em TreeSet com contains " + time + " ms");

        Collection<Carta> cartasCollection = Arrays.asList(new Lacaio("maiorVida", 1, 2, 5, 4), new Lacaio("menorVida", 2, 3, 3, 5), new Lacaio("maiorAtaque", 2, 4,4, 5), new Dano("c", 3, 4), new Buff("d", 3, 3, 3));

        Carta maiorAtaque = cartasCollection.stream().filter(o1 -> o1 instanceof Lacaio).sorted((o1, o2) -> ((Lacaio) o2).getAtaque() - ((Lacaio) o1).getAtaque()).findFirst().orElse(null);
        //System.out.println(maiorAtaque);
        int somaAtaques = cartasCollection.stream().filter(o1 -> o1 instanceof Lacaio).mapToInt(o1 -> ((Lacaio) o1).getAtaque()).sum();
        System.out.println("Soma dos Ataques " +somaAtaques + "\n");
        Collection<Carta> lacaiosPorVida = cartasCollection.stream().filter(o1 -> o1 instanceof Lacaio).sorted((o1, o2) -> ((Lacaio) o2).getVidaAtual() - ((Lacaio) o1).getVidaAtual()).collect(Collectors.toList());
        //System.out.println(lacaiosPorVida);
    }
}
