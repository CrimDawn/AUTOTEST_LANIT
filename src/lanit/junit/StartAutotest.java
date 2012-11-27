package lanit.junit;

import org.junit.runner.JUnitCore;

public class StartAutotest {

    public static String SINGLE_TEST_CASE = "";

    /**
     * Method for run JUnit test suite
     * @param args
     */
    public static void main(String[] args) {
        if (args.length == 1) {
            SINGLE_TEST_CASE = args[0];
        }
        JUnitCore.runClasses(JUnitTestCases.class);
    }
}
