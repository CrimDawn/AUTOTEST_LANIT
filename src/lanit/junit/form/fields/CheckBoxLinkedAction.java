package lanit.junit.form.fields;

import com.thoughtworks.selenium.SeleniumException;
import lanit.junit.selenium.SeleniumPrerequisites;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;

public class CheckBoxLinkedAction extends FieldAction {

    private static Logger log = Logger.getLogger(CheckBoxLinkedAction.class);

    /**
     * Basic step information about checkbox field
     * @param fieldType - type of testing field on the form
     * @param fieldIdentification - type identification testing field on the form
     * @param fieldIdentificationValue - value of type identification testing field on the form
     * @param fieldValue - value for set to testing field on the form
     * @param fieldAutoValue - value that should be in testing field on the form
     * @param fieldReadOnly - readonly attribute for testing field on the form
     * @param fieldFormatFile - type of file format for upload to portal of services
     * @param fieldValidation - define necessary of validation field on the form
     */
    public CheckBoxLinkedAction(String fieldType, String fieldIdentification, String fieldIdentificationValue, String fieldValue, String fieldAutoValue, String fieldReadOnly, String fieldFormatFile, String fieldValidation) {
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
                    this.fieldReadOnly.equals("") &&
                    this.fieldFormatFile.equals("") &&
                    this.fieldValidation.equals("")) {
                String[] checkboxes = this.fieldAutoValue.split("&");
                String actualValue = "";
                for (int i = 0; i < checkboxes.length; i++) {
                    WebElement span = driver.findElement(By.xpath("//ul[@id='" + this.fieldIdentificationValue + "']/li[@class='selected']/span[text()='" + checkboxes[i] + "']"));
                    actualValue += span.getText() + "&";
                }
                actualValue = actualValue.substring(0, actualValue.length() - 1);
                assertEquals(this.fieldAutoValue, actualValue);
            } else if (this.fieldIdentification.equals("id") &&
                    !this.fieldIdentificationValue.equals("") &&
                    !this.fieldValue.equals("") &&
                    this.fieldAutoValue.equals("") &&
                    this.fieldReadOnly.equals("") &&
                    this.fieldFormatFile.equals("") &&
                    this.fieldValidation.equals("")) {
                String[] checkboxes = this.fieldValue.split("&");
                for (int i = 0; i < checkboxes.length; i++) {
                    if (driver.findElements(By.xpath("//ul[@id='" + this.fieldIdentificationValue + "']/li[@class='selected']/span[text()='" + checkboxes[i] + "']")).size() > 0) {
                        driver.findElement(By.xpath("//ul[@id='" + this.fieldIdentificationValue + "']/li[@class='selected']/span[text()='" + checkboxes[i] + "']")).click();
                    }
                }
                for (int i = 0; i < checkboxes.length; i++) {
                    driver.findElement(By.xpath("//ul[@id='" + this.fieldIdentificationValue + "']/li/span[text()='" + checkboxes[i] + "']")).click();
                }
                String actualValue = "";
                for (int i = 0; i < checkboxes.length; i++) {
                    WebElement span = driver.findElement(By.xpath("//ul[@id='" + this.fieldIdentificationValue + "']/li[@class='selected']/span[text()='" + checkboxes[i] + "']"));
                    actualValue += span.getText() + "&";
                }
                actualValue = actualValue.substring(0, actualValue.length() - 1);
                assertEquals(this.fieldValue, actualValue);
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
