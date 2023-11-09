import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class Login {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Welcome to the Dentist's Office!");

        boolean menu1 = false; //counter to rerun the loop if invalid choice is entered
        do {
            System.out.println("Menu\n1. Log in\n2. Create an account");
            int login = scan.nextInt();
            scan.nextLine();

            switch (login) {
                case 1 : // log in
                    System.out.println("Log in as\n1. Patient\n2. Doctor");
                    int identity2 = scan.nextInt();
                    scan.nextLine();
                    System.out.println("Enter your user name:");
                    String username2 = scan.nextLine();
                    System.out.println("Enter your password:");
                    String password2 = scan.nextLine();
                    login(identity2, username2, password2);
                    break;
                case 2 : // create an account
                    System.out.println("Create an account as\n1. Patient\n2. Doctor");
                    int identity = scan.nextInt();
                    scan.nextLine();
                    System.out.println("Enter your full name:");
                    String fullName = scan.nextLine();
                    System.out.println("Enter a user name:");
                    String username = scan.nextLine();
                    System.out.println("Enter a password:");
                    String password = scan.nextLine();
                    System.out.println("Enter your email:");
                    String email = scan.nextLine();
                    System.out.println("Enter your phone number:");
                    String phoneNumber = scan.nextLine();
                    createAccount(identity, fullName, username, password, email, phoneNumber);
                    break;
                default : // invalid choice
                    System.out.println("Please enter a valid choice.");
                    menu1 = true;
                    break;
            }
        } while (menu1);

    }

    public static void login(int identity, String username, String password) {
        if (checkAccount(username, password)) {
            System.out.println("Welcome!");
            // continue as a doctor or patient
            boolean menu2 = false;
            do {
                switch (identity) {
                    case 1:
                        // continue as a patient
                        break;
                    case 2:
                        // continue as a doctor
                        break;
                    default:
                        System.out.println("Please enter a valid choice.");
                        menu2 = true;
                        break;
                }
            } while (menu2);
        } else {
            System.out.println("Error! Account does not exist");
        }

    }
    //creates a new account
    //prints error if account already exists - to do
    public static void createAccount(int identity, String fullName, String username,
                                     String password, String email, String phoneNumber) {
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
            login(identity, username, password);

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
