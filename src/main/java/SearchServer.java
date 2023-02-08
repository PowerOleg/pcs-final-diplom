import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

//это response сервера
//[
//    {
//        "pdfName": "Этапы оценки проекта_ понятия, методы и полезные инструменты.pdf",
//            "page": 12,
//            "count": 6
//    },
//    {
//        "pdfName": "Этапы оценки проекта_ понятия, методы и полезные инструменты.pdf",
//            "page": 4,
//            "count": 3
//    },
//    {
//        "pdfName": "Этапы оценки проекта_ понятия, методы и полезные инструменты.pdf",
//            "page": 5,
//            "count": 3
//    }
//]
public class SearchServer implements Runnable {
    public static final int PORT = 8989;
    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Сервер запущен");
            try (Socket socket = serverSocket.accept();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {



            }


        } catch (IOException e) {
            System.out.println("Сервер не может запуститься");
            throw new RuntimeException(e);
        }


    }
}
