public class Main {

    public static void main(String[] args) {

        Pilha pilha = new Pilha();

        try {
            //pilha.pop();
            pilha.push(1);
            pilha.push(2);
            pilha.push(3);
            try {
                pilha.push(4);
            }catch (PilhaExcecao p){
                System.out.println("Fodeu");
            }
            pilha.pop();
        } catch(PilhaExcecao p) {
        }
    }
}
