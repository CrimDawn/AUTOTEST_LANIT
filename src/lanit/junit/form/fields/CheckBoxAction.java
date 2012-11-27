package lanit.junit.form.fields;

import com.thoughtworks.selenium.SeleniumException;
import lanit.junit.selenium.SeleniumPrerequisites;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;

public class CheckBoxAction extends FieldAction {

    private static Logger log = Logger.getLogger(CheckBoxAction.class);

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
    public CheckBoxAction(String fieldType, String fieldIdentification, String fieldIdentificationValue, String fieldValue, String fieldAutoValue, String fieldReadOnly, String fieldFormatFile, String fieldValidation) {
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
                    this.fieldAutoValue.equals("null") &&
                    this.fieldReadOnly.equals("") &&
                    this.fieldFormatFile.equals("") &&
                    this.fieldValidation.equals("")) {
                WebElement element = driver.findElement(By.xpath("//li[@class='selected']/input[@type='checkbox' and @id='" + this.fieldIdentificationValue + "']"));
                String actualValue = element.getAttribute("id");
                element = ((WebElement) ((JavascriptExecutor) driver).executeScript("return document.getElementById('" + this.fieldIdentificationValue + "').parentNode.getElementsByTagName('span')[0]"));
                actualValue += ":" + element.getText();
                if (actualValue.endsWith(":")) {
                    actualValue += "null";
                }
                assertEquals(this.fieldIdentificationValue + ":" + this.fieldAutoValue, actualValue);
            } else if (this.fieldIdentification.equals("id") &&
                    !this.fieldIdentificationValue.equals("") &&
                    this.fieldValue.equals("null") &&
                    this.fieldAutoValue.equals("") &&
                    this.fieldReadOnly.equals("") &&
                    this.fieldFormatFile.equals("") &&
                    this.fieldValidation.equals("")) {
                if (driver.findElements(By.xpath("//li[@class='selected']/input[@type='checkbox' and @id='" + this.fieldIdentificationValue + "']")).size() > 0) {
                    WebElement a = (WebElement) ((JavascriptExecutor) driver).executeScript("return document.getElementById('" + this.fieldIdentificationValue + "').parentNode.getElementsByTagName('a')[1]");
                    if (a.getAttribute("class").equals("checkDeSelectNew")) {
                        a.click();
                    } else {
                        ((WebElement) ((JavascriptExecutor) driver).executeScript("return document.getElementById('" + this.fieldIdentificationValue + "').parentNode.getElementsByTagName('a')[0]")).click();
                    }
                }
                WebElement a = (WebElement) ((JavascriptExecutor) driver).executeScript("return document.getElementById('" + this.fieldIdentificationValue + "').parentNode.getElementsByTagName('a')[0]");
                if (a.getAttribute("class").equals("checkSelectNew")) {
                    a.click();
                } else {
                    ((WebElement) ((JavascriptExecutor) driver).executeScript("return document.getElementById('" + this.fieldIdentificationValue + "').parentNode.getElementsByTagName('a')[1]")).click();
                }
                WebElement element = driver.findElement(By.xpath("//li[@class='selected']/input[@type='checkbox' and @id='" + this.fieldIdentificationValue + "']"));
                String actualValue = element.getAttribute("id");
                element = ((WebElement) ((JavascriptExecutor) driver).executeScript("return document.getElementById('" + this.fieldIdentificationValue + "').parentNode.getElementsByTagName('span')[0]"));
                actualValue += ":" + element.getText();
                if (actualValue.endsWith(":")) {
                    actualValue += "null";
                }
                assertEquals(this.fieldIdentificationValue + ":" + this.fieldValue, actualValue);
            } else if (this.fieldIdentification.equals("id") &&
                    !this.fieldIdentificationValue.equals("") &&
                    this.fieldValue.equals("") &&
                    !this.fieldAutoValue.equals("") &&
                    this.fieldReadOnly.equals("") &&
                    this.fieldFormatFile.equals("") &&
                    this.fieldValidation.equals("")) {
                WebElement element = driver.findElement(By.xpath("//li[@class='selected']/input[@type='checkbox' and @id='" + this.fieldIdentificationValue + "']"));
                String actualValue = element.getAttribute("id");
                element = ((WebElement) ((JavascriptExecutor) driver).executeScript("return document.getElementById('" + this.fieldIdentificationValue + "').parentNode.getElementsByTagName('span')[0]"));
                if (element.getText().contains(this.fieldAutoValue)) {
                    actualValue += ":" + this.fieldAutoValue;
                } else {
                    actualValue += ":" + element.getText();
                }
                assertEquals(this.fieldIdentificationValue + ":" + this.fieldAutoValue, actualValue);
            } else if (this.fieldIdentification.equals("id") &&
                    !this.fieldIdentificationValue.equals("") &&
                    !this.fieldValue.equals("") &&
                    this.fieldAutoValue.equals("") &&
                    this.fieldReadOnly.equals("") &&
                    this.fieldFormatFile.equals("") &&
                    this.fieldValidation.equals("")) {
                if (driver.findElements(By.xpath("//li[@class='selected']/input[@type='checkbox' and @id='" + this.fieldIdentificationValue + "']")).size() > 0) {
                    driver.findElement(By.xpath("//li[@class='selected']/span[contains(., '" + this.fieldValue + "')]")).click();
                }
                driver.findElement(By.xpath("//li/span[contains(., '" + this.fieldValue + "')]")).click();
                WebElement element = driver.findElement(By.xpath("//li[@class='selected']/input[@type='checkbox' and @id='" + this.fieldIdentificationValue + "']"));
                String actualValue = element.getAttribute("id");
                element = ((WebElement) ((JavascriptExecutor) driver).executeScript("return document.getElementById('" + this.fieldIdentificationValue + "').parentNode.getElementsByTagName('span')[0]"));
                if (element.getText().contains(this.fieldValue)) {
                    actualValue += ":" + this.fieldValue;
                } else {
                    actualValue += ":" + element.getText();
                }
                assertEquals(this.fieldIdentificationValue + ":" + this.fieldValue, actualValue);
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
