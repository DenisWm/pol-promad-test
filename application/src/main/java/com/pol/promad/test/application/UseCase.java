package com.pol.promad.test.application;

public abstract class UseCase<IN, OUT> {

    public abstract OUT execute(IN anIN);
}