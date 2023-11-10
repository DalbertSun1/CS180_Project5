import java.util.ArrayList;
import java.util.Arrays;

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

    public String delete(Doctor doctor) {
        if (doctorList.contains(doctor)) {
            doctorList.remove(doctor);
            return "Successfully removed Doctor " + doctor.getName() + " from " + this.name;
        } else {
            return "Doctor " + doctor.getName() + " is not in " + this.name;
        }
    }

    public String getStatistics() {
        StringBuilder statistics = new StringBuilder();
        for (Doctor doctor : doctorList) {
            statistics.append(Arrays.toString(doctor.getStatistics())).append("\n");
        }
        return statistics.toString();
    }
}
