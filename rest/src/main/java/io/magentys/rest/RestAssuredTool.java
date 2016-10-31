package io.magentys.rest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class RestAssuredTool {

    private final RequestSpecification requestSpecification;

    public RestAssuredTool() {
        this(RestAssured.baseURI);
    }

    public RestAssuredTool(final String baseURI) {
        this.requestSpecification = new RequestSpecBuilder().setBaseUri(baseURI).build();
    }

    public RequestSpecification requestSpecification() {
        return requestSpecification;
    }
}