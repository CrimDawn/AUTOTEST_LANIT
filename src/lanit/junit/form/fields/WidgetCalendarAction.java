package lanit.junit.form.fields;

import com.thoughtworks.selenium.SeleniumException;
import lanit.junit.selenium.SeleniumPrerequisites;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;

import java.util.List;
import java.util.StringTokenizer;

public class WidgetCalendarAction extends FieldAction {

    private static Logger log = Logger.getLogger(WidgetCalendarAction.class);

    /**
     * Basic step information about calendar widget
     * @param fieldType - type of testing field on the form
     * @param fieldIdentification - type identification testing field on the form
     * @param fieldIdentificationValue - value of type identification testing field on the form
     * @param fieldValue - value for set to testing field on the form
     * @param fieldAutoValue - value that should be in testing field on the form
     * @param fieldReadOnly - readonly attribute for testing field on the form
     * @param fieldFormatFile - type of file format for upload to portal of services
     * @param fieldValidation - define necessary of validation field on the form
     */
    public WidgetCalendarAction(String fieldType, String fieldIdentification, String fieldIdentificationValue, String fieldValue, String fieldAutoValue, String fieldReadOnly, String fieldFormatFile, String fieldValidation) {
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
                WebElement element = driver.findElement(By.xpath("//input[@type='text' and @id='" + this.fieldIdentificationValue + "']"));
                String actualValue = element.getAttribute("value");
                actualValue += ":readonly " + element.getAttribute("readonly");
                assertEquals(this.fieldAutoValue + ":readonly null", actualValue);
            } else if (this.fieldIdentification.equals("id") &&
                    !this.fieldIdentificationValue.equals("") &&
                    !this.fieldValue.equals("") &&
                    this.fieldAutoValue.equals("") &&
                    this.fieldReadOnly.equals("Нет") &&
                    this.fieldFormatFile.equals("") &&
                    this.fieldValidation.equals("")) {
                ((JavascriptExecutor) driver).executeScript("document.getElementById('" + this.fieldIdentificationValue + "').value = ''");
                StringTokenizer token = new StringTokenizer(this.fieldValue, ".");
                if (token.countTokens() == 3) {
                    String[] partOfDate = new String[3];
                    //День
                    partOfDate[0] = token.nextToken();
                    if (partOfDate[0].startsWith("0")) {
                        partOfDate[0] = partOfDate[0].substring(1, partOfDate[0].length());
                    }
                    //Месяц
                    partOfDate[1] = token.nextToken();
                    if (partOfDate[1].startsWith("0")) {
                        partOfDate[1] = partOfDate[1].substring(1, partOfDate[1].length());
                    }
                    partOfDate[1] = String.valueOf(Integer.parseInt(partOfDate[1]) - 1);
                    //Год
                    partOfDate[2] = token.nextToken();
                    if (partOfDate[2].startsWith("0")) {
                        partOfDate[2] = partOfDate[2].substring(1, partOfDate[2].length());
                    }
                    driver.findElement(By.id(this.fieldIdentificationValue)).click();
                    WebElement selectMonth = driver.findElement(By.xpath("//select[@class='ui-datepicker-month']"));
                    List<WebElement> options = selectMonth.findElements(By.tagName("option"));
                    for (WebElement option : options) {
                        if (partOfDate[1].equals(option.getAttribute("value"))) {
                            option.click();
                            break;
                        }
                    }
                    WebElement selectYear = driver.findElement(By.xpath("//select[@class='ui-datepicker-year']"));
                    options = selectYear.findElements(By.tagName("option"));
                    for (WebElement option : options) {
                        if (partOfDate[2].equals(option.getAttribute("value"))) {
                            option.click();
                            break;
                        }
                    }
                    driver.findElement(By.xpath("//a[contains(@class, 'ui-state-default') and text()='" + partOfDate[0] + "']")).click();
                    SeleniumPrerequisites.sleep();
                    WebElement element = driver.findElement(By.xpath("//input[@type='text' and @id='" + this.fieldIdentificationValue + "']"));
                    String actualValue = element.getAttribute("value");
                    actualValue += ":readonly " + element.getAttribute("readonly");
                    assertEquals(this.fieldValue + ":readonly null", actualValue);
                } else {
                    log.warn("Формат даты задан неправильно. Используйте маску <dd.mm.yyyy> (например, 23.02.1990)");
                }
            } else if (this.fieldIdentification.equals("id") &&
                    !this.fieldIdentificationValue.equals("") &&
                    this.fieldValue.equals("") &&
                    !this.fieldAutoValue.equals("") &&
                    this.fieldReadOnly.equals("Да") &&
                    this.fieldFormatFile.equals("") &&
                    this.fieldValidation.equals("")) {
                WebElement element = driver.findElement(By.xpath("//input[@type='text' and @id='" + this.fieldIdentificationValue + "']"));
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
