import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.TreeMap;

public class Loader {
    public static void main(String[] args) {
        TreeMap<String, Valute> treeMap = new TreeMap<>();
        String adress = "http://www.cbr.ru/scripts/XML_daily.asp";
        String date = null;
        try {
            Document document = Jsoup.connect(adress).get();
            Elements elements = document.select("Valute");
            //Получаем дату курса
            date = document.select("ValCurs").attr("Date");
            for (Element element : elements) {
                Valute valute = getValute(element);
                treeMap.put(valute.getNumCode(), valute);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        Valute minValute = getMinValute(treeMap);
        String result = String.format("Самая недорогая валюта на %s: цена за 1 единицу %s - %f", date, minValute.getName(), minValute.costPerOne());
        System.out.println(result);
        Valute maxValute = getMaxValute(treeMap);
        result = String.format("Самая дорогая валюта на %s: цена за 1 единицу %s - %f", date, maxValute.getName(), maxValute.costPerOne());
        System.out.println(result);
    }


    //Получаем валюту из элемента
    static Valute getValute (Element element) {
        String numCode = element.select("NumCode").text();
        CharSequence charCode = element.select("CharCode").text();
        int nominal = Integer.parseInt(element.select("Nominal").text());
        String name = element.select("Name").text();
        Float value = Float.parseFloat(element.select("Value").text().replace(',', '.'));
        return new Valute(numCode, charCode, nominal, name, value);
    }

    //Получаем валюту с минимальной стоимостью за единицу
    static Valute getMinValute (TreeMap<String, Valute> treeMap) {
        Valute minValute = null;
        for (Valute valute : treeMap.values()) {
            if (minValute == null) {
                minValute = valute;
            } else if (valute.costPerOne()<minValute.costPerOne()) {
                minValute = valute;
            }
        }
        return minValute;
    }

    //Получаем валюту с максимальной стоимостью за единицу
    static Valute getMaxValute (TreeMap<String, Valute> treeMap) {
        Valute maxValute = null;
        for (Valute valute : treeMap.values()) {
            if (maxValute == null) {
                maxValute = valute;
            } else if (valute.costPerOne()>maxValute.costPerOne()) {
                maxValute = valute;
            }
        }
        return maxValute;
    }
}
