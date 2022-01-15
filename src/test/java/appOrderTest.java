import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class AppOrderTest {
    private String nameFieldSelector = "[data-test-id='name'] input";
    private String phoneFieldSelector = "[data-test-id='phone'] input";
    private String checkBoxSelector = "[data-test-id='agreement'] .checkbox__box";
    private String buttonSelector = "button[type='button']";

    @Test
    public void shouldSuccessfulSendFormWithCorrectData() {
        $(nameFieldSelector).setValue("Иванов Иван-Ивановский");
        $(phoneFieldSelector).setValue("+79228569875");
        $(checkBoxSelector).click();
        $(buttonSelector).click();
        $("[data-test-id='order-success']")
                .shouldHave(text("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."), Duration.ofMillis(5000));
    }

    @Test
    public void shouldNotSendFormWithEmptyName() {

        $(phoneFieldSelector).setValue("+79228569875");
        $(checkBoxSelector).click();
        $(buttonSelector).click();

        $("[data-test-id='name'].input_invalid .input__sub")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    public void shouldNotSendFormWithIncorrectName() {
        $(nameFieldSelector).setValue("ggg ggg");
        $(phoneFieldSelector).setValue("+79228569875");
        $(checkBoxSelector).click();
        $(buttonSelector).click();

        $("[data-test-id='name'].input_invalid .input__sub")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    public void shouldNotSendFormWithEmptyPhone() {
        $(nameFieldSelector).setValue("Иванов Иван-Ивановский");
        $(checkBoxSelector).click();
        $(buttonSelector).click();

        $("[data-test-id='phone'].input_invalid .input__sub")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    public void shouldNotSendFormWithIncorrectPhone() {
        $(nameFieldSelector).setValue("Иванов Иван-Ивановский");
        $(phoneFieldSelector).setValue("79228569875");
        $(checkBoxSelector).click();
        $(buttonSelector).click();

        $("[data-test-id='phone'].input_invalid .input__sub")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    public void shouldNotSendFormWithIncorrectPhone2() {
        $(nameFieldSelector).setValue("Иванов Иван-Ивановский");
        $(phoneFieldSelector).setValue("+79228565");
        $(checkBoxSelector).click();
        $(buttonSelector).click();

        $("[data-test-id='phone'].input_invalid .input__sub")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    public void shouldNotSendFormWithUncheckedCheckbox() {
        $(nameFieldSelector).setValue("Иванов Иван-Ивановский");
        $(phoneFieldSelector).setValue("+79228569875");
        $(buttonSelector).click();

        $("[data-test-id='agreement']")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(cssClass("input_invalid"));
    }

    @BeforeEach
    public void openBrowser() {
        open("http://localhost:9999");
    }
}

