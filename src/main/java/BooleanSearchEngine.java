import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

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

public class BooleanSearchEngine implements SearchEngine {
    private List<Map<String, PageEntry>> listOfMaps = new ArrayList<>(); //можно сделать var
    private Map<String, PageEntry> pageEntryMap; //можно сделать var
    private PageEntry pageEntry1;

    private Map<String, List<PageEntry>> searchList = new HashMap<>();             //можно сделать var


    public Map<String, List<PageEntry>> convert(List<Map<String, PageEntry>> list) {
        Map<String, List<PageEntry>> resultList = new HashMap<>();
        Set<String> words = new TreeSet<>();
        for (Map<String, PageEntry> map : list) {
            words.addAll(map.keySet());
        }

        List<PageEntry> list1;

        for (String word : words) {
            list1 = new ArrayList<>();
            for (Map<String, PageEntry> map : list) {
                for (String mapWord : map.keySet()) {
                    if (mapWord.equalsIgnoreCase(word)) {
                    list1.add(map.get(mapWord));
                    }
                }
            }
            list1 = list1.stream().sorted().collect(Collectors.toList());
            resultList.put(word, list1);
        }
        return resultList;
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

    for (int i = 1; i <= length; i++) {
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
        }
//        System.out.println("2 freqs " + freqs.size());

        //тут метод чтобы для одной страницы положить запись
        //может сделаем переработку тут?

        pageEntryMap = new HashMap<>();
        for (String word : freqs.keySet()) {
            pageEntry1 = new PageEntry(pdf.getName(), i, freqs.get(word));
            pageEntryMap.put(word, pageEntry1);
        }
listOfMaps.add(pageEntryMap);
//конец страницы
    }
    //новый pdf файл
//    System.out.println("3 wordListPerPage " + wordListPerPage.size());

        }

        //конечное действие - преобразование ... в searchList
      searchList = convert(listOfMaps);
//        System.out.println(searchList);                                                 //d
    }

    @Override
    public List<PageEntry> search(String word) {
        return searchList.computeIfAbsent(word, n -> null);
    }
}
