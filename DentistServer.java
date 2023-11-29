import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class DentistServer {
    public static void main(String[] args) {
        int portNumber = 5000;

        try (ServerSocket serverSocket = new ServerSocket(portNumber);
             Socket socket = serverSocket.accept();
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter writer = new PrintWriter(socket.getOutputStream());) {


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
