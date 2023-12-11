import javax.management.relation.RoleUnresolved;
import java.io.*;
import java.net.*;
/**
 * Project 5
 * DentistClient, which will connect to the server and run the login method
 *
 * hostname = localhost
 * port = 6000
 *
 * @author Dalbert Sun, Vihaan Chadha, Jack White, Himaja Narajala, Aaryan Bondre
 * @version December 11th, 2023
 */
public class DentistClient {
    static final int port = 6000;
    static final String hostname = "localhost";
    public static BufferedReader reader;
    public static PrintWriter writer;
    public static Object obj = new Object();
    public static void main(String[] args) {
        try (Socket socket = new Socket(hostname, port)) {
            // writing to server
            writer = new PrintWriter(socket.getOutputStream(), true);

            // reading from server
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            DentistClient thisClient = new DentistClient();
            synchronized (obj) {
                Login.main(new String[0], thisClient);
            }
            //thisClient.run();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception r) {
            r.printStackTrace();
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