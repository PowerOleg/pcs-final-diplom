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
//    можно не поточное впихнуть в поток и работать с этим
//    Stream.of("a1", "a2", "a3")
//                .findFirst()
//                .ifPresent(System.out::println);  // a1

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class BooleanSearchEngine implements SearchEngine {
    private final Map<String, List<PageEntry>> searchList;
    private final File stopWordsFile;
    public BooleanSearchEngine(File pdfsDir, File file) throws IOException {
        this.stopWordsFile = file;                                   //1
        List<Map.Entry<String, Integer>> listOfEntry = new ArrayList<>();
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
        List<PageEntry> pageEntriesForWord;
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

            pageEntriesForWord = new ArrayList<>();
            pageEntry = new PageEntry(pdfName, page, entry.getValue());

            if (resultList.containsKey(entry.getKey())) {
                pageEntriesForWord = resultList.get(entry.getKey());
                pageEntriesForWord.add(pageEntry);
            } else pageEntriesForWord.add(pageEntry);

            pageEntriesForWord = pageEntriesForWord.stream().sorted().collect(Collectors.toList());
            resultList.put(entry.getKey(), pageEntriesForWord);
        }
        return resultList;
    }

    @Override
    public List<PageEntry> search(String words) {
        String[] wordArray = words.split(" ");
        List<List<PageEntry>> listOfPageEntryLists = new ArrayList<>();
        List<PageEntry> listForPageEntrySummed = new ArrayList<>();
        List<PageEntry> removeList = new ArrayList<>();

        List<String> stopList = null;                                        //1  вопрос закрытия bufferedReader потока
        try {
            stopList = StopWords.loadStopWordsList(stopWordsFile);
        } catch (FileNotFoundException e) {
            System.out.println("Выбран не верный файл");
            throw new RuntimeException(e);
        }
        for (String word : wordArray) {
            if (stopList.stream().noneMatch(n -> n.equalsIgnoreCase(word))) {
                System.out.println("ъеъ");                                                   //d
                listOfPageEntryLists.add(searchList.computeIfAbsent(word, n -> null));
            }
        }
        List<PageEntry> bigList = listOfPageEntryLists.stream().flatMap(Collection::stream).collect(Collectors.toList());
        System.out.println("1 " + bigList);                                                    //d


        for (PageEntry pageEntry1 : bigList) {
            for (PageEntry pageEntry2 : bigList) {
                if (pageEntry1 == pageEntry2) continue;
                if (pageEntry1.compare(pageEntry2)) {
                    removeList.add(pageEntry1);
                    removeList.add(pageEntry2);
                    listForPageEntrySummed.add(new PageEntry(pageEntry1.getPdfName(), pageEntry1.getPage(),
                            (pageEntry1.getCount() + pageEntry2.getCount())));
                }
            }
        }
        System.out.println("3 listForPageEntrySummed " + listForPageEntrySummed);           //d

        System.out.println("4 removeList " + removeList);                                   //d
        bigList.removeAll(removeList);
        System.out.println("1 " + bigList);                                                 //d

        List<PageEntry> resultList = new ArrayList<>(bigList);
        resultList.addAll(listForPageEntrySummed.stream().distinct().collect(Collectors.toList()));

        return resultList.stream().sorted().collect(Collectors.toList());
    }
}
