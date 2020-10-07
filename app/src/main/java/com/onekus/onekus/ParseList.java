package com.onekus.onekus;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class ParseList extends AsyncTask<String, Void, Boolean> {

    String base    = "https://kupis.konkuk.ac.kr/sugang/acd/cour/time/SeoulTimetableInfo.jsp?ltYy=2020&ltShtm=B01012";

    String div_pil = "&pobtDiv=B04044";         // 전필
    String div_sun = "&pobtDiv=B04045";         // 전선
    String div_gi  = "&pobtDiv=B04043";         // 지교
    String div_sim = "&pobtDiv=B04054";         // 심교(핵교)
    String div_ki  = "&pobtDiv=B0404P";         // 기교
    String div_il  = "&pobtDiv=B04046";         // 일선

    String fld_lan = "&cultCorsFld=B52001";     // 외국어
    String fld_wrt = "&cultCorsFld=B52002";     // 글쓰기
    String fld_job = "&cultCorsFld=B52003";     // 취창업
    String fld_sw  = "&cultCorsFld=B52004";     // SW
    String fld_per  = "&cultCorsFld=B52005";    // 인성
    String fld_kor = "&cultCorsFld=B52006";     // 한국어
    String fld_exp  = "&cultCorsFld=B52007";    // 사고와표현
    String fld_flan  = "&cultCorsFld=B52008";   // 외국인글쓰기

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        try {
            String url = base;
            String fld = "";
            if(strings[0].equals("CODE")) {
                url += "&sbjtId=" + strings[1];
                Document document = Jsoup.connect(url).get();
                Elements elements = document.body().getElementsByClass("table_bg_white");
                if(!elements.get(1).text().contains("내역이")) {
                    for (int i = 1; i < elements.size(); i++) {
                        Element element = elements.get(i);
                        Fragment_3.name = element.child(4).text().substring(0, element.child(4).text().indexOf("("))
                                + "(" + element.child(3).text() + ")";
                    }
                }
                else {
                    Fragment_3.name = "error";
                }
            }
            else if(strings[0].equals("FAVORITES")) {
                url += "&sbjtId=" + strings[1];
                Document document = Jsoup.connect(url).get();
                Elements elements = document.body().getElementsByClass("table_bg_white");
                if(!elements.get(1).text().contains("내역이")) {
                    for (int i = 1; i < elements.size(); i++) {
                        Element element = elements.get(i);
                        MainActivity.favorites.add(
                                new Lecture(element.child(3).text(),
                                        element.child(4).text().substring(0, element.child(4).text().indexOf("(")),
                                        element.child(9).text(),
                                        element.child(8).text()));
                        Fragment_4.isValidCode = true;
                    }
                }
                else {
                    Fragment_4.isValidCode = false;
                }
            }
            // 심교
            else if (strings[0].contains("심교")) {
                url += div_sim;
                if (strings[0].contains("학문소양및인성함양")) {
                    fld = "학문소양및인성함양";
                }
                else if (strings[0].contains("글로벌인재양성")) {
                    fld = "글로벌인재양성";
                }
                else if (strings[0].contains("사고력증진")) {
                    fld = "사고력증진";
                }
                Document document = Jsoup.connect(url).get();
                Elements elements = document.body().getElementsByClass("table_bg_white");
                if(!elements.get(1).text().contains("내역이")) {
                    for (int i = 1; i < elements.size(); i++) {
                        Element element = elements.get(i);
                        if (element.child(14).text().contains(fld))
                            MainActivity.lectures.add(
                                    new Lecture(element.child(3).text(),
                                            element.child(4).text().substring(0, element.child(4).text().indexOf("(")),
                                            element.child(9).text(),
                                            element.child(8).text()));
                    }
                }
            }
            // 핵교
            else if (strings[0].contains("핵교")) {
                url += div_sim;
                if (strings[0].contains("인문")) {
                    fld = "인문영역";
                }
                else if (strings[0].contains("사회과학")) {
                    fld = "사회과학영역";
                }
                else if (strings[0].contains("자연과학")) {
                    fld = "자연과학영역(자연과학기술융합영역)";
                }
                else if (strings[0].contains("기술융합")) {
                    fld = "기술융합영역(자연과학기술융합영역)";
                }
                else if (strings[0].contains("문화예술")) {
                    fld = "문화예술영역";
                }
                Document document = Jsoup.connect(url).get();
                Elements elements = document.body().getElementsByClass("table_bg_white");
                if(!elements.get(1).text().contains("내역이")) {
                    for (int i = 1; i < elements.size(); i++) {
                        Element element = elements.get(i);
                        if (element.child(16).text().contains(fld)) {
                            MainActivity.lectures.add(
                                    new Lecture(element.child(3).text(),
                                            element.child(4).text().substring(0, element.child(4).text().indexOf("(")),
                                            element.child(9).text(),
                                            element.child(8).text()));
                        }
                    }
                }
            }
            // 그 외
            else {
                // 기교
                if (strings[0].contains("기교")) {
                    url += div_ki;
                    if (strings[0].contains("외국어")) {
                        url += fld_lan;
                    }
                    else if (strings[0].contains("글쓰기")) {
                        url += fld_wrt;
                    }
                    else if (strings[0].contains("취창업")) {
                        url += fld_job;
                    }
                    else if (strings[0].contains("인성")) {
                        url += fld_per;
                    }
                    else if (strings[0].contains("한국어")) {
                        url += fld_kor;
                    }
                    else if (strings[0].contains("사고와표현")) {
                        url += fld_exp;
                    }
                    else if (strings[0].contains("외국인글쓰기")) {
                        url += fld_flan;
                    }
                }
                // 일선
                else if (strings[0].contains("일선")) {
                    url += div_il;
                }
                // 전필
                else if (strings[0].contains("전필")) {
                    url += div_pil + "&openSust=" + strings[1];
                }
                // 전선
                else if (strings[0].contains("전선")) {
                    url += div_sun + "&openSust=" + strings[1];
                }
                // 지교
                else if (strings[0].contains("지교")) {
                    url += div_gi + "&openSust=" + strings[1];
                }
                Document document = Jsoup.connect(url).get();
                Elements elements = document.body().getElementsByClass("table_bg_white");
                if(!elements.get(1).text().contains("내역이")) {
                    for (int i = 1; i < elements.size(); i++) {
                        Element element = elements.get(i);
                        MainActivity.lectures.add(
                                new Lecture(element.child(3).text(),
                                            element.child(4).text().substring(0, element.child(4).text().indexOf("(")),
                                            element.child(9).text(),
                                            element.child(8).text()));
                    }
                }
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