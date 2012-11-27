package lanit.junit.selenium;

import com.opera.core.systems.OperaDriver;
import com.opera.core.systems.runner.OperaRunnerException;
import com.thoughtworks.selenium.SeleniumException;
import lanit.junit.variables.GlobalVariables;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class SeleniumPrerequisites {

    private static Logger log = Logger.getLogger(SeleniumPrerequisites.class);
    private static WebDriver driver = null;

    /**
     * Creation instance of web driver for browser surfing
     */
    public SeleniumPrerequisites() {
        log.setLevel(Level.INFO);
        try {
            if (GlobalVariables.BROWSER_TYPE.equals("firefox")) {
                FirefoxBinary binary = new FirefoxBinary(new File(GlobalVariables.BROWSER_LOCATION_FIREFOX));
                FirefoxProfile profile = new FirefoxProfile();
                firefoxEnableSSLProtocol(profile);
                profile.setPreference("general.useragent.override", "TestNVG");
                driver = new FirefoxDriver(binary, profile);
                log.info("WebDriver успешно активирован");
                firefoxWindowMaximize();
            } else if (GlobalVariables.BROWSER_TYPE.equals("opera")) {
                DesiredCapabilities capabilities = new DesiredCapabilities().opera();
                capabilities.setJavascriptEnabled(true);
                capabilities.setCapability("opera.binary", GlobalVariables.BROWSER_LOCATION_OPERA);
                driver = new OperaDriver(capabilities);
                log.info("WebDriver успешно активирован");
                operaWindowMaximize();
            } else if (GlobalVariables.BROWSER_TYPE.equals("iexplorer")) {
                DesiredCapabilities capabilities = new DesiredCapabilities().internetExplorer();
                capabilities.setJavascriptEnabled(true);
                driver = new InternetExplorerDriver(capabilities);
                log.info("WebDriver успешно активирован");
                iexplorerWindowMaximize();
            } else if (GlobalVariables.BROWSER_TYPE.equals("chrome")) {
                System.setProperty("webdriver.chrome.driver", GlobalVariables.BROWSER_LOCATION_CHROME);
                driver = new ChromeDriver();
                log.info("WebDriver успешно активирован");
            } else if (GlobalVariables.BROWSER_TYPE.equals("safari")) {
                driver = new SafariDriver();
                log.info("WebDriver успешно активирован");
            } else {
                log.error("Указан неверный тип браузера");
            }
            driver.manage().timeouts().pageLoadTimeout(Long.parseLong(GlobalVariables.PAGE_LOAD_TIME_WAITING), TimeUnit.MILLISECONDS);
        } catch (NullPointerException e) {
            log.error(e.getMessage(), e);
        } catch (WebDriverException e) {
            log.error(e.getMessage(), e);
        } catch (OperaRunnerException e) {
            log.error(e.getMessage(), e);
        } catch (NoSuchMethodError e) {
            log.error(e.getMessage(), e);
        } catch (IllegalStateException e) {
            log.error(e.getMessage(), e);
        } catch (UnsupportedOperationException e) {
            log.error(e.getMessage(), e);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * Method maximize browser window for Mozilla Firefox
     */
    private void firefoxWindowMaximize() {
        driver.manage().window().maximize();
    }

    /**
     * Method maximize browser window for Internet Explorer
     */
    private void iexplorerWindowMaximize() {
        driver.manage().window().maximize();
    }

    /**
     * Method maximize browser window for Opera
     */
    private void operaWindowMaximize() {
        try {
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_ALT);
            robot.keyPress(KeyEvent.VK_SPACE);
            robot.keyRelease(KeyEvent.VK_ALT);
            robot.keyRelease(KeyEvent.VK_SPACE);
            Thread.sleep(1000);
            robot.keyPress(KeyEvent.VK_DOWN);
            robot.keyRelease(KeyEvent.VK_DOWN);
            Thread.sleep(100);
            robot.keyPress(KeyEvent.VK_DOWN);
            robot.keyRelease(KeyEvent.VK_DOWN);
            Thread.sleep(100);
            robot.keyPress(KeyEvent.VK_DOWN);
            robot.keyRelease(KeyEvent.VK_DOWN);
            Thread.sleep(100);
            robot.keyPress(KeyEvent.VK_DOWN);
            robot.keyRelease(KeyEvent.VK_DOWN);
            Thread.sleep(100);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
            Thread.sleep(100);
        } catch (AWTException e) {
            log.error(e.getMessage(), e);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * Method enables SSL protocol for Mozilla Firefox browser
     * @param profile - profile of Mozilla Firefox browser
     */
    private void firefoxEnableSSLProtocol(FirefoxProfile profile) {
        profile.setAcceptUntrustedCertificates(true);
        profile.setAssumeUntrustedCertificateIssuer(false);
    }

    /**
     * Method enables SSL protocol for Internet Explorer browser
     */
    private void iexplorerEnableSSLProtocol() {
        try {
            SeleniumPrerequisites.sleep();
            while (driver.getTitle().contains("Certificate Error: Navigation Blocked")) {
                driver.navigate().to("javascript:document.getElementById('overridelink').click()");
            }
        } catch (SeleniumException e) {

        } catch (Exception e) {

        }
    }

    /**
     * Method returns instance of web driver for browser surfing
     * @return web driver instance
     */
    public WebDriver getDriver() {
        return driver;
    }

    /**
     * Method stopping of web driver instance
     */
    public void stopDriver() {
        if (driver != null) {
            driver.quit();
        }
        log.info("WebDriver успешно деактивирован");
    }

    /**
     * Method waits some second
     */
    public static void sleep() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * Method creates screen shot of current web page and save it to directory
     */
    public static void screenShot() {
        try {
            File screenShot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenShot, new File(GlobalVariables.AUTOTEST_REPORT_SCREENSHOT + "/" + GlobalVariables.NUMBER_OF_SCREENSHOT + ".jpg"));
            log.info("ScreenShot: " + GlobalVariables.NUMBER_OF_SCREENSHOT + ".jpg");
            GlobalVariables.NUMBER_OF_SCREENSHOT++;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } catch (NullPointerException e) {
            log.error(e.getMessage(), e);
        } catch (UnhandledAlertException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * Method open personal cabinet and applies non assign email for user if it necessary
     */
    public static void openPersonalCabinet() {
        driver.navigate().to(GlobalVariables.SERVER_URL + "pgu/personcab#_favorites");
        if (driver.findElements(By.xpath("//div[contains(@class, 'ui-dialog ui-widget ui-widget-content ui-corner-all') and contains(@style, ': block')]/div/ul[contains(@class, 'dialog-buttons')]/a[contains(@class, 'button-custom') and text()='Отменить']")).size() > 0) {
            driver.findElement(By.xpath("//div[contains(@class, 'ui-dialog ui-widget ui-widget-content ui-corner-all') and contains(@style, ': block')]/div/ul[contains(@class, 'dialog-buttons')]/a[contains(@class, 'button-custom') and text()='Отменить']")).click();
            SeleniumPrerequisites.sleep();
        }
    }

    /**
     * Method login to portal of services by user
     */
    public void loginToServer() {
        try {
            driver.manage().timeouts().implicitlyWait(Long.parseLong(GlobalVariables.ADDITIONAL_PAGE_LOAD_TIME_WAITING), TimeUnit.MILLISECONDS);
            driver.navigate().to(GlobalVariables.SERVER_URL);
            log.info("Открытие портала по адресу: " + GlobalVariables.SERVER_URL);
            String personalCabLogin = driver.findElement(By.xpath("//a[@class='sign-in']")).getAttribute("href");
            driver.navigate().to(personalCabLogin);
            if (GlobalVariables.BROWSER_TYPE.equals("iexplorer")) {
                iexplorerEnableSSLProtocol();
            }
            driver.findElement(By.id("username")).sendKeys(GlobalVariables.USER_NAME);
            log.info("Задано имя пользователя: " + GlobalVariables.USER_NAME);
            driver.findElement(By.id("password")).sendKeys(GlobalVariables.USER_PASSWORD);
            log.info("Задан пароль пользователя: " + GlobalVariables.USER_PASSWORD);
            try {
                Thread.sleep(15000);
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }
            if (driver.findElements(By.xpath("//button[contains(@class, 'button-blue-big') and contains(@class, 'in_button') and contains(@style, 'display: block')]")).size() > 0) {
                driver.findElement(By.xpath("//button[contains(@class, 'button-blue-big') and contains(@class, 'in_button') and contains(@style, 'display: block')]")).click();
            } else {
                driver.findElement(By.xpath("//button[contains(@class, 'button-blue-big') and contains(@class, 'in_button') and not(contains(@style, 'display: none'))]")).click();
            }
            if (GlobalVariables.BROWSER_TYPE.equals("iexplorer")) {
                iexplorerEnableSSLProtocol();
            }
        } catch (SeleniumException e) {
            log.error(e.getMessage(), e);
            SeleniumPrerequisites.screenShot();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            SeleniumPrerequisites.screenShot();
        }
    }

    /**
     * Method logout from portal of services by user
     */
    public void logoutFromServer() {
        try {
            driver.manage().timeouts().implicitlyWait(Long.parseLong(GlobalVariables.ADDITIONAL_PAGE_LOAD_TIME_WAITING), TimeUnit.MILLISECONDS);
            openPersonalCabinet();
            String personalCabLogout = driver.findElement(By.xpath("//a[@title='Выход']")).getAttribute("href");
            if (GlobalVariables.BROWSER_TYPE.equals("iexplorer")) {
                driver.navigate().to(GlobalVariables.SERVER_URL);
            } else {
                driver.navigate().to(personalCabLogout);
            }
            if (driver.findElements(By.xpath("//a[@class='sign-in']")).size() > 0) {
                log.info("Выход с портала ГосУслуг");
            } else {
                log.warn("Выход с портала ГосУслуг не выполнен");
            }
        } catch (SeleniumException e) {
            log.error(e.getMessage(), e);
            SeleniumPrerequisites.screenShot();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            SeleniumPrerequisites.screenShot();
        }
    }
}
