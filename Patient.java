import java.util.ArrayList;

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

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        boolean menu2 = false;
        do {
            System.out.println("1. Make a new appointment\n2. Cancel an appointment\n3. View approved appointments");
            int choice = scan.nextInt();
            scan.nextLine();
            switch (choice) {
                case 1:
                    // display calendar - to do
                    System.out.println("Enter the date:");
                    int date = scan.nextInt();
                    scan.nextLine();
                    //display doctor list - to do
                    System.out.println("Choose a doctor:");
                    int doctor = scan.nextInt();
                    scan.nextLine();
                    // display available time slots - to do
                    System.out.println("Enter a time:");
                    int time = scan.nextInt();
                    scan.nextLine();
                    makeAppointment(date, doctor, time);
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

    public static void makeAppointment(int date, int doctor, int time) {

    }

    public static void cancelAppointment(int cancel) {

    }

    public static void viewAppointment() {

    }
}
