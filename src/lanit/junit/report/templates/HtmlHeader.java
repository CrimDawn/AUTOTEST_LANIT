package lanit.junit.report.templates;

public class HtmlHeader extends HtmlReport {

    /**
     * Method defines report.htm html structure
     * @return header html content
     */
    @Override
    public String getHtmlResource() {
        return "<head>\n" +
                "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n" +
                "<meta name=\"description\" content=\"Данный отчет содержит ход тестирования форм автотестом\" />\n" +
                "<meta name=\"keywords\" content=\"Отчет\" />\n" +
                "<meta name=\"created by\" content=\"CrimDawn\" />\n" +
                "<meta name=\"created when\" content=\"20/08/2012\" />\n" +
                "<title>Итоговый отчет</title>\n" +
                "<link rel=\"icon\" type=\"image/png\" href=\"picture/favicon.ico\" />\n" +
                "<link rel=\"stylesheet\" type=\"text/css\" href=\"css/report.css\" />\n" +
                "<script type=\"text/javascript\" src=\"js/report.js\"></script>\n" +
                "</head>\n";
    }
}
