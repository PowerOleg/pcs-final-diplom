import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class BooleanSearchEngine implements SearchEngine {
    private List<Map.Entry<String, Integer>> listOfEntry = new ArrayList<>(); //можно сделать var
    private Map<String, List<PageEntry>> searchList;             //можно сделать var


//    Требуется всё также возвращать в качестве результата поиска список из PageEntry,
//    только count в нём должен теперь содержать суммарное количество раз, которое встретилось любое из слов запроса.

    public Map<String, List<PageEntry>> convert(List<Map.Entry<String, Integer>> listOfEntry /*List<Map<String, PageEntry>> list*/) {
        Map<String, List<PageEntry>> resultList = new HashMap<>();

        List<PageEntry> list1;
        String pdfName = "";
        int page = 0;
        PageEntry pageEntry1;

        for (Map.Entry<String, Integer> entry : listOfEntry) {
            if (entry.getKey().startsWith("?")) {
                pdfName = entry.getKey();
                page = entry.getValue();
                continue;
//                listOfEntry.remove(entry);                                              //очень долгая операция
                //надо не забыть удалить строчки с именами файлов и страницами
            }

            list1 = new ArrayList<>();
            pageEntry1 = new PageEntry(pdfName, page, entry.getValue());


if (resultList.containsKey(entry.getKey())) {
list1 = resultList.get(entry.getKey());
list1.add(pageEntry1);
} else list1.add(pageEntry1);



list1 = list1.stream().sorted().collect(Collectors.toList());
            resultList.put(entry.getKey(), list1);
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
//            System.out.println("Файл " + pdf);

            var doc = new PdfDocument(new PdfReader(pdf));
            int length = doc.getNumberOfPages();

            for (int i = 1; i <= length; i++) {
                PdfPage page = doc.getPage(i);
                var text = PdfTextExtractor.getTextFromPage(page);
                var words = text.split("\\P{IsAlphabetic}+");
                listOfEntry.add(Map.entry(("?" + pdf.getName()), i));




        Map<String, Integer> freqs = new HashMap<>(); // мапа, где ключом будет слово, а значением - частота
        for (var word : words) { // перебираем слова
            if (word.isEmpty()) {
                continue;
            }
            word = word.toLowerCase();
            freqs.put(word, freqs.getOrDefault(word, 0) + 1);
        }
        listOfEntry.addAll(freqs.entrySet());

//конец страницы
    }
    //новый pdf файл


        }

        //конечное действие
      searchList = convert(listOfEntry);

    }

    @Override
    public List<PageEntry> search(String word) {
        return searchList.computeIfAbsent(word, n -> null);
    }
}
