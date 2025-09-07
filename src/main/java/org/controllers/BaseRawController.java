package org.controllers;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class BaseRawController extends BaseController {

    public BaseRawController(RequestSpecification requestSpecification) {
        super(requestSpecification);
    }

    public Response post(String endpoint, Object body) {
        RequestSpecification req = given().spec(this.requestSpecification);
        if (body != null) {
            req.body(body);
        }
        return req.post(endpoint)
                .then()
                .extract()
                .response();
    }

    public Response put(String endpoint, Object body) {
        RequestSpecification req = given().spec(this.requestSpecification);
        if (body != null) {
            req.body(body);
        }
        return req.put(endpoint)
                .then()
                .extract()
                .response();
    }

    public void delete(String endpoint) {
        given().spec(this.requestSpecification)
                .delete(endpoint)
                .then()
                .extract()
                .response();
    }
}