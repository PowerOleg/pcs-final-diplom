import java.io.File;

public class Main {
    public static void main(String[] args) throws Exception {
        long startTime = System.nanoTime();
        BooleanSearchEngine engine = new BooleanSearchEngine(new File("pdfs"));
        System.out.println(engine.search("бизнес"));
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println(duration);

 // здесь создайте сервер отвечать на запросы /{word} -> возвращённое значение метода search(word) в JSON-формате
    }
}