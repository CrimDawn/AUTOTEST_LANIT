package lanit.junit.form.fields;

import com.thoughtworks.selenium.SeleniumException;
import lanit.junit.form.fields.validation.UploadFieldValidation;
import lanit.junit.selenium.SeleniumPrerequisites;
import lanit.junit.variables.GlobalVariables;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;

import java.io.File;

public class UploadFileAction extends FieldAction {

    private static Logger log = Logger.getLogger(UploadFileAction.class);

    /**
     * Basic step information about upload file field
     * @param fieldType - type of testing field on the form
     * @param fieldIdentification - type identification testing field on the form
     * @param fieldIdentificationValue - value of type identification testing field on the form
     * @param fieldValue - value for set to testing field on the form
     * @param fieldAutoValue - value that should be in testing field on the form
     * @param fieldReadOnly - readonly attribute for testing field on the form
     * @param fieldFormatFile - type of file format for upload to portal of services
     * @param fieldValidation - define necessary of validation field on the form
     */
    public UploadFileAction(String fieldType, String fieldIdentification, String fieldIdentificationValue, String fieldValue, String fieldAutoValue, String fieldReadOnly, String fieldFormatFile, String fieldValidation) {
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
                    this.fieldAutoValue.equals("x") &&
                    this.fieldReadOnly.equals("") &&
                    !this.fieldFormatFile.equals("") &&
                    this.fieldValidation.equals("")) {
                WebElement elementSpan = driver.findElement(By.xpath("//div[@id='" + this.fieldIdentificationValue + "']/div/ul[@class='qq-upload-list']/li/span[@class='qq-upload-file']"));
                String actualValue = elementSpan.getText();
                assertEquals(GlobalVariables.UPLOAD_FILE_NAME + "." + this.fieldFormatFile, actualValue);
            } else if (this.fieldIdentification.equals("id") &&
                    !this.fieldIdentificationValue.equals("") &&
                    this.fieldValue.equals("x") &&
                    this.fieldAutoValue.equals("") &&
                    this.fieldReadOnly.equals("") &&
                    !this.fieldFormatFile.equals("") &&
                    (this.fieldValidation.equals("Да") || this.fieldValidation.equals("Нет"))) {
                if (this.fieldValidation.equals("Да")) {
                    new UploadFieldValidation().check(driver, this.fieldIdentificationValue);
                }
                if (driver.findElements(By.xpath("//div[@id='" + this.fieldIdentificationValue + "']/div/ul[@class='qq-upload-list']/li/a[contains(., 'Очистить')]")).size() > 0) {
                    driver.findElement(By.xpath("//div[@id='" + this.fieldIdentificationValue + "']/div/ul[@class='qq-upload-list']/li/a[contains(., 'Очистить')]")).click();
                }
                File file = new File(GlobalVariables.UPLOAD_FILE_PATH + "/" + GlobalVariables.UPLOAD_FILE_NAME + "." + this.fieldFormatFile);
                driver.findElement(By.xpath("//div[@id='" + this.fieldIdentificationValue + "']/div/div/input[@type='file']")).sendKeys(file.getAbsolutePath());
                WebElement elementSpan = driver.findElement(By.xpath("//div[@id='" + this.fieldIdentificationValue + "']/div/ul[@class='qq-upload-list']/li/span[@class='qq-upload-file']"));
                String actualValue = elementSpan.getText();
                assertEquals(GlobalVariables.UPLOAD_FILE_NAME + "." + this.fieldFormatFile, actualValue);
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
