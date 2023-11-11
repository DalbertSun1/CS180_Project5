import java.io.*;
import java.util.ArrayList;
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

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Doctor> getDoctorList() {
        return doctorList;
    }

    public void setDoctorList(ArrayList<Doctor> doctorList) {
        this.doctorList = doctorList;
    }

    public ArrayList<Doctor> readDoctors() throws IOException {
        File f = new File("doctors.txt");
        if(!f.exists()) {
            BufferedWriter writer = new BufferedWriter(new FileWriter("doctors.txt"));
            writer.write("");
            writer.close();
        }
        BufferedReader reader = new BufferedReader(new FileReader("doctors.txt"));
        String line;

        while ((line = reader.readLine()) != null) {
            Doctor doctor = new Doctor(line);
            doctorList.add(doctor);
        }
        reader.close();
        return doctorList;
    }

    public void addDoctor(Doctor doctor) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("doctors.txt", true));
        if (!(doctorList.contains(doctor))) {
            doctorList.add(doctor);
            writer.write(String.valueOf(doctor) + "\n");

            System.out.println("Successfully added doctor " + doctor.getName() + " to " + this.name);
        } else {
            System.out.println("Doctor " + doctor.getName() + " is already in " + this.name);
        }
        writer.close();
    }

    public void deleteDoctor(Doctor doctor) throws IOException {
        boolean repeat = false;
        int doctorNum = -1;

        for (int i = 0; i < doctorList.size(); i++) {
            if (String.valueOf(doctorList.get(i)).equals(String.valueOf(doctor))) {
                repeat = true;
                doctorNum = i;
            }
        }
        if (repeat) {
            BufferedWriter writer = new BufferedWriter(new FileWriter("doctors.txt", false));

            doctorList.remove(doctorNum);
            for (Doctor s : doctorList) {
                writer.write(String.valueOf(s));
                writer.newLine();
            }

            writer.close();

            System.out.println("Successfully removed doctor " + doctor.getName() + " from " + this.name);
        }
        else {
            System.out.println("Doctor " + doctor.getName() + " is not in " + this.name);
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
        reader.close();
    }

    //displays pending appointments
    public void viewPending() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("pending.txt"));
        String line;
        int num = 1;

        while ((line = reader.readLine()) != null) {
            System.out.println(num + ": " + line);
        }
        reader.close();
    }

    // TODO: Implement storage of doctor names as well
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

    public void rescheduleAppointmentForSeller(String doctorName, String oldAppointment, String newAppointment) {
        for (Doctor doctor : doctorList) {
            if (doctor.getName().equals(doctorName)) {
                Appointment oldApt = doctor.findAppointment(oldAppointment);
                if (oldApt != null) {
                    Appointment newApt = doctor.findAppointment(newAppointment);
                    if (newApt == null || !newApt.isBooked()) {
                        oldApt.cancelAppointment();
                        oldApt.setCustomerName(null);
                        oldApt.getTime().setAvailable(true);

                        if (newApt != null) {
                            newApt.cancelAppointment();
                            newApt.setCustomerName(oldApt.getCustomerName());
                            newApt.bookAppointment(oldApt.getCustomerName());
                            newApt.getTime().setAvailable(false);
                        } else {
                            Time newTimeSlot = new Time("New Time Slot", false);
                            doctor.addAppointment(new Appointment(newTimeSlot));
                            doctor.findAppointment(newAppointment).bookAppointment(oldApt.getCustomerName());
                        }

                        // Display the updated schedule after rescheduling
                        for (Appointment appointment : doctor.getAppointments()) {
                            System.out.println(appointment.toString());
                        }
                        return;
                    }
                }
            }
        }
        System.out.println("Doctor " + doctorName + " not found.");
    }
}
