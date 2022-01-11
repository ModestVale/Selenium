import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class appOrderTest {
    private String nameFieldSelector = "#root > div > form > div:nth-child(1) > span > span > span.input__box > input";
    private String phoneFieldSelector = "#root > div > form > div:nth-child(2) > span > span > span.input__box > input";
    private String checkBoxSelector = "#root > div > form > div:nth-child(3) > label > span.checkbox__box";
    private String buttonSelector = "#root > div > form > div:nth-child(4) > button";

    @Test
    public void shouldSuccessfulSendFormWithCorrectData() {
        open("http://localhost:9999");

        $(nameFieldSelector).setValue("Иванов Иван-Ивановский");
        $(phoneFieldSelector).setValue("+79228569875");
        $(checkBoxSelector).click();
        $(buttonSelector).click();
        $("#root > div > div > p")
                .shouldHave(text("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."), Duration.ofMillis(5000));
    }

    @Test
    public void shouldNotSendFormWithEmptyName() {
        open("http://localhost:9999");

        $(buttonSelector).click();
        $("#root > div > form > div:nth-child(1) > span")
                .shouldHave(cssClass("input_invalid"), Duration.ofMillis(2000));
    }

    @Test
    public void shouldNotSendFormWithIncorrectName() {
        open("http://localhost:9999");

        $(nameFieldSelector).setValue("ggg ggg");
        $(buttonSelector).click();
        $("#root > div > form > div:nth-child(1) > span")
                .shouldHave(cssClass("input_invalid"), Duration.ofMillis(2000));
    }

    @Test
    public void shouldNotSendFormWithEmptyPhone() {
        open("http://localhost:9999");

        $(nameFieldSelector).setValue("Иванов Иван-Ивановский");
        $(buttonSelector).click();
        $("#root > div > form > div:nth-child(2) > span")
                .shouldHave(cssClass("input_invalid"), Duration.ofMillis(2000));
    }

    @Test
    public void shouldNotSendFormWithIncorrectPhone() {
        open("http://localhost:9999");

        $(nameFieldSelector).setValue("Иванов Иван-Ивановский");
        $(phoneFieldSelector).setValue("79228569875");
        $(buttonSelector).click();
        $("#root > div > form > div:nth-child(2) > span")
                .shouldHave(cssClass("input_invalid"), Duration.ofMillis(2000));
    }

    @Test
    public void shouldNotSendFormWithIncorrectPhone2() {
        open("http://localhost:9999");

        $(nameFieldSelector).setValue("Иванов Иван-Ивановский");
        $(phoneFieldSelector).setValue("+79228565");
        $(buttonSelector).click();
        $("#root > div > form > div:nth-child(2) > span")
                .shouldHave(cssClass("input_invalid"), Duration.ofMillis(2000));
    }

    @Test
    public void shouldNotSendFormWithUncheckedCheckbox() {
        open("http://localhost:9999");

        $(nameFieldSelector).setValue("Иванов Иван-Ивановский");
        $(phoneFieldSelector).setValue("+79228569875");
        $(buttonSelector).click();

        $("#root > div > form > div:nth-child(3) > label")
                .shouldHave(cssClass("input_invalid"), Duration.ofMillis(2000));
    }
}
