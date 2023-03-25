import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class BooleanSearchEngine implements SearchEngine {
    private final Map<String, List<PageEntry>> searchList = new HashMap<>();
    private final StopWords stopWords;

    public BooleanSearchEngine(File pdfsDir, File file) throws IOException {
        stopWords = new StopWords(file);
        for (File pdf : Objects.requireNonNull(pdfsDir.listFiles())) {
            var doc = new PdfDocument(new PdfReader(pdf));
            int length = doc.getNumberOfPages();
            PdfPage page;
            Map<String, Integer> freqs;
            for (int i = 1; i <= length; i++) {
                page = doc.getPage(i);
                var text = PdfTextExtractor.getTextFromPage(page);
                var words = text.split("\\P{IsAlphabetic}+");
                freqs = new HashMap<>();
                for (var word : words) {
                    if (word.isEmpty()) {
                        continue;
                    }
                    word = word.toLowerCase();
                    freqs.put(word, freqs.getOrDefault(word, 0) + 1);
                }
                for (String word : freqs.keySet()) {
                    searchList.put(word, getListOfPageEntries(word, pdf.getName(), i, freqs.get(word)));
                }
            }
        }
    }

    public List<PageEntry> getListOfPageEntries(String word, String pdfName, int pageNumber, int count) {
        List<PageEntry> pageEntriesForWord = new ArrayList<>();
        PageEntry pageEntry = new PageEntry(pdfName, pageNumber, count);
        if (searchList.containsKey(word)) {
            pageEntriesForWord = searchList.get(word);
            pageEntriesForWord.add(pageEntry);
        } else {
            pageEntriesForWord.add(pageEntry);
        }
        return pageEntriesForWord;
    }

    @Override
    public List<PageEntry> search(String words) {
        var wordArray = words.split(" ");
        List<List<PageEntry>> listOfPageEntryLists = new ArrayList<>();
        Arrays.stream(wordArray).forEach(word ->
                listOfPageEntryLists.add(searchList.computeIfAbsent(stopWords.contains(word), m -> Collections.emptyList())));

        List<PageEntry> bigList = listOfPageEntryLists.stream().flatMap(Collection::stream).collect(Collectors.toList());
        Map<ClassForGrouping, Integer> dictionary = new HashMap<>();
        ClassForGrouping classForGrouping;
        for (PageEntry pageEntry : bigList) {
            classForGrouping = new ClassForGrouping(pageEntry.getPdfName(), pageEntry.getPage());
            if (dictionary.containsKey(classForGrouping)) {
                var count = dictionary.get(classForGrouping);
                var newCount = count + pageEntry.getCount();
                dictionary.put(classForGrouping, newCount);
            } else {
                dictionary.put(classForGrouping, pageEntry.getCount());
            }
        }
        List<PageEntry> resultList = new ArrayList<>();
        dictionary.forEach((pdfPage, count) -> resultList.add(new PageEntry(pdfPage.getPdfName(), pdfPage.getPage(), count)));
        Collections.sort(resultList);
        return resultList;
    }

    public Map<String, List<PageEntry>> getSearchList() {
        return searchList;
    }

    public StopWords getStopWords() {
        return stopWords;
    }
}
