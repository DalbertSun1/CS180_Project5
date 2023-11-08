/**
 *  A DentistOffice is our equivalent of a seller. It holds Doctors, each of which has a room
 *  contains the following methods: Add, Delete, getStatistics, Reschedule
 * 
 */
 import java.util.ArrayList;
public class DentistOffice {
    private String name;
    private ArrayList<Doctor> DoctorList = new ArrayList<>();
    public DentistOffice(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public String Add(Doctor doctor) { // add a Doctor to this office
        // returns a success message, otherwise throws exception
        DoctorList.add(doctor);
        return "Succesfully added Doctor " + doctor.getName() + " to " + this.name;
    }
    public String Delete(Doctor doctor) { // remove a Doctor from this office
        // returns a success message, otherwise throws exception
        DoctorList.remove(doctor);
        return "Succesfully removed Doctor " + doctor.getName() + " from " + this.name;
    }
    public String getStatistics() {
        return "";
    }
    

}
