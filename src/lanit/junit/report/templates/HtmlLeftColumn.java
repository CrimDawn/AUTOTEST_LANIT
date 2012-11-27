package lanit.junit.report.templates;

import lanit.junit.report.templates.helper.ParserAutotestLog;

public class HtmlLeftColumn extends HtmlReport {

    /**
     * Method defines report.htm html structure
     * @return left table html content
     */
    @Override
    public String getHtmlResource() {
        return "<table class=\"left\">\n" +
                "<tr>\n" +
                "<td>\n" +
                "<label>Общий прогресс выполнения автотеста:</label>\n" +
                "<div>\n" +
                ParserAutotestLog.htmlResultInfo +
                "</div>\n" +
                "</td>\n" +
                "</tr>\n" +
                "<tr style=\"height: 100%;\">\n" +
                "<td style=\"height: 100%;\">\n" +
                "<label>Шаги выполнения автотеста:</label>\n" +
                "<div>\n" +
                ParserAutotestLog.htmlListOfSteps +
                "</div>\n" +
                "</td>\n" +
                "</tr>\n" +
                "</table>\n";
    }
}
