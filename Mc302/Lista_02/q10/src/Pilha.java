package src;

import java.util.ArrayList;

public class Pilha {

    int max = 3;

    private static ArrayList<Integer> pilha = new ArrayList<>();
    
    public static void main(String[] args) {
       
        try {
            pilha.pop();
            pilha.push(1);
            pilha.push(2);
            pilha.push(3);
            pilha.push(4);
        } catch(PilhaCheia p) {
            System.out.println(p); 
        }

    }
    
    
    public int pop() throws PilhaVazia {
        if(pilha.size() == 0) {
            throw new PilhaVazia();
        } 
        return pilha.remove();
    }
    public void push(int v) throws PilhaCheia {
    
        if(pilha.size() >= 3) {
            throw new PilhaCheia();
        }
        pilha.add(v); 
    }

}
