package lanit.junit.form.fields.validation;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class MemoFieldValidation {

    private static Logger log = Logger.getLogger(MemoFieldValidation.class);
    private String[] letterForLongWord = new String[] {
        "a"
    };
    private String[] specialSymbols = new String[] {
        "!", "#", "$", "%", "^", "~", "<", ">", "?", "{", "}", "[", "]"
    };

    /**
     * Method check text area field for work with special symbols
     * @param driver - instance of web driver for browser surfing
     * @param fieldIdentificationValue - value of type identification testing field on the form
     */
    public void check(WebDriver driver, String fieldIdentificationValue) {
        log.info("Валидация поля с типом textarea");
        checkSpecialSymbols(driver, fieldIdentificationValue);
    }

    /**
     * Method check text area field for work with special symbols
     * @param driver - instance of web driver for browser surfing
     * @param fieldIdentificationValue - value of type identification testing field on the form
     */
    private void checkSpecialSymbols(WebDriver driver, String fieldIdentificationValue) {
        for (int i = 0; i < specialSymbols.length; i++) {
            ((JavascriptExecutor) driver).executeScript("document.getElementById('" + fieldIdentificationValue + "').value = ''");
            ((JavascriptExecutor) driver).executeScript("document.getElementById('" + fieldIdentificationValue + "').value = '" + specialSymbols[i] + "'");
            ((JavascriptExecutor) driver).executeScript("document.getElementById('" + fieldIdentificationValue + "').focus()");
            ((JavascriptExecutor) driver).executeScript("document.getElementById('" + fieldIdentificationValue + "').blur()");
            if (driver.findElements(By.xpath("//label[@for='" + fieldIdentificationValue + "' and @class='error']")).size() > 0) {
                WebElement element = driver.findElement(By.xpath("//label[@for='" + fieldIdentificationValue + "' and @class='error']"));
                String style = element.getAttribute("style");
                if (style.contains("none")) {
                    log.warn("Проверка недопустимости специального символа: " + specialSymbols[i]);
                } else {
                    log.info("Проверка недопустимости специального символа: " + specialSymbols[i]);
                }
            } else {
                log.warn("Проверка недопустимости специального символа: " + specialSymbols[i]);
            }
        }
    }
}
