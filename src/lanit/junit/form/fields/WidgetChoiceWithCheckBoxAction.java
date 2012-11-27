package lanit.junit.form.fields;

import com.thoughtworks.selenium.SeleniumException;
import lanit.junit.selenium.SeleniumPrerequisites;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;

import java.util.List;

public class WidgetChoiceWithCheckBoxAction extends FieldAction {

    private static Logger log = Logger.getLogger(WidgetChoiceWithCheckBoxAction.class);

    /**
     * Basic step information about choice widget with checkboxes
     * @param fieldType - type of testing field on the form
     * @param fieldIdentification - type identification testing field on the form
     * @param fieldIdentificationValue - value of type identification testing field on the form
     * @param fieldValue - value for set to testing field on the form
     * @param fieldAutoValue - value that should be in testing field on the form
     * @param fieldReadOnly - readonly attribute for testing field on the form
     * @param fieldFormatFile - type of file format for upload to portal of services
     * @param fieldValidation - define necessary of validation field on the form
     */
    public WidgetChoiceWithCheckBoxAction(String fieldType, String fieldIdentification, String fieldIdentificationValue, String fieldValue, String fieldAutoValue, String fieldReadOnly, String fieldFormatFile, String fieldValidation) {
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
                List<WebElement> lis = ((List<WebElement>) ((JavascriptExecutor) driver).executeScript("return document.getElementById('" + this.fieldIdentificationValue + "').parentNode.parentNode.parentNode.getElementsByTagName('ul')[0].getElementsByTagName('li')"));
                String actualValue = "";
                for (int i = 0; i < lis.size(); i++) {
                    WebElement li = lis.get(i);
                    actualValue += li.getText() + "&";
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
                List<WebElement> divs = (List<WebElement>) ((JavascriptExecutor) driver).executeScript("return document.getElementById('" + this.fieldIdentificationValue + "').parentNode.parentNode.getElementsByTagName('div')");
                for (int i = 0; i < divs.size(); i++) {
                    WebElement div = divs.get(i);
                    if (div.getAttribute("class").contains("fieldClear") && !div.getAttribute("style").contains("display: none")) {
                        div.click();
                        break;
                    }
                }
                driver.findElement(By.id(this.fieldIdentificationValue)).click();
                String[] checkboxes = this.fieldValue.split("&");
                for (int i = 0; i < checkboxes.length; i++) {
                    driver.findElement(By.xpath("//li/span[contains(., '" + checkboxes[i] + "')]")).click();
                }
                driver.findElement(By.xpath("//div[contains(@style, ': block') and @class='ui-dialog ui-widget ui-widget-content ui-corner-all ']/div[@class='lookup-widget ui-dialog-content ui-widget-content']/table[@class='popupBg']/tbody/tr/td/div[@class='popupFormBg pngFix']/a[@class='popupSelectBut']/span[@class='popupSelectButContent']/span[text()='Сохранить']")).click();
                SeleniumPrerequisites.sleep();
                if (driver.findElements(By.xpath("//div[contains(@style, ': block') and @class='ui-dialog ui-widget ui-widget-content ui-corner-all ']/div[@class='ui-dialog-titlebar ui-widget-header ui-corner-all ui-helper-clearfix']/a[@class='ui-dialog-titlebar-close ui-corner-all']/span[text()='Закрыть']")).size() > 0) {
                    driver.findElement(By.xpath("//div[contains(@style, ': block') and @class='ui-dialog ui-widget ui-widget-content ui-corner-all ']/div[@class='ui-dialog-titlebar ui-widget-header ui-corner-all ui-helper-clearfix']/a[@class='ui-dialog-titlebar-close ui-corner-all']/span[text()='Закрыть']")).click();
                    SeleniumPrerequisites.sleep();
                }
                List<WebElement> lis = ((List<WebElement>) ((JavascriptExecutor) driver).executeScript("return document.getElementById('" + this.fieldIdentificationValue + "').parentNode.parentNode.parentNode.getElementsByTagName('ul')[0].getElementsByTagName('li')"));
                String actualValue = "";
                for (int i = 0; i < lis.size(); i++) {
                    WebElement li = lis.get(i);
                    actualValue += li.getText() + "&";
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
