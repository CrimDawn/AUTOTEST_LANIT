package lanit.junit.form.fields;

import lanit.junit.form.fields.beautifier.IDBeautifier;
import lanit.junit.selenium.SeleniumPrerequisites;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

public abstract class FieldAction {

    private static Logger log = Logger.getLogger(FieldAction.class);

    public String fieldType;
    public String fieldIdentification;
    public String fieldIdentificationValue;
    public String fieldValue;
    public String fieldAutoValue;
    public String fieldReadOnly;
    public String fieldFormatFile;
    public String fieldValidation;
    public WebDriver driver;

    /**
     * Basic step information for form testing
     * @param fieldType - type of testing field on the form
     * @param fieldIdentification - type identification testing field on the form
     * @param fieldIdentificationValue - value of type identification testing field on the form
     * @param fieldValue - value for set to testing field on the form
     * @param fieldAutoValue - value that should be in testing field on the form
     * @param fieldReadOnly - readonly attribute for testing field on the form
     * @param fieldFormatFile - type of file format for upload to portal of services
     * @param fieldValidation - define necessary of validation field on the form
     */
    public FieldAction(String fieldType, String fieldIdentification, String fieldIdentificationValue, String fieldValue, String fieldAutoValue, String fieldReadOnly, String fieldFormatFile, String fieldValidation) {
        this.fieldType = fieldType;
        this.fieldIdentification = fieldIdentification;
        this.fieldIdentificationValue = new IDBeautifier().beatifyID(fieldType, fieldIdentificationValue);
        this.fieldValue = fieldValue;
        this.fieldAutoValue = fieldAutoValue;
        this.fieldReadOnly = fieldReadOnly;
        this.fieldFormatFile = fieldFormatFile;
        this.fieldValidation = fieldValidation;
    }

    /**
     * Method defines previous button
     * @return previous button or not
     */
    public boolean isPrevButton() {
        return this.fieldType.equals("Назад") ? true : false;
    }

    /**
     * Method defines next button
     * @return next button or not
     */
    public boolean isNextButton() {
        return this.fieldType.equals("Далее") ? true : false;
    }

    /**
     * Method defines apply button
     * @return apply button or not
     */
    public boolean isApplyButton() {
        return this.fieldType.equals("Подать заявление") ? true : false;
    }

    /**
     * Method defines new scenario
     * @return new scenario or not
     */
    public boolean isNewScenario() {
        return this.fieldType.equals("Новый сценарий") ? true : false;
    }

    /**
     * Method defines skip step up
     * @return skip step up or not
     */
    public boolean isSkipStepUp() {
        return this.fieldType.equals("Пропустить шаг вперед") ? true : false;
    }

    /**
     * Method defines skip step down
     * @return skip step down or not
     */
    public boolean isSkipStepDown() {
        return this.fieldType.equals("Пропустить шаг назад") ? true : false;
    }

    /**
     * Method compares two string
     * @param expectResult - string with expected result
     * @param actualResult - string with actual result
     */
    public void assertEquals(String expectResult, String actualResult) {
        try {
            org.junit.Assert.assertEquals(expectResult, actualResult);
            log.info("Исследуемая комбинация найдена успешно: " +
                    this.fieldType + "/" +
                    this.fieldIdentification + "/" +
                    this.fieldIdentificationValue + "/" +
                    this.fieldValue + "/" +
                    this.fieldAutoValue + "/" +
                    this.fieldReadOnly + "/" +
                    this.fieldFormatFile + "/" +
                    this.fieldValidation);
            SeleniumPrerequisites.screenShot();
        } catch (org.junit.ComparisonFailure e) {
            log.error(e.getMessage(), e);
            SeleniumPrerequisites.screenShot();
        }
    }

    /**
     * Method start testing field on the form
     * @param driver - instance of web driver for browser surfing
     */
    public abstract void run(WebDriver driver);
}
