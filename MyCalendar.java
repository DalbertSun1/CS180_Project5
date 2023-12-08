import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.*;
import java.util.ArrayList;

public class MyCalendar extends Login {
    boolean menu = false;

    JFrame request;
    int apptIndex = -1;
    int day;
    String username;
    JTextField name;
    JFrame frame;;
    JFrame newFrame = new JFrame();
    JFrame timeFrame = new JFrame();
    Doctor selectedDoctor;

    JFrame pending = new JFrame();
    ArrayList<Doctor> doctors;

    Appointment selectedAppointment;

    JButton nameButton;

    private  Day[] days;
    private String file;
    private String month;
    private int numberMonth;
    private int year;

    public MyCalendar(String file) {
        this.file = file;
    }

//    public Calendar(String doctor) {
//        this.days = new String[31];
//        for (int i = 0; i < 31; i++) {
//            int dayNumber = i++;
//            this.days[i] = String.format("%02d", dayNumber);
//        }
//    }

    public MyCalendar() {
        days = null;
        file = "";
    }

    public MyCalendar(int howManyDays) {
        days = new Day[howManyDays];
        for (int i = 1; i <= howManyDays; i++) {
            days[i - 1] = new Day(i);
        }

    }

    public void Calendar(String file) {
        this.file = file;
    }

    public ArrayList<Doctor> importCalendar() {
        ArrayList<Doctor> doctors = new ArrayList<Doctor>();
        String result = "";
        File f = new File(file);
        FileReader fr;
        String line = "";
        try {
            fr = new FileReader(f);
            BufferedReader bf = new BufferedReader(fr);

            int d = 0;
            //int i = 0;
            boolean stop = false;
            while ((line = bf.readLine()) != null && stop == false) {

                String[] entry = line.split(",");


                if (entry.length == 0) {
                    stop = true;
                    break;
                }
                //System.out.println(entry[0]);
                Doctor addDoctor = new Doctor(entry[0]);
//				for (int i = 0; i < entry.length; i++) {
//					System.out.print(entry[i] + ", ");
//				}
//				System.out.println();
                String m = entry[1].substring(0, entry[1].indexOf('/'));
                checkMonth(Integer.parseInt(m));
                if (Integer.parseInt(entry[1].split("/")[2]) < 2000) {
                    year = 2000 + Integer.parseInt(entry[1].split("/")[2]);
                } else {
                    year = Integer.parseInt(entry[1].split("/")[2]);

                }
                String temp = entry[1].substring(entry[1].indexOf('/') + 1);
                d = Integer.parseInt(temp.substring(0, temp.indexOf('/'))) - 1;
                if (checkDuplicateDoc(days[d], entry[0]) == true) {
                    days[d].getIndividualDoctor(days[d].getIndividualDoctorIndex(entry[0])).addAppointment(new Appointment(entry[2] + " - " + entry[3]));
                }
                if (days[d] != null) {
                    if (checkDuplicateDoc(days[d], entry[0]) == false) {

                        days[d].addDoctor(addDoctor);
                        doctors.add(addDoctor);
                        //i++;

                        Appointment a = new Appointment(entry[2] + " - " + entry[3]);
                        //a.setMaxAttendees(Integer.parseInt(entry[4]));
                        days[d].getIndividualDoctor(days[d].getIndividualDoctorIndex(entry[0])).addAppointment(a);
                    }
                } else {
                    days[d] = new Day(d + 1);
                    if (checkDuplicateDoc(days[d], entry[0]) == false) {
                        days[d].addDoctor(addDoctor);
                        doctors.add(addDoctor);
                        //i++;
                        Appointment a = new Appointment(entry[2] + " - " + entry[3]);
                        //a.setMaxAttendees(Integer.parseInt(entry[4]));
                        days[d].getIndividualDoctor(days[d].getIndividualDoctorIndex(entry[0])).addAppointment(a);
                    }
                }


            }
            //System.out.print(days[d].showDoctorList());


        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        return doctors;
    }

    public void checkMonth(int month) {
        if (days == null) {
            if (month == 1) {
                days = new Day[numberMonth = 31];
                this.month = "JANUARY";
            }
            if (month == 2) {
                days = new Day[numberMonth = 28];
                this.month = "FEBRUARY";
            }
            if (month == 3) {
                days = new Day[numberMonth = 31];
                this.month = "MARCH";
            }
            if (month == 4) {
                days = new Day[numberMonth = 30];
                this.month = "APRIL";
            }
            if (month == 5) {
                days = new Day[numberMonth = 31];
                this.month = "MAY";
            }
            if (month == 6) {
                days = new Day[numberMonth = 30];
                this.month = "JUNE";
            }
            if (month == 7) {
                days = new Day[numberMonth = 31];
                this.month = "JULY";
            }
            if (month == 8) {
                days = new Day[numberMonth = 31];
                this.month = "AUGUST";
            }
            if (month == 9) {
                days = new Day[numberMonth = 30];
                this.month = "SEPTEMBER";
            }
            if (month == 10) {
                days = new Day[numberMonth = 31];
                this.month = "OCTOBER";
            }
            if (month == 11) {
                days = new Day[numberMonth = 30];
                this.month = "NOVEMBER";
            }
            if (month == 12) {
                days = new Day[numberMonth = 31];
                this.month = "DECEMBER";
            }
        }


    }

    public boolean checkDuplicateDoc(Day day, String doctor) {
        boolean d = false;
        if (day != null) {
            if (day.getDoctors() != null) {
                for (int i = 0; i < day.getDoctors().size(); i++) {
                    if (day.getDoctors().get(i).getName().equals(doctor)) {
                        d = true;
                    }
                }
            }


        }


        return d;
    }

    public Day[] getDays() {
        return days;
    }


    public Day getIndividualDay(int day) {
        return days[day - 1];

    }

    public void setDays(int howManyDays) {
        days = new Day[howManyDays];
        for (int i = 1; i <= howManyDays; i++) {
            days[i - 1] = new Day(i);
        }
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getMonth() {
        return month;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getYear() {
        return year;
    }

    public void setFile(String file) {
        this.file = file;
    }

//    public String getDoctor() {
//        return doctor;
//    }
//
//    public void setDoctor(String doctor) {
//        this.doctor = doctor;
//    }


    public void viewCalendar() {
        frame = new JFrame("Calendar");

        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container content = frame.getContentPane();


        JPanel title = new JPanel();
        title.setLayout(new BoxLayout(title, BoxLayout.Y_AXIS));
        JPanel label = createPanel("July 2023");
        title.add(label);

        for (int i = 0; i < 5; i++) {
            JPanel rowPanel = new JPanel();
            for (int j = 1; j < 8; j++) {
                JButton tempButton = assignButton(i, j);

                tempButton.setPreferredSize(new Dimension(75, 25));
                rowPanel.add(tempButton);


            }
            title.add(rowPanel);
        }
        JPanel select = createPanel("--Select a day to view available doctors--");
        title.add(select);


        content.add(BorderLayout.NORTH, title);

        frame.setVisible(true);

    }

    private  JPanel createPanel(String text) {
        JPanel panel = new JPanel();

        // Add some components to the panel
        JLabel label = new JLabel(text);
        panel.add(label);

        return panel;
    }

    ActionListener actionListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() instanceof JButton source) {
                if (isInt(source.getText())) {
                    openNewFrame("July " + source.getText() + ", 2023", Integer.parseInt(source.getText()));
                } else if (source.getText().substring(0,4).equals("Dr. ")){
                    for (Doctor d : doctors){
//                        System.out.println("d.getname: " + d.getName());
//                        System.out.println("source.getText().substring(4): " + source.getText().substring(4));
                        if (d.getName().equals(source.getText().substring(4))) {

                            selectedDoctor = d;
                        }
                    }
                    openDoctorFrame(source.getText().substring(4));


                } else if (source.getText().length() > 12 && isInt(source.getText().substring(11,12))) {
                    for (int i = 0; i < selectedDoctor.getAppointments().size(); i++){
                        Appointment a = selectedDoctor.getAppointments().get(i);
//                        System.out.println("a.getTime(): " + a.getTime());
//                        System.out.println("source.getText(): " + source.getText());
                        if (a.getTime().equals(source.getText().substring(11))) { apptIndex = i; selectedAppointment = a; }
                    }
                    openTimeFrame(source.getText());
                } else if (source == nameButton){
                    frame.dispose();
                    timeFrame.dispose();
                    newFrame.dispose();
                    pending.dispose();

                    selectedAppointment.bookAppointment(name.getText());
                    username = name.getText();
                    selectedDoctor.getAppointments().set(apptIndex, selectedAppointment);

                    makeAppointmentLeBron(selectedAppointment.getTime(), selectedDoctor.getName());

                    request = new JFrame();
                    request.setSize(600, 100);
                    request.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    JPanel panel = new JPanel();
                    panel.add(new JLabel("Your appointment has been requested!"));


                    JButton ok = new JButton("Exit");
                    ok.addActionListener(actionListener);
                    panel.add(ok);
                    request.add(panel);

                    request.setLocationRelativeTo(null);
                    request.setVisible(true);
                } else if (source.getText().equals("Exit")) {
                    //System.out.println("menu is true fucker");
                    request.dispose();


                    menu = true;
                }


            }

        }
    };


    private JButton assignButton(int i, int j) {
        JButton tempButton;
//        System.out.println(((7 * i) + j));
        if (((7 * i) + j) > 31) {
            tempButton = new JButton("--");

        } else if (((7 * i) + j) < 10) {

            tempButton = new JButton("0" + ((7 * i) + j));
        } else {
            String a = "";
            a += ((7 * i) + j);
            tempButton = new JButton(a);
        }
        tempButton.addActionListener(actionListener);



        return tempButton;
    }
    private void openNewFrame(String title, int date) {
        File f = new File("doctors.txt");
        try {
            FileReader fr = new FileReader(f);
            BufferedReader bf = new BufferedReader(fr);
            day = date;
            doctors = days[date-1].getDoctors();
            Container content = newFrame.getContentPane();
            content.removeAll();
            newFrame.setTitle("Make An Appointment: July " + date + ", 2023");
            newFrame.setSize(600, 400);
//
//            JLabel label = new JLabel("July " + date + ", 2023");
//            label.setHorizontalAlignment(SwingConstants.CENTER);
//            newFrame.add(label, BorderLayout.NORTH);


            JPanel buttons = new JPanel();
            buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS));





            String line;
            while ((line = bf.readLine()) != null) {
                if (!line.equals("")) {

                    doctors.add(new Doctor(line));
                }
            }
            days[date-1].setDoctors(doctors);
            int height = 0;
            if (doctors.size() < 5) {
                height = 70;
            } else {
                height = 400/doctors.size();
            }

            for (int i = 0; i < doctors.size(); i++) {
                JButton button = new JButton("Dr. " + doctors.get(i).getName());
                button.setAlignmentX(Component.CENTER_ALIGNMENT);
                button.addActionListener(actionListener);
                button.setMaximumSize(new java.awt.Dimension(300, height));

                buttons.add(button);

            }


            newFrame.add(buttons);



            newFrame.setLocationRelativeTo(null);
            newFrame.setVisible(true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void openDoctorFrame(String doctor) {
        //File f = new File("doctors.txt");

        Container content = timeFrame.getContentPane();
        content.removeAll();
        timeFrame.setTitle("Make An Appointment: Dr. " + doctor);
        timeFrame.setSize(600, 400);


//            FileReader fr = new FileReader(f);
//            BufferedReader bf = new BufferedReader(fr);
        ArrayList<Appointment> times = new ArrayList<Appointment>();
        for (int i = 9; i < 11; i++){
            String a = "";
            a+= (i + ":00 AM - " + (i+1) + ":00 AM");
            Appointment ap = new Appointment(a);
            ap.setCustomerName(username);
            ap.setDoctor(selectedDoctor.getName());
            ap.setDay(day);

            selectedDoctor.getAppointments().add(ap);
        }
        Appointment apt = new Appointment("11:00 AM - 12:00 PM");
        apt.setCustomerName(username);
        apt.setDoctor(selectedDoctor.getName());
        apt.setDay(day);
        selectedDoctor.getAppointments().add(apt);
        Appointment apt2 = new Appointment("12:00 PM - 1:00 PM");
        apt2.setCustomerName(username);
        apt2.setDoctor(selectedDoctor.getName());
        apt2.setDay(day);
        selectedDoctor.getAppointments().add(apt2);
        for (int i = 1; i < 6; i++){
            String a = "";
            a+= (i + ":00 PM - " + (i+1) + ":00 PM");
            Appointment ap = new Appointment(a);
            ap.setCustomerName(username);
            ap.setDoctor(selectedDoctor.getName());
            ap.setDay(day);
            selectedDoctor.getAppointments().add(ap);
        }

        JPanel buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS));





        int height = 400/9;


        for (int i = 0; i < 9; i++) {

            if (!selectedDoctor.getAppointments().get(i).toString().substring(0,1).equals("B") && !selectedDoctor.getAppointments().get(i).toString().substring(0,1).equals("P")){
                JButton button;
                button = new JButton(selectedDoctor.getAppointments().get(i).toString());
                button.addActionListener(actionListener);
                button.setAlignmentX(Component.CENTER_ALIGNMENT);

                button.setMaximumSize(new java.awt.Dimension(300, height));
                buttons.add(button);
            } else {
                JLabel button;
                button = new JLabel(selectedDoctor.getAppointments().get(i).toString());

                button.setAlignmentX(Component.CENTER_ALIGNMENT);

                button.setMaximumSize(new java.awt.Dimension(300, height));
                buttons.add(button);
            }




        }


        timeFrame.add(buttons);



        timeFrame.setLocationRelativeTo(null);
        timeFrame.setVisible(true);

    }

    public  boolean isInt(String str) {
        try {
            // Attempt to parse the string as a int
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            // If an exception is caught, it's not a int
            return false;
        }
    }
    private void openTimeFrame(String time){
        pending.setTitle("Enter your name:");
        pending.setSize(300, 100);

        JPanel panel = new JPanel();
        name = new JTextField(10);

        panel.add(name);

        nameButton = new JButton("Enter");
        nameButton.addActionListener(actionListener);
        panel.add(nameButton);
        pending.add(panel);
        pending.setLocationRelativeTo(null);
        pending.setVisible(true);

    }
    public void showDoctor(String selectedDay) {
        System.out.println("The doctors avaliabile on " + selectedDay + ":");
    }

    public void makeAppointmentLeBron(String time, String nameDoctor) {
        try {
            File f = new File("pending.txt"); //creates pending appointments file
            FileOutputStream fos = new FileOutputStream(f, true);
            PrintWriter pw = new PrintWriter(fos);
            pw.println(username + "," + day + "," + time + "," + nameDoctor);
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
