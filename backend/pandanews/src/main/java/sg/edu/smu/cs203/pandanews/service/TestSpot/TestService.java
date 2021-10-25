package sg.edu.smu.cs203.pandanews.service.testSpot;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class TestService {

    public String testMethod() {
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(false);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setPrintContentOnFailingStatusCode(false);

        try {
            HtmlPage page = webClient.getPage("https://www.moh.gov.sg/news-highlights/details/update-on-local-covid-19-situation-(19-sep-2021)");
            String xpath = "//span";

            String title = page.getTitleText();
            System.out.println("Page Title: " + title);
            List<?> anchors = page.getByXPath(xpath);
            HtmlAnchor link = (HtmlAnchor) anchors.get(0);

            webClient.getCurrentWindow().getJobManager().removeAllJobs();
            webClient.close();
            return link.getTextContent();

        } catch (IOException e) {
            System.out.println("An error occurred: " + e);
        }
        return null;
    }
}
