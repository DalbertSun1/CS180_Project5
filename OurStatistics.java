import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.Array;
import java.util.*;
import java.util.List;

/**
 * Project 5
 * Dentist Office Calendar Marketplace
 *
 * @author Dalbert Sun, Vihaan Chadha, Jack White, Himaja Narajala, Aaryan Bondre
 * @version December 11th, 2023
 */

public class OurStatistics { // handles the statistics section of our projection
    static JFrame frame = new JFrame("Statistics");
    static ArrayList<Object[]> patientData;
    static ArrayList<Object[]> doctorData;

    static ArrayList<Object[]> doctorPatData;
    static String[] patientColumnNames;
    static String[] doctorColumnNames;
    static String[] doctorPatColumnNames;
    static boolean sort = false;

    static List<Doctor> doctorList;

    static List<Doctor> patDoctorList;
    static ArrayList<String> patientList;
    static HashMap<Doctor, String> doctorTimeData;
    static HashMap<String, Integer> patientFrequencyLebron;






//    public OurStatistics

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


        // patientDatawill include a list of patients with the number of approved appointments they made
        // get data
        // and the most popular appointment windows by Doctor.
        // Sellers can choose to sort the dashboard.

    }

    private static void printDentistOfficeDashboard(HashMap<String, Integer> patientFrequency, HashMap<Doctor, String> doctorTimeData, Scanner scanner) {
        boolean printing = true;
        int userChoice = 0;

        patientFrequencyLebron = patientFrequency;

        patientList = new ArrayList<>(patientFrequency.keySet());
        patDoctorList = new ArrayList<>(doctorTimeData.keySet());

        List<Map.Entry<String, Integer>> sortedPatientEntries = new ArrayList<>(patientFrequency.entrySet());
        do {
            StringBuilder output = new StringBuilder();
            String doctorNameFormat = "%-25s";
            String doctorApptFormat = "%s"; // format used to make things look pretty
            String patientNameFormat = "%-25s";
            String patientApptFormat = "%s";
            String[] columns = {String.format(doctorNameFormat, "Doctor Name"), String.format(doctorApptFormat, "Most Frequent Apt. Time")};
            doctorColumnNames = columns;

            // first, add doctor | most frequent time

            doctorData= new ArrayList<Object[]>();
            //output.append(String.format(doctorFormat, "Doctor Name", "Most Frequent Apt. Time"));
            for (Doctor doctor : patDoctorList) {
                String doctorName = doctor.getName();
                String time = doctorTimeData.get(doctor);
                String[] add = new String[2];
                add[0] = doctorName;
                add[1] = time;
                doctorData.add(add);
            }

            //output.append("---------------------------------------------------------\n");
            //output.append(String.format(patientFormat, "Patient Name", "# of Appointments"));
            String[] patColumns = {String.format(patientNameFormat, "Patient Name"), String.format(patientApptFormat, "# of Appointments")};
            doctorPatColumnNames = patColumns;
            doctorPatData = new ArrayList<Object[]>();

            if (userChoice == 2) { // if sorted by # of appointments
                for (Map.Entry<String, Integer> entry : sortedPatientEntries) {
                    String name = entry.getKey();
                    int numApts = patientFrequency.get(name);
                    String numAptsString = numApts + (numApts == 1 ? " appointment" : " appointments");
                    //output.append(String.format(patientFormat, name, numAptsString));

                }
            } else { // if unsorted or sorted by patient name
                for (String name : patientList) {
                    int numApts = patientFrequency.get(name);
                    String numAptsString = numApts + (numApts == 1 ? " appointment" : " appointments");
                    //output.append(String.format(patientFormat, name, numAptsString));
                    String[] add = new String[2];
                    add[0] = name;
                    add[1] = numAptsString;
                    doctorPatData.add(add);
                }
            }
            doctorStats();
//            JFrame frame = new JFrame("Statistics");
//            JTextArea textArea = new JTextArea(output.toString());
//            textArea.setLineWrap(true);
//            textArea.setWrapStyleWord(true);
//            JScrollPane scrollPane = new JScrollPane(textArea);
//            frame.setLayout(new BorderLayout());
//            frame.add(scrollPane, BorderLayout.CENTER);
//            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            frame.setSize(600, 400);
//            frame.setVisible(true);
            System.out.println(output.toString());
            do {
                //System.out.println("Would you like to 1) Sort by patient's name, 2) Sort by # of appointments, 3) Sort by doctor's name, 4) exit?");
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
                        //System.out.println("Not an available option. Type either 1, 2, or 3.");
                    }
                }
            } while (userChoice == 5);

        } while (printing);

    }


    public static void patientDashboard(DentistOffice dentistOffice, Scanner scanner, DentistClient client) {
        // patientDatawill include a list of Doctors by number of patients and the most popular appointment windows by Doctor.
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


    private static void printPatientDashboard(HashMap<Doctor, Integer> doctorPatientData, HashMap<Doctor, String> doctorLebronTimeData, Scanner scanner) {
        boolean printing = true;
        int userChoice = 0;

        doctorList = new ArrayList<>(doctorPatientData.keySet());
        doctorTimeData = doctorLebronTimeData;
        List<Map.Entry<Doctor, Integer>> sortedEntriesByPatients = new ArrayList<>(doctorPatientData.entrySet());
        do {

            StringBuilder output = new StringBuilder();
            //String format = "%-20s | %-15s | %s\n";
            String formatD = "%-20s";
            String formatP = "%-15s";
            String formatT = "%s";

            //output.append(String.format(format, "Doctor Name", "# of Patients", "Most Common Time"));
            String[] columns = {String.format(formatD, "Doctor Name"), String.format(formatP, "# of Patients"), String.format(formatT, "Most Common Time")};
            patientColumnNames = columns;
            patientData= new ArrayList<Object[]>();
            if (userChoice == 2) { // if sorted by # of patients
                for (Map.Entry<Doctor, Integer> entry : sortedEntriesByPatients) {

                    Doctor doctor = entry.getKey();
                    int numPatients = doctorPatientData.get(doctor);
                    String[] add = new String[3];
                    add[0] = doctor.getName();


                    String patientCount = Integer.toString(numPatients);
                    if (numPatients == 1) {
                        patientCount += (" patient");
                    } else {
                        patientCount += (" patients");
                    }

                    String timeString = doctorTimeData.get(doctor);
                    add[1] = patientCount;
//                    add[2] = timeString;
//                    patientData.add(add);
                    //output.append(String.format(format, doctor.getName(), patientCount, timeString));
                }
            } else { // if unsorted or sorted by Doctor's name
                for (Doctor doctor : doctorList) {
                    int numPatients = doctorPatientData.get(doctor);
                    int count =0;
                    String timeString = doctorTimeData.get(doctor);
                    try {
                        BufferedReader bf = new BufferedReader(new FileReader(new File("approved.txt")));
                        String line = "";
                        while ((line = bf.readLine()) != null) {
                            String[] split = line.split(",");
                            if (split[3].equals(doctor.getName())){
                                count++;
                            }
                        }
                    } catch (Exception e) {
                        //add popup saying things
                    }

                    String patientCount = "";
                    patientCount += count;
                    String[] add = new String[3];
                    add[0] = doctor.getName();

                    if (count == 1) {
                        patientCount += (" patient");
                    } else {
                        patientCount += (" patients");
                    }
                    add[1] = patientCount;

                    add[2] = timeString;
                    patientData.add(add);

                    //output.append(String.format(format, doctor.getName(), patientCount, timeString));
                }
            }


            // Create a JTable with the model
            if (sort == false) {
                patientStats();
            }

            //System.out.println(output.toString());

            do {
                //System.out.println("Would you like to 1) Sort by Doctor's name, 2) Sort by # of patients, 3) exit?");
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




                    }
                    case 2 -> {
                        // sort map by # of patients
                        sortedEntriesByPatients.sort(Map.Entry.comparingByValue());
                    }
                    case 3 -> {
                        printing = false;
                    }
                    default -> {
                        //System.out.println("Not an available option. Type either 1, 2, or 3.");
                    }
                }
            } while (userChoice == 5);

        } while (printing);
    }
    static ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() instanceof JButton source){
                if (source.getText().equals("Sort by Doctor's Name")){
                    doctorList.sort(Comparator.comparing(Doctor::getName));
                    ArrayList<String> doctors = new ArrayList<String>();
                    String timeString = "";
                    for (Doctor d : doctorList) {
                        doctors.add(d.getName());
                    }

//                    ArrayList<String> temp = new ArrayList<String>();
//                    for (int i = 0; i < doctors.size(); i+=0) {
//                        int max = Integer.MIN_VALUE;
//                        int index = -1;
//                        for (int j = 0; j < doctors.size(); j++) {
//                            String s = doctors.get(j);
//                                if (max < ((int) s.charAt(0))) {
//                                    max = ((int) s.charAt(0));
//
//                                    index = i;
//                                }
//
//                        }
//                        ////System.out.println(doctors.get(index));
//                        temp.add(doctors.get(index));
//                        doctors.remove(index);
//                    }
//                    doctors = temp;
                    Collections.sort(doctors);

                    patientData= new ArrayList<Object[]>();
                    for (String d : doctors) {
                        int count = 0;
                        try {
                            BufferedReader bf = new BufferedReader(new FileReader(new File("approved.txt")));
                            String line = "";
                            while ((line = bf.readLine()) != null) {
                                String[] split = line.split(",");
                                if (split[3].equals(d)){
                                    count++;
                                }
                            }
                        } catch (IOException ex) {
                            ex.printStackTrace();
                            //add popup saying things
                        }
                        String[] add = new String[3];
                        add[0] = d;
                        String patientCount = "";
                        patientCount += count;


                        if (count == 1) {
                            patientCount += (" patient");
                        } else {
                            patientCount += (" patients");
                        }
                        for (Doctor doc : doctorList) {
                            if (doc.getName().equals(d)) {
                                timeString = doctorTimeData.get(doc);
                            }
                        }

                        add[1] = patientCount;
                        add[2] = timeString;
                        patientData.add(add);
                    }

                    patientStats();
                    sort = true;
                }

                if (source.getText().equals("Sort by # of Patients")) {
                    ArrayList<String> doctors = new ArrayList<String>();
                    String timeString = "";
                    for (Doctor d : doctorList) {
                        ////System.out.println(d.getName());
                        doctors.add(d.getName());
                    }
                    ArrayList<Integer> temp = new ArrayList<Integer>();
                    patientData = new ArrayList<Object[]>();

                    String[][] anotherTemp = new String[doctors.size()][2];
                    for (int i = 0; i < doctors.size(); i++) {

                        String d = doctors.get(i);
                        int count = 0;
                        try {
                            BufferedReader bf = new BufferedReader(new FileReader(new File("approved.txt")));
                            String line = "";
                            while ((line = bf.readLine()) != null) {
                                String[] split = line.split(",");
                                if (split[3].equals(d)) {
                                    count++;
                                }
                            }
                        } catch (IOException ex) {
                            ex.printStackTrace();
                            //add popup saying things
                        }
                        temp.add(count);
                        String ahh = "";
                        ahh += count;
                        anotherTemp[i][0] = d;
//                        //System.out.println(anotherTemp[i][0]);
                        anotherTemp[i][1] = ahh;
//                        //System.out.println(anotherTemp[i][1]);
                    }
                    int[] array = new int[temp.size()];
                    for (int i = 0; i < temp.size(); i++) {
                        array[i] = temp.get(i);
                    }
                    Arrays.sort(array);
                    reverseArray(array);
                    for (int count : array) {
                        String d = "";

                        for (int j = 0; j < anotherTemp.length; j++) {
                            String f = "";
                            f += count;
                            if (Integer.parseInt(anotherTemp[j][1]) == count){
                                //System.out.println("anotherTemp[" + j + "][0]: " + anotherTemp[j][0]);
                                //System.out.println("anotherTemp[" + j + "][1]: " + anotherTemp[j][1]);

                                //System.out.println();
                                d = anotherTemp[j][0];
                                anotherTemp[j][0] = "";
                                anotherTemp[j][1] = "-1";
                                break;
                            }

                        }

                        String[] add = new String[3];
                        add[0] = d;
                        String patientCount = "";
                        patientCount += count;


                        if (count == 1) {
                            patientCount += (" patient");
                        } else {
                            patientCount += (" patients");
                        }
                        for (Doctor doc : doctorList) {
                            if (doc.getName().equals(d)) {
                                timeString = doctorTimeData.get(doc);
                            }
                        }

                        add[1] = patientCount;
                        add[2] = timeString;
                        patientData.add(add);

                    }
                    patientStats();
                    sort = true;
                }
                if (source.getText().equals("Exit")) {
                    frame.dispose();
                    System.exit(0);
                }
                if (source.getText().equals("Sort by # of Appointments")) {
                    doctorPatData = new ArrayList<Object[]>();
                    ArrayList<Integer> temp = new ArrayList<Integer>();

                    String[][] anotherTemp = new String[patientList.size()][2];
                    for (int i = 0; i < patientList.size(); i++) {

                        String p = patientList.get(i);
                        int count = 0;
                        try {
                            BufferedReader bf = new BufferedReader(new FileReader(new File("approved.txt")));
                            String line = "";
                            while ((line = bf.readLine()) != null) {
                                String[] split = line.split(",");
                                if (split[0].equals(p)) {
                                    count++;
                                }
                            }
                        } catch (IOException ex) {
                            ex.printStackTrace();
                            //add popup saying things
                        }
                        temp.add(count);
                        String ahh = "";
                        ahh += count;
                        anotherTemp[i][0] = p;
//                        //System.out.println(anotherTemp[i][0]);
                        anotherTemp[i][1] = ahh;
//                        //System.out.println(anotherTemp[i][1]);
                    }
                    int[] array = new int[temp.size()];
                    for (int i = 0; i < temp.size(); i++) {
                        array[i] = temp.get(i);
                    }
                    Arrays.sort(array);
                    reverseArray(array);
                    for (int count : array) {
                        String p = "";

                        for (int j = 0; j < anotherTemp.length; j++) {

                            if (Integer.parseInt(anotherTemp[j][1]) == count){
                                //System.out.println("anotherTemp[" + j + "][0]: " + anotherTemp[j][0]);
                                //System.out.println("anotherTemp[" + j + "][1]: " + anotherTemp[j][1]);

                                //System.out.println();
                                p = anotherTemp[j][0];
                                anotherTemp[j][0] = "";
                                anotherTemp[j][1] = "-1";
                                break;
                            }

                        }

                        String[] add = new String[2];
                        add[0] = p;
                        String patientCount = "";
                        patientCount += count;


                        if (count == 1) {
                            patientCount += (" appointment");
                        } else {
                            patientCount += (" appointments");
                        }

                        add[1] = patientCount;
                        doctorPatData.add(add);

                    }
                    doctorStats();
                    sort = true;

                }
                if (source.getText().equals("Sort by Patient's Name")) {
                    ArrayList<String> temp = new ArrayList<String>();
                    ArrayList<String> tempPatients = patientList;
//                    for (int i = 0; i < tempPatients.size(); i+=0) {
//                        System.out.println(i + ": " + tempPatients.get(i));
//                        int min = Integer.MAX_VALUE;
//                        int index = -1;
//                        for (int j = 0; j < tempPatients.size(); j++) {
//
//                            String s = tempPatients.get(j);
//                            s = s.toUpperCase();
//                            if (min > ((int) s.charAt(0))) {
//                                min = ((int) s.charAt(0));
//                                index = j;
//                                System.out.println(tempPatients.get(index));
//                            }
//
//                        }
//                        ////System.out.println(doctors.get(index));
//                        temp.add(tempPatients.get(index));
//                        tempPatients.remove(index);
//                    }

                    //tempPatients = temp;
                    Collections.sort(tempPatients);
//                    for (int i = 0; i<temp.size(); i++) {
//                        System.out.println(i + ": " + temp.get(i));
//                    }
//                    tempPatients = temp;

                    doctorPatData= new ArrayList<Object[]>();
                    for (String p : tempPatients) {
                        //System.out.println(p);
                        int count = 0;
                        try {
                            BufferedReader bf = new BufferedReader(new FileReader(new File("approved.txt")));
                            String line = "";
                            while ((line = bf.readLine()) != null) {
                                String[] split = line.split(",");
                                if (split[0].equals(p)){
                                    count++;
                                }
                            }
                        } catch (IOException ex) {
                            ex.printStackTrace();
                            //add popup saying things
                        }
                        String[] add = new String[2];
                        add[0] = p;
                        String patientCount = "";
                        patientCount += count;


                        if (count == 1) {
                            patientCount += (" appointment");
                        } else {
                            patientCount += (" appointments");
                        }

                        add[1] = patientCount;
                        doctorPatData.add(add);
                    }

                    doctorStats();
                    sort = true;


                }

            }

        }
    };


    public static void patientStats() {
        //frame.dispose();

//            JTextArea textArea = new JTextArea(output.toString());
//            textArea.setEditable(false);
//            textArea.setLineWrap(true);
//            textArea.setWrapStyleWord(true);
        String[][] daaaata = patientData.toArray(new String[0][]);
        DefaultTableModel tableModel = new DefaultTableModel(daaaata, patientColumnNames){
            public boolean isCellEditable(int row, int column) {
                // Make all cells uneditable
                return false;
            }
        };
        JTable table = new JTable(tableModel);
        table.setCellSelectionEnabled(false);
        table.setEnabled(false);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.setLayout(new BorderLayout());
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JButton sortDoctor = new JButton("Sort by Doctor's Name");
        JButton sortNumber = new JButton("Sort by # of Patients");
        JButton exitButton = new JButton("Exit");
        sortDoctor.addActionListener(actionListener);
        sortNumber.addActionListener(actionListener);

        exitButton.addActionListener(actionListener);
        JPanel panel = new JPanel();
        panel.add(sortDoctor);
        panel.add(sortNumber);
        panel.add(exitButton);
        frame.add(panel, BorderLayout.SOUTH);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    public static void doctorStats() {
        String[][] daaaata = doctorData.toArray(new String[0][]);
        DefaultTableModel tableModel = new DefaultTableModel(daaaata, doctorColumnNames){
            public boolean isCellEditable(int row, int column) {
                // Make all cells uneditable
                return false;
            }
        };
        JTable table = new JTable(tableModel);
        table.setCellSelectionEnabled(false);
        table.setEnabled(false);
        JScrollPane scrollPane = new JScrollPane(table);
        String[][] daata = doctorPatData.toArray(new String[0][]);
        DefaultTableModel patTableModel = new DefaultTableModel(daata, doctorPatColumnNames){
            public boolean isCellEditable(int row, int column) {
                // Make all cells uneditable
                return false;
            }
        };
        JTable patTable = new JTable(patTableModel);
        patTable.setCellSelectionEnabled(false);
        patTable.setEnabled(false);

        JScrollPane scrollPane2 = new JScrollPane(patTable);
        JPanel panel = new JPanel(new GridLayout(2,1));
        panel.add(scrollPane);
        panel.add(scrollPane2);


        frame.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JButton sortDoctor = new JButton("Sort by Patient's Name");
        JButton sortNumber = new JButton("Sort by # of Appointments");
        JButton exitButton = new JButton("Exit");

        sortDoctor.addActionListener(actionListener);
        sortNumber.addActionListener(actionListener);

        exitButton.addActionListener(actionListener);
        JPanel panel2 = new JPanel();
        panel2.add(sortDoctor);
        panel2.add(sortNumber);
        panel2.add(exitButton);
        frame.add(panel2, BorderLayout.SOUTH);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void reverseArray(int[] arr) {
        int start = 0;
        int end = arr.length - 1;
        while (start < end) {
            // Swap elements at start and end indices
            int temp = arr[start];
            arr[start] = arr[end];
            arr[end] = temp;

            // Move indices towards the center
            start++;
            end--;
        }
    }
}