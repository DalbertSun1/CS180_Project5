// TODO: Make it so that appointments that have already been booked don't show up again
// TODO: Finish calendar csv file stuff
// TODO: Finish update appointment method for both patient and dentist office


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class Login {
    public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner(System.in);
        System.out.println("Welcome to the Dentist's Office!");
        menu(scan);
    }

    public static void menu(Scanner scan) throws IOException {
        boolean menu1 = false; //counter to rerun the loop if invalid choice is entered
        do {
            System.out.println("Menu\n1. Log in\n2. Create an account");
            int login = scan.nextInt();
            scan.nextLine();
            String fullName = "";

            switch (login) {
                case 1 : // log in
                    System.out.println("Log in as\n1. Patient\n2. Doctor");
                    int identity2 = scan.nextInt();
                    scan.nextLine();
                    System.out.println("Enter your user name:");
                    String username2 = scan.nextLine();
                    System.out.println("Enter your password:");
                    String password2 = scan.nextLine();
                    login(fullName, identity2, username2, password2, scan);
                    break;
                case 2 : // create an account
                    System.out.println("Create an account as\n1. Patient\n2. Doctor");
                    int identity = scan.nextInt();
                    scan.nextLine();
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
                    createAccount(identity, fullName, username, password, email, phoneNumber, scan);
                    break;
                default : // invalid choice
                    System.out.println("Please enter a valid choice.");
                    menu1 = true;
                    break;
            }
        } while (menu1);
    }

    public static void login(String fullName, int identity, String username, String password, Scanner scan) throws IOException {
        // TODO: Read pending.txt and approved.txt and assign arraylist of appointments
        DentistOffice d = new DentistOffice("My Dentist Office");

        ArrayList<Doctor> readDoctorList;
        readDoctorList = d.readDoctors();

        for (Doctor value : readDoctorList) {
            value.addAppointment(new Appointment("9:00 AM - 10:00 AM"));
            value.addAppointment(new Appointment("10:00 AM - 11:00 AM"));
            value.addAppointment(new Appointment("11:00 AM - 12:00 PM"));
            value.addAppointment(new Appointment("12:00 PM - 1:00 PM"));
            value.addAppointment(new Appointment("1:00 PM - 2:00 PM"));
            value.addAppointment(new Appointment("2:00 PM - 3:00 PM"));
            value.addAppointment(new Appointment("3:00 PM - 4:00 PM"));
            value.addAppointment(new Appointment("4:00 PM - 5:00 PM"));
            value.addAppointment(new Appointment("5:00 PM - 6:00 PM"));
        }

        d.setDoctorList(readDoctorList);
        if (checkAccount(username, password)) {
            System.out.println("Welcome!");
            // continue as a doctor or patient
            boolean menu2 = false;
            do {
                switch (identity) {
                    case 1:
                        Patient p = new Patient(fullName); // continue as a patient
                        p.go(scan, readDoctorList);
                        break;
                    case 2:
                        // continue as a doctor
                        String doctorName;
                        boolean menu3 = true;

                        do {
                            System.out.println("1. Add Doctor \n2. Remove Doctor \n3. View Approved Appointments " +
                                    "\n4. View Pending Appointments \n5. Approve Appointment \n6. Decline Appointment \n7. Reschedule Appointment \n8. Log out");
                            int choice = scan.nextInt();
                            scan.nextLine();

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
                                    System.out.println("Enter appointment number to approve: ");
                                    int approveNum = scan.nextInt();
                                    scan.nextLine();

                                    d.approveAppointment(approveNum);
                                    break;
                                case 6:
                                    System.out.println("Enter appointment number to decline: ");
                                    int declineNum = scan.nextInt();
                                    scan.nextLine();

                                    d.declineAppointment(declineNum);
                                    break;
                                case 7:
                                    System.out.println("Enter the doctor name");
                                    doctorName = scan.nextLine();

                                    // Find the doctor from the list to reschedule their appointment
                                    Doctor doctorReschedule = null;
                                    for (Doctor doctor : d.getDoctorList()) {
                                        if (doctor.getName().equalsIgnoreCase(doctorName)) {
                                            doctorReschedule = doctor;
                                            break;
                                        }
                                    }

                                    if (doctorReschedule != null) {
                                        System.out.println("Enter the old appointment:");
                                        String oldAppointment = scan.nextLine();
                                        System.out.println("Enter the new appointment:");
                                        String newAppointment = scan.nextLine();

                                        d.rescheduleAppointmentForSeller(doctorName, oldAppointment, newAppointment);

                                    } else {
                                        System.out.println("Doctor " + doctorName + " not found.");
                                    }
                                    break;
                                case 8:
                                    System.out.println("You have logged out.");
                                    menu(scan);
                                    menu3 = false;
                                    break;
                                default:
                                    System.out.println("Please enter a valid choice.");
                                    break;
                            }
                        } while (menu3);

                        break;
                    default:
                        System.out.println("Please enter a valid choice.");
                        menu2 = true;
                        break;
                }

                // TODO: Write new doctors and appointments to pending.txt and approved.txt from arraylist of doctors and appointments

            } while (menu2);
        } else {
            System.out.println("Error! Account does not exist");
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

}
