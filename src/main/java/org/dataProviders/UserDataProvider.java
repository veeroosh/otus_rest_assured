package org.dataProviders;

import org.builders.UserSchemaBuilder;
import org.testng.annotations.DataProvider;

public class UserDataProvider {

    @DataProvider(name = "userSchemaForPost")
    public Object[][] userSchemaForPost() {
        return new Object[][]{
                {UserSchemaBuilder.getUserSchemaForPostRequestWithMandatoryValues()},
                {UserSchemaBuilder.getFullUserSchemaForPostRequest()}
        };
    }
}
