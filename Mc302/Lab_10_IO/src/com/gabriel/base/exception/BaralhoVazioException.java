package com.gabriel.base.exception;

public class BaralhoVazioException extends IllegalArgumentException  {

    private String errMessage;

    public BaralhoVazioException(String errMessage) {
        super(errMessage);
        this.errMessage = errMessage;
    }

    @Override
    public String toString() {
        return "Erro: " + errMessage;
    }
}
