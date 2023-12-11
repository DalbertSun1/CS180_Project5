import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.sql.Array;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.io.PrintWriter;

/**
 * Project 5
 * Dentist Office Calendar Marketplace
 *
 * @author Dalbert Sun, Vihaan Chadha, Jack White, Himaja Narajala, Aaryan Bondre
 * @version December 11th, 2023
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
            //System.out.println("Adding Doctor:");
            BufferedWriter writer = new BufferedWriter(new FileWriter("doctors.txt", true));

            int counter = 0;
        /*for (int i = 0; i < doctorList.size(); i++) {
            if (doctorList.get(i). equals(doctor)) {
                counter++;
            }
        }*/

            BufferedReader reader = new BufferedReader(new FileReader("doctors.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                if (String.valueOf(doctor).equals(line)) {
                    counter++;
                }
            }

            if (counter == 0) {
                //doctorList.add(doctor);
                writer.write(doctor.getName() + "\n");
                writer.flush();
                writer.close();
                reader.close();
                return true;
            } else {
                return false;
            }

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean removeDoctor(Doctor doctor) throws IOException {
        try {
            ArrayList<String> list = new ArrayList<>();
            String docName = String.valueOf(doctor);
            BufferedReader bfr = new BufferedReader(new FileReader("doctors.txt"));
            String line = bfr.readLine();
            while (line != null) {
                if (!(line.equals(docName))) {
                    list.add(line);
                }
                line = bfr.readLine();
            }
            bfr.close();

            File f = new File("doctors.txt");
            FileOutputStream fos = new FileOutputStream(f);
            PrintWriter pw = new PrintWriter(fos);
            for (int i = 0; i < list.size(); i++) {
                pw.println(list.get(i));
            }
            pw.flush();
            pw.close();
            return true;
        } catch (Exception e) {
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
        } else {
            JOptionPane.showMessageDialog(null, "There are no pending appointments", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        return aptList.toArray(new String[0]);
    }

    public static void clientReadDoctorFile(DentistClient client) {
        client.println("readDoctorFile::");

        ArrayList<String> aptList = new ArrayList<>();

        String input = client.readLine(); // get apts from server

        String[] approvedAppointments = new String[0];

        for (String apt : input.split(";")) { // turn apts into a list of strings
            aptList.add(apt);
        }

        if (aptList.isEmpty()) {
            JOptionPane.showMessageDialog(null, "You have no approved appointments at this time.", "Approved appointments",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            approvedAppointments = new String[aptList.size()];
            for (int i = 0; i < approvedAppointments.length; i++) {
                approvedAppointments[i] = aptList.get(i);
            }

            JOptionPane.showConfirmDialog(null, approvedAppointments, "Approved appointments",
                    JOptionPane.OK_CANCEL_OPTION);

        }
    }

    public static void serverReadDoctorFile(DentistServer server) {
        try {
            // read pending apts
            BufferedReader reader = new BufferedReader(new FileReader("approved.txt"));
            ArrayList<String> appointmentsList = new ArrayList<>();

            String line;
            while ((line = reader.readLine()) != null) {
                appointmentsList.add(line);
            }
            reader.close();
            String[] aptList = appointmentsList.toArray(new String[0]);
            // Now you have the approved appointments in the 'aptList' array

            // send aptList to client
            StringBuilder output = new StringBuilder();
            for (String apt : aptList) {
                output.append(apt + ";");
            }
            server.println(output.toString());
        } catch (FileNotFoundException e) {
            server.println("");
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

        String[] pendingAppointments = new String[0];

        for (String apt : input.split(";")) { // turn apts into a list of strings
            aptList.add(apt);
        }


        if (aptList.isEmpty()) {
            JOptionPane.showMessageDialog(null, "You have no pending appointments at this time.", "Pending appointments",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            pendingAppointments = new String[aptList.size()];
            for (int i = 0; i < pendingAppointments.length; i++) {
                pendingAppointments[i] = aptList.get(i);
            }

            JOptionPane.showConfirmDialog(null, pendingAppointments, "Pending appointments",
                    JOptionPane.OK_CANCEL_OPTION);

        }
    }

    public static void serverReadFileStats(DentistServer server) { // reads file and returns printList to client
        try {
            ArrayList<String[]> list = new ArrayList<String[]>();
            ArrayList<String> list2 = new ArrayList<String>(); // stores each line of the file, only for printing purposes

            BufferedReader bfr = new BufferedReader(new FileReader("approved.txt"));
            String line = bfr.readLine();
            // creates array to store each approved appointment separately
            String[] commas = new String[4];

            while (line != null) {
                list2.add(line);
                commas = line.split(",", 4);
                list.add(commas);
                line = bfr.readLine();
            }
            bfr.close();

            //splits list into each parameter
            String[] names = new String[list.size()];
            String[] dates = new String[list.size()];
            String[] times = new String[list.size()];
            String[] doctors = new String[list.size()];
            for (int i = 0; i < list.size(); i++) {
                names[i] = list.get(i)[0];
                dates[i] = list.get(i)[1];
                times[i] = list.get(i)[2];
                doctors[i] = list.get(i)[3];
            }


            ArrayList<String> aptList = new ArrayList<String>();
            System.out.println("Approved appointments:");
            //displays the approved appointments for that person
            for (int i = 0; i < list2.size(); i++) {
                aptList.add(list2.get(i));

            }

            // send aptList to client
            StringBuilder output = new StringBuilder();
            for (String apt : aptList) {
                output.append(apt + ";");
            }
            server.println(output.toString());




        } catch (Exception e) {
            e.printStackTrace();

        }
    }


    //displays pending appointments

    public static String[] viewPending() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("pending.txt"));
        String line = reader.readLine();
        String[] pendingAppointments = new String[0];
        if (line == null) {
            JOptionPane.showMessageDialog(null, "You have no pending appointments at this time.", "Pending appointments",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            ArrayList<String> list = new ArrayList<String>();

            while (line != null) {
                list.add(line);
                line = reader.readLine();
            }

            //adding pending appointments list to an array so that the array can be displayed
            pendingAppointments = new String[list.size()];
            for (int i = 0; i < pendingAppointments.length; i++) {
                pendingAppointments[i] = list.get(i);
            }

            JOptionPane.showConfirmDialog(null, pendingAppointments, "Pending appointments",
                    JOptionPane.OK_CANCEL_OPTION);
        }
        reader.close();
        return pendingAppointments;
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

    public static boolean approveAppointment(String approve) throws IOException {
        try {
            //System.out.println(approve);
            File approved = new File("approved.txt");
            File pending = new File("pending.txt");
            ArrayList<String> approvedList = new ArrayList<>();
            ArrayList<String> pendingList = new ArrayList<>();
            if (!approved.exists()) {
                approved.createNewFile();
            }
            if (!pending.exists()) {pending.createNewFile();}

            BufferedReader pendingReader = new BufferedReader(new FileReader(pending));
            BufferedReader approvedReader = new BufferedReader(new FileReader(approved));
            String line2 = approvedReader.readLine();
            while (line2 != null) {
                approvedList.add(line2);
                line2 = approvedReader.readLine();
            }

            String line = pendingReader.readLine();
            while (line != null) {
                if (line.equals(approve)) {
                    approvedList.add(line);
                } else {
                    pendingList.add(line);
                }
                line = pendingReader.readLine();
            }

            File approvedFile = new File("approved.txt");
            File pendingFile = new File("pending.txt");
            FileOutputStream fos1 = new FileOutputStream(approvedFile);
            FileOutputStream fos2 = new FileOutputStream(pendingFile);
            PrintWriter pw1 = new PrintWriter(fos1);
            PrintWriter pw2 = new PrintWriter(fos2);
            for (int i = 0; i < approvedList.size(); i++) {
                pw1.println(approvedList.get(i));
            }
            pw1.close();
            for (int i = 0; i < pendingList.size(); i++) {
                pw2.println(pendingList.get(i));
            }
            pw2.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean clientApproveAppointment(String choice, DentistClient client) {
        client.println("approveAppointment::" + choice);

        if (client.readLine().equals("true")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean declineAppointment(String decline) throws IOException {
        List<String> pendingAppointments = new ArrayList<>();

        try (BufferedReader pendingReader = new BufferedReader(new FileReader("pending.txt"))) {

            String line1;
            int lineNum = 1;

            while ((line1 = pendingReader.readLine()) != null) {
                if (!(line1.equals(decline))) {
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

    public static boolean clientDeclineAppointment(String choice, DentistClient client) {
        client.println("declineAppointment::" + choice);
        if (client.readLine().equals("true")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean clientRescheduleAppointment(Scanner scan, DentistClient client) throws IOException {
        String name = JOptionPane.showInputDialog(null, "Enter the patient's name:",
                "Reschedule appointment", JOptionPane.QUESTION_MESSAGE);

        client.println("readFile::" + name);

        ArrayList<String> aptList = new ArrayList<>();
        int num = 0;

        String input = client.readLine();

        if (!input.isEmpty()) {
            for (String apt : input.split(";")) {
                aptList.add(apt);
                num++;
            }
        }

        //System.out.println("Approved appointments:");
        //System.out.println("Choice #, Patient Name, Day of Month, Time, Doctor Name");
        //displays the approved appointments for that person

        String[] approvedList = new String[num]; // converts to Array, adds a number before
        for (int i = 0; i < approvedList.length; i++) {
            approvedList[i] = (i + 1) + ":" + aptList.get(i);
        }


        if (aptList.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Name entered does not match any approved appointments",
                    "Reschedule appointment", JOptionPane.ERROR_MESSAGE);
        } else {
            //System.out.println("Which appointment would you like to change?");
            String rescheduleOption = (String) JOptionPane.showInputDialog(null, "Which appointment would you like to reschedule?",
                    "Reschedule appointment", JOptionPane.QUESTION_MESSAGE, null, approvedList,
                    approvedList[0]);
            if ((rescheduleOption == null) || (rescheduleOption.isEmpty())) {
                JOptionPane.showMessageDialog(null, "Back to Menu:", "Reschedule appointment",
                        JOptionPane.ERROR_MESSAGE);
                // TODO: Fix later
                // return;
                return false;
            }

            try {

                boolean timeIsBooked;
                do {
                    timeIsBooked = false;


                    try {

                        String[] dateList = new String[31];
                        int j = 1;
                        for (int i = 0; i < 31; i++) {
                            dateList[i] = "" + j;
                            j++;
                        }
                        String date;
                        do { // gets String date from the user
                            date = (String) JOptionPane.showInputDialog(null, "What date would you like to change it to?",
                                    "Reschedule appointment", JOptionPane.QUESTION_MESSAGE, null, dateList, dateList[0]);
                            if ((date == null) || (date.isEmpty())) {
                                JOptionPane.showMessageDialog(null, "Back to Menu:");
                                //return;
                                // TODO: Fix Later
                            }
                        } while ((date == null) || (date.isEmpty()));
                        int newDate = Integer.parseInt(date);

                        String[] timeslots = new String[9];
                        timeslots[0] = "9:00 AM - 10:00 AM";
                        timeslots[1] = "10:00 AM - 11:00 AM";
                        timeslots[2] = "11:00 AM - 12:00 PM";
                        timeslots[3] = "12:00 PM - 1:00 PM";
                        timeslots[4] = "1:00 PM - 2:00 PM";
                        timeslots[5] = "2:00 PM - 3:00 PM";
                        timeslots[6] = "3:00 PM - 4:00 PM";
                        timeslots[7] = "4:00 PM - 5:00 PM";
                        timeslots[8] = "5:00 PM - 6:00 PM";

                        String newTime = (String) JOptionPane.showInputDialog(null, "What time would you like to change it to?",
                                "Reschedule appointment", JOptionPane.QUESTION_MESSAGE, null, timeslots,
                                timeslots[0]);
                        if ((newTime == null) || (newTime.isEmpty())) {
                            JOptionPane.showMessageDialog(null, "Back to Menu:");
                            //TODO: Fix This
                            //return;
                        }

                        String line = rescheduleOption;
                        int userIndex = Integer.parseInt(line.substring(0, line.indexOf(":")));
                        String lineWithoutNum = line.substring(line.indexOf(":"));

                        String doctorName = line.split(",")[3];



                        //String doctorName = aptList.get(userIndex).split(",")[3];

                        client.println("rescheduleAppointment::" + name + ","
                                + newDate + "," + newTime + "," + doctorName + "," + userIndex);
                        if (!Boolean.parseBoolean(client.readLine())) {
                            timeIsBooked = true;
                            System.out.println("That time and day is already taken. Please choose another.");
                        } else {
                            return true;
                        }

                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Please enter a valid input.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } while (timeIsBooked);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter a valid input.", "Error",
                        JOptionPane.ERROR_MESSAGE);

            }
        }

        return false;
    }
}
