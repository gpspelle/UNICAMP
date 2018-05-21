/*  Gabriel Pellegrino da Silva /17258
    Laboratório 3 :
*/

import com.gabriel.base.*;
import com.gabriel.base.cartas.*;
import com.gabriel.base.cartas.magias.*;
import java.util.UUID;

public class Main { 

    public static void main(String[] args) { 
        Baralho baralho = new Baralho();
        
        Lacaio lac1 = new Lacaio(UUID.randomUUID(), "Paulo", 10, 10, 10, 10);
        Lacaio lac2 = new Lacaio(UUID.randomUUID(), "Beatriz", 5, 5, 5, 5);

        Buff buff = new Buff(UUID.randomUUID(), "Buffer", 10, 20, 30);
        
        Dano dano = new Dano(UUID.randomUUID(), "Dano", 2, 5);
        DanoArea danoArea = new DanoArea(UUID.randomUUID(), "Dano Area", 10, 10);
        
        baralho.adicionarCarta(lac1);
        baralho.adicionarCarta(lac2);
        baralho.adicionarCarta(buff);
        baralho.adicionarCarta(dano);
        baralho.adicionarCarta(danoArea); 
  
        System.out.println(baralho);       
        
        System.out.println(lac1.getNome());

    }
}
