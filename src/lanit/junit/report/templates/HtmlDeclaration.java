package lanit.junit.report.templates;

public class HtmlDeclaration extends HtmlReport {

    /**
     * Method defines report.htm html structure
     * @return main html content
     */
    @Override
    public String getHtmlResource() {
        return "<!DOCTYPE HTML>\n" +
                "<html>\n" +
                new HtmlHeader().getHtmlResource() +
                new HtmlBody().getHtmlResource() +
                "</html>\n";
    }
}
