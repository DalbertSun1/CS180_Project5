import java.util.ArrayList;

/**
 * Project 4
 * Dentist Office Calendar Marketplace
 *
 * @author Dalbert Sun, Vihaan Chadha, Jack White, Himaja Narajala, Aaryan Bondre
 * @version November 13th, 2023
 */

public class Day {
    private int date; // Date of the day
    private ArrayList<Doctor> doctors; // List of doctors available on the day

    public Day(int date) {
        this.date = date;
        this.doctors = new ArrayList<>();
    }

    public int getDate() {
        return date;
    }

    public int getIndividualDoctorIndex(String doctor) {
        int j = -1;
        for (int i = 0; i < doctors.size(); i++) {
            if (doctors.get(i).getName().equals(doctor)) {
                return i;
            }
        }
        return j;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public ArrayList<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(ArrayList<Doctor> doctors) {
        this.doctors = doctors;
    }

    public void addDoctor(Doctor doctor) {
        doctors.add(doctor);
    }

    public void removeDoctor(Doctor doctor) {
        doctors.remove(doctor);
    }

    public Doctor getIndividualDoctor(int index) {
        return doctors.get(index);
    }

    public ArrayList<String> listAppts() {

        ArrayList<String> list = new ArrayList<String>();

        for (int i = 0; i < doctors.size(); i++) {
            for (int j = 0; j < doctors.get(i).getAppointments().size(); j++) {
                String a = doctors.get(i).getAppointments().get(j).getTime();
                String d = "";
                if (doctors.get(i).getName().length() > 9) {
                    d += doctors.get(i).getName().substring(0, 9) + ": " + a.substring(0, a.indexOf('-') - 1);
                } else {
                    String b = doctors.get(i).getName() + ": " + a.substring(0, a.indexOf('-') - 1);
                    if (b.length() < 19) {
                        d += b;
                        for (int k = 0; k < 19 - b.length(); k++) {
                            d += " ";
                        }
                    } else {

                        d += doctors.get(i).getName() + ": " + a.substring(0, a.indexOf('-') - 1);
                    }
                }

                list.add(d);

            }
        }

        return list;
    }

    public String showDoctorList() {
        String result = "";

        if (doctors.size() == 0) {
            return "Sorry! No Doctors Available for this day.";
        }

        for (int i = 0; i < doctors.size(); i++) {
            result += "[" + (i + 1) + "] Dr. " + doctors.get(i).getName() + "\n\n";

        }

        return result;
    }
}
