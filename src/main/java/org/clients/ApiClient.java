package org.clients;

import com.google.inject.Inject;
import org.controllers.BaseRawController;
import org.controllers.UserSchemaController;

public class ApiClient {
  public BaseRawController baseRawController;
  public UserSchemaController userSchemaController;

  @Inject
  public ApiClient(BaseRawController baseRawController, UserSchemaController userSchemaController) {
    this.baseRawController = baseRawController;
    this.userSchemaController = userSchemaController;
  }
}
