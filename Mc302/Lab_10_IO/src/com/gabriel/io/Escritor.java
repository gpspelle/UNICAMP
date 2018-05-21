package com.gabriel.io;

import java.io.FileWriter;
import java.io.IOException;

public class Escritor {

    private FileWriter escritor;
    public Escritor(String path) throws IOException {
        escritor = new FileWriter(path, false);
    }
    public void escreveAtributo(String nomeAtributo, String valor) throws IOException {
        String aux = nomeAtributo + " " + valor + "\n";
        escritor.write(aux);
    }
    public void escreveDelimObj(String nomeObj) throws IOException {
        escritor.write(nomeObj + "\n");
    }
    public void finalizar() throws IOException {
        escritor.close();
    }
}
