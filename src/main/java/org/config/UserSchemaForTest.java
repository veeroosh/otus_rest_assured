package org.config;

import org.builders.UserSchemaBuilder;
import org.schemas.UserSchema;
import java.util.stream.Stream;

public interface UserSchemaForTest {
  static Stream<UserSchema> getUserSchemaForPost() {
    return Stream.of(UserSchemaBuilder.getUserSchemaForPostRequestWithMandatoryValues(),
        UserSchemaBuilder.getFullUserSchemaForPostRequest());
  }
}
