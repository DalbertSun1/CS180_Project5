import org.junit.Test;
import org.junit.After;

import java.lang.reflect.Field;

import org.junit.Assert;
import org.junit.Before;
import org.junit.experimental.runners.Enclosed;
import org.junit.rules.Timeout;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runner.notification.Failure;

import javax.swing.*;
import java.io.*;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

import static org.junit.Assert.*;

/**
 * Project 4 -- RunLocalTest.java
 * A framework to run public test cases.
 *
 * @author Dalbert Sun, Vihaan Chadha, Jack White, Himaja Narajala, Aaryan Bondre
 * @version November 13th, 2023
 */

@RunWith(Enclosed.class)
public class RunLocalTest {
    public static void main(String[] args) {
        runTest(JUnitCore.runClasses(DentistOfficeTest.class), "Dentist Office");
        runTest(JUnitCore.runClasses(LoginTest.class), "Login");
        runTest(JUnitCore.runClasses(PatientTest.class), "Patient");
        runTest(JUnitCore.runClasses(DoctorTest.class), "Doctor");
        runTest(JUnitCore.runClasses(DayTest.class), "Day");
        runTest(JUnitCore.runClasses(AppointmentTest.class), "Appointment");
        runTest(JUnitCore.runClasses(OurStatisticsTest.class), "Our Statistics");
        runTest(JUnitCore.runClasses(MyCalendarTest.class), "My Calendar");
    }

    public static void runTest(Result result, String testName) {
        if (result.wasSuccessful()) {
            System.out.println("Excellent - " + testName + " test ran successfully");
        } else {
            for (Failure failure : result.getFailures()) {
                System.out.println(failure.toString());
            }
        }
    }

    /**
     * Project 4 -- DentistOfficeTest.java
     * A set of public test cases.
     *
     * @author Dalbert Sun, Vihaan Chadha, Jack White, Himaja Narajala, Aaryan Bondre
     * @version November 13th, 2023
     */

    public static class DentistOfficeTest {
        private Doctor doctor;
        private Appointment appointment;

        @Before
        public void setUp() {
            DentistOffice dentistOffice = new DentistOffice("My Dentist Office");
            doctor = new Doctor("Dr. Smith");
            appointment = new Appointment("9:00 AM - 10:00 AM");
        }

        @Test
        public void testAddAppointment() {
            doctor.addAppointment(appointment);
            assertFalse(doctor.getAppointments().isEmpty());
            assertTrue(doctor.getAppointments().contains(appointment));
        }

        @Test
        public void testBookAppointment() {
            String customerName = "John Doe";
            appointment.bookAppointment(customerName);
            assertTrue(appointment.isBooked());
            assertEquals(customerName, appointment.getCustomerName());
        }
    }

    /**
     * Project 4 -- PatientTest.java
     * A set of public test cases.
     *
     * @author Dalbert Sun, Vihaan Chadha, Jack White, Himaja Narajala, Aaryan Bondre
     * @version November 13th, 2023
     */

    public static class PatientTest {
        @Test
        public void testAddAppointment() {
            Patient patient = new Patient("John Doe");
            Appointment appointment = new Appointment("9:00 AM - 10:00 AM");

            assertEquals(0, patient.getAppointments().size());

            patient.addAppointment(appointment);

            assertEquals(1, patient.getAppointments().size());
            assertTrue(patient.getAppointments().contains(appointment));
        }

        @Test
        public void testRemoveAppointment() {
            Patient patient = new Patient("Jane Doe");
            Appointment appointment = new Appointment("10:00 AM - 11:00 AM");

            assertEquals(0, patient.getAppointments().size());

            patient.addAppointment(appointment);

            assertEquals(1, patient.getAppointments().size());
            assertTrue(patient.getAppointments().contains(appointment));

            patient.removeAppointment(appointment);

            assertEquals(0, patient.getAppointments().size());
            assertFalse(patient.getAppointments().contains(appointment));
        }

        @Test
        public void testViewAppointments() {
            Patient patient = new Patient("Alice");
            Appointment appointment1 = new Appointment("1:00 PM - 2:00 PM");
            Appointment appointment2 = new Appointment("2:00 PM - 3:00 PM");

            patient.addAppointment(appointment1);
            patient.addAppointment(appointment2);

            String expectedOutput = "Appointments for Alice:\n" +
                    "Available: 1:00 PM - 2:00 PM\n" +
                    "Available: 2:00 PM - 3:00 PM\n";

            assertEquals(expectedOutput, patient.viewAppointments());
        }

        @Test
        public void testRescheduleAppointment() throws IOException {
            Patient patient = new Patient("Bob");
            Appointment originalAppointment = new Appointment("3:00 PM - 4:00 PM");


            // Add an original appointment
            patient.addAppointment(originalAppointment);

            // Mock user input for rescheduling
            int newDay = 15;
            String newTime = "4:00 PM - 5:00 PM";
            originalAppointment.setDay(newDay);
            originalAppointment.setTime(newTime);

            // Reschedule the appointment

            // Validate the appointment has been rescheduled
            assertEquals(newDay, originalAppointment.getDay());
            assertEquals(newTime, originalAppointment.getTime());
        }
    }

    /**
     * Project 4 -- LoginTest.java
     * A set of public test cases.
     *
     * @author Dalbert Sun, Vihaan Chadha, Jack White, Himaja Narajala, Aaryan Bondre
     * @version November 13th, 2023
     */

    public static class LoginTest {
        @Test
        public void testCheckAccount() {
            // Assuming that "accounts.txt" is a test file with known user accounts
            String testFile = "test_accounts.txt";
            // createTestAccountsFile(testFile);

            // Test when the account exists
            assertTrue(Login.checkAccount("testUser1", "testPassword1"));

            // Test when the account does not exist
            assertFalse(Login.checkAccount("nonexistentUser", "nonexistentPassword"));

            // Cleanup: delete the test file
            File file = new File(testFile);
            file.deleteOnExit();
        }

        private void createTestAccountsFile(String filename) {
            // Create a test file with sample accounts
            try {
                File testFile = new File(filename);
                testFile.createNewFile();
                Login.createAccount(1, "John Doe", "testUser1", "testPassword1", "john.doe@example.com", "9876543210", new Scanner("1"));
                Login.createAccount(2, "Dr. Smith", "testUser2", "testPassword2", "smith@example.com", "1239874560", new Scanner("2"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Project 4 -- DoctorTest.java
     * A set of public test cases.
     *
     * @author Dalbert Sun, Vihaan Chadha, Jack White, Himaja Narajala, Aaryan Bondre
     * @version November 13th, 2023
     */

    public static class DoctorTest {

        @Test
        public void testGetName() {
            // Test the getName method
            Doctor doctor = new Doctor("Dr. Smith");
            assertEquals("Dr. Smith", doctor.getName());
        }

        @Test
        public void testSetName() {
            // Test the setName method
            Doctor doctor = new Doctor("Dr. Smith");
            doctor.setName("Dr. Johnson");
            assertEquals("Dr. Johnson", doctor.getName());
        }

        @Test
        public void testAddAppointment() {
            // Test the addAppointment method
            Doctor doctor = new Doctor("Dr. Smith");
            Appointment appointment = new Appointment("9:00 AM - 10:00 AM");
            doctor.addAppointment(appointment);
            assertTrue(doctor.getAppointments().contains(appointment));
        }

        @Test
        public void testRemoveAppointment() {
            // Test the removeAppointment method
            Doctor doctor = new Doctor("Dr. Smith");
            Appointment appointment = new Appointment("9:00 AM - 10:00 AM");
            doctor.addAppointment(appointment);
            doctor.removeAppointment(appointment);
            assertFalse(doctor.getAppointments().contains(appointment));
        }

        @Test
        public void testIsAvailable() {
            // Test the isAvailable method
            Doctor doctor = new Doctor("Dr. Smith");
            Appointment availableAppointment = new Appointment("9:00 AM - 10:00 AM");
            Appointment bookedAppointment = new Appointment("10:00 AM - 11:00 AM");
            bookedAppointment.bookAppointment("John Doe");
            doctor.addAppointment(availableAppointment);
            doctor.addAppointment(bookedAppointment);

            assertTrue(doctor.isAvailable(availableAppointment));
            assertFalse(doctor.isAvailable(bookedAppointment));
        }

        @Test
        public void testFindAppointment() {
            // Test the findAppointment method
            Doctor doctor = new Doctor("Dr. Smith");
            Appointment appointment = new Appointment("9:00 AM - 10:00 AM");
            appointment.bookAppointment("John Doe");
            doctor.addAppointment(appointment);

            assertEquals(appointment, doctor.findAppointment("John Doe"));
            assertNull(doctor.findAppointment("Jane Doe"));
        }
    }

    /**
     * Project 4 -- DayTest.java
     * A set of public test cases.
     *
     * @author Dalbert Sun, Vihaan Chadha, Jack White, Himaja Narajala, Aaryan Bondre
     * @version November 13th, 2023
     */

    public static class DayTest {

        @Test
        public void testAddAndRemoveDoctor() {
            // Test the addDoctor and removeDoctor methods
            Day day = new Day(1);
            Doctor doctor1 = new Doctor("Dr. John");
            Doctor doctor2 = new Doctor("Dr. Jane");

            day.addDoctor(doctor1);
            assertEquals(1, day.getDoctors().size());

            day.addDoctor(doctor2);
            assertEquals(2, day.getDoctors().size());

            day.removeDoctor(doctor1);
            assertEquals(1, day.getDoctors().size());
        }
    }

    /**
     * Project 4 -- AppointmentTest.java
     * A set of public test cases.
     *
     * @author Dalbert Sun, Vihaan Chadha, Jack White, Himaja Narajala, Aaryan Bondre
     * @version November 13th, 2023
     */

    public static class AppointmentTest {

        @Test
        public void testBookAppointment() {
            // Test the bookAppointment method
            Appointment appointment = new Appointment("9:00 AM - 10:00 AM");
            assertFalse(appointment.isBooked());

            appointment.bookAppointment("John Doe");

            assertTrue(appointment.isBooked());
            assertEquals("John Doe", appointment.getCustomerName());
        }

        @Test
        public void testCancelAppointment() {
            // Test the cancelAppointment method
            Appointment appointment = new Appointment("9:00 AM - 10:00 AM");
            appointment.bookAppointment("John Doe");

            assertTrue(appointment.isBooked());

            appointment.cancelAppointment();

            assertFalse(appointment.isBooked());
            assertNull(appointment.getCustomerName());
        }

        @Test
        public void testToString() {
            // Test the toString method
            Appointment bookedAppointment = new Appointment("9:00 AM - 10:00 AM");
            bookedAppointment.bookAppointment("John Doe");

            assertEquals("Booked: 9:00 AM - 10:00 AM with John Doe", bookedAppointment.toString());

            Appointment availableAppointment = new Appointment("11:00 AM - 12:00 PM");
            assertEquals("Available: 11:00 AM - 12:00 PM", availableAppointment.toString());
        }
    }

    public static class OurStatisticsTest {
        @Test
        public void testDentistOfficeDashboard() {
            // Create a sample DentistOffice instance
            DentistOffice dentistOffice = createSampleDentistOffice();
            String input = "4\n";
            ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
            System.setIn(inputStream);

            // Test the dentistOfficeDashboard method

            // Reset System.in after the test
            System.setIn(System.in);
        }

        @Test
        public void testPatientDashboard() {
            // Create a sample DentistOffice instance
            DentistOffice dentistOffice = createSampleDentistOffice();
            String input = "3\n";
            ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
            System.setIn(inputStream);

            // Test the patientDashboard method

            // Reset System.in after the test
            System.setIn(System.in);
        }

        // Helper method to create a sample DentistOffice instance
        private DentistOffice createSampleDentistOffice() {
            // Return the DentistOffice instance
            String name = "My Dentist Office";
            return new DentistOffice(name);
        }
    }

    public static class MyCalendarTest {
        @Test
        public void testCheckDuplicateDoc() {
            MyCalendar myCalendar = new MyCalendar();
            Day day = new Day(1);
            Doctor doctor = new Doctor("Dr. John Doe");
            day.addDoctor(doctor);

            // Test when the doctor is not a duplicate
            assertFalse(myCalendar.checkDuplicateDoc(day, "Dr. Jane Doe"));

            // Test when the doctor is a duplicate
            assertTrue(myCalendar.checkDuplicateDoc(day, "Dr. John Doe"));
        }
    }

}

