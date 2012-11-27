package lanit.junit.testcase;

import com.thoughtworks.selenium.SeleniumException;
import lanit.junit.form.fields.FieldAction;
import lanit.junit.selenium.SeleniumPrerequisites;
import lanit.junit.variables.GlobalVariables;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TestCaseExecutor {

    private static Logger log = Logger.getLogger(TestCaseExecutor.class);
    private WebDriver driver = null;
    private TestCaseActionInfo testCaseActionInfo = null;

    /**
     * Instance for test case execute with all JUnit lifecycle (Before, Test, After)
     * @param driver - web driver instance for browser surfing
     * @param testCaseActionInfo - main information about current test case
     */
    public TestCaseExecutor(WebDriver driver, TestCaseActionInfo testCaseActionInfo) {
        if (driver != null) {
            this.driver = driver;
            this.testCaseActionInfo = testCaseActionInfo;
        } else {
            log.error("Ошибка при создании исследователя формы");
        }
    }

    /**
     * Method parsing of region path and navigate to final destination
     * @return - name of selected region
     */
    public String chooseRegion() {
        String chooseRegion = "";
        try {
            driver.manage().timeouts().implicitlyWait(Long.parseLong(GlobalVariables.ADDITIONAL_PAGE_LOAD_TIME_WAITING), TimeUnit.MILLISECONDS);
            SeleniumPrerequisites.openPersonalCabinet();
            driver.findElement(By.id("region-selected")).click();
            SeleniumPrerequisites.screenShot();
            String[] region = testCaseActionInfo.getRegionForService().split(">");
            for (int i = 0; i < region.length; i++) {
                driver.findElement(By.xpath("//td[@class='region_item_text' and text()='" + region[i] + "']")).click();
                log.info("Выбран парамерт региона: " + region[i]);
                SeleniumPrerequisites.screenShot();
                SeleniumPrerequisites.sleep();
            }
            if (driver.findElements(By.xpath("//ul[@class='crumbs region_crumbs block']")).size() > 0) {
                WebElement ul = driver.findElements(By.xpath("//ul[@class='crumbs region_crumbs block']")).get(0);
                if (ul.getAttribute("style").contains(": block")) {
                    WebElement select = driver.findElements(By.xpath("//ul[@class='crumbs region_crumbs block']/li[@class='last']/button[@class='select']")).get(0);
                    select.click();
                    SeleniumPrerequisites.sleep();
                }
            }
            chooseRegion = driver.findElement(By.id("region-selected")).getText();
        } catch (SeleniumException e) {
            log.error(e.getMessage(), e);
            SeleniumPrerequisites.screenShot();
        } catch (UnhandledAlertException e) {
            log.error(e.getMessage(), e);
            SeleniumPrerequisites.screenShot();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            SeleniumPrerequisites.screenShot();
        }
        return chooseRegion;
    }

    /**
     * Method parsing of service path and save href to final destination
     */
    public void chooseService() {
        if (!GlobalVariables.CURRENT_SERVICE_HREF.equals("")) {
            GlobalVariables.CURRENT_SERVICE_HREF = "";
        }
        String serviceHref = "";
        try {
            driver.findElement(By.xpath("//a[text()='Электронные услуги']")).click();
            SeleniumPrerequisites.screenShot();
            SeleniumPrerequisites.sleep();
            String pathToService = testCaseActionInfo.getPathToService();
            String sameService = "";
            if (pathToService.indexOf("&") != -1) {
                sameService = pathToService.substring(pathToService.indexOf("&"), pathToService.length());
                pathToService = pathToService.substring(0, pathToService.indexOf("&"));
                sameService = sameService.substring(1, sameService.length());
            }
            String[] service = pathToService.split(">");
            for (int i = 0; i < service.length - 1; i++) {
                driver.findElement(By.xpath("//td[contains(., '" + service[i] + "')]")).click();
                log.info("Выбор департамента: " + service[i]);
                SeleniumPrerequisites.screenShot();
                SeleniumPrerequisites.sleep();
            }
            if (sameService.equals("")) {
//                String href = driver.findElement(By.xpath("//a[contains(., '" + service[service.length - 1] + "')]")).getAttribute("href");
//                log.info("Выбор услуги из электронного каталога: " + service[service.length - 1]);
//                href = href.substring(href.lastIndexOf("/") + 1, href.lastIndexOf("."));
//                String serviceTargetExtId = href.substring(0, href.indexOf("_"));
//                String s = href.substring(href.indexOf("_") + 1, href.length());
//                if (s.equals(testCaseActionInfo.getName())) {
//                    log.info("Корректный идентификатор формы: " + s);
//                } else {
//                    log.warn("Некорректный идентификатор формы: " + s);
//                }
//                serviceHref = GlobalVariables.SERVER_URL + "services/s" + testCaseActionInfo.getName() + "/init?serviceTargetExtId=" + serviceTargetExtId;
//                int delimitNumber = serviceHref.indexOf(".ru/") + 4;
//                String standNumber = testCaseActionInfo.getName().substring(1, 3);
//                if (standNumber.startsWith("0")) {
//                    standNumber = "1" + standNumber;
//                }
//                serviceHref = serviceHref.substring(0, delimitNumber) + standNumber + "/" + serviceHref.substring(delimitNumber, serviceHref.length());
//                if (GlobalVariables.SERVER_URL.equals("http://www.gosuslugi.ru/")) {
//                    standNumber = testCaseActionInfo.getName().substring(1, 3);
//                    serviceHref = serviceHref.replace("www", standNumber);
//                }
                driver.findElement(By.xpath("//a[contains(., '" + service[service.length - 1] + "')]")).click();
                log.info("Выбор услуги из электронного каталога: " + service[service.length - 1]);
                SeleniumPrerequisites.screenShot();
                SeleniumPrerequisites.sleep();
                checkFormID();
                serviceHref = saveFormURL();
            } else {
                driver.findElement(By.xpath("//a[contains(., '" + service[service.length - 1] + "')]")).click();
                log.info("Выбор услуги из электронного каталога: " + service[service.length - 1]);
                SeleniumPrerequisites.screenShot();
                SeleniumPrerequisites.sleep();
                driver.findElement(By.xpath("//ul[@class='related-services']/li/div/div/div/a[contains(@href, '" + testCaseActionInfo.getName() + "')]/span/span/span[contains(., '" + sameService + "')]")).click();
                log.info("Выбор смежной услуги из электронного каталога: " + sameService);
                SeleniumPrerequisites.screenShot();
                SeleniumPrerequisites.sleep();
                checkFormID();
                serviceHref = saveFormURL();
            }
            GlobalVariables.CURRENT_SERVICE_HREF = serviceHref;
        } catch (NoSuchElementException e) {
            log.warn(e.getMessage(), e);
            SeleniumPrerequisites.screenShot();
        } catch (SeleniumException e) {
            log.error(e.getMessage(), e);
            SeleniumPrerequisites.screenShot();
        } catch (UnhandledAlertException e) {
            log.error(e.getMessage(), e);
            SeleniumPrerequisites.screenShot();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            SeleniumPrerequisites.screenShot();
        }
        if (GlobalVariables.CURRENT_SERVICE_HREF.equals("")) {
            driver.navigate().to(GlobalVariables.SERVER_URL + testCaseActionInfo.getURLForService());
            GlobalVariables.CURRENT_SERVICE_HREF = saveFormURL();
        }
    }

    /**
     * Method checks form id from current URL and compare with form id from TestSuite.xls
     */
    private void checkFormID() {
        String href = ((JavascriptExecutor) driver).executeScript("return window.location.href;").toString();
        href = href.substring(href.lastIndexOf("/") + 1, href.lastIndexOf("."));
        String s = href.substring(href.indexOf("_") + 1, href.length());
        if (s.equals(testCaseActionInfo.getName())) {
            log.info("Корректный идентификатор формы: " + s);
        } else {
            log.warn("Некорректный идентификатор формы: " + s);
        }
    }

    /**
     * Method saves current URL to global variable
     * @return current URL of testing form
     */
    private String saveFormURL() {
        SeleniumPrerequisites.sleep();
        SeleniumPrerequisites.sleep();
        SeleniumPrerequisites.sleep();
        String serviceHref = "";
        try {
            WebElement getServiceButton = driver.findElement(By.xpath("//div[contains(@class, 'center-left ext-buttons')]/a[text()='Получить услугу']"));
            serviceHref = getServiceButton.getAttribute("href");
            SeleniumPrerequisites.sleep();

        } catch (SeleniumException e) {
            log.error(e.getMessage(), e);
            SeleniumPrerequisites.screenShot();
        } catch (UnhandledAlertException e) {
            log.error(e.getMessage(), e);
            SeleniumPrerequisites.screenShot();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            SeleniumPrerequisites.screenShot();
        }
        return serviceHref;
    }

    /**
     * Method execute main surfing to each form step and defines correct form or not
     * @param testPlan - hash map of steps for form testing
     */
    public void inspectForm(HashMap<Integer, FieldAction> testPlan) {
        try {
            driver.manage().timeouts().implicitlyWait(Long.parseLong(GlobalVariables.STEP_ACTION_TIME_WAITING), TimeUnit.MILLISECONDS);
            boolean newStep = true;
            int stepNumber = 1;
            for (int i = 1; i <= testPlan.size(); i++) {
                if (testPlan.get(i).isSkipStepUp()) {
                    stepNumber++;
                } else if (testPlan.get(i).isSkipStepDown()) {
                    stepNumber--;
                } else {
                    if (newStep && !testPlan.get(i).isNewScenario()) {
                        SeleniumPrerequisites.sleep();
                        WebElement element = driver.findElement(By.xpath("//a[@class='stepMenuItemLink active']"));
                        if (element.getText().startsWith(stepNumber + ".")) {
                            log.info("Шаг №" + stepNumber + " на исследуемой форме открыт");
                            SeleniumPrerequisites.screenShot();
                            testStepColSpan();
                            newStep = false;
                        } else {
                            log.error("Шаг №" + stepNumber + " на исследуемой форме не был открыт");
                            SeleniumPrerequisites.screenShot();
                            break;
                        }
                    }
                    if (testPlan.get(i).isNextButton()) {
                        newStep = true;
                        stepNumber++;
                    } else if (testPlan.get(i).isPrevButton()) {
                        newStep = true;
                        stepNumber--;
                    } else if (testPlan.get(i).isApplyButton()) {
                        newStep = true;
                        stepNumber = 1;
                    }
                }
                if (!testPlan.get(i).isNewScenario()) {
                    log.info("Тестирование поля на текущем шаге");
                }
                testPlan.get(i).run(driver);
                if (testPlan.get(i).isApplyButton()) {
                    getStatus();
                } else if (testPlan.get(i).isNewScenario() && testPlan.get(i).fieldAutoValue.equals("Нет")) {
                    for (i++; i <= testPlan.size(); i++) {
                        if (i < testPlan.size()) {
                            if (testPlan.get(i + 1).isNewScenario()) {
                                break;
                            }
                        }
                    }
                }
            }
        } catch (NoSuchElementException e) {
            log.error(e.getMessage(), e);
            SeleniumPrerequisites.screenShot();
        } catch (NullPointerException e) {
            log.error(e.getMessage(), e);
            SeleniumPrerequisites.screenShot();
        } catch (UnhandledAlertException e) {
            log.error(e.getMessage(), e);
            SeleniumPrerequisites.screenShot();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            SeleniumPrerequisites.screenShot();
        }
    }

    /**
     * Method defines correct or not table structure on current step of form for testing
     */
    private void testStepColSpan() {
        log.info("Проверка структуры колонок шага");
        WebElement mainTable = driver.findElement(By.xpath("//table[@class='form-body']"));
        List<WebElement> trs = mainTable.findElements(By.tagName("tr"));
        int bestColumnCount = 0;
        for (int i = 0; i < trs.size(); i++) {
            if (i == 0) {
                List<WebElement> tds = trs.get(i).findElements(By.tagName("td"));
                int column = getColSpanFromTD(tds);
                log.info("Строка " + (i + 1) + " содержит " + column + " колонок");
                bestColumnCount = column;
            } else {
                List<WebElement> tds = trs.get(i).findElements(By.tagName("td"));
                int column = getColSpanFromTD(tds);
                if (column == bestColumnCount) {
                    log.info("Строка " + (i + 1) + " содержит " + column + " колонок");
                } else {
                    log.warn("Строка " + (i + 1) + " содержит " + column + " колонок");
                }
            }
        }
    }

    /**
     * Method search column span attribute in each td from array list
     * @param tds - array list of td from table row
     * @return finally column count in table row
     */
    private int getColSpanFromTD(List<WebElement> tds) {
        int columnCount = 0;
        for (int i = 0; i < tds.size(); i++) {
            if (tds.get(i).getAttribute("colspan") == null) {
                columnCount++;
            } else {
                columnCount += Integer.parseInt(tds.get(i).getAttribute("colspan"));
            }
        }
        return columnCount;
    }

    /**
     * Method defines final status of service providing after all steps testing
     */
    private void getStatus() {
        try {
            driver.manage().timeouts().implicitlyWait(Long.parseLong(GlobalVariables.ADDITIONAL_PAGE_LOAD_TIME_WAITING), TimeUnit.MILLISECONDS);
            if (driver.findElements(By.xpath("//div[@class='lastStepText']")).size() > 0) {
                String lastMessage = driver.findElement(By.xpath("//div[@class='lastStepText']")).getText();
                log.info(lastMessage);
                String numberService = lastMessage.replaceAll("[^\\d+]", "");
                driver.findElement(By.xpath("//span[contains(., 'Информация по данному заявлению')]")).click();
                if (driver.findElements(By.xpath("//dd[contains(., '" + numberService + "')]")).size() > 0) {
                    String resultStatus = driver.findElement(By.xpath("//dt[contains(., 'Статус')]/../dd")).getText();
                    log.info("Заявление с номером " + numberService + " подано: статус - " + resultStatus);
                } else {
                    log.error("Заявление с номером " + numberService + " не найдено в списке поданных заявлений на получение услуги: статус - Отсутствует");
                }
                SeleniumPrerequisites.screenShot();
            } else {
                log.error("Номер заявления не был найден. Заявка на получение услуги не отправлена");
                log.error("Заявление с пустым номером не найдено в списке поданных заявлений на получение услуги");
                SeleniumPrerequisites.screenShot();
            }
            SeleniumPrerequisites.sleep();
            driver.manage().timeouts().implicitlyWait(Long.parseLong(GlobalVariables.STEP_ACTION_TIME_WAITING), TimeUnit.MILLISECONDS);
        } catch (NoSuchElementException e) {
            log.error(e.getMessage(), e);
            SeleniumPrerequisites.screenShot();
        } catch (NullPointerException e) {
            log.error(e.getMessage(), e);
            SeleniumPrerequisites.screenShot();
        } catch (UnhandledAlertException e) {
            log.error(e.getMessage(), e);
            SeleniumPrerequisites.screenShot();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            SeleniumPrerequisites.screenShot();
        }
    }
}
