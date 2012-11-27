package lanit.junit.testcase;

import lanit.junit.excel.JExcelParser;
import lanit.junit.form.fields.FieldAction;
import lanit.junit.variables.GlobalVariables;

import java.util.HashMap;

public class TestCaseActionInfo {

    private JExcelParser excelParser = null;

    /**
     * Information about current test case steps for form testing
     * @param workbookPath - name of folder with TestSuite.xls with information about form testing
     */
    public TestCaseActionInfo(String workbookPath) {
        this.excelParser = new JExcelParser(GlobalVariables.TEST_SERVICES_CATALOG + "/" + workbookPath + "/" + GlobalVariables.TEST_SUITE_NAME);
    }

    /**
     * Method defines scope of test case steps for form testing
     * @return hash map of steps for current form testing
     */
    public HashMap<Integer, FieldAction> createTestPlan() {
        return excelParser.getSheetActionContent("Тестовые шаги");
    }

    /**
     * Method return name of current test case
     * @return name of service
     */
    public String getName() {
        return excelParser.getNameOfService("Тестовый план");
    }

    /**
     * Method return path to region of current test case
     * @return path to region
     */
    public String getRegionForService() {
        return excelParser.getRegionForService("Тестовый план");
    }

    /**
     * Method return path to service into service catalog of current test case
     * @return path to service
     */
    public String getPathToService() {
        return excelParser.getPathToService("Тестовый план");
    }

    /**
     * Method return URL for service into service catalog of current test case
     * @return URL for service
     */
    public String getURLForService() {
        return excelParser.getURLForService("Адрес");
    }
}
