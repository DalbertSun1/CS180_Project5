

import java.io.*;
import java.net.Socket;
import java.util.*;

/**
 * Project 5
 * Dentist Office Calendar Marketplace
 *
 * @author Dalbert Sun, Vihaan Chadha, Jack White, Himaja Narajala, Aaryan Bondre
 * @version November 13th, 2023
 */

public class Login {
    public static void main(String[] args, DentistClient client) throws IOException {
        Scanner scan = new Scanner(System.in);
        System.out.println("Welcome to the Dentist's Office!");
        menu(scan, client);
    }

    public static void menu(Scanner scan, DentistClient client) throws IOException {
        boolean menu1 = false; //counter to rerun the loop if invalid choice is entered
        do {
            System.out.println("Menu\n1. Log in\n2. Create an account");
            try {
                String input1 = scan.nextLine();
                int login = Integer.parseInt(input1);

                String fullName = "";

                switch (login) {
                    case 1: // log in
                        boolean menu2 = false;
                        do {
                            menu2 = false;
                            System.out.println("Log in as\n1. Patient\n2. Dentist Office\n3. Back");
                            try {
                                String input2 = scan.nextLine();
                                int identity2 = Integer.parseInt(input2);

                                if (identity2 == 1 || identity2 == 2) {
                                    System.out.println("Enter your user name:");
                                    String username2 = scan.nextLine();
                                    System.out.println("Enter your password:");
                                    String password2 = scan.nextLine();
                                    postLoginMenu(fullName, identity2, username2, password2, scan, client);
                                } else if (identity2 == 3) {
                                    menu1 = true;
                                } else {
                                    System.out.println("Please enter a valid choice.");
                                    menu2 = true;
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Please enter an integer.");
                                menu2 = true;
                            }
                        } while (menu2);
                        break;
                    case 2: // create an account
                        boolean menu3 = false;
                        do {
                            menu3 = false;
                            System.out.println("Create an account as\n1. Patient\n2. Doctor\n3. Back");
                            try {
                                String input2 = scan.nextLine();
                                int identity = Integer.parseInt(input2);

                                if (identity == 1 || identity == 2) {
                                    System.out.println("Enter your full name:");
                                    fullName = scan.nextLine();
                                    System.out.println("Enter a user name:");
                                    String username = scan.nextLine();
                                    System.out.println("Enter a password:");
                                    String password = scan.nextLine();
                                    System.out.println("Enter your email:");
                                    String email = scan.nextLine();
                                    System.out.println("Enter your phone number:");
                                    String phoneNumber = scan.nextLine();
                                    clientCreateAccount(fullName, username, password, email, phoneNumber, scan, client);
                                } else if (identity == 3) {
                                    menu1 = true;
                                } else {
                                    System.out.println("Please enter a valid choice.");
                                    menu3 = true;
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Please enter an integer.");
                                menu3 = true;
                            }

                        } while (menu3);
                        break;
                    default: // invalid choice
                        System.out.println("Please enter a valid choice.");
                        menu1 = true;
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter an integer.");
                menu1 = true;
            }
        } while (menu1);
    }

    public static void postLoginMenu(String fullName, int identity, String username, String password, Scanner scan, DentistClient client) throws IOException {

        DentistOffice d = new DentistOffice("My Dentist Office");
        if (clientAuthenticate(username, password, client)) {
            System.out.println("Welcome!");
            // continue as a doctor or patient
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
                                        client.println("addDoctor::" + doctorName);
                                        if (client.readLine().equals("true")) {
                                            System.out.println("Successfully added Doctor");
                                        }
                                        break;
                                    case 2:
                                        System.out.println("Enter the doctor's full name: ");
                                        doctorName = scan.nextLine();
                                        client.println("removeDoctor::" + doctorName);
                                        if (client.readLine().equals("true")) {
                                            System.out.println("Successfully removed Doctor");
                                        }
                                        break;
                                    case 3:
                                        d.clientReadDoctorFile(client);
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
                                        menu(scan, client);
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
    public static void clientCreateAccount(String fullName, String username,
                                     String password, String email, String phoneNumber, Scanner scan, DentistClient client) {
        try {

            if (clientAuthenticate(username, password, client)) {
                System.out.println("Error! Account already exists");
            } else {
                //adding the account details to the file
                client.println("createAccount::" + fullName + "," + username + "," + password + "," + email + "," + phoneNumber);
                System.out.println("Account successfully created!");
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
