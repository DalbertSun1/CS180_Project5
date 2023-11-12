import java.io.*;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Scanner;

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

    public void go(Scanner scan, ArrayList<Doctor> doctors) throws IOException {
        boolean menu2 = false;
        boolean menu3 = false;
        MyCalendar cal = new MyCalendar(31);
        do {
            System.out.println("1. Make a new appointment\n2. Cancel an appointment\n3. View approved appointments\n4. Reschedule an appointment\n5. Log out");
            int choice = scan.nextInt();
            scan.nextLine();
            switch (choice) {
                case 1: //create appointment
                    System.out.println(cal.viewCalendar()); // display calendar
                    System.out.println("Select a day to view available doctors:");
                    int date = scan.nextInt();
                    scan.nextLine();


                    Day selectedDay = new Day(cal.getIndividualDay(date).getDate());
                    selectedDay.setDoctors(doctors);

                    System.out.println(selectedDay.showDoctorList() + "\n"); //display doctor list

                    while (selectedDay.getDoctors().isEmpty()) {
                        System.out.println(cal.viewCalendar());
                        System.out.println("Select another day to view available doctors:");
                        date = scan.nextInt();
                        scan.nextLine();
                        selectedDay = cal.getIndividualDay(date);


                        System.out.println(selectedDay.showDoctorList() + "\n");

                    }
                    System.out.println("Choose a doctor to view available appointments:");


                    int doctor = scan.nextInt();
                    scan.nextLine();

                    Doctor doc = selectedDay.getIndividualDoctor(doctor - 1);
                    System.out.println("\nDr. " + doc.getName());
                    for (int i = 0; i < doc.getAppointments().size(); i++) { // display available time slots
                        System.out.println("[" + (i + 1) + "] " + doc.getAppointments().get(i).toString());
                    }

                    System.out.println("Select a time:");
                    int appt = scan.nextInt();
                    scan.nextLine();

                    if (doc.getAppointments().get(appt - 1).isBooked()) {
                        System.out.println("Sorry! That's already booked");

                    } else {
                        System.out.println("Enter your name");
                        this.name = scan.nextLine();
                        doc.getAppointments().get(appt - 1).bookAppointment(name);
                        System.out.println("\nAppointment booked!");
                    }
                    Appointment appointment = doc.getAppointments().get(appt - 1);
                    makeAppointment(date, doc, appointment);
                    menu2 = true;
                    break;
                case 2: //cancel appointment
                    do {
                        String[] a = readFile(); //display approved appointments
                        if (a.length == 0) {
                            System.out.println("You have no approved appointments to cancel.");
                        } else {
                            System.out.println("Choose an appointment to cancel:");
                            int cancel = scan.nextInt();
                            scan.nextLine();
                            //checking for valid choice
                            int counter = 1;
                            for (int i = 1; i <= a.length; i++) {
                                if (cancel == i) {
                                    counter = 0;
                                }
                            }
                            if (counter == 0) {
                                cancelAppointment(cancel);
                            } else {
                                System.out.println("Please enter a valid choice.");
                                menu3 = true;
                            }
                        }
                    } while (menu3);
                    menu2 = true;
                    break;
                case 3: //view approved appointments
                    System.out.println("Enter your name: ");
                    String checkName = scan.nextLine();
                    BufferedReader reader = new BufferedReader(new FileReader("approved.txt"));
                    String line;
                    int num = 1;
                    boolean found = false;

                    while((line = reader.readLine()) != null) {
                        String[] confirmName = line.split(",");
                        if (confirmName[0].equals(checkName)) {
                            System.out.println(num + ": " + line);
                            num++;
                            found = true;
                        }
                    }

                    if (!found) {
                        System.out.println("You have no approved appointments at this time.");
                    }

                    reader.close();
                    menu2 = true;
                    break;
                case 4:
                    rescheduleAppointment(scan);
                    menu2 = true;
                    break;
                case 5: //log out
                    System.out.println("You have logged out.");
                    Login l = new Login();
                    l.menu(scan);
                    break;
                default:
                    System.out.println("Please enter a valid choice.");
                    menu2 = true;
                    break;
            }
        } while (menu2);
    }

    public void makeAppointment(int date, Doctor doctor, Appointment appointment) {
        try {
            File f = new File("pending.txt"); //creates pending appointments file
            FileOutputStream fos = new FileOutputStream(f, true);
            PrintWriter pw = new PrintWriter(fos);
            pw.println(name + "," + date + "," + appointment.getTime() + "," + doctor.getName());
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cancelAppointment(int cancel) {
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

        } catch (Exception e) {
            e.printStackTrace();
        }
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
            int userIndex = scan.nextInt() - 1;
            scan.nextLine();
            boolean timeIsBooked = false;
            do {
                System.out.println("What day would you like to change it to?");
                int newDay = scan.nextInt();
                String newTime = "";
                int newTimeInt;
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
                    newTimeInt = scan.nextInt();
                    scan.nextLine();
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


            } while (timeIsBooked);
        }


        reader1.close();

    }


    public String[] readFile() {
        try {
            ArrayList<String> list2 = new ArrayList<String>(); // stores each line of the file, only for printing purposes

            BufferedReader bfr = new BufferedReader(new FileReader("approved.txt"));
            String line = bfr.readLine();
            // creates array to store each approved appointment separately
            String[] commas = new String[4];

            while (line != null) {
                list2.add(line);
                commas = line.split(",", 4);
                line = bfr.readLine();
            }
            bfr.close();

            //displays the approved appointments
            String[] printList = new String[list2.size()];
            System.out.println("Approved appointments:");
            for (int i = 0; i < printList.length; i++) {
                printList[i] = list2.get(i);
                System.out.println((i + 1) + ". " + printList[i]);
            }

            return printList;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
