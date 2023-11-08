import java.util.ArrayList;

public class Doctor {
    private String name; // Doctor's name
    private ArrayList<Appointment> appointments; // List of appointments for the doctor

    public Doctor(String name) {
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

    public boolean isAvailable(Time time) {
        // Check if the doctor has an available appointment slot at the specified time
        for (Appointment appointment : appointments) {
            if (appointment.getTime().equals(time) && !appointment.isBooked()) {
                return true;
            }
        }
        return false;
    }

    public Appointment findAppointment(String customerName) { // returns the appointment of a given customer
        // assumes there is only one appointment for each customer name
        // returns null if there is no such appoinment with given customer name
        for (Appointment appointment : appointments) {
            if (customerName.equalsIgnoreCase(appointment.getCustomerName())) {
                return appointment;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return name;
    }
}
