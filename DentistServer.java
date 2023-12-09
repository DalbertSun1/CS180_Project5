import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class DentistServer implements Runnable {

    static final int port = 6000;
    ServerSocket serverSocket;
    Socket socket;
    BufferedReader reader;
    PrintWriter writer;
    public static Object obj = new Object();

    public DentistServer(Socket socket) {
        this.socket = socket;


    }

    public void run() {
        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        boolean clientConnected = true;
        int i = 0;
        while (clientConnected) {
            System.out.println("Loop " + i);
            System.out.println("Waiting for client input...");
            String rawMessage = readLine();
            System.out.println("rawMessage = " + rawMessage);
            String methodChoice = rawMessage.split("::")[0];
            System.out.println("methodChoice = " + methodChoice);
            String[] params = new String[0];
            try {
                params = rawMessage.split("::")[1].split(",");
            } catch (ArrayIndexOutOfBoundsException ignored) {};


            DentistOffice d = new DentistOffice("My Dentist Office");

            switch (methodChoice) {

                // login functions
                case "authenticate" -> {
                    String username = params[0];
                    String password = params[1];
                    synchronized (obj) {
                        println(Login.checkAccount(username, password) + "");
                    }
                }
                case "createAccount" -> {
                    synchronized (obj) {
                        println(Boolean.toString(Login.serverCreateAccount(params[0], params[1], params[2], params[3], params[4])));
                    }
                }


                // Patient side functions
                case "makeAppointment" -> {
                    String name = params[0];
                    int date = Integer.parseInt(params[1]);
                    String appointmentTime = params[3];
                    String doctorName = params[2];

                    synchronized (obj) {
                        Appointment appointment = new Appointment(appointmentTime);
                        Doctor doctor = new Doctor(doctorName);

                        println(Patient.makeAppointment(name, date, doctor, appointment));
                    }
                }

                case "cancelAppointment" -> {
                    String name = params[0];
                    String choice = params[1];
                    synchronized (obj) {
                        println(Patient.cancelAppointment(name, choice) + "");
                    }
                }





                // DentistOffice functions

                case "addDoctor" -> {
                    synchronized (obj) {
                        boolean result = d.addDoctor(new Doctor(params[0]));
                        println(String.valueOf(result));
                    }
                }
                case "removeDoctor" -> {
                    synchronized (obj) {
                        boolean result = d.removeDoctor(new Doctor(params[0]));
                        println(String.valueOf(result));
                    }
                }
                case "readDoctors" -> {
                    synchronized (obj) {
                        StringBuilder output = new StringBuilder();
                        for (Doctor doctor : d.getDoctorList()) {
                            output.append(doctor.getName()).append(",");
                        }
                        println(output.toString());
                    }

                }
                case "readDoctorFile" -> {
                    synchronized (obj) {
                        d.serverReadDoctorFile(this);
                    }
                }
                case "readDoctorPendingFile" -> {
                    synchronized (obj) {
                        d.serverReadDoctorPendingFile(this);
                    }
                }
                case "approveAppointment" -> {
                    String line = (params[0]);
                    synchronized (obj) {
                        try {
                            println(DentistOffice.approveAppointment(line) + "");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                case "declineAppointment" -> {
                    String line = (params[0]);
                    synchronized (obj) {
                        try {
                            println(DentistOffice.declineAppointment(line) + "");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                case "rescheduleAppointment" -> {
                    Scanner scan = new Scanner(System.in);
                    synchronized (obj) {
                        try {
                            println(String.valueOf(Patient.serverRescheduleAppointment(params[0],
                                    params[1], params[2], params[3], Integer.valueOf(params[4]))));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                case "importCalendar" -> {
                    synchronized (obj) {
                        MyCalendar calendar = new MyCalendar(params[0]);
                        ArrayList<Doctor> addD = calendar.importCalendar();
                        for (int j = 0; j < addD.size(); j++) {
                            d.addDoctor(addD.get(j));
                        }
                        println("true");
                    }
                }

                // other functions

                case "readFile" -> { // reads file for all apts that match the given name
                    String name = params[0];
                    synchronized (obj) {
                        Patient.serverReadFile(name, this);
                    }
                }

                default -> {
                    synchronized (obj) {
                        clientConnected = false;
                    }
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
        writer.println(input);
        writer.flush();
        System.out.println("Wrote to client -> " + input);
    }



}
