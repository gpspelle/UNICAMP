/*  Gabriel Pellegrino da Silva /172358
    Laboratório 3 
*/

import com.gabriel.util.Util;
import com.gabriel.base.*;

public class Main { 

    public static void main(String[] args) { 
        CartaLacaio lac1 = new CartaLacaio(1, "Frodo Bolseiro", 2, 1, 1, 1); 
        CartaLacaio lac2 = new CartaLacaio(2, "Aragorn", 5, 7, 7, 6);
        CartaLacaio lac3 = new CartaLacaio(3, "Legolas", 8, 4, 4, 6);
        Baralho baralho = new Baralho();
        
        baralho.adicionarCarta(lac1);
        baralho.adicionarCarta(lac2);
        baralho.adicionarCarta(lac3);
       
        baralho.embaralhar();
        baralho.embaralhar();
        baralho.embaralhar();
  
        System.out.println("------------------------------------\n");
 
        BaralhoArrayList baralhoArrayList = new BaralhoArrayList();
        
        baralhoArrayList.adicionarCarta(lac1);
        baralhoArrayList.adicionarCarta(lac2);
        baralhoArrayList.adicionarCarta(lac3);
    
        baralhoArrayList.embaralhar();
        baralhoArrayList.embaralhar();
        baralhoArrayList.embaralhar();
   
        CartaLacaio lac4 = baralhoArrayList.comprarCarta(); 
        CartaLacaio lac5 = baralhoArrayList.comprarCarta(); 
        
        System.out.println("------------------------------------\n");
        System.out.println("Carta Comprada\n");
        System.out.println(lac4);

        Util.buffar(lac4, 100, 10);
        Util.buffar(lac5, 30);
      
        System.out.println("------------------------------------\n");
        System.out.println("Cartas Buffadas\n");
        System.out.println(lac4);
        System.out.println(lac5); 
    
    }
}
