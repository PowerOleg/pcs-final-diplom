import java.io.File;
import java.io.IOException;

public class Main {
    public static final String STOP_WORDS_FILE_NAME = "stop-ru.txt";
    public static final String DIRECTORY_OF_PDFS = "pdfs";

    public static void main(String[] args) {
        BooleanSearchEngine engine = null;
        try {
            engine = new BooleanSearchEngine(new File(DIRECTORY_OF_PDFS), new File(STOP_WORDS_FILE_NAME));
        } catch (IOException e) {
            System.out.println("Выбран не верный файл со стоп словами");
            throw new RuntimeException(e);
        }
        MainServer mainServer = new MainServer(engine, new ServerLogic());
        mainServer.start();
    }
}