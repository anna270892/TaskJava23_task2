package org.example.registration.registrations;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class RegistrationDataGenerator {
    private static final Faker faker = new Faker(new Locale("ru"));

    static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(7777)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    public static String getRandomLogin() {
        return faker.name().username();
    }

    public static String getRandomPassword() {
        return faker.internet().password();
    }

    public static RegistrationDto getUser(String status) {
        return new RegistrationDto(getRandomLogin(), getRandomPassword(), status);
    }

    public static RegistrationDto getRegisteredUser(String status) {
        return request(getUser(status));
    }

    private static RegistrationDto request(RegistrationDto user) {
        given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
        return user;
    }
}