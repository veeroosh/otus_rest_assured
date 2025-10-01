import static org.builders.ApiErrorSchemaBuilder.updateBodyIsMissing;
import static org.builders.ApiErrorSchemaBuilder.usernameDoesNotExist;
import static org.config.constants.Endpoints.CREATE_USER;
import static org.config.constants.Endpoints.userByUsername;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.inject.Inject;
import io.restassured.response.Response;
import jdk.jfr.Description;
import org.clients.ApiClient;
import org.config.DataGenerator;
import org.apache.http.HttpStatus;
import org.assertj.core.api.SoftAssertions;
import org.builders.UserSchemaBuilder;
import org.extensions.ApiExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.schemas.ApiErrorSchema;
import org.schemas.UserSchema;

@ExtendWith(ApiExtension.class)
public class PutUserApiTest {

  @Inject
  private ApiClient apiClient;

  @Test
  @Description("Редактирование полей пользователя")
  void updateUser() {
    // создание пользователя
    UserSchema userSchemaForPost = UserSchemaBuilder.getFullUserSchemaForPostRequest();
    apiClient.baseRawController.post(CREATE_USER, userSchemaForPost);
    // схема для редактирования пользователя
    UserSchema userSchemaForPut = UserSchemaBuilder.getFullUserSchemaForPutRequest(userSchemaForPost.getUsername());

    // редактирование пользователя
    apiClient.baseRawController.put(userByUsername(userSchemaForPost.getUsername()), userSchemaForPut);
    // получение отредактированного пользователя
    UserSchema userByGet = apiClient.userSchemaController.get(userByUsername(userSchemaForPost.getUsername()));

    // удаление пользователя
    apiClient.baseRawController.delete(userByUsername(userSchemaForPost.getUsername()));
    // сравнение ожидаемого и фактического результата
    assertEquals(userSchemaForPut.setId(userByGet.getId()), userByGet);
  }

  @Test
  @Description("Невозможно отредактировать пользователя с пустым телом запроса")
  void unableToUpdateUserWithEmptyBody() {
    // создание пользователя
    UserSchema userSchemaForPost = UserSchemaBuilder.getUserSchemaForPostRequestWithMandatoryValues();
    apiClient.baseRawController.post(CREATE_USER, userSchemaForPost);
    SoftAssertions softAssert = new SoftAssertions();

    // редактирование пользователя с пустым телом запроса
    Response response = apiClient.baseRawController.put(userByUsername(userSchemaForPost.getUsername()), null);
    // получение пользователя после попытки редактирования
    UserSchema userByGet = apiClient.userSchemaController.get(userByUsername(userSchemaForPost.getUsername()));

    // ассерты ответа редактирования
    softAssert.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_METHOD_NOT_ALLOWED);
    softAssert.assertThat(response.as(ApiErrorSchema.class)).isEqualTo(updateBodyIsMissing());
    // ассерт, что пользователь не изменился
    softAssert.assertThat(userSchemaForPost.setId(userByGet.getId())).isEqualTo(userByGet);

    // удаление пользователя
    apiClient.baseRawController.delete(userByUsername(userSchemaForPost.getUsername()));
    softAssert.assertAll();
  }

  @Test
  @Description("Невозможно отредактировать пользователя по несуществующему username пользователя")
  void unableToUpdateUserByNonexistentUserId() {
    // генерация несуществующего имени пользователя
    String nonexistentUsername = DataGenerator.getUsername();
    SoftAssertions softAssert = new SoftAssertions();

    // редактирование пользователя по несуществующему username
    Response response = apiClient.baseRawController
        .put(userByUsername(nonexistentUsername), UserSchemaBuilder.getFullUserSchemaForPutRequest(nonexistentUsername));

    // ассерты ответа редактирования
    softAssert.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_BAD_REQUEST);
    softAssert.assertThat(response.as(ApiErrorSchema.class)).isEqualTo(usernameDoesNotExist(nonexistentUsername));
    softAssert.assertAll();
  }
}
