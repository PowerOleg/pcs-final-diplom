import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;

import java.io.File;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        BooleanSearchEngine engine = new BooleanSearchEngine(new File("pdfs"));
        System.out.println(engine.search("бизнес"));

        // здесь создайте сервер, который отвечал бы на нужные запросы
        // слушать он должен порт 8989
        // отвечать на запросы /{word} -> возвращённое значение метода search(word) в JSON-формате

//        Map<String, List<PageEntry>> resultList = new HashMap<>();
//        List<Map.Entry<String, Integer>> listOfEntry = new ArrayList<>();
//        listOfEntry.add(Map.entry("качества", 2));
//        listOfEntry.add(Map.entry("разработке", 10));
//        listOfEntry.add(Map.entry("специалистов", 1));
//        listOfEntry.add(Map.entry("разработке", 1));
//        PageEntry pageEntry;
//        for (Map.Entry<String, Integer> entry : listOfEntry) {
////            if (entry.getKey().startsWith("?")) {
////                pdfName = entry.getKey();
////                page = entry.getValue();
////                continue;
//////                listOfEntry.remove(entry);                                              //очень долгая операция
////                //надо не забыть удалить строчки с именами файлов и страницами
////            }
//
//
//            pageEntry = new PageEntry("pdfName1", 0, entry.getValue());
//
//            resultList.put(entry.getKey(), new ArrayList<PageEntry>());
////            resultList.merge(entry.getKey(), new ArrayList<PageEntry>(), (a,b) -> b.add(pageEntry));
//        }
    }
}