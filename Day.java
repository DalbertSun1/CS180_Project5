import java.util.ArrayList;

public class Day {
    private String date; // Date of the day
    private ArrayList<Doctor> doctors; // List of doctors available on the day

    public Day(String date) {
        this.date = date;
        this.doctors = new ArrayList<>();
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<Doctor> getDoctors() {
        return doctors;
    }

    public void addDoctor(Doctor doctor) {
        doctors.add(doctor);
    }

    public void removeDoctor(Doctor doctor) {
        doctors.remove(doctor);
    }

    @Override
    public String toString() {
        return date;
    }
}
