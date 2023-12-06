


import java.io.*;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Project 4
 * Dentist Office Calendar Marketplace
 *
 * @author Dalbert Sun, Vihaan Chadha, Jack White, Himaja Narajala, Aaryan Bondre
 * @version November 13th, 2023
 */

public class Patient {
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

    public synchronized void go(Scanner scan, ArrayList<Doctor> doctors, DentistOffice d, DentistClient client) throws IOException {
        boolean menu2 = false;
        boolean menu3 = false;
        int sum = 1;
        MyCalendar cal = new MyCalendar(31);
        do {
            System.out.println("1. Make a new appointment\n2. Cancel an appointment\n3. View approved appointments\n4. Reschedule an appointment\n" +
                    "5. View Statistics\n6. Log out");

            try {
                String input1 = scan.nextLine();
                int choice = Integer.parseInt(input1);

                switch (choice) {
                    case 1:
                        boolean invalidInput = false;
                        int date = 0;
                        do {

                            System.out.println(cal.viewCalendar()); // display calendar
                            System.out.println("Select a day to view available doctors:");
                            try {
                                String input2 = scan.nextLine();
                                date = Integer.parseInt(input2);
                                if (date <= 0 || date > 31) {
                                    invalidInput = true;
                                    System.out.println("Choose a day between 0 and 31");
                                } else {
                                    invalidInput = false;
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Please enter an integer.");
                                invalidInput = true;
                            }
                        } while (invalidInput);

                        Day selectedDay = new Day(cal.getIndividualDay(date).getDate());
                        selectedDay.setDoctors(doctors);

                        System.out.println(selectedDay.showDoctorList() + "\n"); //display doctor list

                        while (selectedDay.getDoctors().isEmpty()) {
                            System.out.println(cal.viewCalendar());
                            System.out.println("Select another day to view available doctors:");
                            try {
                                String input3 = scan.nextLine();
                                date = Integer.parseInt(input3);

                                selectedDay = cal.getIndividualDay(date);


                                System.out.println(selectedDay.showDoctorList() + "\n");
                            } catch (NumberFormatException e) {
                                System.out.println("Please enter an integer.");
                            }

                        }

                        System.out.println("Choose a doctor to view available appointments:");
                        try {
                            String input4 = scan.nextLine();
                            int doctor = Integer.parseInt(input4);


                            Doctor doc = selectedDay.getIndividualDoctor(doctor - 1);
                            System.out.println("\nDr. " + doc.getName());


                            ArrayList<String> show = printAppointments(selectedDay, doc);
                            for (int i = 0; i < show.size(); i++) {
                                System.out.println(sum + ": " + show.get(i));
                                sum++;
                            }
                            sum = 1;

                            System.out.println("Select a time:");
                            try {
                                String input6 = scan.nextLine();
                                int appt = Integer.parseInt(input6);

                                String chosenTime = show.get(appt - 1);


                                System.out.println("Enter your name:");
                                this.name = scan.nextLine();
                                Appointment appointment = new Appointment(chosenTime);
                                appointment.bookAppointment(name);
                                if (clientPending(name, date, doc, appointment, client)) {  // send info to server
                                    System.out.println("\nAppointment booked!");
                                } else {
                                    System.out.println("Error during appointment booking");
                                }
                                menu2 = true;
                            } catch (NumberFormatException e) {
                                System.out.println("Please enter an integer.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Please enter an integer.");
                        }

                        break;
                    case 2:
                        do {
                            String[] a = clientReadFile(scan, client); //display approved appointments
                            if (a.length == 0) {
                                System.out.println("You have no approved appointments to cancel.");
                            } else {
                                System.out.println("Choose an appointment to cancel:");
                                try {
                                    String input5 = scan.nextLine();
                                    int cancel = Integer.parseInt(input5);

                                    //checking for valid choice
                                    int counter = 1;
                                    for (int i = 1; i <= a.length; i++) {
                                        if (cancel == i) {
                                            counter = 0;
                                        }
                                    }
                                    if (counter == 0) {
                                        if (clientCancelAppointment(cancel, client)) {
                                            System.out.println("Appointment cancelled.");
                                        }
                                    } else {
                                        System.out.println("Please enter a valid choice.");
                                        menu3 = true;
                                    }
                                } catch (NumberFormatException e) {
                                    System.out.println("Please enter an integer.");
                                }
                            }
                        } while (menu3);
                        menu2 = true;
                        break;
                    case 3:
                        clientReadFile(scan, client);
                        menu2 = true;
                        break;
                    case 4:
                        rescheduleAppointment(scan);
                        menu2 = true;
                        break;
                    case 5:
                        OurStatistics.patientDashboard(d, scan);
                        break;
                    case 6:
                        System.out.println("You have logged out.");
                        Login l = new Login();
                        l.menu(scan, client);
                        break;
                    default:
                        System.out.println("Please enter a valid choice.");
                        menu2 = true;
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter an integer.");
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

    public static boolean cancelAppointment(int cancel) {
        try {
            ArrayList<String> list1 = new ArrayList<String>();
            BufferedReader bfr = new BufferedReader(new FileReader("approved.txt"));
            String line = bfr.readLine();
            int counter = 1;
            while (line != null) {
                if (counter != cancel) {
                    list1.add(line);
                }
                line = bfr.readLine();
                counter++;
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
        }
        return false;
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

            String[] aptList = new String[list2.size()];

            System.out.println("Approved appointments:");
            //displays the approved appointments for that person
            for (int i = 0; i < aptList.length; i++) {
                if (name.equals(names[i])) {
                    aptList[i] = list2.get(i);
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
    public String[] clientReadFile(Scanner scan, DentistClient client) { // returns a list of the apts corresponding to
        // the Patient's name

        System.out.println("Enter your name:");
        String name = scan.nextLine();
        client.println("readFile::" + name);

        ArrayList<String> aptList = new ArrayList<>();

        String input = client.readLine();

        for (String apt : input.split(";")) {
            aptList.add(apt);
        }

        System.out.println("Approved appointments:");
        //displays the approved appointments for that person
        for (String apt : aptList) {
            System.out.println(apt);
        }

        return aptList.toArray(new String[0]);


    }



    public void rescheduleAppointment(Scanner scan) throws IOException {
        BufferedReader reader1 = new BufferedReader(new FileReader("approved.txt"));

        int currentLine = 1;
        boolean found1 = false;
        String line;
        ArrayList<String> lines = new ArrayList<>();
        String[] lineSplit;

        System.out.println("Enter your name: ");
        String checkName = scan.nextLine();

        System.out.println("Choice #, Patient Name, Day of Month, Time, Doctor Name");
        while ((line = reader1.readLine()) != null) {
            lines.add(line);
            lineSplit = line.split(",");
            if (lineSplit[0].equals(checkName)) {
                System.out.println(currentLine + ": " + line);
                currentLine++;
                found1 = true;
            }
        }

        if (!found1) {
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


                        // check if given time is already taken
                        line = lines.get(userIndex);
                        lineSplit = line.split(",");
                        // get this line, turn into a list, switch

                        String doctorName = lineSplit[3];


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
                    } catch (NumberFormatException e) {
                        System.out.println("Please enter an integer.");
                    }
                } while (timeIsBooked);
            } catch (NumberFormatException e) {
                System.out.println("Please enter an integer.");
            }
        }


        reader1.close();

    }

    private ArrayList<String> printAppointments(Day day, Doctor doctor) throws IOException {
        ArrayList<String> isBookedAppointmentList = new ArrayList<>();
        ArrayList<String> returnList = new ArrayList<>();
        ArrayList<Integer> dayList = new ArrayList<>();
        ArrayList<String> timeList = new ArrayList<>();
        ArrayList<String> doctorList = new ArrayList<>();
        ArrayList<String> fullList = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new FileReader("pending.txt"));
        String line1;
        while ((line1 = reader.readLine()) != null) {
            fullList.add(line1);
        }

        reader = new BufferedReader(new FileReader("approved.txt"));
        String line2;
        while ((line2 = reader.readLine()) != null) {
            fullList.add(line2);
        }

        for (int i = 0; i < fullList.size(); i++) {
            String[] split = fullList.get(i).split(",");
            dayList.add(Integer.parseInt(split[1]));
            timeList.add(split[2]);
            doctorList.add(split[3]);
        }

        for (int i = 0; i < dayList.size(); i++) {
            if (day.getDate() == dayList.get(i) && doctor.getName().equals(doctorList.get(i))) {
                System.out.println("This time must not be shown: " + timeList.get(i));
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

        reader.close();
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

    public static boolean clientCancelAppointment(int choice, DentistClient client) {
        client.println("cancelAppointment::" + choice);

        if (client.readLine().equals("true")) {
            return true;
        } else {
            return false;
        }
    }
}
