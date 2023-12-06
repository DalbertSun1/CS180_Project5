import java.util.HashMap;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;

/**
 * Project 4
 * Dentist Office Calendar Marketplace
 *
 * @author Dalbert Sun, Vihaan Chadha, Jack White, Himaja Narajala, Aaryan Bondre
 * @version November 13th, 2023
 */

public class OurStatistics { // handles the statistics section of our projection

    public static void dentistOfficeDashboard(DentistOffice dentistOffice, Scanner scanner, DentistClient client) {
        HashMap<String, Integer> patientFrequency = new HashMap<>(); // maps patient names to # of apts
        HashMap<Doctor, String> doctorTimeData = new HashMap<>(); // maps Doctors to their most frequent time

        for (Doctor doctor : dentistOffice.getDoctorList()) {
            HashMap<String, Integer> patientData; // maps patient names to integer # of appointments
            HashMap<String, Integer> timeData; // maps string time to frequency of appointment slot

            patientData = doctor.getStatistics(client)[0];
            timeData = doctor.getStatistics(client)[1];


            for (String name : patientData.keySet()) {
                if (patientFrequency.keySet().contains(name)) {
                    int currentValue = patientFrequency.get(name);
                    patientFrequency.put(name, currentValue + patientData.get(name));
                } else {
                    patientFrequency.put(name, patientData.get(name));
                }
            }

            if (timeData.isEmpty()) {
                doctorTimeData.put(doctor, "No Appointments");
            } else {
                // sort through Times to find the most frequent one
                String mostFrequent = timeData.keySet().iterator().next();
                for (String thisTime : timeData.keySet()) {
                    if (timeData.get(thisTime) > timeData.get(mostFrequent)) {
                        mostFrequent = thisTime;
                    }
                }
                doctorTimeData.put(doctor, mostFrequent);
            }


        }
        printDentistOfficeDashboard(patientFrequency, doctorTimeData, scanner);


        // Data will include a list of patients with the number of approved appointments they made
        // get data
        // and the most popular appointment windows by Doctor.
        // Sellers can choose to sort the dashboard.

    }

    private static void printDentistOfficeDashboard(HashMap<String, Integer> patientFrequency, HashMap<Doctor, String> doctorTimeData, Scanner scanner) {
        boolean printing = true;
        int userChoice = 0;

        List<String> patientList = new ArrayList<>(patientFrequency.keySet());
        List<Doctor> doctorList = new ArrayList<>(doctorTimeData.keySet());

        List<Map.Entry<String, Integer>> sortedPatientEntries = new ArrayList<>(patientFrequency.entrySet());
        do {
            StringBuilder output = new StringBuilder();
            String doctorFormat = "%-25s | %s\n"; // format used to make things look pretty
            String patientFormat = "%-25s | %s\n";

            // first, add doctor | most frequent time
            output.append(String.format(doctorFormat, "Doctor Name", "Most Frequent Apt. Time"));
            for (Doctor doctor : doctorList) {
                String doctorName = doctor.getName();
                String time = doctorTimeData.get(doctor);
                output.append(String.format(doctorFormat, doctorName, time));
            }

            output.append("---------------------------------------------------------\n");
            output.append(String.format(patientFormat, "Patient Name", "# of Appointments"));

            if (userChoice == 2) { // if sorted by # of appointments
                for (Map.Entry<String, Integer> entry : sortedPatientEntries) {
                    String name = entry.getKey();
                    int numApts = patientFrequency.get(name);
                    String numAptsString = numApts + (numApts == 1 ? " appointment" : " appointments");
                    output.append(String.format(patientFormat, name, numAptsString));
                }
            } else { // if unsorted or sorted by patient name
                for (String name : patientList) {
                    int numApts = patientFrequency.get(name);
                    String numAptsString = numApts + (numApts == 1 ? " appointment" : " appointments");
                    output.append(String.format(patientFormat, name, numAptsString));
                }
            }

            System.out.println(output.toString());
            do {
                System.out.println("Would you like to 1) Sort by patient's name, 2) Sort by # of appointments, 3) Sort by doctor's name, 4) exit?");
                try {
                    userChoice = scanner.nextInt();
                    scanner.nextLine();
                } catch (Exception e) {
                    userChoice = 5;
                    scanner.nextLine();
                }

                switch (userChoice) {
                    case 1 -> {
                        // sort map by patient's name
                        patientList.sort(null);
                    }
                    case 2 -> {
                        // sort map by # of appointments
                        sortedPatientEntries.sort(Map.Entry.comparingByValue());
                    }
                    case 3 -> {
                        // sort by doctor's name
                        doctorList.sort(Comparator.comparing(Doctor::getName));
                    }

                    case 4 -> {
                        printing = false;
                    }
                    case 5 -> {
                        System.out.println("Not an available option. Type either 1, 2, or 3.");
                    }
                }
            } while (userChoice == 5);

        } while (printing);

    }


    public static void patientDashboard(DentistOffice dentistOffice, Scanner scanner, DentistClient client) {
        // Data will include a list of Doctors by number of patients and the most popular appointment windows by Doctor.
        // Customers can choose to sort the dashboard.

        // get necessary data
        HashMap<Doctor, Integer> doctorPatientData = new HashMap<>(); // maps Doctors to # of patients
        HashMap<Doctor, String> doctorTimeData = new HashMap<>(); // maps Doctors to most frequent time
        for (Doctor doctor : dentistOffice.getDoctorList()) {
            HashMap<String, Integer> patientData; // maps patient names to integer # of appointments
            HashMap<String, Integer> timeData; // maps String time to frequency of appointment slot

            patientData = doctor.getStatistics(client)[0];
            timeData = doctor.getStatistics(client)[1];

            // create a hashmap with key Doctor, value = # of patients
            doctorPatientData.put(doctor, patientData.keySet().size());

            if (timeData.isEmpty()) {
                doctorTimeData.put(doctor, "No Appointments");
            } else {
                // sort through Times to find the most frequent one
                String mostFrequent = timeData.keySet().iterator().next();
                for (String thisTime : timeData.keySet()) {
                    if (timeData.get(thisTime) > timeData.get(mostFrequent)) {
                        mostFrequent = thisTime;
                    }
                }
                doctorTimeData.put(doctor, mostFrequent);
            }

        }

        printPatientDashboard(doctorPatientData, doctorTimeData, scanner);


        // 1: Dr James | 4 patients
        // 2: Dr Henry | 1 patient


    }


    private static void printPatientDashboard(HashMap<Doctor, Integer> doctorPatientData, HashMap<Doctor, String> doctorTimeData, Scanner scanner) {
        boolean printing = true;
        int userChoice = 0;

        List<Doctor> doctorList = new ArrayList<>(doctorPatientData.keySet());
        List<Map.Entry<Doctor, Integer>> sortedEntriesByPatients = new ArrayList<>(doctorPatientData.entrySet());
        do {

            StringBuilder output = new StringBuilder();
            String format = "%-20s | %-15s | %s\n";
            output.append(String.format(format, "Doctor Name", "# of Patients", "Most Common Time"));

            if (userChoice == 2) { // if sorted by # of patients
                for (Map.Entry<Doctor, Integer> entry : sortedEntriesByPatients) {

                    Doctor doctor = entry.getKey();
                    int numPatients = doctorPatientData.get(doctor);

                    String patientCount = Integer.toString(numPatients);
                    if (numPatients == 1) {
                        patientCount += (" patient");
                    } else {
                        patientCount += (" patients");
                    }

                    String timeString = doctorTimeData.get(doctor);

                    output.append(String.format(format, doctor.getName(), patientCount, timeString));
                }
            } else { // if unsorted or sorted by Doctor's name
                for (Doctor doctor : doctorList) {
                    int numPatients = doctorPatientData.get(doctor);

                    String patientCount = Integer.toString(numPatients);
                    if (numPatients == 1) {
                        patientCount += (" patient");
                    } else {
                        patientCount += (" patients");
                    }

                    String timeString = doctorTimeData.get(doctor);

                    output.append(String.format(format, doctor.getName(), patientCount, timeString));
                }
            }

            System.out.println(output.toString());

            do {
                System.out.println("Would you like to 1) Sort by Doctor's name, 2) Sort by # of patients, 3) exit?");
                try {
                    userChoice = scanner.nextInt();
                    scanner.nextLine();
                } catch (Exception e) {
                    scanner.nextLine();
                    userChoice = 5;
                }

                switch (userChoice) {
                    case 1 -> {
                        // sort map by Doctor's name
                        doctorList.sort(Comparator.comparing(Doctor::getName));
                    }
                    case 2 -> {
                        // sort map by # of patients
                        sortedEntriesByPatients.sort(Map.Entry.comparingByValue());
                    }
                    case 3 -> {
                        printing = false;
                    }
                    default -> {
                        System.out.println("Not an available option. Type either 1, 2, or 3.");
                    }
                }
            } while (userChoice == 5);

        } while (printing);
    }
}
