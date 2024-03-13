package com.devsuperior.dscatalog.services;

public class ResouceEntityNotFoundExeption extends Exception {
    private static final long serialVersionUID = 1L;

    public ResouceEntityNotFoundExeption(String msg) {
        super(msg);
    }
}
