package org;

import com.github.javafaker.Faker;

public class DataGenerator {
    private static final Faker faker = new Faker();

    public static String getUsername() {
        return faker.name().username().replace(".", "");
    }

    public static String getEmail() {
        return faker.internet().emailAddress();
    }

    public static String getFirstName() {
        return faker.name().firstName();
    }

    public static String getLastName() {
        return faker.name().lastName();
    }

    public static String getPassword() {
        return faker.internet().password();
    }

    public static String getPhone() {
        return faker.phoneNumber().phoneNumber();
    }

    public static Integer getRandomUserState() {
        return faker.bool().bool() ? 1 : 0;
    }
}
