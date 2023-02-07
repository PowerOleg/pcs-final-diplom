import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class BooleanSearchEngine implements SearchEngine {
    private List<Map<String, Integer>> wordListPerPage = new ArrayList<>(); //можно сделать var

    private Map<String, List<PageEntry>> resultList = new HashMap<>();             //можно сделать var

    //    Сканируя каждый пдф-файл вы перебираете его страницы, для каждой страницы извлекаете из неё слова и
//    подсчитываете их количество. После подсчёта, для каждого уникального слова пдф-файла создаёте объект PageEntry
//    и сохраняете в мапу в поле.

//    Для подсчёта частоты слов
//1 создаем список уникальных слов как указано ниже
//2     List<PageEntry>> =  список уникальных слов + еще что-то???









//List<PageEntry> содержит список из номеров страниц pdf файла.
//    запрос слова бизнес выдает список где показано на какой !странице!, в каком pdf файле эта страница и
//    сколько раз встретилось данное слово
//[PageEntry{pdf=Этапы оценки проекта_ понятия, методы и полезные инструменты.pdf, page=12, count=6},
// PageEntry{pdf=Этапы оценки проекта_ понятия, методы и полезные инструменты.pdf, page=4, count=3},
// PageEntry{pdf=Этапы оценки проекта_ понятия, методы и полезные инструменты.pdf, page=5, count=3},
// PageEntry{pdf=1. DevOps_MLops.pdf, page=5, count=2},
// PageEntry{pdf=Что такое блокчейн.pdf, page=1, count=2},
// PageEntry{pdf=Что такое блокчейн.pdf, page=3, count=2}]

//это response сервера
//[
//    {
//        "pdfName": "Этапы оценки проекта_ понятия, методы и полезные инструменты.pdf",
//            "page": 12,
//            "count": 6
//    },
//    {
//        "pdfName": "Этапы оценки проекта_ понятия, методы и полезные инструменты.pdf",
//            "page": 4,
//            "count": 3
//    },
//    {
//        "pdfName": "Этапы оценки проекта_ понятия, методы и полезные инструменты.pdf",
//            "page": 5,
//            "count": 3
//    }
//]


    /* !! */   //главное - метод пробегания по pdf файлу и для каждой страницы выполняется метод добавления одной PageEntry
//тут метод преобразования данных после индексации в результирующий лист.
    //вычисляем кол-во страниц в документе и пробегаем
//    public void addPageList(Map<String, Integer> freqs, String pdfName, int page) {
//        List<Map<String, Integer>> pageEntryList1 = new ArrayList<>();
//        //        у каждого слова свой список страниц с этим словом
//        for (String word : freqs.keySet()) {
//            int count = freqs.get(word);
//            pageEntryList1.add(new PageEntry(pdfName, page, count));
//        }
//
//
//
//
//
//
//
//
//        pageList.add(pageEntryList1);
//    }


    public void addResultList(List<Map<String, Integer>> wordListPerPage) {
        String word = "";
//тут будет упорядочивание из листалистов слов-количеств для страниц в resultList

        List<PageEntry> pageEntryList2 = new ArrayList<>();


        resultList.put(word, pageEntryList2);
    }



    public BooleanSearchEngine(File pdfsDir) throws IOException {
//        Spliterator<File> spliterator = Arrays.stream(pdfsDir.listFiles()).spliterator();
//        spliterator.


        //сделать 1
//    public class XmlStream {
//        static Stream<Node> of(NodeList list) {
//            return IntStream.range(0, list.getLength()).mapToObj(list::item);
//        }
//    }

//    сделать 2
//    public static Stream<Integer> diff(Stream<Integer> stream) {
//        return pairMap(stream, (a, b) -> b - a);
//    }



for (File pdf : pdfsDir.listFiles()) {
    System.out.println("Файл " + pdf);

    var doc = new PdfDocument(new PdfReader(pdf));
    int length = doc.getNumberOfPages();

    for (int i = 0; i < length; i++) {
        PdfPage page = doc.getPage(i);
        var text = PdfTextExtractor.getTextFromPage(page);
        var words = text.split("\\P{IsAlphabetic}+");

        Map<String, Integer> freqs = new HashMap<>(); // мапа, где ключом будет слово, а значением - частота
        for (var word : words) { // перебираем слова
            if (word.isEmpty()) {
                continue;
            }
            word = word.toLowerCase();
            freqs.put(word, freqs.getOrDefault(word, 0) + 1);
            System.out.println("2 freqs " + freqs.size());
        }

        //тут метод чтобы для одной страницы положить запись
        wordListPerPage.add(freqs);
    }
    System.out.println("3 wordListPerPage " + wordListPerPage.size());





        }
    addResultList(wordListPerPage);
    }

    @Override
    public List<PageEntry> search(String word) {
        return resultList.computeIfAbsent(word, n -> null);
    }
}
