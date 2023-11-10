import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import java.io.FileWriter;
public class CalendarFromFile {
    // a customer calls this class, which will convert the given calendar into a CSV file
    // conversely, a CSV file can be converted into a calendar
    public static void writeCalendar(MyCalendar calender, String filepath) {
        try (FileWriter writer = new FileWriter(new File(filepath));
        CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {
            csvPrinter.printRecord(person.getName(), person.getAge(), person.getEmail());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static MyCalendar readCalendar(String filepath) {

    }

}