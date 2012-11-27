package lanit.junit.form.fields.validation;

import lanit.junit.selenium.SeleniumPrerequisites;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class MandatoryFieldValidation {

    private static Logger log = Logger.getLogger(MandatoryFieldValidation.class);

    /**
     * Method finds all required fields and accounting their
     * @param driver - instance of web driver for browser surfing
     */
    public void preCheck(WebDriver driver) {
        log.info("Валидация обязательных полей на шаге");
        List<WebElement> mandatoryLabels = driver.findElements(By.xpath("//label[contains(@class, 'fieldRequired')]"));
        if (mandatoryLabels.size() == 0) {
            log.info("Обязательные поля для заполнения отсутствуют");
        } else {
            log.info("Количество обязательных полей для заполнения: " + mandatoryLabels.size());
        }
    }

    /**
     * Method finds all non filling required fields and accounting their
     * @param driver - instance of web driver for browser surfing
     */
    public void postCheck(WebDriver driver) {
        List<WebElement> mandatoryErrors = driver.findElements(By.xpath("//*[@class='error']"));
        if (mandatoryErrors.size() == 0) {
            log.info("Все обязательные поля были заполнены");
        } else {
            log.error("Количество незаполненных обязательных полей: " + mandatoryErrors.size());
            SeleniumPrerequisites.screenShot();
        }
    }
}
