package entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlImage;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import entity.Advertisement;

import java.io.StringWriter;
import java.util.List;

public class SiteReader {
    private final String url;


    private String jsonString;

    /**
     * Конструктор класса с инициализацией и ЮРЛ и обьекта в виде строки Json
     */
    public SiteReader(String url) {
        this.url = url;
        WebClient client = new WebClient();
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);
        try {
            HtmlPage page = client.getPage(url);
            List<HtmlElement> items = page.getByXPath("//div[@class='offer-wrapper']");
            if (items.isEmpty()) {
                System.out.println("No items found!");
            } else {
                ObjectMapper mapper = new ObjectMapper();
                StringWriter writer = new StringWriter();

                for (HtmlElement item : items) {
                    // System.out.println(item.asXml());

                    HtmlImage element = item.getFirstByXPath(".//table/tbody/tr[1]/td[1]/a/img");
                    HtmlAnchor anchor = item.getFirstByXPath(".//table/tbody/tr[1]/td[2]/div/h3/a");
                    HtmlPage advPage=client.getPage(anchor.getHrefAttribute());
                    HtmlElement advItem=advPage.getHtmlElementById("textContent");

                    if (element != null) {
                        Advertisement adv = new Advertisement();
                        adv.setTitle(anchor.getTextContent().trim());
                        adv.setUrl(anchor.getHrefAttribute());
                        adv.setImageSrc(element.getSrcAttribute());
                        adv.setDescription(advItem.getTextContent().trim());
                        mapper.writeValue(writer, adv);


                    } else {
                        Advertisement adv = new Advertisement();
                        adv.setTitle(anchor.getTextContent().trim());
                        adv.setUrl(anchor.getHrefAttribute());
                        adv.setImageSrc("No Img");
                        adv.setDescription(advItem.getTextContent().trim());
                        mapper.writeValue(writer, adv);

                    }


                }
                jsonString=writer.toString();


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * This method gets Json string
     */
    public String getJsonString() {
        return jsonString;
    }


}