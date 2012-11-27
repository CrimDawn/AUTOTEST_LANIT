package lanit.junit.form.fields.validation;

import lanit.junit.variables.GlobalVariables;
import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;

import java.io.File;

public class UploadFieldValidation {

    private static Logger log = Logger.getLogger(UploadFieldValidation.class);
    private String[] correctFieldsFormatFile = new String[] {
        "doc", "docx", "jpeg", "jpg", "mdi", "ods", "odt", "pdf", "png", "tiff", "xls", "xlsx"
    };
    private String[] incorrectFieldsFormatFile = new String[] {
        "xml"
    };

    /**
     * Method check upload field for work with different types of file
     * @param driver - instance of web driver for browser surfing
     * @param fieldIdentificationValue - value of type identification testing field on the form
     */
    public void check(WebDriver driver, String fieldIdentificationValue) {
        log.info("Валидация поля с типом file");
        checkCorrectFileType(driver, fieldIdentificationValue);
        checkIncorrectFileType(driver, fieldIdentificationValue);
    }

    /**
     * Method check upload field for work with correct types of file
     * @param driver - instance of web driver for browser surfing
     * @param fieldIdentificationValue - value of type identification testing field on the form
     */
    private void checkCorrectFileType(WebDriver driver, String fieldIdentificationValue) {
        for (int i = 0; i < correctFieldsFormatFile.length; i++) {
            if (driver.findElements(By.xpath("//div[@id='" + fieldIdentificationValue + "']/div/ul[@class='qq-upload-list']/li/a[contains(., 'Очистить')]")).size() > 0) {
                driver.findElement(By.xpath("//div[@id='" + fieldIdentificationValue + "']/div/ul[@class='qq-upload-list']/li/a[contains(., 'Очистить')]")).click();
            }
            File file = new File(GlobalVariables.UPLOAD_FILE_PATH + "/" + GlobalVariables.UPLOAD_FILE_NAME + "." + correctFieldsFormatFile[i]);
            driver.findElement(By.xpath("//div[@id='" + fieldIdentificationValue + "']/div/div/input[@type='file']")).sendKeys(file.getAbsolutePath());
            try {
                Alert alert = driver.switchTo().alert();
                alert.accept();
                log.warn("Проверка допустимости файла с типом: " + correctFieldsFormatFile[i]);
            } catch (NoAlertPresentException e) {
                log.info("Проверка допустимости файла с типом: " + correctFieldsFormatFile[i]);
            }
        }
    }

    /**
     * Method check upload field for work with incorrect types of file
     * @param driver - instance of web driver for browser surfing
     * @param fieldIdentificationValue - value of type identification testing field on the form
     */
    private void checkIncorrectFileType(WebDriver driver, String fieldIdentificationValue) {
        for (int i = 0; i < incorrectFieldsFormatFile.length; i++) {
            if (driver.findElements(By.xpath("//div[@id='" + fieldIdentificationValue + "']/div/ul[@class='qq-upload-list']/li/a[contains(., 'Очистить')]")).size() > 0) {
                driver.findElement(By.xpath("//div[@id='" + fieldIdentificationValue + "']/div/ul[@class='qq-upload-list']/li/a[contains(., 'Очистить')]")).click();
            }
            File file = new File(GlobalVariables.UPLOAD_FILE_PATH + "/" + GlobalVariables.UPLOAD_FILE_NAME + "." + incorrectFieldsFormatFile[i]);
            driver.findElement(By.xpath("//div[@id='" + fieldIdentificationValue + "']/div/div/input[@type='file']")).sendKeys(file.getAbsolutePath());
            try {
                Alert alert = driver.switchTo().alert();
                alert.accept();
                log.info("Проверка недопустимости файла с типом: " + incorrectFieldsFormatFile[i]);
            } catch (NoAlertPresentException e) {
                log.warn("Проверка недопустимости файла с типом: " + incorrectFieldsFormatFile[i]);
            }
        }
    }
}
