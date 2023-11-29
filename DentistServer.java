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
            boolean clientConnected = true;

            while (clientConnected) {
                String rawMessage = reader.readLine();
                String methodChoice = rawMessage.split(":")[0];
                String[] params = rawMessage.split(":")[1].split(",");

                switch (methodChoice) {
                    case "authenticate" -> {
                        String username = params[1];
                        String password = params[2];
                        writer.write(Login.checkAccount(username, password) + "\n");
                    }
                    case "method2" -> {}
                    case "method3" -> {}
                    default -> {}

                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
