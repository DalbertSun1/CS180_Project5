// TODO: Ask a user for their name before cancelling an appointment and only display cancellable appointments under that name
// TODO: Allow cancelling on pending appointments too
// TODO: Add error handling in update appointment (if the user types in an integer that does not correspond to an updatable appointment, print an error message and loop again)
//       Look at numPending method in Login.java for guidance
//       Must be done in Patient and DentistOffice
// TODO: Add error handling for choosing a date (if integer inputted is greater than 31, print error message and loop again


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

    public void go(Scanner scan, ArrayList<Doctor> doctors, DentistOffice d) throws IOException {
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
                        System.out.println(cal.viewCalendar()); // display calendar
                        System.out.println("Select a day to view available doctors:");
                        try {
                            String input2 = scan.nextLine();
                            int date = Integer.parseInt(input2);


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
                                    System.out.println("PLease enter an integer.");
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

                                System.out.println("Select a time:");
                                try {
                                    String input6 = scan.nextLine();
                                    int appt = Integer.parseInt(input6);

                                    String chosenTime = show.get(appt - 1);


                                    System.out.println("Enter your name:");
                                    this.name = scan.nextLine();
                                    Appointment appointment = new Appointment(chosenTime);
                                    appointment.bookAppointment(name);
                                    System.out.println("\nAppointment Booked!");

                                    makeAppointment(date, doc, appointment);
                                    menu2 = true;
                                } catch (NumberFormatException e) {
                                    System.out.println("Please enter an integer.");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Please enter an integer.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Please enter an integer.");
                        }
                        break;
                    case 2:
                        do {
                            String[] a = readFile(); //display approved appointments
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
                                        cancelAppointment(cancel, a);
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
                    case 5:
                        OurStatistics.patientDashboard(d, scan);
                        break;
                    case 6:
                        System.out.println("You have logged out.");
                        Login l = new Login();
                        l.menu(scan);
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

    public void cancelAppointment(int cancel, String[] list) {
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


    public String[] readFile() {
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

    public void rescheduleAppointment(Scanner scan) throws IOException {



        BufferedReader reader1 = new BufferedReader(new FileReader("approved.txt"));

        int currentLine = 1;
        boolean found1 = false;
        String line;
        ArrayList<String> lines = new ArrayList<>();
        String[] lineSplit;

        System.out.println("Enter your name: ");
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
            System.out.println("You have no approved appointments at this time.");
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
            }
            else if (i == 2) {
                printAppointment = (i + 9) + ":00 AM" + " - " + (i + 10) + ":00 PM";
            }
            else if (i == 3) {
                printAppointment = (i + 9) + ":00 PM" + " - " + (i - 2) + ":00 PM";
            }
            else {
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
}
