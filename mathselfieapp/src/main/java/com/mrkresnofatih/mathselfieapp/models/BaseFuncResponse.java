package com.mrkresnofatih.mathselfieapp.models;

public class BaseFuncResponse<T> {
    private T data;
    private String error;

    public BaseFuncResponse() {
    }

    public BaseFuncResponse(T data, String error) {
        this.data = data;
        this.error = error;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
