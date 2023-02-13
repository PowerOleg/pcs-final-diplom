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
        List<PageEntry> listForPageEntrySummed = new ArrayList<>();
        List<PageEntry> removeList = new ArrayList<>();

        for (String word : wordArray) {
            if (stopWords.getStopWordsList().stream().noneMatch(n -> n.equalsIgnoreCase(word))) {
                listOfPageEntryLists.add(searchList.computeIfAbsent(word, n -> null));
            }
        }
        List<PageEntry> bigList = listOfPageEntryLists.stream().flatMap(Collection::stream).collect(Collectors.toList());
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
        bigList.removeAll(removeList);
        var resultList = new ArrayList<>(bigList);
        resultList.addAll(listForPageEntrySummed.stream().distinct().collect(Collectors.toList()));
        return resultList.stream().sorted().collect(Collectors.toList());
    }

    public Map<String, List<PageEntry>> getSearchList() {
        return searchList;
    }

    public StopWords getStopWords() {
        return stopWords;
    }
}
