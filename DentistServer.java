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
    public synchronized static void main(String[] args) {
        DentistServer thisServer = new DentistServer();
        thisServer.run();
    }

    synchronized void run() {
        boolean clientConnected = true;
        int i = 0;
        while (clientConnected) {
            System.out.println("Loop " + i);
            System.out.println("Waiting for client input...");
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
                    String name = params[0];
                    int date = Integer.parseInt(params[1]);
                    String appointmentTime = params[2];
                    String doctorName = params[3];

                    Appointment appointment = new Appointment(appointmentTime);
                    Doctor doctor = new Doctor(doctorName);

                    println(Patient.makeAppointment(name, date, doctor, appointment));
                }
                case "approveAppointment" -> {}

                case "readFile" -> {
                    String name = params[0];
                    Patient.serverReadFile(name, this);
                }


                default -> {
                    clientConnected = false;
                }

            }
            i++;
        }
    }

    public synchronized String readLine() {
        try {
            String line = reader.readLine();
            System.out.println("Read from client -> " + line);
            return line;
        } catch (IOException e) {
            return e.getMessage();
        }

    }

    public synchronized void println(String input) {
        System.out.println("Wrote to client -> " + input);
        writer.println(input);
        writer.flush();
    }



}
