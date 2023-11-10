\import java.util.ArrayList;
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

    public void go(Scanner scan) {
        boolean menu2 = false;
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
                    
                    
                    Day selectedDay = cal.getIndividualDay(date);
                    
                    
                    System.out.println(selectedDay.showDoctorList() + "\n"); //display doctor list
                    
                    while (selectedDay.getDoctors().size() == 0) {
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
            			doc.getAppointments().get(appt - 1).bookAppointment(scan.nextLine());
            			System.out.println("\nAppointment Booked!");
            		}
                    Time time = doc.getAppointments().get(appt).getTime();
                    makeAppointment(date, doc, time);
                    break;
                case 2:
                    //display approved appointments arraylist with numbers
                    System.out.println("Choose an appointment:");
                    int cancel = scan.nextInt();
                    scan.nextLine();
                    cancelAppointment(cancel);
                    break;
                case 3:
                    //display approved appointments
                    viewAppointment();
                    break;
                default:
                    System.out.println("Please enter a valid choice.");
                    menu2 = true;
                    break;
            }
        } while (menu2);
    }

    public static void makeAppointment(int date, Doctor doctor, Time time) {

    }

    public static void cancelAppointment(int cancel) {

    }

    public static void viewAppointment() {

    }
}
