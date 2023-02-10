import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.stream.Collectors;
public class StopWords {
    public static List<String> loadStopWordsList(File file) throws FileNotFoundException {
        BufferedReader in = new BufferedReader(new FileReader(file));
        return in.lines().collect(Collectors.toList());
    }
}
