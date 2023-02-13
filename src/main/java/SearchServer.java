import java.io.*;
import java.net.Socket;

public class SearchServer implements Runnable {
    private final Socket socket;
    private final BooleanSearchEngine booleanSearchEngine;
    private final ServerLogic serverLogic;

    public SearchServer(Socket socket, BooleanSearchEngine booleanSearchEngine, ServerLogic serverLogic) {
        this.socket = socket;
        this.booleanSearchEngine = booleanSearchEngine;
        this.serverLogic = serverLogic;
    }

    @Override
    public void run() {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
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
        }