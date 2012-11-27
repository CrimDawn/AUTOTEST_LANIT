package lanit.junit.report;

import org.apache.log4j.Logger;
import java.util.ArrayList;

public class FinalReportParts {

    private static Logger log = Logger.getLogger(FinalReportParts.class);
    private ArrayList<String> autotestLog;
    private ArrayList<String> prerequisitePart = new ArrayList<String>();
    private ArrayList<String> mainPart = new ArrayList<String>();
    private ArrayList<String> postrequisitePart = new ArrayList<String>();

    /**
     * Definition array list of rows from autotest.log file
     * @param autotestLog - array list of log rows
     */
    public FinalReportParts(ArrayList<String> autotestLog) {
        this.autotestLog = autotestLog;
    }

    /**
     * Method return array list of prerequisites log rows
     * @return array list of log rows
     */
    public ArrayList<String> getPrerequisitePart() {
        return prerequisitePart;
    }

    /**
     * Method return array list of main part of form testing log rows
     * @return array list of log rows
     */
    public ArrayList<String> getMainPart() {
        return mainPart;
    }

    /**
     * Method return array list of post requisites log rows
     * @return array list of log rows
     */
    public ArrayList<String> getPostrequisitePart() {
        return postrequisitePart;
    }

    /**
     * Definition prerequisitePart, mainPart and postrequisitePart array lists with rows from autotest.log file
     */
    public void initializeAutotestLogParts() {
        if (autotestLog.size() > 0) {
            boolean itIsPrerequisitePart = true;
            boolean itIsPostrequisitePart = false;
            for (int i = 0; i < autotestLog.size(); i++) {
                if (itIsPrerequisitePart) {
                    this.prerequisitePart.add(autotestLog.get(i));
                    if (autotestLog.get(i).contains("Завершение метода: вход в личный кабинет")) {
                        itIsPrerequisitePart = false;
                    }
                } else if (itIsPostrequisitePart) {
                    this.postrequisitePart.add(autotestLog.get(i));
                } else {
                    if (autotestLog.get(i).contains("Инициализация общих постреквизитов тестовых планов")) {
                        this.postrequisitePart.add(autotestLog.get(i));
                        itIsPostrequisitePart = true;
                    } else {
                        this.mainPart.add(autotestLog.get(i));
                    }
                }
            }
        }
    }
}
