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
        if (!(doctorList.contains(doctor))) {
            doctorList.add(doctor);
            writer.write(String.valueOf(doctor) + "\n");

            System.out.println("Successfully added doctor " + doctor.getName() + " to " + this.name);
        } else {
            System.out.println("Doctor " + doctor.getName() + " is already in " + this.name);
        }
        writer.close();
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

            System.out.println("Successfully removed doctor " + doctor.getName() + " from " + this.name);
        } else {
            System.out.println("Doctor " + doctor.getName() + " is not in " + this.name);
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
            String[] appointments = getAppointments();
            // Now you have the appointments in the 'appointments' array
            if (appointments.length == 0) {
                JOptionPane.showMessageDialog(null, "You have no approved appointments.", "Approved Appointments", JOptionPane.ERROR_MESSAGE);
            } else {
                for (int i = 0; i < appointments.length; i++) {

                    System.out.println((i + 1) + ": " + appointments[i]);

                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle the IOException appropriately
        }
    }

    public void viewApproved() {
        viewApprovedAppointments();
    }


    //displays pending appointments
    public int viewPending() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("pending.txt"));
        String line = reader.readLine();
        int num = 1;
        if (line == null) {
            JOptionPane.showMessageDialog(null, "You have no pending appointments.", "Pending Appointments", JOptionPane.ERROR_MESSAGE);
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

    // TODO: Implement storage of doctor names as well
    public void approveAppointment(int line) throws IOException {
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
        }
    }

    public void declineAppointment(int line) throws IOException {
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
        }
    }


    public void rescheduleAppointment(Scanner scan) throws IOException {


        BufferedReader reader1 = new BufferedReader(new FileReader("approved.txt"));


        int currentLine = 1;
        boolean found1 = false;
        String line;
        ArrayList<String> lines = new ArrayList<>();
        String[] lineSplit;


        System.out.println("Enter patient name: ");
        String checkName = scan.nextLine();
        int numOptions = 0;


        while ((line = reader1.readLine()) != null) { // read file and print appts
            lines.add(line);
            lineSplit = line.split(",");
            if (lineSplit[0].equals(checkName)) {
                System.out.println(currentLine + ": " + line);
                currentLine++;
                found1 = true;
                numOptions++;
            }
        }


        if (!found1) {
            JOptionPane.showMessageDialog(null, "Patient has no approved appointments at this time.", "Reschedule Appointments", JOptionPane.ERROR_MESSAGE);
        } else {
            boolean invalidInput = false;
            int userIndex = 0;
            do {
                System.out.println("Which appointment would you like to change?");
                try {
                    int input1 = Integer.parseInt(scan.nextLine());
                    userIndex = (input1) - 1;


                    if (input1 > numOptions || input1 <= 0) {
                        System.out.println("Invalid input. Choose a given number.");
                        invalidInput = true;


                    } else {
                        invalidInput = false;
                        break;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Please enter an integer.");
                    invalidInput = true;


                }


            } while (invalidInput);


            boolean timeIsBooked = false;
            int newDay = 0;
            String newTime = "";
            int newTimeInt = 0;


            do { // loop through day choice until an available day is chosen


                do {
                    System.out.println("What day would you like to change it to?");
                    try {
                        String input2 = scan.nextLine();
                        newDay = Integer.parseInt(input2);
                        if (newDay > 31 || newDay <= 0) {
                            invalidInput = true;
                            System.out.println("Day choice cannot be greater than 31 or less than 1");
                        } else {
                            invalidInput = false;
                            break;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Please enter an integer.");
                        invalidInput = true;
                    }
                } while (invalidInput);


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


                // check if given time is already taken
                line = lines.get(userIndex);
                lineSplit = line.split(",");
                // get this line, turn into a list, switch


                String doctorName = lineSplit[3];


                timeIsBooked = false;


                for (String thisLine : lines) {
                    lineSplit = thisLine.split(",");
                    if (lineSplit[3].equals(doctorName)) {
                        if (lineSplit[1].equals(Integer.toString(newDay))) {
                            if (lineSplit[2].equals(newTime)) {
                                System.out.println("Time unavailable. Try again.");
                                timeIsBooked = true;
                            }
                        }
                    }
                }


            } while (timeIsBooked);


            if (!timeIsBooked) {
                lineSplit[2] = newTime;
                lineSplit[1] = Integer.toString(newDay);
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


        }


        reader1.close();


    }

}
