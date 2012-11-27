package lanit.junit.form.fields;

import com.thoughtworks.selenium.SeleniumException;
import lanit.junit.form.fields.validation.MemoFieldValidation;
import lanit.junit.selenium.SeleniumPrerequisites;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;

public class MemoAction extends FieldAction {

    private static Logger log = Logger.getLogger(MemoAction.class);

    /**
     * Basic step information about memo field
     * @param fieldType - type of testing field on the form
     * @param fieldIdentification - type identification testing field on the form
     * @param fieldIdentificationValue - value of type identification testing field on the form
     * @param fieldValue - value for set to testing field on the form
     * @param fieldAutoValue - value that should be in testing field on the form
     * @param fieldReadOnly - readonly attribute for testing field on the form
     * @param fieldFormatFile - type of file format for upload to portal of services
     * @param fieldValidation - define necessary of validation field on the form
     */
    public MemoAction(String fieldType, String fieldIdentification, String fieldIdentificationValue, String fieldValue, String fieldAutoValue, String fieldReadOnly, String fieldFormatFile, String fieldValidation) {
        super(fieldType, fieldIdentification, fieldIdentificationValue, fieldValue, fieldAutoValue, fieldReadOnly, fieldFormatFile, fieldValidation);
    }

    /**
     * Method start testing field on the form
     * @param driver - instance of web driver for browser surfing
     */
    @Override
    public void run(WebDriver driver) {
        this.driver = driver;
        try {
            if (this.fieldIdentification.equals("id") &&
                    !this.fieldIdentificationValue.equals("") &&
                    this.fieldValue.equals("") &&
                    !this.fieldAutoValue.equals("") &&
                    this.fieldReadOnly.equals("Нет") &&
                    this.fieldFormatFile.equals("") &&
                    this.fieldValidation.equals("")) {
                WebElement element = driver.findElement(By.xpath("//textarea[@id='" + this.fieldIdentificationValue + "']"));
                String actualValue = element.getAttribute("value");
                actualValue += ":readonly " + element.getAttribute("readonly");
                assertEquals(this.fieldAutoValue + ":readonly null", actualValue);
            } else if (this.fieldIdentification.equals("id") &&
                    !this.fieldIdentificationValue.equals("") &&
                    !this.fieldValue.equals("") &&
                    this.fieldAutoValue.equals("") &&
                    this.fieldReadOnly.equals("Нет") &&
                    this.fieldFormatFile.equals("") &&
                    (this.fieldValidation.equals("Да") || this.fieldValidation.equals("Нет"))) {
                if (this.fieldValidation.equals("Да")) {
                    new MemoFieldValidation().check(driver, this.fieldIdentificationValue);
                }
                ((JavascriptExecutor) driver).executeScript("document.getElementById('" + this.fieldIdentificationValue + "').value = ''");
                driver.findElement(By.id(this.fieldIdentificationValue)).sendKeys(this.fieldValue);
                WebElement element = driver.findElement(By.id(this.fieldIdentificationValue));
                String actualValue = element.getAttribute("value");
                actualValue += ":readonly " + element.getAttribute("readonly");
                assertEquals(this.fieldValue + ":readonly null", actualValue);
            } else if (this.fieldIdentification.equals("id") &&
                    !this.fieldIdentificationValue.equals("") &&
                    this.fieldValue.equals("") &&
                    !this.fieldAutoValue.equals("") &&
                    this.fieldReadOnly.equals("Да") &&
                    this.fieldFormatFile.equals("") &&
                    this.fieldValidation.equals("")) {
                WebElement element = driver.findElement(By.xpath("//textarea[@id='" + this.fieldIdentificationValue + "']"));
                String actualValue = element.getAttribute("value");
                actualValue += ":readonly " + element.getAttribute("readonly");
                assertEquals(this.fieldAutoValue + ":readonly true", actualValue);
            } else {
                log.warn("Текущая исследуемая комбинация некорректна: " +
                        this.fieldType + "/" +
                        this.fieldIdentification + "/" +
                        this.fieldIdentificationValue + "/" +
                        this.fieldValue + "/" +
                        this.fieldAutoValue + "/" +
                        this.fieldReadOnly + "/" +
                        this.fieldFormatFile + "/" +
                        this.fieldValidation);
            }
        } catch (InvalidSelectorException e) {
            log.error(e.getMessage(), e);
            SeleniumPrerequisites.screenShot();
        } catch (SeleniumException e) {
            log.error(e.getMessage(), e);
            SeleniumPrerequisites.screenShot();
        } catch (ElementNotVisibleException e) {
            log.error(e.getMessage(), e);
            SeleniumPrerequisites.screenShot();
        } catch (NullPointerException e) {
            log.error(e.getMessage(), e);
            SeleniumPrerequisites.screenShot();
        } catch (WebDriverException e) {
            log.error(e.getMessage(), e);
            SeleniumPrerequisites.screenShot();
        }
    }
}
