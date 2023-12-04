package org.example.registration.test;

import org.example.registration.registrations.RegistrationDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class AuthTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:7777/");
    }

    //успешный вход в личный кабинет
    @Test
    void activeRegisteredUser() {
        var registeredUser = RegistrationDto.Registration.getRegisteredUser("active");
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $("button.button").click();
        $("h2").shouldHave(text("Личный кабинет")).shouldBe(visible);
    }

    //не успешный вход в личный кабинет
    @Test
    void notActiveRegisteredUser() {
        var notRegisteredUser = RegistrationDto.Registration.getUser("active");
        $("[data-test-id='login'] input").setValue(notRegisteredUser.getLogin());
        $("[data-test-id='password'] input").setValue(notRegisteredUser.getPassword());
        $("button.button").click();
        $("[data-test-id='error-notification'] .notification__content")
                .shouldHave(text("Ошибка! Неверно указан логин или пароль"))
                .shouldBe(visible);
    }

    //заблокированный пользователь
    @Test
    void ShouldGetErrorMessageIfLoginWithBlockedRegisteredUser() {
        var blockedUser = RegistrationDto.Registration.getRegisteredUser("blocked");
        var wrongPassword = RegistrationDto.getRandomPassword();
        $("[data-test-id='login'] input").setValue(blockedUser.getLogin());
        $("[data-test-id='password'] input").setValue(blockedUser.getPassword());
        $("button.button").click();
        $("[data-test-id='error-notification'] .notification__content")
                .shouldHave(text("Ошибка! Пользователь заблокирован"), Duration.ofSeconds(11))
                .shouldBe(visible);
    }

    //невалидный логин
    @Test
    void notValidLogin() {
        var registeredUser = RegistrationDto.Registration.getRegisteredUser("active");
        var wrongLogin = RegistrationDto.getRandomLogin();
        $("[data-test-id='login'] input").setValue(wrongLogin);
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $("button.button").click();
        $("[data-test-id='error-notification'] .notification__content")
                .shouldHave(text("Ошибка! Неверно указан логин или пароль"), Duration.ofSeconds(11))
                .shouldBe(visible);
    }

    //пустое поле "Логин"
    @Test
    void emptyFieldLogin() {
        var registeredUser = RegistrationDto.Registration.getRegisteredUser("active");
        $("[data-test-id='login'] input").clear();
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $("button.button").click();
        $("[data-test-id='login'] .input__sub").shouldHave(text("Поле обязательно для заполнения")).shouldBe(visible);
    }

    //невалидный пароль
    @Test
    void notValidPassword() {
        var registeredUser = RegistrationDto.Registration.getRegisteredUser("active");
        var wrongPassword = RegistrationDto.getRandomPassword();
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(wrongPassword);
        $("button.button").click();
        $("[data-test-id='error-notification'] .notification__content")
                .shouldHave(text("Ошибка! Неверно указан логин или пароль"), Duration.ofSeconds(11))
                .shouldBe(visible);
    }

    //пустое поле "Пароль"
    @Test
    void emptyFieldPassword() {
        var registeredUser = RegistrationDto.Registration.getRegisteredUser("active");
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").clear();
        $("button.button").click();
        $("[data-test-id='password'] .input__sub").shouldHave(text("Поле обязательно для заполнения")).shouldBe(visible);
    }

    //отправка пустой формы
    @Test
    void emptyFieldLoginAndPassword() {
        var registeredUser = RegistrationDto.Registration.getRegisteredUser("active");
        $("[data-test-id='login'] input").clear();
        $("[data-test-id='password'] input").clear();
        $("button.button").click();
        $("[data-test-id='login'] .input__sub").shouldHave(text("Поле обязательно для заполнения")).shouldBe(visible);
        $("[data-test-id='password'] .input__sub").shouldHave(text("Поле обязательно для заполнения")).shouldBe(visible);
    }
}

