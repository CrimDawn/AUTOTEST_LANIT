package lanit.junit.form.fields;

import com.thoughtworks.selenium.SeleniumException;
import lanit.junit.selenium.SeleniumPrerequisites;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;

import java.util.List;

public class WidgetChoiceAction extends FieldAction {

    private static Logger log = Logger.getLogger(WidgetChoiceAction.class);

    /**
     * Basic step information about choice widget
     * @param fieldType - type of testing field on the form
     * @param fieldIdentification - type identification testing field on the form
     * @param fieldIdentificationValue - value of type identification testing field on the form
     * @param fieldValue - value for set to testing field on the form
     * @param fieldAutoValue - value that should be in testing field on the form
     * @param fieldReadOnly - readonly attribute for testing field on the form
     * @param fieldFormatFile - type of file format for upload to portal of services
     * @param fieldValidation - define necessary of validation field on the form
     */
    public WidgetChoiceAction(String fieldType, String fieldIdentification, String fieldIdentificationValue, String fieldValue, String fieldAutoValue, String fieldReadOnly, String fieldFormatFile, String fieldValidation) {
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
                WebElement element = driver.findElement(By.xpath("//input[@type='text' and @id='" + this.fieldIdentificationValue + "']"));
                String actualValue = element.getAttribute("value");
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
                    if (div.getAttribute("class").contains("fieldClear") && !div.getAttribute("style").contains(": none")) {
                        div.click();
                        break;
                    }
                }
                driver.findElement(By.id(this.fieldIdentificationValue)).click();
                SeleniumPrerequisites.sleep();
                SeleniumPrerequisites.sleep();
                SeleniumPrerequisites.sleep();
                String lastParameter = this.fieldValue.split("&")[this.fieldValue.split("&").length - 1];
                if (driver.findElements(By.xpath("//div[contains(@style, ': block') and @class='ui-dialog ui-widget ui-widget-content ui-corner-all ']/div[@class='lookup-widget ui-dialog-content ui-widget-content']/table[@class='popupBg']/tbody/tr[2]/td/div[@class='popupFormBg pngFix']/div[@class='whiteBox']/div[@class='whiteBoxContent']/div[@class='searchDiv']/table[@class='searchField']/tbody/tr[1]/td[@class='searchFieldRight']/div[@class='searchFieldLeft']/input")).size() > 0) {
                    String[] parameters = this.fieldValue.split("&");
                    for (int i = 0; i < parameters.length; i++) {
                        driver.findElement(By.xpath("//div[contains(@style, ': block') and @class='ui-dialog ui-widget ui-widget-content ui-corner-all ']/div[@class='lookup-widget ui-dialog-content ui-widget-content']/table[@class='popupBg']/tbody/tr[2]/td/div[@class='popupFormBg pngFix']/div[@class='whiteBox']/div[@class='whiteBoxContent']/div[@class='searchDiv']/table[@class='searchField']/tbody/tr[1]/td[@class='searchFieldRight']/div[@class='searchFieldLeft']/input")).sendKeys(parameters[i]);
                        driver.findElement(By.xpath("//div[contains(@style, ': block') and @class='ui-dialog ui-widget ui-widget-content ui-corner-all ']/div[@class='lookup-widget ui-dialog-content ui-widget-content']/table[@class='popupBg']/tbody/tr[2]/td/div[@class='popupFormBg pngFix']/div[@class='whiteBox']/div[@class='whiteBoxContent']/div[@class='searchDiv']/table[@class='searchField']/tbody/tr[1]/td[@class='searchFieldBut']/a")).click();
                        SeleniumPrerequisites.sleep();
                        SeleniumPrerequisites.sleep();
                        SeleniumPrerequisites.sleep();
                        if ((i + 1) < parameters.length) {
                            driver.findElement(By.xpath("//td[text()='" + parameters[i] + "']")).click();
                        }
                    }
                }
                driver.findElement(By.xpath("//td[text()='" + lastParameter + "']")).click();
                SeleniumPrerequisites.sleep();
                SeleniumPrerequisites.sleep();
                SeleniumPrerequisites.sleep();
                if (driver.findElements(By.xpath("//div[contains(@style, ': block') and @class='ui-dialog ui-widget ui-widget-content ui-corner-all ']/div[@class='ui-dialog-titlebar ui-widget-header ui-corner-all ui-helper-clearfix']/a[@class='ui-dialog-titlebar-close ui-corner-all']/span[text()='Закрыть']")).size() > 0) {
                    driver.findElement(By.xpath("//div[contains(@style, ': block') and @class='ui-dialog ui-widget ui-widget-content ui-corner-all ']/div[@class='ui-dialog-titlebar ui-widget-header ui-corner-all ui-helper-clearfix']/a[@class='ui-dialog-titlebar-close ui-corner-all']/span[text()='Закрыть']")).click();
                    SeleniumPrerequisites.sleep();
                    SeleniumPrerequisites.sleep();
                    SeleniumPrerequisites.sleep();
                }
                WebElement element = driver.findElement(By.id(this.fieldIdentificationValue));
                String actualValue = element.getAttribute("value");
                assertEquals(lastParameter, actualValue);
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
