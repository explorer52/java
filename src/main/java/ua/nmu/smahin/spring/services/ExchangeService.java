package ua.nmu.smahin.spring.services;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@Service
public class ExchangeService {
    protected Double rate;
    private static final String privatbankURL = "https://api.privatbank.ua/p24api/pubinfo?exchange&coursid=5";

    public ExchangeService() {rate= 0.0;}

    public Double getRate() throws IOException {
        if (rate!=0.0) return rate;

        URL url = new URL(privatbankURL);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setDoOutput(true);
        con.setRequestMethod("GET");
        con.connect();

        BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
        StringBuilder jsonContent = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonContent.append(line);
        }
        reader.close();

        Gson gson = new Gson();
        List<CurrencyRate> rateJson = gson.fromJson(jsonContent.toString(), new TypeToken<List<CurrencyRate>>(){}.getType());

        rate = 1/rateJson.get(1).getBuy();

        return rate;

    }
}

class CurrencyRate {
    private String ccy;

    @SerializedName("base_ccy")
    private String baseCcy;

    private double buy;
    private double sale;

    public double getBuy() {
        return buy;
    }

    public void setBuy(double buy) {
        this.buy = buy;
    }

    public double getSale() {
        return sale;
    }

    public void setSale(double sale) {
        this.sale = sale;
    }
}
