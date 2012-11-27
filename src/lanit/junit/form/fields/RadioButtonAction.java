package lanit.junit.form.fields;

import com.thoughtworks.selenium.SeleniumException;
import lanit.junit.selenium.SeleniumPrerequisites;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;

public class RadioButtonAction extends FieldAction {

    private static Logger log = Logger.getLogger(RadioButtonAction.class);

    /**
     * Basic step information about radio button
     * @param fieldType - type of testing field on the form
     * @param fieldIdentification - type identification testing field on the form
     * @param fieldIdentificationValue - value of type identification testing field on the form
     * @param fieldValue - value for set to testing field on the form
     * @param fieldAutoValue - value that should be in testing field on the form
     * @param fieldReadOnly - readonly attribute for testing field on the form
     * @param fieldFormatFile - type of file format for upload to portal of services
     * @param fieldValidation - define necessary of validation field on the form
     */
    public RadioButtonAction(String fieldType, String fieldIdentification, String fieldIdentificationValue, String fieldValue, String fieldAutoValue, String fieldReadOnly, String fieldFormatFile, String fieldValidation) {
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
                WebElement span = ((WebElement) ((JavascriptExecutor) driver).executeScript("return document.getElementById('" + this.fieldIdentificationValue + "').parentNode.getElementsByTagName('span')[0]"));
                WebElement element = driver.findElement(By.xpath("//li[@class='selected']/input[@type='radio' and @id='" + this.fieldIdentificationValue + "']"));
                String actualValue = element.getAttribute("id");
                actualValue += ":" + span.getText();
                actualValue += ":readonly " + element.getAttribute("readonly");
                assertEquals(this.fieldIdentificationValue + ":" + this.fieldAutoValue + ":readonly null", actualValue);
            } else if (this.fieldIdentification.equals("id") &&
                    !this.fieldIdentificationValue.equals("") &&
                    !this.fieldValue.equals("") &&
                    this.fieldAutoValue.equals("") &&
                    this.fieldReadOnly.equals("Нет") &&
                    this.fieldFormatFile.equals("") &&
                    this.fieldValidation.equals("")) {
                WebElement span = ((WebElement) ((JavascriptExecutor) driver).executeScript("return document.getElementById('" + this.fieldIdentificationValue + "').parentNode.getElementsByTagName('span')[0]"));
                span.click();
                WebElement element = driver.findElement(By.xpath("//li[@class='selected']/input[@type='radio' and @id='" + this.fieldIdentificationValue + "']"));
                String actualValue = element.getAttribute("id");
                actualValue += ":" + span.getText();
                actualValue += ":readonly " + element.getAttribute("readonly");
                assertEquals(this.fieldIdentificationValue + ":" + this.fieldValue + ":readonly null", actualValue);
            } else if (this.fieldIdentification.equals("id") &&
                    !this.fieldIdentificationValue.equals("") &&
                    this.fieldValue.equals("") &&
                    !this.fieldAutoValue.equals("") &&
                    this.fieldReadOnly.equals("Да") &&
                    this.fieldFormatFile.equals("") &&
                    this.fieldValidation.equals("")) {
                WebElement element = driver.findElement(By.xpath("//li[@class='selected']/input[@type='radio' and @id='" + this.fieldIdentificationValue + "']"));
                String actualValue = element.getAttribute("id");
                actualValue += ":readonly " + element.getAttribute("readonly");
                assertEquals(this.fieldIdentificationValue + ":readonly true", actualValue);
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
