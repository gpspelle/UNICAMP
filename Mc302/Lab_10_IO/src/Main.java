/*  Gabriel Pellegrino da Silva /172358
    Laboratório 8 :
*/

import com.gabriel.base.*;
import com.gabriel.base.cartas.*;
import com.gabriel.base.cartas.magias.Buff;
import com.gabriel.base.cartas.magias.Dano;
import com.gabriel.base.cartas.magias.DanoArea;
import com.gabriel.base.controle.Controle;
import com.gabriel.base.service.impl.ProcessadorServiceImpl;
import com.gabriel.io.Escritor;
import com.gabriel.io.ILaMaSerializable;
import com.gabriel.io.Leitor;
import com.gabriel.util.Util;

import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {

        String path = "BancoDeDados.txt";

        Escritor escritor = new Escritor(path);
        Leitor leitor = new Leitor(path);

        Lacaio lac = new Lacaio(UUID.randomUUID(), "Batman", 1, 1, 1, 1, HabilidadesLacaio.EXAUSTAO);
        Dano dan = new Dano(UUID.randomUUID(), "RaioDoMal", 1, 1);
        Buff buf = new Buff(UUID.randomUUID(), "CogumeloDoMario", 1, 1, 1);
        DanoArea danA = new DanoArea(UUID.randomUUID(), "ExplosaoTchakabum", 1, 1);

        List<ILaMaSerializable> myList = new ArrayList<>();
        myList.add(lac);
        myList.add(dan);
        myList.add(buf);
        myList.add(danA);

        System.out.println(myList);
        try {
            lac.escreveAtributos(escritor);
            dan.escreveAtributos(escritor);
            buf.escreveAtributos(escritor);
            danA.escreveAtributos(escritor);
            escritor.finalizar();
        }
        catch(IOException e) {
            System.err.println("Erro no nome do arquivo!" + e);
        }

        List<ILaMaSerializable> lista = leitor.leObjetos();
        System.out.println(lista);
    }
}
