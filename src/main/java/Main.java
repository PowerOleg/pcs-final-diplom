import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {
        BooleanSearchEngine engine = new BooleanSearchEngine(new File("pdfs"));
//        System.out.println(engine.search("бизнес"));

//еще не использовал->   Полистайте методы doc чтобы найти способ узнать количество * страниц в документе.





/* !! */   //главное - метод пробегания по pdf файлу и для каждой страницы выполняется метод добавления одной PageEntry
//тут метод преобразования данных после индексации в результирующий лист.
        //вычисляем кол-во страниц в документе и пробегаем
        engine.pageEntryList.add(new PageEntry("1.DevOps_MLops", 1, 10)); //откуда взять аргументы?
//        System.out.println("1 " + engine.pageEntryList);





/* !! */ //    для каждой страницы одного pdf своя мапа. у меня есть то что мне нужно: имя pdf, номер страницы, count
        //это то что будет храниться после индексации
        File pdf = new File(".\\pdfs\\1. DevOps_MLops.pdf");
        var doc = new PdfDocument(new PdfReader(pdf));

/* ! */        int page1 = 1;
        PdfPage page = doc.getPage(page1);
        var text = PdfTextExtractor.getTextFromPage(page);
        var words = text.split("\\P{IsAlphabetic}+");

    Map<String, Integer> freqs = new HashMap<>(); // мапа, где ключом будет слово, а значением - частота
for (var word : words) { // перебираем слова
        if (word.isEmpty()) {
            continue;
        }
        word = word.toLowerCase();
        freqs.put(word, freqs.getOrDefault(word, 0) + 1);
    }
        System.out.println("2 " + freqs.size());
        System.out.println("3 " + freqs);














        // здесь создайте сервер, который отвечал бы на нужные запросы
        // слушать он должен порт 8989
        // отвечать на запросы /{word} -> возвращённое значение метода search(word) в JSON-формате
    }
}