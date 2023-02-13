import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MainServer {
    public static final int PORT = 8989;
    private final BooleanSearchEngine booleanSearchEngine;
    private final ServerLogic serverLogic;

    public MainServer(BooleanSearchEngine booleanSearchEngine, ServerLogic serverLogic) {
        this.booleanSearchEngine = booleanSearchEngine;
        this.serverLogic = serverLogic;
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Сервер запущен");
            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    new Thread(new SearchServer(socket, booleanSearchEngine, serverLogic)).start();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

