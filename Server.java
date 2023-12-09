import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        try {
            ServerSocket serverSocket = new ServerSocket(6000);
            while (true) {
                Socket socket = serverSocket.accept();
                DentistServer server = new DentistServer(socket);
                new Thread(server).start();
            }
        } catch (IOException e) {
            throw new IOException();
        }

    }
}