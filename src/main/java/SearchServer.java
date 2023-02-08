import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SearchServer implements Runnable {
    public static final int PORT = 8989;
    private final BooleanSearchEngine booleanSearchEngine;
    private final ServerLogic serverLogic;

    public SearchServer(BooleanSearchEngine booleanSearchEngine, ServerLogic serverLogic) {
        this.booleanSearchEngine = booleanSearchEngine;
        this.serverLogic = serverLogic;
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Сервер запущен");
            while (true) {
                try (Socket socket = serverSocket.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                     BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {
                    String clientRequest = in.readLine();
                    System.out.println("Запрос клиента: " + clientRequest);
                    String response = serverLogic.makeResponse(clientRequest, booleanSearchEngine);
                    out.write(response);
                    out.newLine();
                    out.flush();
                    System.out.println("Ответ сервера: " + response);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }


            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
