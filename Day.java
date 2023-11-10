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

    public Doctor getIndividualDoctor(int index) {
    	return doctors.get(index);
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

    @Override
    public String toString() {
        return date;
    }
}
