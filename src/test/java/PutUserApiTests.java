import io.restassured.response.Response;
import org.BaseApiTest;
import org.DataGenerator;
import org.apache.http.HttpStatus;
import org.builders.UserSchemaBuilder;
import org.schemas.ApiErrorSchema;
import org.schemas.UserSchema;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static org.config.Endpoints.CREATE_USER;
import static org.config.Endpoints.userByUsername;
import static org.builders.ApiErrorSchemaBuilder.updateBodyIsMissing;
import static org.builders.ApiErrorSchemaBuilder.usernameDoesNotExist;
import static org.testng.Assert.assertEquals;

public class PutUserApiTests extends BaseApiTest {

    @Test(description = "Редактирование полей пользователя")
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

    @Test(description = "Невозможно отредактировать пользователя с пустым телом запроса")
    void unableToUpdateUserWithEmptyBody() {
        // создание пользователя
        UserSchema userSchemaForPost = UserSchemaBuilder.getUserSchemaForPostRequestWithMandatoryValues();
        apiClient.baseRawController.post(CREATE_USER, userSchemaForPost);
        SoftAssert softAssert = new SoftAssert();

        // редактирование пользователя с пустым телом запроса
        Response response = apiClient.baseRawController.put(userByUsername(userSchemaForPost.getUsername()), null);
        // получение пользователя после попытки редактирования
        UserSchema userByGet = apiClient.userSchemaController.get(userByUsername(userSchemaForPost.getUsername()));

        // ассерты ответа редактирования
        softAssert.assertEquals(response.getStatusCode(), HttpStatus.SC_METHOD_NOT_ALLOWED);
        softAssert.assertEquals(response.as(ApiErrorSchema.class), updateBodyIsMissing());
        // ассерт, что пользователь не изменился
        softAssert.assertEquals(userSchemaForPost.setId(userByGet.getId()), userByGet);

        // удаление пользователя
        apiClient.baseRawController.delete(userByUsername(userSchemaForPost.getUsername()));
        softAssert.assertAll();
    }

    @Test(description = "Невозможно отредактировать пользователя по несуществующему username пользователя")
    void unableToUpdateUserByNonexistentUserId() {
        // генерация несуществующего имени пользователя
        String nonexistentUsername = DataGenerator.getUsername();
        SoftAssert softAssert = new SoftAssert();

        // редактирование пользователя по несуществующему username
        Response response = apiClient.baseRawController
                .put(userByUsername(nonexistentUsername), UserSchemaBuilder.getFullUserSchemaForPutRequest(nonexistentUsername));

        // ассерты ответа редактирования
        softAssert.assertEquals(response.getStatusCode(), HttpStatus.SC_BAD_REQUEST);
        softAssert.assertEquals(response.as(ApiErrorSchema.class), usernameDoesNotExist(nonexistentUsername));
        softAssert.assertAll();
    }
}
