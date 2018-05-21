package com.gabriel.io;

import java.io.IOException;

public interface ILaMaSerializable {
    void escreveAtributos(Escritor fw) throws IOException;
}
