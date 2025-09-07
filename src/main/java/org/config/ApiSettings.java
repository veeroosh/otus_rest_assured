package org.config;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class ApiSettings {

    public static RequestSpecification requestSpecification() {
        RequestSpecBuilder tmp = new RequestSpecBuilder()
                .setBaseUri("https://petstore.swagger.io/v2/")
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .setRelaxedHTTPSValidation()
                .log(LogDetail.ALL);
        return tmp.build();
    }
}
