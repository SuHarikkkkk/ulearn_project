package parser;
import model.Grant;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class CsvParser {
    public static List<Grant> parse(String path) {
        List<Grant> grants = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            //считываем первую строчку(заголовки)
            String header = br.readLine();
            if (header == null) {
                return grants;
            }
            String line;
            //читаем файл построчно
            while ((line = br.readLine()) != null) {
                String[] cols = line.split("\t");
                //если колонок меньше - пропускаем строку
                if (cols.length < 6) continue;
                //извлекаем поля по индексу
                String company = cols[0].trim();
                String street = cols[1].trim();
                double amount = parseMoney(cols[2]);
                Integer year = parseInt(cols[3]);
                String type = cols[4].trim();
                Integer jobs = parseInt(cols[5]);
                Grant g = new Grant(company, street, amount, year, type, jobs);
                grants.add(g);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return grants;
    }

    //создаем парсер, чтобы убирать знаки доллара и разделители тысяч у денег
    private static double parseMoney(String s) {
        if (s == null) return 0;
        s = s.replace("$", "").replace(",", "").trim();
        try {
            return Double.parseDouble(s);
        } catch (Exception e) {
            return 0;
        }
    }

    //создаём парсер для целых чисел, в нашем случае это года или количество рабочих мест
    private static Integer parseInt(String s) {
        if (s == null || s.isBlank()) return null;
        s = s.replace(",", "").trim();
        try {
            return Integer.parseInt(s);
        } catch (Exception e) {
            return null;
        }
    }
}
