package org.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import org.clients.ApiClient;
import org.config.constants.ApiSettings;
import org.controllers.BaseRawController;
import org.controllers.UserSchemaController;

public class ApiGuiceModule extends AbstractModule {

  @Provides
  @Singleton
  public ApiClient getApiClient(BaseRawController baseRawController, UserSchemaController userSchemaController) {
    return new ApiClient(baseRawController, userSchemaController);
  }

  @Provides
  @Singleton
  public BaseRawController getBaseRawController() {
    return new BaseRawController(ApiSettings.requestSpecification());
  }

  @Provides
  @Singleton
  public UserSchemaController getUserSchemaController() {
    return new UserSchemaController(ApiSettings.requestSpecification());
  }
}
