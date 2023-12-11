import javax.management.relation.RoleUnresolved;
import java.io.*;
import java.net.*;

import javax.swing.*;

/**
 * Project 5
 * DentistClient, which will connect to the server and run the login method
 * <p>
 * hostname = localhost
 * port = 6000
 *
 * @author Dalbert Sun, Vihaan Chadha, Jack White, Himaja Narajala, Aaryan Bondre
 * @version December 11th, 2023
 */
public class DentistClient {
    static final int port = 6000;
    static String hostname;
    public static BufferedReader reader;
    public static PrintWriter writer;
    public static Object obj = new Object();

    public static void main(String[] args) {
        String message = "Enter the hostname (default is 'localhost'):";

        // Show input dialog with a default message
        hostname = JOptionPane.showInputDialog(null, message);

        // Check if input is null or empty and set default
        if (hostname == null || hostname.isEmpty()) {
            hostname = "localhost";
        }

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
        } catch (ConnectException e) {
            JOptionPane.showMessageDialog(null, "Client could not connect to server. Likely, the server is not online or the port is already taken.", "Connection Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception r) {
            r.printStackTrace();
        }
    }

    public synchronized String readLine() {
        try {
            String line = reader.readLine();
            // This print statement is extremely useful for understanding the program and bug testing
            // System.out.println("Read from server -> " + line);
            return line;
        } catch (IOException e) {
            return e.getMessage();
        }

    }

    public synchronized void println(String input) {
        // This print statement is extremely useful for understanding the program and bug testing
        // System.out.println("Wrote to server -> " + input);
        writer.println(input);
        writer.flush();
    }
}