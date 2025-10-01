package org.config;

import com.github.javafaker.Faker;

public class DataGenerator {
  private static final Faker FAKER = new Faker();

  public static String getUsername() {
    return FAKER.name().username().replace(".", "");
  }

  public static String getEmail() {
    return FAKER.internet().emailAddress();
  }

  public static String getFirstName() {
    return FAKER.name().firstName();
  }

  public static String getLastName() {
    return FAKER.name().lastName();
  }

  public static String getPassword() {
    return FAKER.internet().password();
  }

  public static String getPhone() {
    return FAKER.phoneNumber().phoneNumber();
  }

  public static Integer getRandomUserState() {
    return FAKER.bool().bool() ? 1 : 0;
  }
}
