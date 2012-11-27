package lanit.junit.testcase;

public class TestCaseBasicInfo {

    private String number = "";
    private String alias = "";
    private String execute = "";

    /**
     * Main information about current test case (like service, region etc)
     * @param number - number of current test case starting from 1
     * @param alias - unique alias of current test case (defines sheet name with TestCaseActionInfo with steps for form testing)
     * @param execute - defines executable of current test case (yes/no)
     */
    public TestCaseBasicInfo(String number, String alias, String execute) {
        this.number = number;
        this.alias = alias;
        this.execute = execute;
    }

    /**
     * Method return number of current test case
     * @return number
     */
    public String getNumber() {
        return number;
    }

    /**
     * Method return alias of current test case
     * @return alias
     */
    public String getAlias() {
        return alias;
    }

    /**
     * Method return executable of current test case
     * @return executable
     */
    public String getExecute() {
        return execute;
    }
}
