import java.io.*;
import java.net.Socket;
import java.sql.SQLOutput;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Project 5
 * Dentist Office Calendar Marketplace
 *
 * @author Dalbert Sun, Vihaan Chadha, Jack White, Himaja Narajala, Aaryan Bondre
 * @version December 11th, 2023
 */


public class Login extends JComponent {

    static Scanner scan;

    public static void main(String[] args, DentistClient client) throws IOException {
        int welcome1  = JOptionPane.showConfirmDialog(null, "Welcome to Dentist Office!", "Welcome", JOptionPane.OK_CANCEL_OPTION);
        if (welcome1 != JOptionPane.OK_OPTION) {
            JOptionPane.showMessageDialog(null, "Thank you for using the Dentist Office", "Dentist Office", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        scan = new Scanner(System.in);
        try {
            menu(scan, client);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static JTextField noEdit(String prompt) {
        JTextField textField = new JTextField(prompt);
        textField.setEditable(false);
        return textField;
    }
    public void start(DentistClient client) throws IOException {
        menu(scan, client);
    }
    public static void menu(Scanner scan, DentistClient client) throws IOException {
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
                            String[] userMenu = {"Patient", "DentistOffice"};
                            String userOption;
                            try {

                                userOption = (String) JOptionPane.showInputDialog(null, "Log in as",
                                        "Dentist Office", JOptionPane.QUESTION_MESSAGE, null, userMenu, userMenu[0]);
                                if ((userOption == null) || (userOption.isEmpty())) {
//                                    JOptionPane.showMessageDialog(null, "Thank you for using Dentist Office!");
                                    menu(scan, client);
                                    return;
                                }

                                int testOption;
                                if (userOption.equals("Patient")) {
                                    testOption = 1;
                                } else if (userOption.equals("DentistOffice")) {
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

                                    postLoginMenu(fullName, testOption, username, password, scan, client);

                                } else if (loginResult == JOptionPane.CANCEL_OPTION) {
                                    menu(scan, client);
                                }
                                else {
                                    menu(scan, client);
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
                                    menu(scan, client);
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

                                    clientCreateAccount(testOption, fullName, username, password, email, number, scan, client);

                                }
                                else {
                                    //JOptionPane.showMessageDialog(null, "Thank you for using Dentist Office!");
                                    menu(scan, client);
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

    public static void postLoginMenu(String fullName, int identity, String username, String password, Scanner scan, DentistClient client) throws IOException {
        DentistOffice d = new DentistOffice("My Dentist Office", client);
        if (clientAuthenticate(username, password, client)) {
            int welcome = JOptionPane.showConfirmDialog(null, "Welcome!", "Dentist Office", JOptionPane.OK_CANCEL_OPTION);
            if (welcome != JOptionPane.OK_OPTION) {
                menu(scan, client);
                return;
            }
            boolean menu2 = false;
            do {
                switch (identity) {
                    case 1:
                        Patient p = new Patient(fullName); // continue as a patient
                        p.go(scan, d.getDoctorList(), d, client);
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
                                    menu(scan, client);
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

                                            client.println("addDoctor::" + fullName);
                                            if (client.readLine().equals("true")) {
                                                // TODO - dialog that confirms doctor was added successfully;
                                                JOptionPane.showMessageDialog(null, "Successfully Added Doctor.", "Add Doctor",
                                                        JOptionPane.INFORMATION_MESSAGE);
                                            }
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
                                            client.println("removeDoctor::" + fullName);
                                            if (client.readLine().equals("true")) {
                                                JOptionPane.showMessageDialog(null, "Successfully Removed Doctor.", "Remove Doctor",
                                                        JOptionPane.INFORMATION_MESSAGE);
                                                // TODO TURN PRINTLN INTO DIALOG
                                            } else {
                                                JOptionPane.showMessageDialog(null, "The doctor you specified does not exist.", "Remove Doctor",
                                                        JOptionPane.INFORMATION_MESSAGE);
                                            }
                                        }
                                        else if (loginResult1 == JOptionPane.CANCEL_OPTION) {
                                            JOptionPane.showMessageDialog(null, "Back to menu:");
                                        }
                                        break;
                                    case "View Approved Appointments":
                                        d.clientReadDoctorFile(client);
                                        break;
                                    case "View Pending Appointments":
                                        d.clientReadDoctorPendingFile(client);
                                        break;
                                    case "Approve Appointment":
                                        String[] pendingAppointments = d.clientGetPendingAppointments(client);
                                        String approveOption;
                                        if (pendingAppointments.length != 0) {
                                            approveOption = (String) JOptionPane.showInputDialog(null, "Which appointment would you like to approve?",
                                                    "Approve appointment", JOptionPane.QUESTION_MESSAGE, null, pendingAppointments,
                                                    pendingAppointments[0]);
                                            if ((approveOption == null) || (approveOption.isEmpty())) {
                                                //JOptionPane.showMessageDialog(null, "Thank you for using Dentist Office!");
                                                menu3 = true;
                                            } else {
                                                DentistOffice.clientApproveAppointment(approveOption, client);
                                                JOptionPane.showMessageDialog(null, "Appointment approved.", "Approve appointment",
                                                        JOptionPane.INFORMATION_MESSAGE);

                                            }
                                        }
                                        break;

                                    case "Decline Appointment":
                                        String[] pendingAppointments1 = d.clientGetPendingAppointments(client);
                                        String declineOption;
                                        if (pendingAppointments1.length != 0) {
                                            declineOption = (String) JOptionPane.showInputDialog(null, "Which appointment would you like to decline?",
                                                    "Decline appointment", JOptionPane.QUESTION_MESSAGE, null, pendingAppointments1,
                                                    pendingAppointments1[0]);
                                            if ((declineOption == null) || (declineOption.isEmpty())) {
                                                //JOptionPane.showMessageDialog(null, "Thank you for using Dentist Office!");
                                                menu3 = true;
                                            } else {
                                                DentistOffice.clientDeclineAppointment(declineOption, client);
                                                JOptionPane.showMessageDialog(null, "Appointment declined.", "Decline appointment",
                                                        JOptionPane.INFORMATION_MESSAGE);

                                            }
                                        }
                                        break;
                                    case "Reschedule Appointment":
                                        //d.rescheduleAppointment(scan);
                                        if (DentistOffice.clientRescheduleAppointment(scan, client)) {
                                            JOptionPane.showMessageDialog(null, "Rescheduled successfully.");
                                        }
                                        else {
                                            JOptionPane.showMessageDialog(null, "Could not reschedule appointment.", "Error",
                                                    JOptionPane.ERROR_MESSAGE);
                                        }
                                        break;
                                    case "View Statistics":
                                        OurStatistics.dentistOfficeDashboard(d, scan, client);
                                        break;
                                    case "Import Calendar":
                                        //try {
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

                                            client.println("importCalendar::" + path);
                                            if (client.readLine().equals("true")) {
                                                System.out.println("Calendar imported successfully!");
                                            }
                                        }
                                        else if (loginResult2 == JOptionPane.CANCEL_OPTION) {
                                            JOptionPane.showMessageDialog(null, "Back to Menu:", "Error", JOptionPane.ERROR_MESSAGE);
                                        }
                                        /*}
                                        catch (FileNotFoundException e) {
                                            JOptionPane.showMessageDialog(null, "Please enter a valid file path.", "Import Calendar", JOptionPane.ERROR_MESSAGE);
                                        }*/

                                        break;
                                    case "Log Out":
                                        JOptionPane.showMessageDialog(null, "You have logged out.");
                                        menu(scan, client);

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
            menu(scan, client);
        }

    }

    public static boolean clientAuthenticate(String username, String password, DentistClient client) throws IOException {

        // send to server to authenticate
        client.println("authenticate::" + username + "," + password);

        // receive server response
        if (client.readLine().equals("true")) {
            return true;
        } else {
            return false;
        }
    }

    //creates a new account
    //prints error if account already exists - to do
    public static void clientCreateAccount(int identity, String fullName, String username,
                                           String password, String email, String phoneNumber, Scanner scan, DentistClient client) {
        try {

            if (clientAuthenticate(username, password, client)) {
                JOptionPane.showMessageDialog(null, "Error! Account already exists.", "Create an Account", JOptionPane.ERROR_MESSAGE);
            } else {
                //adding the account details to the file
                client.println("createAccount::" + fullName + "," + username + "," + password + "," + email + "," + phoneNumber);
                if (Boolean.parseBoolean(client.readLine())) {
                    int success  = JOptionPane.showConfirmDialog(null, "Account successfully created!", "Create an Account", JOptionPane.OK_CANCEL_OPTION);
                    if (success != JOptionPane.OK_OPTION) {
                        menu(scan, client);
                        return;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Account could not be created.");
                }

            }

            menu(scan, client);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static boolean serverCreateAccount(String fullName, String username,
                                              String password, String email, String phoneNumber) {
        try {
            File f = new File("accounts.txt"); //creates accounts file
            FileOutputStream fos = new FileOutputStream(f, true);
            PrintWriter pw = new PrintWriter(fos);
            //username is FIRST, password is LAST - more convenient to check
            //if the username and password match a case in accounts.txt, then new account will not be created

            pw.println(fullName + "," + username + "," + password + "," + email + "," + phoneNumber);
            System.out.println("Account successfully created!");
            pw.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
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

    public static int numPending(DentistClient client) throws IOException {
        int num = 0;
        for (String apt : DentistOffice.clientGetPendingAppointments(client)) {
            num++;
        }
        return num;
    }


}
