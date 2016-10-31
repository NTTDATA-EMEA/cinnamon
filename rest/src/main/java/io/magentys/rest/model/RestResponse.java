package io.magentys.rest.model;

public interface RestResponse<T> {

    T data();

    int statusCode();

    Body body();

    boolean failed();

    boolean ok();
}