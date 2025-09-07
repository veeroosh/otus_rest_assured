package org.controllers;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.schemas.UserSchema;

public class UserSchemaController extends BaseController {

    public UserSchemaController(RequestSpecification requestSpecification) {
        super(requestSpecification);
    }

    public UserSchema get(String endpoint) {
        return RestAssured.given()
                .spec(this.requestSpecification)
                .when()
                .get(endpoint)
                .then().log().all()
                .extract()
                .as(UserSchema.class);
    }
}