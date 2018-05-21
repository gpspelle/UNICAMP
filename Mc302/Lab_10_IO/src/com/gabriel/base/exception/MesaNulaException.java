package com.gabriel.base.exception;

public class MesaNulaException extends Exception {

    private String errMessage;

    public MesaNulaException(String errMessage) {
        this.errMessage = errMessage;
    }

    @Override
    public String toString() {
        return "Erro: " + errMessage;
    }
}
