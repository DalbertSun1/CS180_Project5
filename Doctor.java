import java.util.ArrayList;

public class Doctor {
    private ArrayList<Appointment> appointments ;

    public Doctor(ArrayList<Appointment> appointments) {
        this.appointments = appointments;
    }
    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(ArrayList<Appointment> appointments) {
        this.appointments = appointments;
    }

}