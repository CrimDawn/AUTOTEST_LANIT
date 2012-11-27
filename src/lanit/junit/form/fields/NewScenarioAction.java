package lanit.junit.form.fields;

import com.thoughtworks.selenium.SeleniumException;
import lanit.junit.selenium.SeleniumPrerequisites;
import lanit.junit.variables.GlobalVariables;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;

public class NewScenarioAction extends FieldAction {

    private static Logger log = Logger.getLogger(NewScenarioAction.class);

    /**
     * Basic step information about new scenario with new user on the form
     * @param fieldType - type of testing field on the form
     * @param fieldIdentification - type identification testing field on the form
     * @param fieldIdentificationValue - value of type identification testing field on the form
     * @param fieldValue - value for set to testing field on the form
     * @param fieldAutoValue - value that should be in testing field on the form
     * @param fieldReadOnly - readonly attribute for testing field on the form
     * @param fieldFormatFile - type of file format for upload to portal of services
     * @param fieldValidation - define necessary of validation field on the form
     */
    public NewScenarioAction(String fieldType, String fieldIdentification, String fieldIdentificationValue, String fieldValue, String fieldAutoValue, String fieldReadOnly, String fieldFormatFile, String fieldValidation) {
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
            if (this.fieldIdentification.equals("") &&
                    this.fieldIdentificationValue.equals("") &&
                    this.fieldValue.equals("") &&
                    (this.fieldAutoValue.equals("Да") || this.fieldAutoValue.equals("Нет")) &&
                    this.fieldReadOnly.equals("") &&
                    this.fieldFormatFile.equals("") &&
                    this.fieldValidation.equals("")) {
                if (this.fieldAutoValue.equals("Да")) {
                    driver.navigate().to(GlobalVariables.CURRENT_SERVICE_HREF);
                    if (driver.getTitle().contains("Выбор черновика заявления")) {
                        String serviceHref = ((JavascriptExecutor) driver).executeScript("return window.location.href;").toString();
                        serviceHref = serviceHref.replaceFirst("init", "initForm");
                        driver.navigate().to(serviceHref);
                    }
                }
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
