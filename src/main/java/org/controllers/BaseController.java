package org.controllers;

import io.restassured.specification.RequestSpecification;

public class BaseController {

  public final RequestSpecification requestSpecification;

  public BaseController(RequestSpecification requestSpecification) {
    this.requestSpecification = requestSpecification;
  }
}
