import javax.swing.*;
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

    public DentistOffice(String name) {
        this.name = name;
    }

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

    public ArrayList<Doctor> readDoctors() throws IOException {
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
    }

    public void addDoctor(Doctor doctor) throws IOException {
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
            doctorList.add(doctor);
            writer.write(String.valueOf(doctor) + "\n");
            JOptionPane.showMessageDialog(null, "Successfully added doctor " + doctor.getName() + " to " + this.name);

        } else {
            JOptionPane.showMessageDialog(null,"Doctor " + doctor.getName() + " is already in " + this.name);
        }
        writer.close();
        reader.close();
    }

    public void deleteDoctor(Doctor doctor) throws IOException {
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

            JOptionPane.showMessageDialog(null, "Successfully removed doctor " + doctor.getName() + " from " + this.name);
        } else {
            JOptionPane.showMessageDialog(null, "Doctor " + doctor.getName() + " is not in " + this.name);
        }
    }

    // displays approved appointments

    public static String[] getAppointments() throws IOException {
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

    public void viewApprovedAppointments() {
        try {
            String[] approvedAppointments = getAppointments();
            // Now you have the appointments in the 'appointments' array
            if (approvedAppointments.length == 0) {
                //System.out.println("You have no approved appointments.");
                JOptionPane.showMessageDialog(null, "You have no approved appointments at this time.", "Approved appointments",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                /*for (int i = 0; i < appointments.length; i++) {

                    System.out.println((i + 1) + ": " + appointments[i]);

                }*/
                JOptionPane.showMessageDialog(null, approvedAppointments, "Approved appointments",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle the IOException appropriately
        }
    }

    public void viewApproved() {
        viewApprovedAppointments();
    }


    //displays pending appointments
    public String[] viewPending() throws IOException {
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

            JOptionPane.showMessageDialog(null, pendingAppointments, "Pending appointments",
                    JOptionPane.INFORMATION_MESSAGE);
        }
        return pendingAppointments;
    }

    // TODO: Implement storage of doctor names as well
    public void approveAppointment(String approve) throws IOException {
        List<String> pendingAppointments = new ArrayList<>();

        try (BufferedWriter approvedWriter = new BufferedWriter(new FileWriter("approved.txt", true));
             BufferedReader pendingReader = new BufferedReader(new FileReader("pending.txt"))) {

            String line1;
            int lineNum = 1;

            while ((line1 = pendingReader.readLine()) != null) {
                if (line1.equals(approve)) {
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
        }
    }

    public void declineAppointment(String decline) throws IOException {
        List<String> pendingAppointments = new ArrayList<>();

        try (BufferedReader pendingReader = new BufferedReader(new FileReader("pending.txt"))) {

            String line1;
            int lineNum = 1;

            while ((line1 = pendingReader.readLine()) != null) {
                if (line1.equals(decline)) {
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
        }
    }


    public void rescheduleAppointment(Scanner scan) throws IOException {
        BufferedReader reader1 = new BufferedReader(new FileReader("approved.txt"));
        int counter = 0;
        boolean found1 = false;
        String line;
        ArrayList<String> lines = new ArrayList<>();
        String[] lineSplit;

        String checkName = JOptionPane.showInputDialog(null, "Enter your name",
                "Reschedule appointment", JOptionPane.QUESTION_MESSAGE);
        //System.out.println("Enter your name: ");
        //String checkName = scan.nextLine();
        //System.out.println("Choice #, Patient Name, Day of Month, Time, Doctor Name");

        ArrayList<String> approvedAppointments = new ArrayList<String>();
        while ((line = reader1.readLine()) != null) {
            lines.add(line);
            lineSplit = line.split(",");
            if (lineSplit[0].equals(checkName)) {
                //System.out.println(currentLine + ": " + line);
                counter++;
                approvedAppointments.add(line);
                found1 = true;
            }
        }

        String[] approvedList = new String[counter];
        for (int i = 0; i < approvedList.length; i++) {
            approvedList[i] = approvedAppointments.get(i);
        }

        if (!found1) {
            //System.out.println("You have no approved appointments at this time.");
            JOptionPane.showMessageDialog(null, "You have no approved appointments at this time.", "Reschedule appointment",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            //System.out.println("Which appointment would you like to change?");
            String rescheduleOption;
            do {
                rescheduleOption = (String) JOptionPane.showInputDialog(null, "Which appointment would you like to reschedule?",
                        "Reschedule appointment", JOptionPane.QUESTION_MESSAGE, null, approvedList,
                        approvedList[0]);
                if ((rescheduleOption == null) || (rescheduleOption.isEmpty())) {
                    JOptionPane.showMessageDialog(null, "Please select a valid option!", "Reschedule appointment",
                            JOptionPane.ERROR_MESSAGE);
                }
            } while ((rescheduleOption == null)  || (rescheduleOption.isEmpty()));

            try {
                //String input1 = scan.nextLine();
                //int userIndex = Integer.parseInt(input1) - 1;

                boolean timeIsBooked;
                do {
                    timeIsBooked = false;
                    //System.out.println("What day would you like to change it to?");

                    try {
                        //String input2 = scan.nextLine();
                        String[] dateList = new String[31];
                        int j = 1;
                        for (int i = 0; i < 31; i++) {
                            dateList[i] = "" + j;
                            j++;
                        }
                        String date = (String) JOptionPane.showInputDialog(null, "What date would you like to change it to?",
                                "Reschedule appointment", JOptionPane.QUESTION_MESSAGE, null, dateList, dateList[0]);
                        if ((date == null) || (date.isEmpty())) {
                            JOptionPane.showMessageDialog(null, "Thank you for using Dentist Office!");
                            return;
                        }

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
                            JOptionPane.showMessageDialog(null, "Thank you for using Dentist Office!");
                            return;
                        }

                        // check if given time is already taken
                        line = rescheduleOption;
                        //line = lines.get(userIndex);
                        lineSplit = line.split(",");
                        // get this line, turn into a list, switch

                        String doctorName = lineSplit[3];


                        for (String thisLine : lines) {
                            lineSplit = thisLine.split(",");
                            if (lineSplit[3].equals(doctorName)) {
                                if (lineSplit[1].equals(Integer.toString(newDate))) {
                                    if (lineSplit[2].equals(newTime)) {
                                        //System.out.println("Time unavailable. Try again.");
                                        JOptionPane.showMessageDialog(null, "Time unavailable. Try again.", "Reschedule appointment",
                                                JOptionPane.ERROR_MESSAGE);
                                        timeIsBooked = true;
                                    }
                                }
                            }
                        }

                        int userIndex = 0;
                        for (int i = 0; i < lines.size(); i++) {
                            if (lines.get(i).equals(rescheduleOption)) {
                                userIndex = i;
                            }
                        }

                        if (!timeIsBooked) {
                            JOptionPane.showMessageDialog(null, "Appointment rescheduled.", "Reschedule appointment",
                                    JOptionPane.INFORMATION_MESSAGE);
                            lineSplit[2] = newTime;
                            lineSplit[1] = Integer.toString(newDate);
                            lineSplit[0] = checkName;
                            String newApt = "";
                            for (String x : lineSplit) {
                                newApt += x + ",";
                            }
                            newApt = newApt.substring(0, newApt.length() - 1);
                            lines.set(userIndex, newApt);

                            BufferedWriter writer1 = new BufferedWriter(new FileWriter("approved.txt"));
                            for (String thisLine : lines) {
                                writer1.write(thisLine + "\n");
                            }
                            writer1.close();

                        }
                    } catch (NumberFormatException e) {
                        //System.out.println("Please enter an integer.");
                        JOptionPane.showMessageDialog(null, "Please enter a valid input.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } while (timeIsBooked);
            } catch (NumberFormatException e) {
                //System.out.println("Please enter an integer.");
                JOptionPane.showMessageDialog(null, "Please enter a valid input.", "Error",
                        JOptionPane.ERROR_MESSAGE);

            }
        }
        reader1.close();

    }
}
