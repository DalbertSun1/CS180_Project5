import java.io.*;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * Project 4
 * Dentist Office Calendar Marketplace
 *
 * @author Dalbert Sun, Vihaan Chadha, Jack White, Himaja Narajala, Aaryan Bondre
 * @version November 13th, 2023
 */

public class DentistOffice {
    private String name;
    private ArrayList<Doctor> doctorList = new ArrayList<>();

    public DentistOffice(String name) { // this is the server version of DentistOffice

        this.name = name;
        this.setDoctorList(readDoctors());
    }
    public DentistOffice(String name, DentistClient client) {
        this.name = name;
        this.setDoctorList(clientReadDoctors(client));
    } // this is the client version of DentistOffice


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Doctor> getDoctorList() {
        return doctorList;
    }


    public void setDoctorList(ArrayList<Doctor> doctorList) {
        this.doctorList = doctorList;
    }

    public ArrayList<Doctor> readDoctors() {
        try {
            File f = new File("doctors.txt");
            if (!f.exists()) {
                BufferedWriter writer = new BufferedWriter(new FileWriter("doctors.txt"));
                writer.write("");
                writer.close();
            }
            BufferedReader reader = new BufferedReader(new FileReader("doctors.txt"));
            String line;

            while ((line = reader.readLine()) != null) {
                Doctor doctor = new Doctor(line);
                doctorList.add(doctor);
            }
            reader.close();
            return doctorList;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public ArrayList<Doctor> clientReadDoctors(DentistClient client) {
        client.println("readDoctors::");
        ArrayList<Doctor> doctorList = new ArrayList<>();
        String input = client.readLine();
        for (String name : input.split(",")) {
            doctorList.add(new Doctor(name));
        }
        return doctorList;
    }


    public boolean addDoctor(Doctor doctor) {
    try {
        BufferedWriter writer = new BufferedWriter(new FileWriter("doctors.txt", true));
        if (!(doctorList.contains(doctor))) {
            doctorList.add(doctor);
            writer.write(String.valueOf(doctor) + "\n");

            System.out.println("Successfully added doctor " + doctor.getName() + " to " + this.name);
        } else {
            System.out.println("Doctor " + doctor.getName() + " is already in " + this.name);
        }
        writer.close();
        return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean removeDoctor(Doctor doctor) {
        try {
            boolean repeat = false;
            int doctorNum = -1;

            for (int i = 0; i < doctorList.size(); i++) {
                if (String.valueOf(doctorList.get(i)).equals(String.valueOf(doctor))) {
                    repeat = true;
                    doctorNum = i;
                }
            }
            if (repeat) {
                BufferedWriter writer = new BufferedWriter(new FileWriter("doctors.txt", false));

                doctorList.remove(doctorNum);
                for (Doctor s : doctorList) {
                    writer.write(String.valueOf(s));
                    writer.newLine();
                }

                writer.close();

                System.out.println("Successfully removed doctor " + doctor.getName() + " from " + this.name);
                return true;
            } else {
                System.out.println("Doctor " + doctor.getName() + " is not in " + this.name);
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }


    }

    // displays approved appointments

    public static String[] serverGetAppointments() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("approved.txt"));
        ArrayList<String> appointmentsList = new ArrayList<>();

        String line;
        while ((line = reader.readLine()) != null) {
            appointmentsList.add(line);
        }
        reader.close();
        String[] appointments = new String[appointmentsList.size()];
        for (int i = 0; i < appointmentsList.size(); i++) {
            appointments[i] = appointmentsList.get(i);
        }

        return appointments;
    }
    public static String[] clientGetAppointments(DentistClient client) {
        client.println("readDoctorFile::");

        ArrayList<String> aptList = new ArrayList<>();

        String input = client.readLine(); // get apts from server

        for (String apt : input.split(";")) { // turn apts into a list of strings
            aptList.add(apt);
        }
        return aptList.toArray(new String[0]);
    }
    public static String[] clientGetPendingAppointments(DentistClient client) {
        client.println("readDoctorPendingFile::");

        ArrayList<String> aptList = new ArrayList<>();

        String input = client.readLine(); // get apts from server

        if (!input.isEmpty()) {
            for (String apt : input.split(";")) {
                aptList.add(apt);
            }
        }
        return aptList.toArray(new String[0]);
    }

    public static void clientReadDoctorFile(DentistClient client) {
        client.println("readDoctorFile::");

        ArrayList<String> aptList = new ArrayList<>();

        String input = client.readLine(); // get apts from server

        for (String apt : input.split(";")) { // turn apts into a list of strings
            aptList.add(apt);
        }

        if (aptList.isEmpty()) {
            System.out.println("You have no approved appointments.");
        } else {
            System.out.println("Approved appointments:");
            for (int i = 0; i < aptList.size(); i++) {

                System.out.println((i + 1) + ": " + aptList.get(i));

            }
        }
    }

    public static void serverReadDoctorFile(DentistServer server) {
        try {
            String[] aptList = serverGetAppointments();
            // Now you have the appointments in the 'appointments' array

            // send aptList to client
            StringBuilder output = new StringBuilder();
            for (String apt : aptList) {
                output.append(apt + ";");
            }
            server.println(output.toString());

        } catch (IOException e) {
            e.printStackTrace(); // Handle the IOException appropriately
        }
    }
    public static void serverReadDoctorPendingFile(DentistServer server) {
        try {
            // read pending apts
            BufferedReader reader = new BufferedReader(new FileReader("pending.txt"));
            ArrayList<String> appointmentsList = new ArrayList<>();

            String line;
            while ((line = reader.readLine()) != null) {
                appointmentsList.add(line);
            }
            reader.close();
            String[] aptList = appointmentsList.toArray(new String[0]);
            // Now you have the pending appointments in the 'aptList' array

            // send aptList to client
            StringBuilder output = new StringBuilder();
            for (String apt : aptList) {
                output.append(apt + ";");
            }
            server.println(output.toString());

        } catch (IOException e) {
            e.printStackTrace(); // Handle the IOException appropriately
        }
    }
    public static void clientReadDoctorPendingFile(DentistClient client) {
        client.println("readDoctorPendingFile::");

        ArrayList<String> aptList = new ArrayList<>();

        String input = client.readLine(); // get apts from server

        for (String apt : input.split(";")) { // turn apts into a list of strings
            aptList.add(apt);
        }


        if (aptList.isEmpty()) {
            System.out.println("You have no pending appointments.");
        } else {
            System.out.println("Pending appointments:");
            for (int i = 0; i < aptList.size(); i++) {

                System.out.println((i + 1) + ": " + aptList.get(i));

            }
        }
    }





    //displays pending appointments
    public static int viewPending() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("pending.txt"));
        String line = reader.readLine();
        int num = 1;
        if (line == null) {
            System.out.println("You have no pending appointments.");
            num = 0;
        } else {
            while (line != null) {
                System.out.println(num + ": " + line);
                num++;
                line = reader.readLine();
            }
            reader.close();
        }
        return num;
    }

    public static String serverViewPending() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("pending.txt"));
        String line = reader.readLine();
        int num = 1;
        if (line == null) {
            System.out.println("You have no pending appointments.");
            num = 0;
        } else {
            while (line != null) {
                System.out.println(num + ": " + line);
                num++;
                line = reader.readLine();
            }
            reader.close();
        }
        return "true";
    }

    // TODO: Implement storage of doctor names as well
    public static boolean approveAppointment(int line) throws IOException {
        List<String> pendingAppointments = new ArrayList<>();

        try (BufferedWriter approvedWriter = new BufferedWriter(new FileWriter("approved.txt", true));
             BufferedReader pendingReader = new BufferedReader(new FileReader("pending.txt"))) {

            String line1;
            int lineNum = 1;

            while ((line1 = pendingReader.readLine()) != null) {
                if (lineNum == line) {
                    // Process the approved appointment
                    approvedWriter.write(line1);
                    approvedWriter.newLine();
                } else {
                    // Add non-approved appointments to the temporary list
                    pendingAppointments.add(line1);
                }
                lineNum++;
            }
        }

        // Write the updated pending appointments back to "pending.txt"
        try (BufferedWriter pendingWriter = new BufferedWriter(new FileWriter("pending.txt"))) {
            for (String appointment : pendingAppointments) {
                pendingWriter.write(appointment);
                pendingWriter.newLine();
            }
            return true;
        }
    }

    public static boolean clientApproveAppointment(int choice, DentistClient client) {
        client.println("approveAppointment::" + choice);

        if (client.readLine().equals("true")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean declineAppointment(int line) throws IOException {
        List<String> pendingAppointments = new ArrayList<>();

        try (BufferedReader pendingReader = new BufferedReader(new FileReader("pending.txt"))) {

            String line1;
            int lineNum = 1;

            while ((line1 = pendingReader.readLine()) != null) {
                if (!(lineNum == line)) {
                    pendingAppointments.add(line1);
                }
                lineNum++;
            }
        }

        // Write the updated pending appointments back to "pending.txt"
        try (BufferedWriter pendingWriter = new BufferedWriter(new FileWriter("pending.txt"))) {
            for (String appointment : pendingAppointments) {
                pendingWriter.write(appointment);
                pendingWriter.newLine();
            }
            return true;
        }
    }

    public static boolean clientDeclineAppointment(int choice, DentistClient client) {
        client.println("declineAppointment::" + choice);

        if (client.readLine().equals("true")) {
            return true;
        } else {
            return false;
        }
    }


    public static boolean clientRescheduleAppointment(Scanner scan, DentistClient client) throws IOException {

        System.out.println("Enter patient name:");
        String name = scan.nextLine();
        client.println("readFile::" + name);

        ArrayList<String> aptList = new ArrayList<>();

        String input = client.readLine();

        for (String apt : input.split(";")) {
            aptList.add(apt);
        }

        System.out.println("Approved appointments:");
        System.out.println("Choice #, Patient Name, Day of Month, Time, Doctor Name");
        //displays the approved appointments for that person
        int i = 1;
        for (String apt : aptList) {
            System.out.println(i++ + ":" + apt);
        }


        if (aptList.isEmpty()) {
            System.out.println("You have no approved appointments at this time.");
        } else {
            System.out.println("Which appointment would you like to change?");
            try {
                String input1 = scan.nextLine();
                int userIndex = Integer.parseInt(input1) - 1;

                boolean timeIsBooked = false;
                do {
                    System.out.println("What day would you like to change it to?");
                    try {
                        String input2 = scan.nextLine();
                        int newDay = Integer.parseInt(input2);

                        String newTime = "";
                        int newTimeInt = 0;
                        do {

                            System.out.println("What time would you like to change it to?");

                            System.out.println("1. 9:00 AM - 10:00 AM");
                            System.out.println("2. 10:00 AM - 11:00 AM");
                            System.out.println("3. 11:00 AM - 12:00 PM");
                            System.out.println("4. 12:00 PM - 1:00 PM");
                            System.out.println("5. 1:00 PM - 2:00 PM");
                            System.out.println("6. 2:00 PM - 3:00 PM");
                            System.out.println("7. 3:00 PM - 4:00 PM");
                            System.out.println("8. 4:00 PM - 5:00 PM");
                            System.out.println("9. 5:00 PM - 6:00 PM");

                            try {
                                String input3 = scan.nextLine();
                                newTimeInt = Integer.parseInt(input3);

                                switch (newTimeInt) {
                                    case 1 -> {
                                        newTime = "9:00 AM - 10:00 AM";
                                    }
                                    case 2 -> {
                                        newTime = "10:00 AM - 11:00 AM";
                                    }
                                    case 3 -> {
                                        newTime = "11:00 AM - 12:00 PM";
                                    }
                                    case 4 -> {
                                        newTime = "12:00 PM - 1:00 PM";
                                    }
                                    case 5 -> {
                                        newTime = "1:00 PM - 2:00 PM";
                                    }
                                    case 6 -> {
                                        newTime = "2:00 PM - 3:00 PM";
                                    }
                                    case 7 -> {
                                        newTime = "3:00 PM - 4:00 PM";
                                    }
                                    case 8 -> {
                                        newTime = "4:00 PM - 5:00 PM";
                                    }
                                    case 9 -> {
                                        newTime = "5:00 PM - 6:00 PM";
                                    }
                                    default -> {
                                        System.out.println("You typed an incorrect choice. ");
                                    }
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Please enter an integer.");
                            }

                        } while (newTimeInt < 1 || newTimeInt > 9);


                        String doctorName = aptList.get(userIndex).split(",")[3];

                        client.println("rescheduleAppointment::" + name + ","
                                + newDay + "," + newTime + "," + doctorName + "," + (userIndex + 1));
                        if (!Boolean.parseBoolean(client.readLine())) {
                            timeIsBooked = true;
                            System.out.println("That time and day is already taken. Please choose another.");
                        } else {
                            return true;
                        }






                    } catch (NumberFormatException e) {
                        System.out.println("Please enter an integer.");
                    }
                } while (timeIsBooked);
            } catch (NumberFormatException e) {
                System.out.println("Please enter an integer.");
            }
        }



        return false;

    }

}
