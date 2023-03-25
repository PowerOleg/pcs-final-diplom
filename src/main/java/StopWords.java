import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Чтобы при вызове метода search(), каждый раз не обращаться к файлу stop-ru.txt
 * был создан данный класс с полем List<String> stopWordsList, куда нужно только один раз загрузить список
 */

public class StopWords {
    private final List<String> stopWordsList;

    public StopWords(File file) throws IOException {
        this.stopWordsList = loadStopWordsList(file);
    }

    public List<String> loadStopWordsList(File file) throws IOException {
        try (BufferedReader in = new BufferedReader(new FileReader(file))) {
            return in.lines().collect(Collectors.toList());
        }
    }

    public String contains(String word) {
        if (this.stopWordsList.stream().noneMatch(n -> n.equalsIgnoreCase(word))) {
            return word;
        }
        return null;
    }

    public List<String> getStopWordsList() {
        return stopWordsList;
    }
}
