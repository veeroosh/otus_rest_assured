package org.builders;

import org.DataGenerator;
import org.schemas.UserSchema;

public class UserSchemaBuilder {

    public static UserSchema getFullUserSchemaForPutRequest(String username) {
        return UserSchema.builder()
                .username(username)
                .firstName(DataGenerator.getFirstName())
                .lastName(DataGenerator.getLastName())
                .email(DataGenerator.getEmail())
                .password(DataGenerator.getPassword())
                .phone(DataGenerator.getPhone())
                .userStatus(DataGenerator.getRandomUserState())
                .build();
    }

    public static UserSchema getFullUserSchemaForPostRequest() {
        return getFullUserSchemaForPutRequest(DataGenerator.getUsername());
    }

    public static UserSchema getUserSchemaForPostRequestWithMandatoryValues() {
        return UserSchema.builder()
                .username(DataGenerator.getUsername())
                .firstName(DataGenerator.getFirstName())
                .email(DataGenerator.getEmail())
                .password(DataGenerator.getPassword())
                .userStatus(DataGenerator.getRandomUserState())
                .build();
    }
}
