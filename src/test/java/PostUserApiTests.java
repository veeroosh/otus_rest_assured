import io.restassured.response.Response;
import org.BaseApiTest;
import org.apache.http.HttpStatus;
import org.builders.UserSchemaBuilder;
import org.dataProviders.UserDataProvider;
import org.schemas.ApiErrorSchema;
import org.schemas.UserSchema;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static org.config.Endpoints.CREATE_USER;
import static org.config.Endpoints.userByUsername;
import static org.builders.ApiErrorSchemaBuilder.createBodyIsMissing;
import static org.builders.ApiErrorSchemaBuilder.duplicatUsername;
import static org.testng.Assert.assertEquals;

public class PostUserApiTests extends BaseApiTest {

    @Test(description = "Создание пользователя",
            dataProviderClass = UserDataProvider.class, dataProvider = "userSchemaForPost")
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

    @Test(description = "Невозможно создать пользователя с пустым телом запроса")
    void unableToCreateUserWithEmptyBody() {
        SoftAssert softAssert = new SoftAssert();

        // запрос создания пользователя с пустым телом запроса
        Response response = apiClient.baseRawController.post(CREATE_USER, null);

        // ассерты ответа создания
        softAssert.assertEquals(response.getStatusCode(), HttpStatus.SC_METHOD_NOT_ALLOWED);
        softAssert.assertEquals(response.as(ApiErrorSchema.class), createBodyIsMissing());
        // здесь должна быть еще проверка, что такой пользователь не создан в бд
        // но доступа к бд нет
        softAssert.assertAll();
    }

    @Test(description = "Невозможно создать пользователя с дубликатом username")
    void unableToCreateUserWithDuplicateUsername() {
        // создание пользователя
        UserSchema userSchemaForPost = UserSchemaBuilder.getUserSchemaForPostRequestWithMandatoryValues();
        apiClient.baseRawController.post(CREATE_USER, userSchemaForPost);
        SoftAssert softAssert = new SoftAssert();

        // запрос создания пользователя с дубликатом username
        Response response = apiClient.baseRawController.post(CREATE_USER,
                UserSchemaBuilder.getUserSchemaForPostRequestWithMandatoryValues().setUsername(userSchemaForPost.getUsername()));

        // ассерты ответа создания
        softAssert.assertEquals(response.getStatusCode(), HttpStatus.SC_BAD_REQUEST);
        softAssert.assertEquals(response.as(ApiErrorSchema.class), duplicatUsername());
        // т.к нет доступа к бд, то больше проверок нет
        // иначе была бы проверка, что нет созданного дубликата

        // удаление пользователя
        apiClient.baseRawController.delete(userByUsername(userSchemaForPost.getUsername()));
        softAssert.assertAll();
    }
}
