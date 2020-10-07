package com.onekus.onekus;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class ParseNumber extends AsyncTask<Void, Void, Boolean> {

    String url;
    int basket;
    int type;
    int count;
    int limit;

    ParseNumber(String grade, String code) {
        switch(grade) {
            case "전체" :
                url = "https://kupis.konkuk.ac.kr/sugang/acd/cour/aply/CourInwonInqTime.jsp?ltYy=2020&ltShtm=B01012&sbjtId=" + code;
                type = 0;
                break;
            case "1학년" :
                url = "https://kupis.konkuk.ac.kr/sugang/acd/cour/aply/CourBasketInwonInq.jsp?ltYy=2020&ltShtm=B01012&promShyr=1&fg=B&sbjtId=" + code;
                type = 1;
                break;
            case "2학년" :
                url = "https://kupis.konkuk.ac.kr/sugang/acd/cour/aply/CourBasketInwonInq.jsp?ltYy=2020&ltShtm=B01012&promShyr=2&fg=B&sbjtId=" + code;
                type = 1;
                break;
            case "3학년" :
                url = "https://kupis.konkuk.ac.kr/sugang/acd/cour/aply/CourBasketInwonInq.jsp?ltYy=2020&ltShtm=B01012&promShyr=3&fg=B&sbjtId=" + code;
                type = 1;
                break;
            case "4학년" :
                url = "https://kupis.konkuk.ac.kr/sugang/acd/cour/aply/CourBasketInwonInq.jsp?ltYy=2020&ltShtm=B01012&promShyr=4&fg=B&sbjtId=" + code;
                type = 1;
                break;
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(Void... values) {
        try {
            Document document = Jsoup.connect(url).get();
            if (type == 0) {
                Elements elements = document.body().getElementsByClass("table_bg_white");
                count = Integer.parseInt(elements.get(1).text());
                limit = Integer.parseInt(elements.get(2).text());
            }
            else if (type == 1) {
                Elements elements = document.select("td");
                basket = Integer.parseInt(elements.get(0).text());
                count = Integer.parseInt(elements.get(1).text().split("/")[0].trim());
                limit = Integer.parseInt(elements.get(1).text().split("/")[1].trim());
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
    }

}