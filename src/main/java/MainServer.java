import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class MainServer {
    private final BooleanSearchEngine booleanSearchEngine;
    private final ServerLogic serverLogic;

    public MainServer(BooleanSearchEngine booleanSearchEngine, ServerLogic serverLogic) {
        this.booleanSearchEngine = booleanSearchEngine;
        this.serverLogic = serverLogic;
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(Main.PORT)) {
            System.out.println("Сервер запущен");
            while (true) {
                try (Socket socket = serverSocket.accept()) {
                    serverService(socket);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void serverService(Socket socket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {
            String clientRequest = in.readLine().toLowerCase();
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
}

