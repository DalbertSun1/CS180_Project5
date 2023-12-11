import java.io.*;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import javax.swing.JOptionPane;

/**
 * Project 5
 * Dentist Office Calendar Marketplace
 *
 * @author Dalbert Sun, Vihaan Chadha, Jack White, Himaja Narajala, Aaryan Bondre
 * @version December 11th, 2023
 */

public class Patient extends Login {
    private String name; // Customer's name
    private ArrayList<Appointment> appointments; // List of customer's appointments

    public Patient(String name) {
        this.name = name;
        this.appointments = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }

    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
    }

    public void removeAppointment(Appointment appointment) {
        appointments.remove(appointment);
    }

    public String viewAppointments() {
        StringBuilder appointmentInfo = new StringBuilder("Appointments for " + name + ":\n");
        for (Appointment appointment : appointments) {
            appointmentInfo.append(appointment.toString()).append("\n");
        }
        return appointmentInfo.toString();
    }

    @Override
    public String toString() {
        return name;
    }

    public void go(ArrayList<Doctor> doctors, DentistOffice d, DentistClient client) throws IOException {
        boolean menu2 = false;
        boolean menu3 = false;
        int sum = 1;
        MyCalendar cal = new MyCalendar(31);
        do {
            String[] patientMenu = {"Make a new appointment", "Cancel an appointment",
                    "View approved appointments", "Reschedule an appointment", "View Statistics", "Log Out"};
            String patientOption;
            patientOption = (String) JOptionPane.showInputDialog(null, "Choose an option.",
                    "Menu", JOptionPane.QUESTION_MESSAGE, null, patientMenu,
                    patientMenu[0]);
            if ((patientOption == null) || (patientOption.isEmpty())) {
                //JOptionPane.showMessageDialog(null, "Thank you for using Dentist Office!");
                super.start(client);
                return;
            }

            try {


                switch (patientOption) {
                    case "Make a new appointment":
                        boolean invalidInput = false;
                        int date = 0;
                        cal.viewCalendar(client);
                        while(cal.getResultComplete() == false) {
                            continue;
                        }
                        clientPending(cal.getResult(), client);
                        break;
                    case "Cancel an appointment":
                        do {
                            String[] a = clientReadFile(client); //display approved appointments
                            ArrayList<String> apptList = new ArrayList<String>();

                            if (a == null) {

                                //JOptionPane.showMessageDialog(null, "You have no approved appointments to cancel.", "Cancel an appointment",
                                //        JOptionPane.ERROR_MESSAGE);
                                menu2 = true;

                            } else {
                                for (int i = 0; i < a.length; i++) {
                                    if (a[i] != null) {
                                        apptList.add(a[i]);
                                    }
                                }
                                String[] b = new String[apptList.size()];
                                for (int i = 0; i < apptList.size(); i++) {
                                    b[i] = apptList.get(i);
                                }

                                if (b.length == 0) {
                                    menu2 = true;

                                } else {
                                    String cancelOption = (String) JOptionPane.showInputDialog(null, "Choose an appointment to cancel.",
                                            "Cancel an appointment", JOptionPane.QUESTION_MESSAGE, null, b,
                                            b[0]);
                                    if ((cancelOption == null) || (cancelOption.isEmpty())) {
                                        JOptionPane.showMessageDialog(null, "Back to menu:", "Error", JOptionPane.ERROR_MESSAGE);
                                        break;
                                    }

                                    try {
                                        //checking for valid choice
                                        int counter = 1;
                                        for (int i = 0; i < a.length; i++) {
                                            if (a[i] != null) {
                                                if (a[i].equals(cancelOption)) {
                                                    counter = 0;
                                                }
                                            }
                                        }
                                        if (counter == 0) {
                                            //TODO : add gui asking for name
                                            clientCancelAppointment(cancelOption, client);
                                            JOptionPane.showMessageDialog(null, "Appointment canceled.", "Cancel an appointment",
                                                    JOptionPane.INFORMATION_MESSAGE);

                                        } else {
                                            JOptionPane.showMessageDialog(null, "Please select a valid option!", "Cancel an appointment",
                                                    JOptionPane.ERROR_MESSAGE);
                                            menu3 = true;
                                            break;
                                        }

                                    } catch (NumberFormatException e) {
                                        JOptionPane.showMessageDialog(null, "Please enter an integer.", "Cancel an appointment",
                                                JOptionPane.ERROR_MESSAGE);
                                        menu3 = true;
                                    }
                                }
                            }
                        } while (menu3);
                        menu2 = true;
                        break;
                    case "View approved appointments":
                        clientReadFile(client);

                        menu2 = true;
                        break;
                    case "Reschedule an appointment":
                        if (clientRescheduleAppointment(client)) {
                            JOptionPane.showMessageDialog(null, "Rescheduled successfully.");
                        }
                        else {
                            JOptionPane.showMessageDialog(null, "Could not reschedule appointment.", "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                        menu2 = true;
                        break;
                    case "View Statistics":
                        clientFileStats(d, client);
                        break;
                    case "Log Out":
                        JOptionPane.showMessageDialog(null, "You have logged out.");
                        Login l = new Login();

                        menu(client);
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Please select a valid option!", "Menu",
                                JOptionPane.ERROR_MESSAGE);
                        menu2 = true;
                        break;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter a valid input.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                menu2 = true;
            }

        } while (menu2);
    }



    public synchronized static String makeAppointment(String name, int date, Doctor doctor, Appointment appointment) {
        try {
            File f = new File("pending.txt"); //creates pending appointments file
            FileOutputStream fos = new FileOutputStream(f, true);
            PrintWriter pw = new PrintWriter(fos);
            pw.println(name + "," + date + "," + appointment.getTime() + "," + doctor.getName());
            pw.close();
            return "true";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "false";
    }


    public static boolean cancelAppointment(String cancelOption) {
        try {
            ArrayList<String> list1 = new ArrayList<String>();
            BufferedReader bfr = new BufferedReader(new FileReader("approved.txt"));
            String line = bfr.readLine();
            while (line != null) {
                if (!(line.equals(cancelOption))) {
                    list1.add(line);
                }
                line = bfr.readLine();
            }
            bfr.close();

            File f = new File("approved.txt");
            FileOutputStream fos = new FileOutputStream(f);
            PrintWriter pw = new PrintWriter(fos);
            for (int i = 0; i < list1.size(); i++) {
                pw.println(list1.get(i));
            }
            pw.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void serverReadFile(String name, DentistServer server) { // reads file and returns printList to client
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
            //System.out.println("Approved appointments:");
            //displays the approved appointments for that person
            for (int i = 0; i < list2.size(); i++) {
                if (name.equals(names[i])) {
                    aptList.add(list2.get(i));
                }
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

    public static void serverReadFile(DentistServer server) { // ONLY USED FOR STATISTICS
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
    public static String[] clientReadFile(DentistClient client) throws IOException { // returns a list of the apts corresponding to
        // the Patient's name

        String name = JOptionPane.showInputDialog(null, "Enter your name",
                "Appointments", JOptionPane.QUESTION_MESSAGE);
        client.println("readFile::" + name);

        String[] approvedList = new String[0];

        if (name == null) {
            approvedList = null;
        } else {

            ArrayList<String> aptList = new ArrayList<>();

            String input = client.readLine();


            if (!input.isEmpty()) {
                for (String apt : input.split(";")) {
                    aptList.add(apt);
                }

                approvedList = new String[aptList.size()];
                for (int i = 0; i < aptList.size(); i++) {
                    approvedList[i] = aptList.get(i);
                }

                JOptionPane.showMessageDialog(null, approvedList, "Approved appointments",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "You have no approved appointments at this time.", "Approved appointments",
                        JOptionPane.ERROR_MESSAGE);
                ArrayList<Doctor> doctors = new ArrayList<>();
                DentistOffice d = new DentistOffice(name);
                Patient p = new Patient(name);
                p.go(doctors, d, client);
            }
        }
        return approvedList;

    }

    public static String[] clientFileStats(DentistOffice d, Scanner scan, DentistClient client) { // returns a list of the apts approved


        client.println("readStats::");


        ArrayList<String> aptList = new ArrayList<>();

        String input = client.readLine();
        String[] approvedList = new String[0];

        if (!input.isEmpty()) {
            for (String apt : input.split(";")) {
                aptList.add(apt);
            }

            approvedList = new String[aptList.size()];
            for (int i = 0; i < aptList.size(); i++) {
                approvedList[i] = aptList.get(i);
            }

            OurStatistics.patientDashboard(approvedList, d, scan, client);


        } else {
            JOptionPane.showMessageDialog(null, "There are no approved appointments at this time.", "Approved appointments",
                    JOptionPane.ERROR_MESSAGE);

        }

        return approvedList;

    }


    public static boolean clientRescheduleAppointment(DentistClient client) throws IOException {
        String name = JOptionPane.showInputDialog(null, "Enter your name:",
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
                                return false;
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
                            return false;
                        }

                        String line = rescheduleOption;
                        int userIndex = Integer.parseInt(line.substring(0, line.indexOf(":")));
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
    public static boolean serverRescheduleAppointment(String patientName, String day, String time, String doctorName, int userIndex) throws IOException {
        ArrayList<String> lines = new ArrayList<String>(Arrays.asList(DentistOffice.serverGetAppointments()));
        String[] lineSplit = new String[4];


        int fileCounter = 0;
        int nameCounter = 0;
        int fileIndex = 0;
        for (String thisLine : lines) {
            lineSplit = thisLine.split(",");

            if (lineSplit[0].equals(patientName)) {
                nameCounter++;
                if (nameCounter == userIndex) {
                    fileIndex = fileCounter;
                }
            }

            if (lineSplit[3].equals(doctorName)) {
                if (lineSplit[1].equals(day)) {
                    if (lineSplit[2].equals(time)) {
                        return false;
                    }
                }
            }
            fileCounter++;
        }


        String newApt = patientName + "," + day + "," + time + "," + doctorName;
        lines.set(fileIndex, newApt);

        BufferedWriter writer1 = new BufferedWriter(new FileWriter("approved.txt"));
        for (String thisLine : lines) {
            writer1.write(thisLine + "\n");
        }
        writer1.close();
        return true;
    }

    private ArrayList<String> printAppointments(Day day, Doctor doctor, DentistClient client) throws IOException {
        ArrayList<String> isBookedAppointmentList = new ArrayList<>();
        ArrayList<String> returnList = new ArrayList<>();
        ArrayList<Integer> dayList = new ArrayList<>();
        ArrayList<String> timeList = new ArrayList<>();
        ArrayList<String> doctorList = new ArrayList<>();
        ArrayList<String> fullList = new ArrayList<>();

        // adds all approved apts to the fullList
        for (String apt : DentistOffice.clientGetAppointments(client)) {
            if (!apt.isEmpty()) {
                fullList.add(apt);
            }
        }
        // adds all pending apts to the fullList
        for (String apt : DentistOffice.clientGetPendingAppointments(client)) {
            if (!apt.isEmpty()) {
                fullList.add(apt);
            }
        }

        for (int i = 0; i < fullList.size(); i++) {
            String[] split = fullList.get(i).split(",");
            dayList.add(Integer.parseInt(split[1]));
            timeList.add(split[2]);
            doctorList.add(split[3]);
        }

        for (int i = 0; i < dayList.size(); i++) {
            if (day.getDate() == dayList.get(i) && doctor.getName().equals(doctorList.get(i))) {
                isBookedAppointmentList.add(timeList.get(i));
            }
        }

        for (int i = 0; i < 9; i++) {
            String printAppointment;
            if (i <= 1) {
                printAppointment = (i + 9) + ":00 AM" + " - " + (i + 10) + ":00 AM";
            } else if (i == 2) {
                printAppointment = (i + 9) + ":00 AM" + " - " + (i + 10) + ":00 PM";
            } else if (i == 3) {
                printAppointment = (i + 9) + ":00 PM" + " - " + (i - 2) + ":00 PM";
            } else {
                printAppointment = (i - 3) + ":00 PM" + " - " + (i - 2) + ":00 PM";
            }

            returnList.add(printAppointment);

        }
        for (int j = 0; j < isBookedAppointmentList.size(); j++) {
            returnList.remove(isBookedAppointmentList.get(j));
        }


        return returnList;
    }

    public static boolean clientPending(String name, int date, Doctor doctor, Appointment appointment, DentistClient client) {
        // send to server
        client.println("makeAppointment::" + name + "," + date + "," + doctor.getName() + "," + appointment.getTime());

        if (client.readLine().equals("true")) {
            return true;
        } else {
            return false;
        }
    }
    public static boolean clientPending(String string, DentistClient client) {
        // send to server
        client.println("makeAppointment::" + string);

        if (client.readLine().equals("true")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean clientCancelAppointment(String choice, DentistClient client) {

        client.println("cancelAppointment::" + choice);

        if (client.readLine().equals("true")) {
            return true;
        } else {
            return false;
        }
    }


}
