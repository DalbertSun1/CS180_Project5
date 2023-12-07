import java.io.*;
import java.sql.SQLOutput;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Project 4
 * Dentist Office Calendar Marketplace
 *
 * @author Dalbert Sun, Vihaan Chadha, Jack White, Himaja Narajala, Aaryan Bondre
 * @version November 13th, 2023
 */

public class Login extends JComponent{

    static Scanner scan;

    public static void main(String[] args) throws IOException {

//        SwingUtilities.invokeLater(new Login());
//    }
//    public void run() {
        int welcome1  = JOptionPane.showConfirmDialog(null, "Welcome to Dentist Office!", "Welcome", JOptionPane.OK_CANCEL_OPTION);
        if (welcome1 != JOptionPane.OK_OPTION) {
            JOptionPane.showMessageDialog(null, "Thank you for using the Dentist Office", "Dentist Office", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        scan = new Scanner(System.in);
        try {
            menu(scan);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static JTextField noEdit(String prompt) {
        JTextField textField = new JTextField(prompt);
        textField.setEditable(false);
        return textField;
    }
    public void start() throws IOException {
        menu(scan);
    }
    public static void menu(Scanner scan) throws IOException {
        boolean menu1 = false; //counter to rerun the loop if invalid choice is entered
        do {
            String[] options = {"Log in", "Create an Account", "Exit"};

            int result = JOptionPane.showOptionDialog(null, "Choose an option:", "Dentist Office",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, options, options[1]);
            try {

                String fullName = "";

                switch (result) {
                    case 0: // log in
                        boolean menu2;
                        do {
                            String[] userMenu = {"Patient", "Doctor"};
                            String userOption;
                            try {

                                userOption = (String) JOptionPane.showInputDialog(null, "Log in as",
                                        "Dentist Office", JOptionPane.QUESTION_MESSAGE, null, userMenu, userMenu[0]);
                                if ((userOption == null) || (userOption.isEmpty())) {
//                                    JOptionPane.showMessageDialog(null, "Thank you for using Dentist Office!");
                                    menu(scan);
                                    return;
                                }

                                int testOption;
                                if (userOption.equals("Patient")) {
                                    testOption = 1;
                                } else if (userOption.equals("Doctor")) {
                                    testOption = 2;
                                } else {
                                    testOption = 3;
                                }

                                menu2 = false;
                                JPanel panel = new JPanel(new GridLayout(2, 2));
                                JTextField usernameField = new JTextField();
                                JPasswordField passwordField = new JPasswordField();

                                panel.add(noEdit("Username:"));
                                panel.add(usernameField);
                                panel.add(noEdit("Password:"));
                                panel.add(passwordField);

                                int loginResult = JOptionPane.showConfirmDialog(null, panel, "Log in",
                                        JOptionPane.OK_CANCEL_OPTION);

                                if (loginResult == JOptionPane.OK_OPTION) {
                                    String username = usernameField.getText();
                                    String password = new String(passwordField.getPassword());

                                    login(fullName, testOption, username, password, scan);

                                } else if (loginResult == JOptionPane.CANCEL_OPTION) {
                                    menu(scan);
                                }
                                else {
                                    menu(scan);
                                    return;
                                }
                            } catch (NumberFormatException e) {
                                JOptionPane.showMessageDialog(null, "Please choose a valid choice.", "Dentist Office", JOptionPane.ERROR_MESSAGE);
                                menu2 = true;
                            }

                        } while (menu2);
                        break;

                    case 1: // create an account
                        boolean menu3;
                        do {
                            String[] userMenu = {"Patient", "Doctor"};
                            String userOption2;
                            try {
                                userOption2 = (String) JOptionPane.showInputDialog(null, "Create an account as",
                                        "Dentist Office", JOptionPane.QUESTION_MESSAGE, null, userMenu, userMenu[0]);
                                if ((userOption2 == null) || (userOption2.isEmpty())) {
                                    menu(scan);
                                    return;
                                }

                                int testOption;
                                if (userOption2.equals("Patient")) {
                                    testOption = 1;
                                } else if (userOption2.equals("Doctor")) {
                                    testOption = 2;
                                } else {
                                    testOption = 3;
                                }

                                menu3 = false;


                                JPanel panel = new JPanel(new GridLayout(5, 2));
                                JTextField fullNameField = new JTextField();
                                JTextField usernameField = new JTextField();
                                JPasswordField passwordField = new JPasswordField();
                                JTextField emailField = new JTextField();
                                JTextField numberField = new JTextField();

                                panel.add(noEdit("Full Name:"));
                                panel.add(fullNameField);
                                panel.add(noEdit("Username:"));
                                panel.add(usernameField);
                                panel.add(noEdit("Password:"));
                                panel.add(passwordField);
                                panel.add(noEdit("Email:"));
                                panel.add(emailField);
                                panel.add(noEdit("Phone Number:"));
                                panel.add(numberField);

                                int loginResult = JOptionPane.showConfirmDialog(null, panel, "Create Account",
                                        JOptionPane.OK_CANCEL_OPTION);

                                if (loginResult == JOptionPane.OK_OPTION) {
                                    fullName = fullNameField.getText();
                                    String username = usernameField.getText();
                                    String password = new String(passwordField.getPassword());
                                    String email = emailField.getText();
                                    String number = numberField.getText();

                                    createAccount(testOption, fullName, username, password, email, number, scan);

                                }
                                else {
                                    //JOptionPane.showMessageDialog(null, "Thank you for using Dentist Office!");
                                    menu(scan);
                                    return;
                                }
                            } catch (NumberFormatException e) {
                                JOptionPane.showMessageDialog(null, "Please choose a valid choice.", "Dentist Office", JOptionPane.ERROR_MESSAGE);
                                menu3 = true;
                            }

                        } while (menu3);
                        break;

                    case 2: // logout
                        JOptionPane.showMessageDialog(null, "Thank you for using Dentist Office!", "Dentist Office", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Thank you for using Dentist Office!", "Dentist Office", JOptionPane.INFORMATION_MESSAGE);
                        break;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number.", "Dentist Office", JOptionPane.ERROR_MESSAGE);
                menu1 = true;
            }
        } while (menu1);
    }

    public static void login(String fullName, int identity, String username, String password, Scanner scan) throws IOException {
        DentistOffice d = new DentistOffice("My Dentist Office");

        ArrayList<Doctor> readDoctorList;
        readDoctorList = d.readDoctors();

        d.setDoctorList(readDoctorList);


        if (checkAccount(username, password)) {
            int welcome = JOptionPane.showConfirmDialog(null, "Welcome!", "Dentist Office", JOptionPane.OK_CANCEL_OPTION);
            if (welcome != JOptionPane.OK_OPTION) {
                menu(scan);
                return;
            }
            boolean menu2 = false;
            do {
                switch (identity) {
                    case 1:
                        Patient p = new Patient(fullName); // continue as a patient
                        p.go(scan, readDoctorList, d);
                        break;
                    case 2:
                        // continue as a doctor
                        String doctorName;
                        boolean menu3 = true;

                        do {
                            String[] userMenu = {"Add Doctor", "Remove Doctor", "View Approved Appointments", "View Pending Appointments",
                                    "Approve Appointment", "Decline Appointment", "Reschedule Appointment", "View Statistics", "Import Calendar",
                                    "Log Out"};
                            String userOption;

                            try {
                                userOption = (String) JOptionPane.showInputDialog(null, "Choose an option",
                                        "Menu", JOptionPane.QUESTION_MESSAGE, null, userMenu, userMenu[0]);
                                if ((userOption == null) || (userOption.isEmpty())) {
                                    //JOptionPane.showMessageDialog(null, "Thank you for using Dentist Office!");
                                    menu(scan);
                                    return;
                                }



                                int testOption;
                                if (userOption.equals("Add Doctor")) {
                                    testOption = 1;
                                } else if (userOption.equals("Remove Doctor")) {
                                    testOption = 2;
                                } else if (userOption.equals("View Approved Appointments")) {
                                    testOption = 3;
                                }else if (userOption.equals("View Pending Appointments")) {
                                    testOption = 4;
                                }else if (userOption.equals("Approve Appointment")) {
                                    testOption = 5;
                                }else if (userOption.equals("Decline Appointment")) {
                                    testOption = 6;
                                }else if (userOption.equals("Reschedule Appointment")) {
                                    testOption = 7;
                                } else if (userOption.equals("View Statistics")) {
                                    testOption = 8;
                                }else if (userOption.equals("Import Calendar")) {
                                    testOption = 9;
                                } else {
                                    testOption = 10;
                                }

                                switch (userOption) {
                                    case "Add Doctor":
                                        JPanel panel = new JPanel(new GridLayout(1, 1));
                                        JTextField fullNameField = new JTextField();


                                        panel.add(noEdit("Enter the new doctor's full name:"));
                                        panel.add(fullNameField);

                                        int loginResult = JOptionPane.showConfirmDialog(null, panel, "Add Doctor",
                                                JOptionPane.OK_CANCEL_OPTION);

                                        if (loginResult == JOptionPane.OK_OPTION) {
                                            fullName = fullNameField.getText();

                                            Doctor addDoctor = new Doctor(fullName);
                                            d.addDoctor(addDoctor);
                                        }
                                        else if (loginResult == JOptionPane.CANCEL_OPTION) {
                                            JOptionPane.showMessageDialog(null, "Back to menu:");
                                        }
                                        break;
                                    case "Remove Doctor":
                                        JPanel panel1 = new JPanel(new GridLayout(1, 1));
                                        JTextField fullNameField1 = new JTextField();


                                        panel1.add(noEdit("Enter the doctor's full name:"));
                                        panel1.add(fullNameField1);

                                        int loginResult1 = JOptionPane.showConfirmDialog(null, panel1, "Remove Doctor",
                                                JOptionPane.OK_CANCEL_OPTION);

                                        if (loginResult1 == JOptionPane.OK_OPTION) {
                                            fullName = fullNameField1.getText();
                                            Doctor deleteDoctor = new Doctor(fullName);
                                            d.deleteDoctor(deleteDoctor);
                                        }
                                        else if (loginResult1 == JOptionPane.CANCEL_OPTION) {
                                            JOptionPane.showMessageDialog(null, "Back to menu:");
                                        }
                                        break;
                                    case "View Approved Appointments":
                                        d.viewApproved();
                                        break;
                                    case "View Pending Appointments":
                                        d.viewPending();
                                        break;
                                    case "Approve Appointment":
                                        String[] pendingAppointments = d.viewPending();
                                        String approveOption;
                                        if (pendingAppointments.length != 0) {
                                            approveOption = (String) JOptionPane.showInputDialog(null, "Which appointment would you like to approve?",
                                                    "Approve appointment", JOptionPane.QUESTION_MESSAGE, null, pendingAppointments,
                                                    pendingAppointments[0]);
                                            if ((approveOption == null) || (approveOption.isEmpty())) {
                                                //JOptionPane.showMessageDialog(null, "Thank you for using Dentist Office!");
                                                menu3 = true;
                                                break;
                                            } else {
                                                d.approveAppointment(approveOption);
                                                JOptionPane.showMessageDialog(null, "Appointment approved.", "Approve appointment",
                                                        JOptionPane.INFORMATION_MESSAGE);
                                            }
                                        }
                                        break;

                                    case "Decline Appointment":
                                        String[] pendingAppointments1 = d.viewPending();
                                        String declineOption;
                                        if (pendingAppointments1.length != 0) {
                                            declineOption = (String) JOptionPane.showInputDialog(null, "Which appointment would you like to decline?",
                                                    "Decline appointment", JOptionPane.QUESTION_MESSAGE, null, pendingAppointments1,
                                                    pendingAppointments1[0]);
                                            if ((declineOption == null) || (declineOption.isEmpty())) {
                                                //JOptionPane.showMessageDialog(null, "Thank you for using Dentist Office!");
                                                menu3 = true;
                                            } else {
                                                d.declineAppointment(declineOption);
                                                JOptionPane.showMessageDialog(null, "Appointment declined.", "Decline appointment",
                                                        JOptionPane.INFORMATION_MESSAGE);
                                            }
                                        }
                                        break;

                                    case "Reschedule Appointment":
                                        d.rescheduleAppointment(scan);
                                        break;
                                    case "View Statistics":
                                        OurStatistics.dentistOfficeDashboard(d, scan);
                                        break;
                                    case "Import Calendar":
                                        try {
                                            JOptionPane.showMessageDialog(null, "Please format your .csv file by rows in orders of [1] Doctor " +
                                                    "[2] Date (Month/Day/Year) [3] Start Time [4] End Time [5] Max Attendees");

                                            JPanel panel2 = new JPanel(new GridLayout(1, 1));
                                            JTextField pathField = new JTextField();


                                            panel2.add(noEdit("Enter a filepath:"));
                                            panel2.add(pathField);

                                            int loginResult2 = JOptionPane.showConfirmDialog(null, panel2, "Import Calendar",
                                                    JOptionPane.OK_CANCEL_OPTION);

                                            if (loginResult2 == JOptionPane.OK_OPTION) {
                                                String path = pathField.getText();

                                                File file = new File(path);
                                                if (!file.exists()) {
                                                    throw new FileNotFoundException();
                                                }
                                                MyCalendar c = new MyCalendar(path);
                                                ArrayList<Doctor> addD = c.importCalendar();
                                                for (int j = 0; j < addD.size(); j++) {
                                                    d.addDoctor(addD.get(j));
                                                }
                                            }
                                            else if (loginResult2 == JOptionPane.CANCEL_OPTION) {
                                                JOptionPane.showMessageDialog(null, "Back to Menu:", "Error", JOptionPane.ERROR_MESSAGE);
                                            }
                                        } catch (FileNotFoundException e) {
                                            JOptionPane.showMessageDialog(null, "Please enter a valid file path.", "Import Calendar", JOptionPane.ERROR_MESSAGE);
                                        }
                                        break;
                                    case "Log Out":
                                        JOptionPane.showMessageDialog(null, "You have logged out.");
                                        menu(scan);
                                        menu3 = false;
                                        break;
                                    default:
                                        JOptionPane.showMessageDialog(null, "Please select a valid choice.", "Dentist Office", JOptionPane.ERROR_MESSAGE);
                                        break;
                                }


                            } catch (NumberFormatException e) {
                                JOptionPane.showMessageDialog(null, "Please select a valid choice.", "Dentist Office", JOptionPane.ERROR_MESSAGE);
                            }

                        } while (menu3);
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Please select a valid choice.", "Dentist Office", JOptionPane.ERROR_MESSAGE);
                        menu2 = true;
                        break;
                }

            } while (menu2);
        } else {
            JOptionPane.showMessageDialog(null, "Error! Account doesn't exist.", "Dentist Office", JOptionPane.ERROR_MESSAGE);
            menu(scan);
        }

    }

    public static void createAccount(int identity, String fullName, String username,
                                     String password, String email, String phoneNumber, Scanner scan) {
        try {
            File f = new File("accounts.txt"); //creates accounts file
            FileOutputStream fos = new FileOutputStream(f, true);
            PrintWriter pw = new PrintWriter(fos);
            //username is FIRST, password is LAST - more convenient to check
            if (checkAccount(username, password)) {
                JOptionPane.showMessageDialog(null, "Error! Account doesn't exist.", "Create an Account", JOptionPane.ERROR_MESSAGE);
            } else {
                //adding the account details to the file
                pw.println(fullName + "," + username + "," + password + "," + email + "," + phoneNumber);
                int success  = JOptionPane.showConfirmDialog(null, "Account successfully created!", "Create an Account", JOptionPane.OK_CANCEL_OPTION);
                if (success != JOptionPane.OK_OPTION) {
                    menu(scan);
                    return;
                }
            }
            pw.close();
            login(fullName, identity, username, password, scan);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //checks if the account exists or not
    public static boolean checkAccount(String username, String password) {
        boolean exists = false;
        try {
            ArrayList<String[]> list = new ArrayList<String[]>();
            BufferedReader bfr = new BufferedReader(new FileReader("accounts.txt"));
            String line = bfr.readLine();
            // creates array to store each detail separately
            // index: 0 - full name, 1 - username, 2 - password, 3 - email, 4 - phone number
            String[] commas = new String[5];
            while (line != null) {
                commas = line.split(",", 6);
                list.add(commas);
                line = bfr.readLine();
            }
            bfr.close();

            // checks if username and password exists in the array
            for (int i = 0; i < list.size(); i++) {
                if (username.equals(list.get(i)[1]) && password.equals(list.get(i)[2])) {
                    return true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return exists;
    }

    public static int numPending() throws IOException {
        int num = 0;
        BufferedReader reader = new BufferedReader(new FileReader("pending.txt"));
        String line;

        while ((line = reader.readLine()) != null) {
            num++;
        }

        reader.close();
        return num;
    }


}
