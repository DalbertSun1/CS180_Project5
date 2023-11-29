import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;


/**
 * DentistClient, which will connect to the server and run the login method
 * hostname = localhost
 * port = 6969
 * @author Jack White
 * @version 11-29-23
 */
public class DentistClient {
    static final int port = 5000;
    static final String hostname = "localhost";


    public static void main(String[] args) {
        try (Socket socket = new Socket(hostname, port);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter writer = new PrintWriter(socket.getOutputStream())) {

            writer.println("Hello server!");
            System.out.println(reader.readLine());

            Login.main(new String[0]);


        } catch (UnknownHostException e) {
            System.out.println(e.getMessage());
            System.out.println("Client could not connect. Server not running currently, or incorrect port, or incorrect hostname");
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
