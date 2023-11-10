<<<<<<< HEAD
/**
 *  A DentistOffice is our equivalent of a seller. It holds Doctors, each of which has a room
 *  contains the following methods: Add, Delete, getStatistics, 
 * 
 */
 import java.util.ArrayList;
 import java.util.HashMap;
=======
import java.util.ArrayList;
import java.util.Arrays;

>>>>>>> 5d84cdb0540533b9edd726ebc9a356d1ed52c963
public class DentistOffice {
    private String name;
    private ArrayList<Doctor> doctorList = new ArrayList<>();

    public DentistOffice(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public String add(Doctor doctor) {
        if (!doctorList.contains(doctor)) {
            doctorList.add(doctor);
            return "Successfully added Doctor " + doctor.getName() + " to " + this.name;
        } else {
            return "Doctor " + doctor.getName() + " is already in " + this.name;
        }
    }
<<<<<<< HEAD

    public String getCustomerStatistics() {
        StringBuilder statistics = new StringBuilder();
        for (Doctor doctor : DoctorList) {
            HashMap<String, Integer> customerData = doctor.getStatistics()[0]; // a hashmap with key = customerName, value = # of appointments per customer
            // iterate over keys in customerData
            for (String customerName : customerData.keySet()) {
                statistics += customerName + " : " + customerData.get(customerName);
            }

        }
        return statistics.toString();
    }
    public String getTimeStatistics() {
        StringBuilder statistics = new StringBuilder();
        for (Doctor doctor : DoctorList) {
            HashMap<Time, Integer> timeData = doctor.getStatistics()[1]; // a hashmap with key = Time, value = # of appointments at this time
            // iterate over keys in customerData
            for (Time time : timeData.keySet()) {
                statistics += time.getTimeslot() + " : " + timeData.get(time);
            }
        }

        return statistics.toString();
    }
    
=======
>>>>>>> 5d84cdb0540533b9edd726ebc9a356d1ed52c963

    public String delete(Doctor doctor) {
        if (doctorList.contains(doctor)) {
            doctorList.remove(doctor);
            return "Successfully removed Doctor " + doctor.getName() + " from " + this.name;
        } else {
            return "Doctor " + doctor.getName() + " is not in " + this.name;
        }
    }


}
