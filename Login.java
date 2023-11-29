import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.FileReader;
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

public class Login extends JComponent {

    JButton loginButton;

    JTextField usernameText;
    JTextField passwordText;
    Login paint;


    public static void main(String[] args) throws IOException {
        JOptionPane.showMessageDialog(null, "Welcome to Dentist Office!", "Welcome",
                JOptionPane.INFORMATION_MESSAGE);
        Scanner scan = new Scanner(System.in);
        menu(scan);

    }

    public static void menu(Scanner scan) throws IOException {
        boolean menu1 = false; //counter to rerun the loop if invalid choice is entered
        do {
            String[] options = {"Log in", "Create an Account"};
            int result = JOptionPane.showOptionDialog(null, "Choose an option.", "Dentist Office",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, options, options[1]);
            try {

                String fullName = "";

                switch (result) {
                    case 0: // log in
                        boolean menu2 = false;
                        do {
                            String[] userMenu = {"Patient", "Doctor"};
                            String userOption;
                            try {
                                do {
                                    userOption = (String) JOptionPane.showInputDialog(null, "Log in as",
                                            "Dentist Office", JOptionPane.QUESTION_MESSAGE, null, userMenu, userMenu[0]);
                                    if ((userOption == null) || (userOption.isEmpty())) {
                                        JOptionPane.showMessageDialog(null, "Please select a valid option!",
                                                "Dentist Office", JOptionPane.ERROR_MESSAGE);
                                    }
                                } while ((userOption == null) || (userOption.isEmpty()));

                                int testOption;
                                if (userOption.equals("Patient")) {
                                    testOption = 1;
                                } else if (userOption.equals("Doctor")) {
                                    testOption = 2;
                                } else {
                                    testOption = 3;
                                }

                                menu2 = false;
                                // Get username and password in a single JOptionPane
                                JPanel panel = new JPanel(new GridLayout(2, 2));
                                JTextField usernameField = new JTextField();
                                JPasswordField passwordField = new JPasswordField();
                                panel.add(new JTextField("Username:"));
                                panel.add(usernameField);
                                panel.add(new JTextField("Password:"));
                                panel.add(passwordField);

                                int loginResult = JOptionPane.showConfirmDialog(null, panel, "Log in",
                                        JOptionPane.OK_CANCEL_OPTION);

                                if (loginResult == JOptionPane.OK_OPTION) {
                                    String username = usernameField.getText();
                                    String password = new String(passwordField.getPassword());

                                    // Call the login method with username and password
                                    login(fullName, testOption, username, password, scan);

                                } else {
                                    JOptionPane.showMessageDialog(null, "Login canceled.");
                                }
                            } catch (NumberFormatException e) {
                                JOptionPane.showMessageDialog(null, "Please choose a valid choice.", "Dentist Office", JOptionPane.ERROR_MESSAGE);
                                menu2 = true; // Repeat the outer loop
                            }

                        } while (menu2);
                        break;

                    case 1: // create an account
                        boolean menu3 = false;
                        do {
                            String[] userMenu = {"Patient", "Doctor"};
                            String userOption;
                            try {
                                do {
                                    userOption = (String) JOptionPane.showInputDialog(null, "Create an account as",
                                            "Dentist Office", JOptionPane.QUESTION_MESSAGE, null, userMenu, userMenu[0]);
                                    if ((userOption == null) || (userOption.isEmpty())) {
                                        JOptionPane.showMessageDialog(null, "Please select a valid option!",
                                                "Dentist Office", JOptionPane.ERROR_MESSAGE);
                                    }
                                } while ((userOption == null) || (userOption.isEmpty()));


                                int testOption;
                                if (userOption.equals("Patient")) {
                                    testOption = 1;
                                } else if (userOption.equals("Doctor")) {
                                    testOption = 2;
                                } else {
                                    testOption = 3;
                                }

                                menu3 = false;
                                //System.out.println("Create an account as\n1. Patient\n2. Doctor\n3. Back");
//                                String input2 = scan.nextLine();
//                                int identity = Integer.parseInt(input2);

                                JPanel panel = new JPanel(new GridLayout(5, 2));
                                JTextField fullNameField = new JTextField();
                                JTextField usernameField = new JTextField();
                                JPasswordField passwordField = new JPasswordField();
                                JTextField emailField = new JTextField();
                                JTextField numberField = new JTextField();

                                panel.add(new JTextField("Full Name:"));
                                panel.add(fullNameField);
                                panel.add(new JTextField("Username:"));
                                panel.add(usernameField);
                                panel.add(new JTextField("Password:"));
                                panel.add(passwordField);
                                panel.add(new JTextField("Email:"));
                                panel.add(emailField);
                                panel.add(new JTextField("Phone Number:"));
                                panel.add(numberField);

                                int loginResult = JOptionPane.showConfirmDialog(null, panel, "Create Account",
                                        JOptionPane.OK_CANCEL_OPTION);

                                if (loginResult == JOptionPane.OK_OPTION) {
                                    fullName = fullNameField.getText();
                                    String username = usernameField.getText();
                                    String password = new String(passwordField.getPassword());
                                    String email = emailField.getText();
                                    String number = numberField.getText();

                                    // Call the login method with username and password
                                    createAccount(testOption, fullName, username, password, email, number, scan);

                                } else {
                                    JOptionPane.showMessageDialog(null, "Creating Account canceled.");
                                }
                            } catch (NumberFormatException e) {
                                JOptionPane.showMessageDialog(null, "Please choose a valid choice.", "Dentist Office", JOptionPane.ERROR_MESSAGE);
                                menu3 = true; // Repeat the outer loop
                            }

                        } while (menu3);
                        break;
                    default: // invalid choice
                        JOptionPane.showMessageDialog(null, "Please choose a valid choice.", "Dentist Office", JOptionPane.ERROR_MESSAGE);
                        menu1 = true;
                        break;
                }
            } catch (NumberFormatException e) {
                e.printStackTrace(); // For debugging purposes
                JOptionPane.showMessageDialog(null, "Please enter a valid number.", "Dentist Office", JOptionPane.ERROR_MESSAGE);
                menu1 = true;
            }
        } while (menu1);
    }

    public static void login(String fullName, int identity, String username, String password, Scanner scan) throws IOException {
        // TODO: Read pending.txt and approved.txt and assign arraylist of appointments
        DentistOffice d = new DentistOffice("My Dentist Office");

        ArrayList<Doctor> readDoctorList;
        readDoctorList = d.readDoctors();

        d.setDoctorList(readDoctorList);
        if (checkAccount(username, password)) {
            System.out.println("Welcome!");
            // continue as a doctor or patient
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
                            System.out.println("1. Add Doctor \n2. Remove Doctor \n3. View Approved Appointments " +
                                    "\n4. View Pending Appointments \n5. Approve Appointment \n" +
                                    "6. Decline Appointment \n7. Reschedule Appointment \n8. View Statistics \n9. Import Calendar\n" +
                                    "10. Log out");
                            try {
                                String input1 = scan.nextLine();
                                int choice = Integer.parseInt(input1);

                                switch (choice) {
                                    case 1:
                                        System.out.println("Enter the new doctor's full name: ");
                                        doctorName = scan.nextLine();
                                        Doctor addDoctor = new Doctor(doctorName);
                                        d.addDoctor(addDoctor);
                                        break;
                                    case 2:
                                        System.out.println("Enter the doctor's full name: ");
                                        doctorName = scan.nextLine();
                                        Doctor deleteDoctor = new Doctor(doctorName);
                                        d.deleteDoctor(deleteDoctor);
                                        break;
                                    case 3:
                                        d.viewApproved();
                                        break;
                                    case 4:
                                        d.viewPending();
                                        break;
                                    case 5:
                                        int pending = d.viewPending();
                                        if (pending != 0) {
                                            try {
                                                System.out.println("Enter appointment number to approve: ");
                                                String input2 = scan.nextLine();
                                                int approveNum = Integer.parseInt(input2);

                                                if (approveNum > numPending()) {
                                                    System.out.println("Please enter a valid choice.");
                                                } else {
                                                    d.approveAppointment(approveNum);
                                                    System.out.println("Appointment approved!");
                                                }
                                            } catch (NumberFormatException e) {
                                                System.out.println("Please enter an integer.");
                                            }
                                        }

                                        break;
                                    case 6:
                                        int pending1 = d.viewPending();
                                        if (pending1 != 0) {
                                            try {
                                                System.out.println("Enter appointment number to decline: ");
                                                String input3 = scan.nextLine();
                                                int declineNum = Integer.parseInt(input3);

                                                if (declineNum > numPending()) {
                                                    System.out.println("Please enter a valid choice.");
                                                } else {
                                                    d.declineAppointment(declineNum);
                                                    System.out.println("Appointment declined!");
                                                }
                                            } catch (NumberFormatException e) {
                                                System.out.println("Please enter an integer.");
                                            }
                                        }

                                        break;
                                    case 7:
                                        d.rescheduleAppointment(scan);
                                        break;
                                    case 8:
                                        OurStatistics.dentistOfficeDashboard(d, scan);
                                        break;
                                    case 9:
                                        System.out.println("Please format your .csv file by rows in orders of [1] Doctor [2] Date (Month/Day/Year) [3] Start Time [4] End Time [5] Max Attendees");

                                        System.out.println("Enter a filepath:");
                                        String path = scan.nextLine();
                                        MyCalendar c = new MyCalendar(path);
                                        ArrayList<Doctor> addD = c.importCalendar();
                                        for (int j = 0; j < addD.size(); j++) {
                                            d.addDoctor(addD.get(j));
                                        }

                                    case 10:
                                        System.out.println("You have logged out.");
                                        menu(scan);
                                        menu3 = false;
                                        break;
                                    default:
                                        System.out.println("Please enter a valid choice.");
                                        break;
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Please enter an integer.");
                            }

                        } while (menu3);
                        break;
                    default:
                        System.out.println("Please enter a valid choice.");
                        menu2 = true;
                        break;
                }

            } while (menu2);
        } else {
            System.out.println("Error! Account does not exist");
            menu(scan);
        }

    }

    //creates a new account
    //prints error if account already exists - to do
    public static void createAccount(int identity, String fullName, String username,
                                     String password, String email, String phoneNumber, Scanner scan) {
        try {
            File f = new File("accounts.txt"); //creates accounts file
            FileOutputStream fos = new FileOutputStream(f, true);
            PrintWriter pw = new PrintWriter(fos);
            //username is FIRST, password is LAST - more convenient to check
            if (checkAccount(username, password)) {
                System.out.println("Error! Account already exists");
            } else {
                //adding the account details to the file
                pw.println(fullName + "," + username + "," + password + "," + email + "," + phoneNumber);
                System.out.println("Account successfully created!");
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
