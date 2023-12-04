import java.io.*;
import java.net.*;


/**
 * DentistClient, which will connect to the server and run the login method
 * hostname = localhost
 * port = 6000
 * @author Jack White
 * @version 11-29-23
 */
public class DentistClient {
    static final int port = 6000;
    static final String hostname = "localhost";

    public Socket socket;
    public BufferedReader reader;
    public PrintWriter writer;



    public DentistClient() {
        try {
            socket = new Socket(hostname, port);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);
        } catch (ConnectException e) {
            throw new RuntimeException("Server is either not online, or incorrect hostname/port");
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }



    public synchronized static void main(String[] args) {
        DentistClient thisClient = new DentistClient();
        thisClient.run();
    }


    public synchronized void run() {
        try {
            Login.main(new String[0], this);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("ERROR!");
        }
    }


    public synchronized String readLine() {
        try {
            String line = reader.readLine();
            System.out.println("Read from server -> " + line);
            return line;
        } catch (IOException e) {
            return e.getMessage();
        }

    }
    public synchronized void println(String input) {
        System.out.println("Wrote to server -> " + input);
        writer.println(input);
        writer.flush();
    }
}
