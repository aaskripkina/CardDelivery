import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {
    public String setUpDate(long addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }


    @Test
    void shouldSubmitRequest(){
        Configuration.holdBrowserOpen = true;
        String newDate = setUpDate(3, "dd.MM.yyyy");

        open("http://localhost:9999/");
        $x("//*[@data-test-id = 'city']//input").setValue("Петрозаводск");
        $x("//*[@data-test-id = 'date']//input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $x("//*[@data-test-id = 'date']//input").setValue(newDate);
        $x("//*[@data-test-id = 'name']//input").setValue("Иван Иванов");
        $x("//*[@data-test-id = 'phone']//input").setValue("+78123456789");
        $x("//*[@data-test-id = 'agreement']").click();
        $x("//*[text() = 'Забронировать']").click();

        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + newDate), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }
}