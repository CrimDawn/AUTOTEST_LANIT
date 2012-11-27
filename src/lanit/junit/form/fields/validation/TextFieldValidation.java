package lanit.junit.form.fields.validation;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class TextFieldValidation {

    private static Logger log = Logger.getLogger(TextFieldValidation.class);
    private String[] letterForLongWord = new String[] {
        "a"
    };
    private String[] specialSymbols = new String[] {
        "!", "#", "$", "%", "^", "~", "<", ">", "?", "{", "}", "[", "]"
    };
    private String[] correctHintCombinations = new String[] {
        "+7(123)12345", "+7(1234)12345", "+7(12345)12345", "+7(123)123456", "+7(123)1234567",
        "8(123)12345", "8(1234)12345", "8(12345)12345", "8(123)123456", "8(123)1234567",
        "(123)12345", "(1234)12345", "(12345)12345", "(123)123456", "(123)1234567"
    };
    private String[] incorrectHintCombinations = new String[] {
        "+9(123)12345", "+7(12)12345", "+7(123456)12345", "+7(12345)1234", "+7(12345)12345678"
    };

    /**
     * Method check text field for work with special symbols
     * @param driver - instance of web driver for browser surfing
     * @param fieldIdentificationValue - value of type identification testing field on the form
     */
    public void check(WebDriver driver, String fieldIdentificationValue) {
        log.info("Валидация поля с типом text");
        checkSpecialSymbols(driver, fieldIdentificationValue);
        if (fieldIdentificationValue.equals("phone")) {
            checkPhoneHints(driver, fieldIdentificationValue);
        }
    }

    /**
     * Method check text field for work with special symbols
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

    /**
     * Method check text field with "phone" identification for work with different mask combinations
     * @param driver - instance of web driver for browser surfing
     * @param fieldIdentificationValue - value of type identification testing field on the form
     */
    private void checkPhoneHints(WebDriver driver, String fieldIdentificationValue) {
        for (int i = 0; i < correctHintCombinations.length; i++) {
            ((JavascriptExecutor) driver).executeScript("document.getElementById('" + fieldIdentificationValue + "').value = ''");
            ((JavascriptExecutor) driver).executeScript("document.getElementById('" + fieldIdentificationValue + "').value = '" + correctHintCombinations[i] + "'");
            ((JavascriptExecutor) driver).executeScript("document.getElementById('" + fieldIdentificationValue + "').focus()");
            ((JavascriptExecutor) driver).executeScript("document.getElementById('" + fieldIdentificationValue + "').blur()");
            if (driver.findElements(By.xpath("//label[@for='" + fieldIdentificationValue + "' and @class='error']")).size() > 0) {
                WebElement element = driver.findElement(By.xpath("//label[@for='" + fieldIdentificationValue + "' and @class='error']"));
                String style = element.getAttribute("style");
                if (style.contains("none")) {
                    log.info("Проверка допустимости маски для телефона: " + correctHintCombinations[i]);
                } else {
                    log.warn("Проверка допустимости маски для телефона: " + correctHintCombinations[i]);
                }
            } else {
                log.info("Проверка допустимости маски для телефона: " + correctHintCombinations[i]);
            }
        }
        for (int i = 0; i < incorrectHintCombinations.length; i++) {
            ((JavascriptExecutor) driver).executeScript("document.getElementById('" + fieldIdentificationValue + "').value = ''");
            ((JavascriptExecutor) driver).executeScript("document.getElementById('" + fieldIdentificationValue + "').value = '" + incorrectHintCombinations[i] + "'");
            ((JavascriptExecutor) driver).executeScript("document.getElementById('" + fieldIdentificationValue + "').focus()");
            ((JavascriptExecutor) driver).executeScript("document.getElementById('" + fieldIdentificationValue + "').blur()");
            if (driver.findElements(By.xpath("//label[@for='" + fieldIdentificationValue + "' and @class='error']")).size() > 0) {
                WebElement element = driver.findElement(By.xpath("//label[@for='" + fieldIdentificationValue + "' and @class='error']"));
                String style = element.getAttribute("style");
                if (style.contains("none")) {
                    log.warn("Проверка недопустимости маски для телефона: " + incorrectHintCombinations[i]);
                } else {
                    log.info("Проверка недопустимости маски для телефона: " + incorrectHintCombinations[i]);
                }
            } else {
                log.warn("Проверка недопустимости маски для телефона: " + incorrectHintCombinations[i]);
            }
        }
    }
}
