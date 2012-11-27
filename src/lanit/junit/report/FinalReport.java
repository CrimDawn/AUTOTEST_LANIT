package lanit.junit.report;

import lanit.junit.archivator.ZipArchivator;
import lanit.junit.mail.EMailSender;
import lanit.junit.report.templates.HtmlDeclaration;
import lanit.junit.report.templates.helper.ParserAutotestLog;
import lanit.junit.variables.GlobalVariables;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

public class FinalReport {

    private static Logger log = Logger.getLogger(FinalReport.class);
    private PrintWriter writer = null;
    private ArrayList<String> autotestLog = new ArrayList<String>();
    private String reportPath = "";
    int tabCount = 0;

    /**
     * Creation result report about executable of form testing
     */
    public FinalReport() {
        try {
            createReportStructure();
            writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(new File(GlobalVariables.AUTOTEST_REPORT_FILE), true), "UTF-8"));
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(GlobalVariables.AUTOTEST_LOG_FILE)), "UTF-8"));
            String logRow = "";
            while ((logRow = reader.readLine()) != null) {
                autotestLog.add(logRow);
            }
            reader.close();
            FinalReportParts finalReportParts = new FinalReportParts(autotestLog);
            finalReportParts.initializeAutotestLogParts();
            writeLogToReportFile(finalReportParts);
            ZipArchivator.directoryToZip(new File(reportPath), new File(GlobalVariables.AUTOTEST_REPORT_ZIP));
            if (GlobalVariables.MAIL_SEND.equals("true")) {
                log.info("Формирование письма с отчетом о результате выполнения автотеста");
                EMailSender.send();
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * Creation all necessary folders and files for report generation
     */
    private void createReportStructure() {
        try {
            //create reports/report_ root folder
            reportPath = "reports/report_";
            if (!GlobalVariables.AUTOTEST_REPORT_REWRITE.equals("true")) {
                reportPath +=  + new Date().getTime();
                new File(reportPath).mkdir();
            }
            //create reports/report_/report.htm file
            GlobalVariables.AUTOTEST_REPORT_FILE = reportPath + "/" + GlobalVariables.AUTOTEST_REPORT_FILE;
            if (new File(GlobalVariables.AUTOTEST_REPORT_FILE).exists()) {
                new File(GlobalVariables.AUTOTEST_REPORT_FILE).delete();
            }
            new File(GlobalVariables.AUTOTEST_REPORT_FILE).createNewFile();
            //create reports/report_/screenshots folder
            if (!new File(reportPath + "/screenshots").exists()) {
                new File(reportPath + "/screenshots").mkdir();
            } else {
                for (File screenShot : new File(reportPath + "/screenshots").listFiles()) {
                    screenShot.delete();
                }
            }
            //copy reports/report_/js, reports/report_/css, reports/report_/picture folders content
            FileUtils.copyDirectory(new File("reports/common"), new File(reportPath));
            //copy reports/report_/screenshots folder content
            FileUtils.copyDirectory(new File(GlobalVariables.AUTOTEST_REPORT_SCREENSHOT), new File(reportPath + "/screenshots"));
            //copy autotest.log file
            FileUtils.copyFile(new File(GlobalVariables.AUTOTEST_LOG_FILE), new File(reportPath + "/" + GlobalVariables.AUTOTEST_LOG_FILE));
            GlobalVariables.AUTOTEST_LOG_FILE = reportPath + "/" + GlobalVariables.AUTOTEST_LOG_FILE;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * Creation report.htm file content
     * @param finalReportParts - array list of rows from autotest.log file
     */
    private void writeLogToReportFile(FinalReportParts finalReportParts) {
        ParserAutotestLog.defineLeftTopPartData(finalReportParts.getPrerequisitePart(), finalReportParts.getMainPart());
        ParserAutotestLog.defineLeftBottomPartData(finalReportParts.getPrerequisitePart(), finalReportParts.getMainPart(), finalReportParts.getPostrequisitePart());
        ParserAutotestLog.defineRightPartData();
        String htmlReport = new HtmlDeclaration().getHtmlResource();
        writer.write(htmlReport);
        writer.flush();
        writer.close();
        formatReport();
    }

    /**
     * Formatting report.htm file with tab spacing
     */
    private void formatReport() {
        try {
            BufferedReader formatReader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(GlobalVariables.AUTOTEST_REPORT_FILE)), "UTF-8"));
            ArrayList<String> reportRows = new ArrayList<String>();
            String reportRow = "";
            while ((reportRow = formatReader.readLine()) != null) {
                reportRows.add(reportRow);
            }
            formatReader.close();
            writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(new File(GlobalVariables.AUTOTEST_REPORT_FILE), false), "UTF-8"));
            String tab = "";
            for (int i = 0; i < reportRows.size(); i++) {
                tab = tabCounting(reportRows.get(i));
                writer.write(tab);
                writer.write(reportRows.get(i));
                writer.write("\n");
                writer.flush();
            }
            writer.close();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * Definition necessary tabulating for current row
     * @param reportRow - current row from report.htm file
     * @return current row with tabulating
     */
    private String tabCounting(String reportRow) {
        Pattern pattern1 = Pattern.compile("^<!.*?>"); //<!...> -  doctype tag detected
        Pattern pattern2 = Pattern.compile("^<[^/].*?/>"); //<.../> - single tag detected
        Pattern pattern3 = Pattern.compile("^<[^/].*?>.*</.*>$"); //<...>...<.../> - open and close tag detected
        Pattern pattern4 = Pattern.compile("^<[^!/].*?[^/]>"); //<...> - open tag detected
        Pattern pattern5 = Pattern.compile("^</.*?>"); //</...> - close tag detected
        String tab = "";
        if (pattern1.matcher(reportRow).find()) {
            tab = tabCountDetected(tabCount);
        } else if (pattern2.matcher(reportRow).find()) {
            tab = tabCountDetected(tabCount);
        } else if (pattern3.matcher(reportRow).find()) {
            tab = tabCountDetected(tabCount);
        } else if (pattern4.matcher(reportRow).find()) {
            tab = tabCountDetected(tabCount);
            tabCount++;
        } else if (pattern5.matcher(reportRow).find()) {
            tabCount--;
            tab = tabCountDetected(tabCount);
        } else {
            tab = tabCountDetected(tabCount);
        }
        return tab;
    }

    /**
     * Definition count of necessary tabulating
     * @param tabCount - current count of tabulating
     * @return tabulating for current row
     */
    private String tabCountDetected(int tabCount) {
        String tab = "";
        for (int i = 0; i < tabCount; i++) {
            tab += "\t";
        }
        return tab;
    }
}
