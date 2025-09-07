package org.builders;

import org.schemas.ApiErrorSchema;

public class ApiErrorSchemaBuilder {

    public static ApiErrorSchema createBodyIsMissing() {
        return ApiErrorSchema.builder()
                .type("error")
                .message("Create body is missing")
                .build();
    }

    public static ApiErrorSchema updateBodyIsMissing() {
        return ApiErrorSchema.builder()
                .type("error")
                .message("Update body is missing")
                .build();
    }

    public static ApiErrorSchema duplicatUsername() {
        return ApiErrorSchema.builder()
                .type("error")
                .message("Unable to create/update user with duplicate username")
                .build();
    }

    public static ApiErrorSchema usernameDoesNotExist(String username) {
        return ApiErrorSchema.builder()
                .type("error")
                .message(String.format("Username %s does not exist", username))
                .build();
    }
}
