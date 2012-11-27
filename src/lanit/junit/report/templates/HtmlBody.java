package lanit.junit.report.templates;

public class HtmlBody extends HtmlReport {

    /**
     * Method defines report.htm html structure
     * @return body html content
     */
    @Override
    public String getHtmlResource() {
        return "<body onload=\"setColor()\">\n" +
                "<div id=\"main\">\n" +
                "<table class=\"base\">\n" +
                "<tr>\n" +
                "<th>\n" +
                "<i>Итоговый отчет тестирования форм</i>\n" +
                "</th>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td>\n" +
                "<div id=\"content\">\n" +
                "<table class=\"content\">\n" +
                "<tr>\n" +
                "<td style=\"width: 500px; vertical-align: top;\">\n" +
                new HtmlLeftColumn().getHtmlResource() +
                "</td>\n" +
                "<td style=\"vertical-align: top;\">\n" +
                new HtmlRightColumn().getHtmlResource() +
                "</td>\n" +
                "</tr>\n" +
                "</table>\n" +
                "</div>\n" +
                "</td>\n" +
                "</tr>\n" +
                "</table>\n" +
                "<div id=\"globalScreenShot\">\n" +
                "<table>\n" +
                "<tr>\n" +
                "<td>\n" +
                "<img />\n" +
                "</td>\n" +
                "<td id=\"closeGlobalScreenShot\" onclick=\"hideScreen();\">\n" +
                "<a>X</a>\n" +
                "</td>\n" +
                "</tr>\n" +
                "</table>\n" +
                "</div>\n" +
                "</div>\n" +
                "</body>\n";
    }
}
