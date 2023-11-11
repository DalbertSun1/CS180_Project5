import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class DentistOffice {
    private String name;
    private ArrayList<Doctor> doctorList = new ArrayList<>();

    public DentistOffice(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public String addDoctor(Doctor doctor) {
        if (!doctorList.contains(doctor)) {
            doctorList.add(doctor);
            return "Successfully added Doctor " + doctor.getName() + " to " + this.name;
        } else {
            return "Doctor " + doctor.getName() + " is already in " + this.name;
        }
    }

    public String deleteDoctor(Doctor doctor) {
        if (doctorList.contains(doctor)) {
            doctorList.remove(doctor);
            return "Successfully removed Doctor " + doctor.getName() + " from " + this.name;
        } else {
            return "Doctor " + doctor.getName() + " is not in " + this.name;
        }
    }

    // displays approved appointments
    public void viewApproved() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("approved.txt"));
        String line;
        int num = 1;

        while ((line = reader.readLine()) != null) {
            System.out.println(num + ": " + line);
        }
    }

    //displays pending appointments
    public void viewPending() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("pending.txt"));
        String line;
        int num = 1;

        while ((line = reader.readLine()) != null) {
            System.out.println(num + ": " + line);
        }
    }

    public void approveAppointment(int line) throws IOException {
        List<String> pendingAppointments = new ArrayList<>();

        try (BufferedWriter approvedWriter = new BufferedWriter(new FileWriter("approved.txt", true));
             BufferedReader pendingReader = new BufferedReader(new FileReader("pending.txt"))) {

            String line1;
            int lineNum = 1;

            while ((line1 = pendingReader.readLine()) != null) {
                if (lineNum == line) {
                    // Process the approved appointment
                    approvedWriter.write(line1);
                } else {
                    // Add non-approved appointments to the temporary list
                    pendingAppointments.add(line1);
                }
                lineNum++;
            }
        }

        // Write the updated pending appointments back to "pending.txt"
        try (BufferedWriter pendingWriter = new BufferedWriter(new FileWriter("pending.txt"))) {
            for (String appointment : pendingAppointments) {
                pendingWriter.write(appointment);
                pendingWriter.newLine();
            }
        }
    }

    public void declineAppointment(int line) throws IOException {
        List<String> pendingAppointments = new ArrayList<>();

        try (BufferedReader pendingReader = new BufferedReader(new FileReader("pending.txt"))) {

            String line1;
            int lineNum = 1;

            while ((line1 = pendingReader.readLine()) != null) {
                if (!(lineNum == line)) {
                    pendingAppointments.add(line1);
                }
                lineNum++;
            }
        }

        // Write the updated pending appointments back to "pending.txt"
        try (BufferedWriter pendingWriter = new BufferedWriter(new FileWriter("pending.txt"))) {
            for (String appointment : pendingAppointments) {
                pendingWriter.write(appointment);
                pendingWriter.newLine();
            }
        }
    }

    public String getCustomerStatistics() {
        StringBuilder statistics = new StringBuilder();
        for (Doctor doctor : doctorList) {
            HashMap<String, Integer> customerData = doctor.getStatistics()[0]; // a hashmap with key = customerName, value = # of appointments per customer
            // iterate over keys in customerData
            for (String customerName : customerData.keySet()) {
                statistics.append(customerName).append(" : ").append(customerData.get(customerName));
            }

        }
        return statistics.toString();
    }
    public String getTimeStatistics() {
        StringBuilder statistics = new StringBuilder();
        for (Doctor doctor : doctorList) {
            HashMap<Time, Integer> timeData = doctor.getStatistics()[1]; // a hashmap with key = Time, value = # of appointments at this time
            // iterate over keys in customerData
            for (Time time : timeData.keySet()) {
                statistics.append(time.getTimeslot()).append(" : ").append(timeData.get(time));
            }
        }

        return statistics.toString();
    }
}
