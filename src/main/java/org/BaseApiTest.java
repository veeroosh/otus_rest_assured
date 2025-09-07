package org;

import lombok.SneakyThrows;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = DummyApp.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
@WebMvcTest
public class BaseApiTest {
    public ApiClient apiClient;

    @SneakyThrows
    public BaseApiTest() {
        apiClient = new ApiClient(ApiSettings.requestSpecification());
    }
}
