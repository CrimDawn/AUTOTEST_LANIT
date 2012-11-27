package lanit.junit.report.templates;

import lanit.junit.report.templates.helper.ParserAutotestLog;

public class HtmlRightColumn extends HtmlReport {

    /**
     * Method defines report.htm html structure
     * @return right table html content
     */
    @Override
    public String getHtmlResource() {
        return "<table class=\"right\">\n" +
                "<tr>\n" +
                "<td>\n" +
                "<label>Общая информация о выполняемом шаге автотеста:</label>\n" +
                "<div>\n" +
                ParserAutotestLog.htmlInfoOfSteps +
                "</div>\n" +
                "</td>\n" +
                "</tr>\n" +
                "</table>\n";
    }
}
