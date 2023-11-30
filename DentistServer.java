import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class DentistServer {

    static final int port = 6000;
    ServerSocket serverSocket;
    Socket socket;
    BufferedReader reader;
    PrintWriter writer;

    public DentistServer() {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Waiting for client....");
            socket = serverSocket.accept();
            System.out.println("Client connected.");
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public static void main(String[] args) {
        DentistServer thisServer = new DentistServer();
        thisServer.run();


    }

    void run() {

        boolean clientConnected = true;
        while (clientConnected) {
            System.out.println("blocking for client");
            String rawMessage = readLine();
            System.out.println("rawMessage = " + rawMessage);
            String methodChoice = rawMessage.split(":")[0];
            System.out.println("methodChoice = " + methodChoice);
            String[] params = rawMessage.split(":")[1].split(",");

            switch (methodChoice) {
                case "authenticate" -> {
                    String username = params[0];
                    String password = params[1];
                    println(Login.checkAccount(username, password) + "\n");
                }
                case "makeAppointment" -> {

                }
                case "approveAppointment" -> {}

                case "readFile" -> {
                    String name = params[0];
                    Patient.serverReadFile(name, this);
                }


                default -> {}

            }
        }
    }

    public String readLine() {
        try {
            String line = reader.readLine();
            System.out.println("read from client -> " + line);
            return line;
        } catch (IOException e) {
            return e.getMessage();
        }

    }

    public void println(String input) {
        System.out.println("Wrote to client -> " + input);
        writer.println(input);
    }



}
