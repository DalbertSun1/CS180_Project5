import java.io.*;
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

    public void go(Scanner scan, ArrayList<Doctor> doctors) {
        boolean menu2 = false;
        boolean menu3 = false;
        MyCalendar cal = new MyCalendar(31);
        do {
            System.out.println("1. Make a new appointment\n2. Cancel an appointment\n3. View approved appointments");
            int choice = scan.nextInt();
            scan.nextLine();
            switch (choice) {
                case 1:
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
                        System.out.println("Sorry! thats already booked");

                    } else {
                        System.out.println("Enter your name");
                        this.name = scan.nextLine();
                        doc.getAppointments().get(appt - 1).bookAppointment(name);
                        System.out.println("\nAppointment Booked!");
                    }
                    Appointment appointment = doc.getAppointments().get(appt - 1);
                    makeAppointment(date, doc, appointment);
                    break;
                case 2:
                    do {
                        String[] a = readFile(); //display approved appointments
                        System.out.println("Choose an appointment:");
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
                    } while (menu3);
                    break;
                case 3:
                    readFile(); //displays approved appointments
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
            System.out.println("Appointment made successfully!");
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cancelAppointment(int cancel) {
        try {
            String[] list = readFile();
            ArrayList<String> list1 = new ArrayList<String>();
            BufferedReader bfr = new BufferedReader(new FileReader("approved.txt"));
            String line = bfr.readLine();
            int counter = 1;
            while (line != null) {
                counter++;
                if (counter != cancel) {
                    list1.add(line);
                }
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

    public void rescheduleAppointmentForCustomer(Doctor currentDoctor, Appointment currentAppointment, Doctor newDoctor, Appointment newAppointment) {
        // Cancel the current appointment
        currentAppointment.cancelAppointment();

        // Remove appointment from patient's list
        this.appointments.remove(currentAppointment);

        // Book a new appointment with the new doctor
        newAppointment.bookAppointment(this.name);

        // Add the new appointment to the patient's list
        this.appointments.add(newAppointment);

        // Update schedule for current doctor
        System.out.println("Updated schedule for " + currentDoctor.getName() + ":");
        for (Appointment appointment : currentDoctor.getAppointments()) {
            System.out.println(appointment.toString());
        }

        // Update schedule for new doctor
        System.out.println("Updated schedule for " + newDoctor.getName() + ":");
        for (Appointment appointment : newDoctor.getAppointments()) {
            System.out.println(appointment.toString());
        }
    }

    // TODO: Method to read appointments from approved and pending, assigning each appointment to isBooked
    // Used so appointments that have already been booked don't show up again
    public void readAppointments() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("pending.txt"));

        reader.close();
    }

}
