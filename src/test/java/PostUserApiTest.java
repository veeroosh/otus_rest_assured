import static org.builders.ApiErrorSchemaBuilder.createBodyIsMissing;
import static org.builders.ApiErrorSchemaBuilder.duplicatUsername;
import static org.config.constants.Endpoints.CREATE_USER;
import static org.config.constants.Endpoints.userByUsername;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.inject.Inject;
import io.restassured.response.Response;
import jdk.jfr.Description;
import lombok.SneakyThrows;
import org.apache.http.HttpStatus;
import org.assertj.core.api.SoftAssertions;
import org.builders.UserSchemaBuilder;
import org.clients.ApiClient;
import org.extensions.ApiExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.schemas.ApiErrorSchema;
import org.schemas.UserSchema;

@ExtendWith(ApiExtension.class)
public class PostUserApiTest {

  @Inject
  private ApiClient apiClient;

  @SneakyThrows
  @ParameterizedTest
  @Description("Создание пользователя")
  @MethodSource("org.config.UserSchemaForTest#getUserSchemaForPost")
  void createUser(UserSchema userSchema) {
    // запрос создания пользователя
    apiClient.baseRawController.post(CREATE_USER, userSchema);
    // получение созданного пользователя
    UserSchema userByGet = apiClient.userSchemaController.get(userByUsername(userSchema.getUsername()));

    // удаление пользователя
    apiClient.baseRawController.delete(userByUsername(userSchema.getUsername()));
    // сравнение ожидаемого и фактического результатов
    assertEquals(userByGet, userSchema.setId(userByGet.getId()));
  }

  @Test
  @Description("Невозможно создать пользователя с пустым телом запроса")
  void unableToCreateUserWithEmptyBody() {
    SoftAssertions softAssert = new SoftAssertions();

    // запрос создания пользователя с пустым телом запроса
    Response response = apiClient.baseRawController.post(CREATE_USER, null);

    // ассерты ответа создания
    softAssert.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_METHOD_NOT_ALLOWED);
    softAssert.assertThat(response.as(ApiErrorSchema.class)).isEqualTo(createBodyIsMissing());
    // здесь должна быть еще проверка, что такой пользователь не создан в бд
    // но доступа к бд нет
    softAssert.assertAll();
  }

  @Test
  @Description("Невозможно создать пользователя с дубликатом username")
  void unableToCreateUserWithDuplicateUsername() {
    // создание пользователя
    UserSchema userSchemaForPost = UserSchemaBuilder.getUserSchemaForPostRequestWithMandatoryValues();
    apiClient.baseRawController.post(CREATE_USER, userSchemaForPost);
    SoftAssertions softAssert = new SoftAssertions();

    // запрос создания пользователя с дубликатом username
    Response response = apiClient.baseRawController.post(CREATE_USER,
        UserSchemaBuilder.getUserSchemaForPostRequestWithMandatoryValues().setUsername(userSchemaForPost.getUsername()));

    // ассерты ответа создания
    softAssert.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_BAD_REQUEST);
    softAssert.assertThat(response.as(ApiErrorSchema.class)).isEqualTo(duplicatUsername());
    // т.к нет доступа к бд, то больше проверок нет
    // иначе была бы проверка, что нет созданного дубликата

    // удаление пользователя
    apiClient.baseRawController.delete(userByUsername(userSchemaForPost.getUsername()));
    softAssert.assertAll();
  }
}
