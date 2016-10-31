package io.magentys.rest.model;

import io.restassured.response.ResponseBody;

public class RestAssuredBody implements Body {

    private final ResponseBody body;

    public RestAssuredBody(ResponseBody body) {
        this.body = body;
    }

    @Override
    public String prettyPrint() {
        return body.prettyPrint();
    }

    public static RestAssuredBody from(ResponseBody body) {
        return new RestAssuredBody(body);
    }
}