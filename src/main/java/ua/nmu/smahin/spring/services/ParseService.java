package ua.nmu.smahin.spring.services;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.nmu.smahin.spring.models.ItemModel;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class ParseService {
    @Autowired
    RepositoryService repository;

    @Autowired
    ExchangeService exchangeService;

    private static final String epicenterURL = "https://epicentrk.ua/ua/search/";

    public List<ItemModel> parseByQuery(String query) throws IOException {
        List<ItemModel> result = new ArrayList<>();
        try {
            Document document = Jsoup.connect(epicenterURL + "?q=" + query).get();

            Elements list = document.getElementsByClass("_7d58lF _8rEC+O _swlyeK _ucSvqj _qBnvc4 _9j7oAi _F9O1n7 _4Y9fku");
            for (Element item : list) {
                if (!item.hasAttr("itemprop")) continue;

                String name = item.getElementsByClass("_zG-baD").text();
                Double price = Double.valueOf(item.select("div[data-product-price-main] data").attr("value"));
                String imgURL = item.getElementsByClass("_k9FXGl").select("img").attr("src");

                ItemModel itemModel = new ItemModel();
                itemModel.setName(name);
                itemModel.setPriceUAH(price);
                itemModel.setPriceUSD(Double.valueOf(String.format(Locale.US, "%.2f", price * exchangeService.getRate())));
                itemModel.setImgURL(imgURL);
                repository.add(itemModel);
                result.add(itemModel);
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return result;
    }

}
