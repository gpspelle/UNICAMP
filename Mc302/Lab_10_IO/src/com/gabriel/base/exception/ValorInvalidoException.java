package com.gabriel.base.exception;

public class ValorInvalidoException extends Exception {

    private String errMessage;

    public ValorInvalidoException(String errMessage) {
        super(errMessage);
    }

    @Override
    public String toString() {
        return "Erro: " + errMessage;
    }
}
