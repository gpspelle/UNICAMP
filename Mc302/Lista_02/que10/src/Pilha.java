import java.util.ArrayList;

public class Pilha {

    int max = 3;

    private static ArrayList<Integer> pilha = new ArrayList<>();

    public Pilha() {

    }

    public int pop() throws PilhaVazia {
        if (pilha.size() == 0) {
            throw new PilhaVazia();
        }
        return pilha.remove(pilha.size()-1);
    }

    public void push(int v) throws PilhaCheia {

        if (pilha.size() >= 3) {
            throw new PilhaCheia();
        }
        pilha.add(v);
    }
}
