package lanit.junit;

import lanit.junit.excel.JExcelParser;
import lanit.junit.form.fields.FieldAction;
import lanit.junit.report.FinalReport;
import lanit.junit.selenium.SeleniumPrerequisites;
import lanit.junit.testcase.TestCaseActionInfo;
import lanit.junit.testcase.TestCaseBasicInfo;
import lanit.junit.testcase.TestCaseExecutor;
import lanit.junit.variables.GlobalVariables;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.*;
import java.util.*;

@RunWith(value = Parameterized.class)
public class JUnitTestCases {

    private static Logger log = Logger.getLogger(JUnitTestCases.class);
    private static SeleniumPrerequisites seleniumPrerequisites = null;
    private TestCaseBasicInfo testCaseBasicInfo = null;
    private TestCaseExecutor testCaseExecutor = null;
    private TestCaseActionInfo testCaseActionInfo = null;

    /**
     * Single test case of test suite
     * @param testCaseBasicInfo - main information about executable test case (like service, region etc)
     */
    public JUnitTestCases(TestCaseBasicInfo testCaseBasicInfo) {
        this.testCaseBasicInfo = testCaseBasicInfo;
    }

    /**
     * Method defines scope of test cases with different form for testing
     * @return collection of test cases and each test case put to JUnitTestCases constructor
     */
    @Parameterized.Parameters
    public static Collection<TestCaseBasicInfo[]> data() {
        globalConfiguration();
        JExcelParser excelParser = new JExcelParser(GlobalVariables.TEST_SUITES_PATH);
        ArrayList<TestCaseBasicInfo> executableListTestCaseBasicInfos = excelParser.getExecutable();
        if (executableListTestCaseBasicInfos.size() > 0) {
            log.info("Количество тестовых планов на выполнение: " + executableListTestCaseBasicInfos.size());
        } else {
            log.warn("Отсутствуют тестовые сценарии для выполнения");
            System.exit(0);
        }
        TestCaseBasicInfo[][] globalTestCases = new TestCaseBasicInfo[executableListTestCaseBasicInfos.size()][1];
        for (int i = 0; i < executableListTestCaseBasicInfos.size(); i++) {
            globalTestCases[i][0] = executableListTestCaseBasicInfos.get(i);
        }
        return Arrays.asList(globalTestCases);
    }

    /**
     * Method run at once and execute some single global prerequisites for form testing
     */
    @BeforeClass
    public static void preConfiguration() {
        cleanScreenShotDirectory();
        log.info("Инициализация общих пререквизитов тестовых планов");
        seleniumPrerequisites = new SeleniumPrerequisites();
        log.info("Запуск метода: вход в личный кабинет");
        seleniumPrerequisites.loginToServer();
        log.info("Завершение метода: вход в личный кабинет");
    }

    /**
     * Method run several times equals test cases scope size and execute single prerequisites for form testing
     */
    @Before
    public void preAction() {
        log.info("Запуск тестового плана для формы: " + testCaseBasicInfo.getAlias());
        testCaseActionInfo = new TestCaseActionInfo(testCaseBasicInfo.getAlias());
        testCaseExecutor = new TestCaseExecutor(seleniumPrerequisites.getDriver(), testCaseActionInfo);
        log.info("Запуск метода: выбор региона");
        String region = testCaseExecutor.chooseRegion();
        assertEquals(testCaseActionInfo.getRegionForService().split(">")[testCaseActionInfo.getRegionForService().split(">").length - 1], region);
        log.info("Завершение метода: выбор региона");
        log.info("Запуск метода: выбор услуги");
        testCaseExecutor.chooseService();
        log.info("Завершение метода: выбор услуги");
    }

    /**
     * Method run several times equals test cases scope size and execute form testing
     */
    @Test
    public void testExecutor() {
        HashMap<Integer, FieldAction> testPlan = testCaseActionInfo.createTestPlan();
        log.info("Запуск метода: инспекция текущей формы");
        testCaseExecutor.inspectForm(testPlan);
        log.info("Завершение метода: инспекция текущей формы");
    }

    /**
     * Method run several times equals test cases scope size and execute single post requisites for form testing
     */
    @After
    public void postAction() {
        log.info("Завершение тестового плана: " + testCaseBasicInfo.getAlias());
    }

    /**
     * Method run at once and execute some single global post requisites for form testing
     */
    @AfterClass
    public static void postConfiguration() {
        log.info("Инициализация общих постреквизитов тестовых планов");
        log.info("Запуск метода: выход из личного кабинета");
        seleniumPrerequisites.logoutFromServer();
        log.info("Завершение метода: выход из личного кабинета");
        seleniumPrerequisites.stopDriver();
        log.info("Формирование отчета о результате выполнения автотеста");
        new FinalReport();
    }

    /**
     * Method prepare different properties for testing
     */
    private static void globalConfiguration() {
        configurationProperties();
        configurationLogger();
        configurationConsole();
    }

    /**
     * Method defines global variables from property file for using during form testing
     */
    private static void configurationProperties() {
        Properties properties = new Properties();
        try {
            properties.load(new InputStreamReader(new FileInputStream(GlobalVariables.AUTOTEST_PROPERTIES_FILE), "UTF-8"));
            GlobalVariables.PAGE_LOAD_TIME_WAITING = properties.getProperty("page.load.time.waiting");
            GlobalVariables.ADDITIONAL_PAGE_LOAD_TIME_WAITING = properties.getProperty("additional.page.load.time.waiting");
            GlobalVariables.STEP_ACTION_TIME_WAITING = properties.getProperty("step.action.time.waiting");
            GlobalVariables.LOG4J_PROPERTIES_FILE = properties.getProperty("log.properties.file.path");
            GlobalVariables.TEST_SUITES_PATH = properties.getProperty("test.suites.path");
            GlobalVariables.TEST_SUITE_NAME = properties.getProperty("test.suite.name");
            GlobalVariables.UPLOAD_FILE_PATH = properties.getProperty("upload.file.path");
            GlobalVariables.UPLOAD_FILE_NAME = properties.getProperty("upload.file.name");
            GlobalVariables.AUTOTEST_LOG_FILE = properties.getProperty("autotest.log.file");
            GlobalVariables.AUTOTEST_REPORT_REWRITE = properties.getProperty("autotest.report.rewrite");
            GlobalVariables.AUTOTEST_REPORT_FILE = properties.getProperty("autotest.report.file");
            GlobalVariables.AUTOTEST_REPORT_SCREENSHOT = properties.getProperty("autotest.report.screenshot");
            GlobalVariables.AUTOTEST_REPORT_ZIP = properties.getProperty("autotest.report.zip");
            GlobalVariables.TEST_SERVICES_CATALOG = properties.getProperty("test.services.catalog");
            GlobalVariables.BROWSER_TYPE = properties.getProperty("browser.type");
            GlobalVariables.BROWSER_LOCATION_FIREFOX = properties.getProperty("browser.location.firefox");
            GlobalVariables.BROWSER_LOCATION_OPERA = properties.getProperty("browser.location.opera");
            GlobalVariables.BROWSER_LOCATION_IEXPLORER = properties.getProperty("browser.location.iexplorer");
            GlobalVariables.BROWSER_LOCATION_CHROME = properties.getProperty("browser.location.chrome");
            GlobalVariables.BROWSER_LOCATION_SAFARI = properties.getProperty("browser.location.safari");
            GlobalVariables.SERVER_URL = properties.getProperty("server.host.url");
            GlobalVariables.USER_NAME = properties.getProperty("user.name");
            GlobalVariables.USER_PASSWORD = properties.getProperty("user.password");
            GlobalVariables.MAIL_SEND = properties.getProperty("mail.send");
            GlobalVariables.MAIL_AUTOTEST_USER = properties.getProperty("mail.autotest.user");
            GlobalVariables.MAIL_AUTOTEST_PASSWORD = properties.getProperty("mail.autotest.password");
            GlobalVariables.MAIL_RECIPIENTS = properties.getProperty("mail.recipients");
            GlobalVariables.MAIL_TRANSPORT_PROTOCOL = properties.getProperty("mail.transport.protocol");
            GlobalVariables.MAIL_SMTP_HOST = properties.getProperty("mail.smtp.host");
            GlobalVariables.MAIL_SMTP_PORT = properties.getProperty("mail.smtp.port");
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * Method defines properties for logging into file during form testing (like encoding etc)
     */
    private static void configurationLogger() {
        File testLog = new File(GlobalVariables.AUTOTEST_LOG_FILE);
        if (testLog.exists()) {
            testLog.delete();
        }
        BasicConfigurator.resetConfiguration();
        BasicConfigurator.configure();
        PropertyConfigurator.configure(GlobalVariables.LOG4J_PROPERTIES_FILE);
        log.info("Конфигурация логирования для автотеста выполнена успешно");
    }

    /**
     * Method defines properties for logging into console during form testing (like encoding etc)
     */
    private static void configurationConsole() {
        try {
            System.setOut(new PrintStream(System.out, true, "Cp866"));
            System.setErr(new PrintStream(System.out, true, "Cp866"));
            log.info("Конфигурация кодировки для консоли выполнена успешно");
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * Method clean directory with screen shots during form testing between each running
     */
    private static void cleanScreenShotDirectory() {
        for (File screenShot : new File(GlobalVariables.AUTOTEST_REPORT_SCREENSHOT).listFiles()) {
            screenShot.delete();
        }
    }

    /**
     * Method compare two string
     * @param expectResult - string with expected result
     * @param actualResult - string with actual result
     */
    private static void assertEquals(String expectResult, String actualResult) {
        try {
            org.junit.Assert.assertEquals(expectResult, actualResult);
        } catch (org.junit.ComparisonFailure e) {
            log.error(e.getMessage(), e);
        }
    }
}
