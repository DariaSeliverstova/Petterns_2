package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

import static com.codeborne.selenide.Selenide.*;
import static ru.netology.Data.Registration.getRegisteredUser;
import static ru.netology.Data.Registration.getUser;
import static ru.netology.Data.getRandomLogin;
import static ru.netology.Data.getRandomPassword;

public class PetternsTest_2 {
    @BeforeEach
    void setup() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
    }

    @Test
    public void shouldRegisteredActiveUser() {
        var registeredUser = getRegisteredUser("active");
        $x("//input[@name='login']").setValue(registeredUser.getLogin());
        $x("//input[@name='password']").setValue(registeredUser.getPassword());
        $x("//span[@class='button__text']").click();
        $x("//h2[@class='heading heading_size_l heading_theme_alfa-on-white']").shouldHave(Condition.text("Личный кабинет"));
    }

    @Test
    public void shouldNotRegisteredUser() {
        var notRegisteredUser = getUser("active");
        $x("//input[@name='login']").setValue(notRegisteredUser.getLogin());
        $x("//input[@name='password']").setValue(notRegisteredUser.getPassword());
        $x("//span[@class='button__text']").click();
        $x("//div[@data-test-id='error-notification']").shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"));

    }

    @Test
    public void shouldBlockedUser() {
        var blockedUser = getRegisteredUser("blocked");
        $x("//input[@name='login']").setValue(blockedUser.getLogin());
        $x("//input[@name='password']").setValue(blockedUser.getPassword());
        $x("//span[@class='button__text']").click();
        $x("//div[@data-test-id='error-notification']").shouldHave(Condition.text("Ошибка! Пользователь заблокирован"));
    }

    @Test
    public void shouldWrongLogin() {
        var registeredUser = getRegisteredUser("active");
        var wrongLogin = getRandomLogin();
        $x("//input[@name='login']").setValue((wrongLogin));
        $x("//input[@name='password']").setValue(registeredUser.getPassword());
        $x("//span[@class='button__text']").click();
        $x("//div[@data-test-id='error-notification']").shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    public void shouldWrongPassword() {
        var registeredUser = getRegisteredUser("active");
        var wrongPassword = getRandomPassword();
        $x("//input[@name='login']").setValue((registeredUser.getLogin()));
        $x("//input[@name='password']").setValue(wrongPassword);
        $x("//span[@class='button__text']").click();
        $x("//div[@data-test-id='error-notification']").shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"));
    }
}


