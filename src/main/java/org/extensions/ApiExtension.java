package org.extensions;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.modules.ApiGuiceModule;

public class ApiExtension implements BeforeEachCallback {
  private Injector injector;

  @Override
  public void beforeEach(ExtensionContext context) {
    injector = Guice.createInjector(new ApiGuiceModule());
    Object testInstance = context.getTestInstance().orElseThrow();
    injector.injectMembers(testInstance);
    if (testInstance instanceof org.clients.ApiClient) {
      injector.injectMembers(testInstance);
    }
  }
}
