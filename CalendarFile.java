
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.*;

import java.util.ArrayList;
import java.util.Arrays;
public class CalendarFile {
    // a customer calls this class, which will convert the given calendar into a CSV file
    // conversely, a CSV file can be converted into a calendar

    // a calendar holds days, which holds doctors, which holds appointments
    public static void writeCalendar(MyCalendar calendar, String filepath) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(new File(filepath)))) {
            int i = 0;
            writer.println("dayIndex, date, startDoctor, doctorName, startApt, time, isAvailable, isBooked, customerName, endApt, endDoctor, ");
            for (Day thisDay : calendar.getDays()) {
                // each Day marked with a new line
                StringBuilder line = new StringBuilder();
                line.append(i + "," + thisDay.getDate() + ",");
                i++;
                for (Doctor doctor : thisDay.getDoctors()) {
                    line.append("startDoctor,");
                    line.append(doctor.getName() + ",");
                    for (Appointment apt : doctor.getAppointments()) {
                        line.append("startApt,");
                        // add time
                        line.append(apt.getTime().toString() + "," + apt.getTime().isAvailable() + ",");
                        if (apt.isBooked()) {
                            // add isBooked, add customerName
                            line.append("true," + apt.getCustomerName() + ",");
                        } else {
                            line.append("false,null,");
                        }

                        line.append("endApt,");
                    }
                    line.append("endDoctor,");
                }
                writer.println(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static MyCalendar readCalendar(String filepath) {
        ArrayList<Day> days = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(filepath)))) {
            String line;
            // iterate through days
            while ((line = reader.readLine()) != null) { // while there are days left
                //"dayIndex, date, startDoctor, doctorName, startApt, time, isAvailable, isBooked, customerName, endApt, endDoctor,;
                ArrayList<String> values = new ArrayList<>(Arrays.asList(line.split(",")));
                Day thisDay = new Day(Integer.parseInt(values.get(1))); // initialized day with


                days.add(thisDay);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}