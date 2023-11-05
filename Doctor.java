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

    @Override
    public String toString() {
        return name;
    }
}
