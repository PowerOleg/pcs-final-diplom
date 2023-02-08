import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;

import java.io.File;


public class Main {
    public static void main(String[] args) throws Exception {
        BooleanSearchEngine engine = new BooleanSearchEngine(new File("pdfs"));
//        System.out.println(engine.search("бизнес"));

        Thread searchServer = new Thread(new SearchServer(engine, new ServerLogic()));
        searchServer.start();
//        ServerLogic serverLogic = new ServerLogic();
//        System.out.println("1 " + serverLogic.convert(new PageEntry("pdf1", 1, 10)));
//        String s = serverLogic.makeResponse("бизнес", engine);
//        System.out.println("2 " + s);
        // здесь создайте сервер, который отвечал бы на нужные запросы
        // слушать он должен порт 8989
        // отвечать на запросы /{word} -> возвращённое значение метода search(word) в JSON-формате
    }
}