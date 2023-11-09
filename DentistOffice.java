/**
 *  A DentistOffice is our equivalent of a seller. It holds Doctors, each of which has a room
 *  contains the following methods: Add, Delete, getStatistics, 
 * 
 */
 import java.util.ArrayList;
 import java.util.HashMap;
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
    public String getCustomerStatistics() {
        String output;
        for (Doctor doctor: DoctorList) {
            HashMap<String, Integer> customerData = doctor.getStatistics()[0]; // a hashmap with key = customerName, value = # of appointments per customer
            // iterate over keys in customerData
            for (String customerName : customerData.keySet()) {
                output += customerName + " : " + customerData.get(customerName);
            }

        return output;
    }
    public String getTimeStatistics() {
        String output;
        for (Doctor doctor: DoctorList) {
            HashMap<Time, Integer> timeData = doctor.getStatistics()[1]; // a hashmap with key = Time, value = # of appointments at this time
            // iterate over keys in customerData
            for (Time time : timeData.keySet()) {
                output += time.getTimeslot() + " : " + timeData.get(time);
            }

        return output;
    }
    

}
