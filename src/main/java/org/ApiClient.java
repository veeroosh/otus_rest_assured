package org;

import io.restassured.specification.RequestSpecification;
import org.controllers.BaseRawController;
import org.controllers.UserSchemaController;

public class ApiClient {

    private final RequestSpecification requestSpecification;
    public BaseRawController baseRawController;
    public UserSchemaController userSchemaController;

    public ApiClient(RequestSpecification requestSpecification) {
        this.requestSpecification = requestSpecification;
        initializeControllers();
    }

    private void initializeControllers() {
        this.baseRawController = new BaseRawController(this.requestSpecification);
        this.userSchemaController = new UserSchemaController(this.requestSpecification);
    }
}
