import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Project 5
 * Dentist Office Calendar Marketplace
 *
 * @author Dalbert Sun, Vihaan Chadha, Jack White, Himaja Narajala, Aaryan Bondre
 * @version December 11th, 2023
 */

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