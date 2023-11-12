import java.util.HashMap;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;

public class OurStatistics { // handles the statistics section of our projection

    public static void dentistOfficeDashboard(DentistOffice dentistOffice) {
        HashMap<String, Integer> patientFrequency = new HashMap<>(); // maps patient names to # of apts
        HashMap<Doctor, String> doctorTimeData = new HashMap<>(); // maps Doctors to their most frequent time

        for (Doctor doctor : dentistOffice.getDoctorList()) {
            HashMap<String, Integer> patientData; // maps patient names to integer # of appointments
            HashMap<String, Integer> timeData; // maps string time to frequency of appointment slot

            patientData = doctor.getStatistics()[0];
            timeData = doctor.getStatistics()[1];


            for (String name : patientData.keySet()) {
                if (patientFrequency.keySet().contains(name)) {
                    int currentValue = patientFrequency.get(name);
                    patientFrequency.put(name, currentValue + patientData.get(name));
                } else {
                    patientFrequency.put(name, patientData.get(name));
                }
            }

            // sort through Times to find the most frequent one
            String mostFrequent = timeData.keySet().iterator().next();
            for (String thisTime : timeData.keySet()) {
                if (timeData.get(thisTime) > timeData.get(mostFrequent)) {
                    mostFrequent = thisTime;
                }
            }
            doctorTimeData.put(doctor, mostFrequent);

        }
        printDentistOfficeDashboard(patientFrequency, doctorTimeData);


        // Data will include a list of patients with the number of approved appointments they made
        // get data
        // and the most popular appointment windows by Doctor.
        // Sellers can choose to sort the dashboard.

    }
    private static void printDentistOfficeDashboard(HashMap<String, Integer> patientFrequency, HashMap<Doctor, String> doctorTimeData) {
        boolean printing = true;
        int userChoice = 0;
        Scanner scanner = new Scanner(System.in);
        List<String> patientList = new ArrayList<>(patientFrequency.keySet());

        List<Doctor> doctorList = new ArrayList<>(doctorTimeData.keySet());
        doctorList.sort(Comparator.comparing(Doctor::getName));

        List<Map.Entry<String, Integer>> sortedPatientEntries = new ArrayList<>(patientFrequency.entrySet());
        do {
            StringBuilder output = new StringBuilder();
            // first, add doctor | most frequent time
            output.append("Doctor Name | Most Frequent Apt. Time");
            for (Doctor doctor : doctorList) {
                output.append(doctor.getName() + " | " + doctorTimeData.get(doctor) + "\n");
            }


            output.append("Patient Name | # of Appointments\n");
            if (userChoice == 2) { // if sorted by # of appointments
                for (Map.Entry<String, Integer> entry : sortedPatientEntries) {

                    String name = entry.getKey();
                    int numApts = patientFrequency.get(name);
                    output.append(numApts);
                    if (numApts == 1) { output.append(" appointment | ");} else {output.append(" appointments | ");}
                    output.append("\n");

                }
            } else { // if unsorted or sorted by patient name
                for (String name : patientList) {

                    output.append(name + " | "); // name = John Doe |
                    int numApts = patientFrequency.get(name);
                    output.append(numApts);
                    if (numApts == 1) { output.append(" appointment | ");} else {output.append(" appointments | ");}
                    output.append("\n");

                }
            }



            System.out.println(output.toString());

            do {
                System.out.println("Would you like to 1) Sort by patient's name, 2) Sort by # of appointments, 3) exit?");
                try {
                    userChoice = scanner.nextInt();
                    scanner.nextLine();
                } catch (Exception e) {
                    userChoice = 5;
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
                        printing = false;
                    }
                    default -> {
                        System.out.println("Not an available option. Type either 1, 2, or 3.");
                    }
                }
            } while (userChoice == 5);

        } while (printing);

    }









    public static void patientDashboard(DentistOffice dentistOffice) {
    // Data will include a list of Doctors by number of patients and the most popular appointment windows by Doctor.
        // Customers can choose to sort the dashboard.

        // get necessary data
        HashMap<Doctor, Integer> doctorPatientData = new HashMap<>(); // maps Doctors to # of patients
        HashMap<Doctor, String> doctorTimeData = new HashMap<>(); // maps Doctors to most frequent time
        for (Doctor doctor : dentistOffice.getDoctorList()) {
            HashMap<String, Integer> patientData; // maps patient names to integer # of appointments
            HashMap<String, Integer> timeData; // maps String time to frequency of appointment slot

            patientData = doctor.getStatistics()[0];
            timeData = doctor.getStatistics()[1];

            // create a hashmap with key Doctor, value = # of patients
            doctorPatientData.put(doctor, patientData.keySet().size());

            // sort through Times to find the most frequent one
            String mostFrequent = timeData.keySet().iterator().next();
            for (String thisTime : timeData.keySet()) {
                if (timeData.get(thisTime) > timeData.get(mostFrequent)) {
                    mostFrequent = thisTime;
                }
            }
            doctorTimeData.put(doctor, mostFrequent);

        }

        printPatientDashboard(doctorPatientData, doctorTimeData);






        // 1: Dr James | 4 patients
        // 2: Dr Henry | 1 patient


    }


    private static void printPatientDashboard(HashMap<Doctor, Integer> doctorPatientData, HashMap<Doctor, String> doctorTimeData) {
        boolean printing = true;
        int userChoice = 0;
        Scanner scanner = new Scanner(System.in);
        List<Doctor> doctorList = new ArrayList<>(doctorPatientData.keySet());
        List<Map.Entry<Doctor, Integer>> sortedEntriesByPatients = new ArrayList<>(doctorPatientData.entrySet());
        do {
            StringBuilder output = new StringBuilder();
            output.append("Doctor Name | # of Patients | Most Common Time\n");
            if (userChoice == 2) { // if sorted by # of patients
                for (Map.Entry<Doctor, Integer> entry : sortedEntriesByPatients) {

                    Doctor doctor = entry.getKey();
                    output.append(doctor.getName() + " | "); // Dr. James |

                    int numPatients = doctorPatientData.get(doctor);
                    output.append(numPatients);
                    if (numPatients == 1) { output.append(" patient | ");} else {output.append(" patients | ");}

                    String timeString = doctorTimeData.get(doctor);
                    output.append(timeString + "\n");

                }
            } else { // if unsorted or sorted by Doctor's name
                for (Doctor doctor : doctorList) {
                    // Dr. James has 4 patients | 5:00 pm
                    output.append(doctor.getName() + " | "); // Dr. James |

                    int numPatients = doctorPatientData.get(doctor);
                    output.append(numPatients);
                    if (numPatients == 1) { output.append(" patient | ");} else {output.append(" patients | ");}

                    String timeString = doctorTimeData.get(doctor);
                    output.append(timeString + "\n");

                }
            }



            System.out.println(output.toString());

            do {
                System.out.println("Would you like to 1) Sort by Doctor's name, 2) Sort by # of patients, 3) exit?");
                try {
                    userChoice = scanner.nextInt();
                    scanner.nextLine();
                } catch (Exception e) {
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



        // create a menu for patients to sort
    }
}
