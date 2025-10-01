package org.config.constants;

public interface Endpoints {
  String CREATE_USER = "/user";

  static String userByUsername(String username) {
    return String.format("%s/%s", CREATE_USER, username);
  }
}