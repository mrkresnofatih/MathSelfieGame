package com.mrkresnofatih.mathselfieapp.exceptions;

public class RecordNotFoundException extends Exception {
    public RecordNotFoundException() {
        super("Cannot find record in persistence store");
    }
}
