/*
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
}*/

import java.io.*;
import java.net.*;

// Server class
class Server {
    public static void main(String[] args)
    {
        ServerSocket serverSocket = null;

        try {
            // server is listening on port 1234
            serverSocket = new ServerSocket(6000);
            serverSocket.setReuseAddress(true);

            // running infinite loop for getting client request
            while (true) {

                // socket object to receive incoming client requests
                Socket client = serverSocket.accept();

                // Displaying that new client is connected to server
                System.out.println("New client connected" + client.getInetAddress().getHostAddress());

                // create a new thread object
                DentistServer clientSocket = new DentistServer(client);

                // This thread will handle the client separately
                new Thread(clientSocket).start();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}