//попробовать сделать в конструкторе //        Spliterator<File> spliterator = Arrays.stream(pdfsDir.listFiles()).spliterator();
////        spliterator.
//
//
////сделать 1
////    public class XmlStream {
////        static Stream<Node> of(NodeList list) {
////            return IntStream.range(0, list.getLength()).mapToObj(list::item);
////        }
////    }
//
////    сделать 2
////    public static Stream<Integer> diff(Stream<Integer> stream) {
////        return pairMap(stream, (a, b) -> b - a);
////    }

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BooleanSearchEngine implements SearchEngine {
    private List<Map.Entry<String, Integer>> listOfEntry = new ArrayList<>(); //можно сделать var
    private Map<String, List<PageEntry>> searchList;             //можно сделать var

    public BooleanSearchEngine(File pdfsDir) throws IOException {
        for (File pdf : pdfsDir.listFiles()) {
            var doc = new PdfDocument(new PdfReader(pdf));
            int length = doc.getNumberOfPages();
            PdfPage page;
            Map<String, Integer> freqs;
            for (int i = 1; i <= length; i++) {
                page = doc.getPage(i);
                var text = PdfTextExtractor.getTextFromPage(page);
                var words = text.split("\\P{IsAlphabetic}+");

                listOfEntry.add(Map.entry(("?" + pdf.getName()), i));
                freqs = new HashMap<>();
                for (var word : words) {
                    if (word.isEmpty()) {
                        continue;
                    }
                    word = word.toLowerCase();
                    freqs.put(word, freqs.getOrDefault(word, 0) + 1);
                }
                listOfEntry.addAll(freqs.entrySet());
//конец страницы
            }
//конец pdf файла
        }
//конечное действие
        searchList = convert(listOfEntry);
    }
    
    public Map<String, List<PageEntry>> convert(List<Map.Entry<String, Integer>> listOfEntry) {
        Map<String, List<PageEntry>> resultList = new HashMap<>();
        List<PageEntry> pageEntries;
        PageEntry pageEntry;
        String pdfName = "";
        int page = 0;

        for (Map.Entry<String, Integer> entry : listOfEntry) {
            if (entry.getKey().startsWith("?")) {
                pdfName = entry.getKey();
                pdfName = pdfName.substring(1);
                page = entry.getValue();
                continue;
            }

            pageEntries = new ArrayList<>();
            pageEntry = new PageEntry(pdfName, page, entry.getValue());

            if (resultList.containsKey(entry.getKey())) {
                pageEntries = resultList.get(entry.getKey());
                pageEntries.add(pageEntry);
            } else pageEntries.add(pageEntry);

            pageEntries = pageEntries.stream().sorted().collect(Collectors.toList());
            resultList.put(entry.getKey(), pageEntries);
        }
        return resultList;
    }

    @Override
    public List<PageEntry> search(String word) {
        return searchList.computeIfAbsent(word, n -> null);
    }
}
