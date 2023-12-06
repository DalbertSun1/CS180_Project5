import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.*;
import java.util.ArrayList;

public class MyCalendar {
    static boolean menu = false;
    static int day;
    static String username;
    static JTextField name;
    static JFrame frame;;
    static JFrame newFrame = new JFrame();
    static JFrame timeFrame = new JFrame();
    static Doctor selectedDoctor;

    static JFrame pending = new JFrame();
    static ArrayList<Doctor> doctors;

    static Appointment selectedAppointment;

    static JButton nameButton;

    private static Day[] days;
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

    private static JPanel createPanel(String text) {
        JPanel panel = new JPanel();

        // Add some components to the panel
        JLabel label = new JLabel(text);
        panel.add(label);

        return panel;
    }

    static ActionListener actionListener = new ActionListener() {

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
                    for (Appointment a : selectedDoctor.getAppointments()){
//                        System.out.println("a.getTime(): " + a.getTime());
//                        System.out.println("source.getText(): " + source.getText());
                        if (a.getTime().equals(source.getText().substring(11))) { selectedAppointment = a; }
                    }
                    openTimeFrame(source.getText());
                } else if (source == nameButton){
                    frame.dispose();
                    timeFrame.dispose();
                    newFrame.dispose();
                    pending.dispose();

                    selectedAppointment.bookAppointment(name.getText());
                    username = name.getText();
                    makeAppointmentLeBron(selectedAppointment.getDay(), selectedAppointment.getTime(), selectedDoctor.getName());

                    JFrame request = new JFrame();
                    request.setSize(600, 100);
                    request.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    JPanel panel = new JPanel();
                    panel.add(new JLabel("Your appointment has been requested!"));


                    JButton ok = new JButton("Back to Menu");
                    ok.addActionListener(actionListener);
                    panel.add(ok);
                    request.add(panel);

                    request.setLocationRelativeTo(null);
                    request.setVisible(true);
                } else if (source.getText().equals("Back to Menu")) {
                    System.out.println("menu is true fucker");

                    menu = true;
                }


            }

        }
    };


    private static JButton assignButton(int i, int j) {
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
    private static void openNewFrame(String title, int date) {
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
                doctors.add(new Doctor(line));
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
    private static void openDoctorFrame(String doctor) {
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
                selectedDoctor.getAppointments().add(new Appointment(a));
            }
            selectedDoctor.getAppointments().add(new Appointment("11:00 AM - 12:00 PM"));
            selectedDoctor.getAppointments().add(new Appointment("12:00 PM - 1:00 PM"));
            for (int i = 1; i < 6; i++){
                String a = "";
                a+= (i + ":00 PM - " + (i+1) + ":00 PM");
                selectedDoctor.getAppointments().add(new Appointment(a));
            }

            JPanel buttons = new JPanel();
            buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS));





            int height = 400/9;


            for (int i = 0; i < 9; i++) {
                JButton button = new JButton(selectedDoctor.getAppointments().get(i).toString());
                button.setAlignmentX(Component.CENTER_ALIGNMENT);
                if (!selectedDoctor.getAppointments().get(i).isBooked()){
                    button.addActionListener(actionListener);
                }

                button.setMaximumSize(new java.awt.Dimension(300, height));

                buttons.add(button);

            }


            timeFrame.add(buttons);



            timeFrame.setLocationRelativeTo(null);
            timeFrame.setVisible(true);

    }

    public static boolean isInt(String str) {
        try {
            // Attempt to parse the string as a int
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            // If an exception is caught, it's not a int
            return false;
        }
    }
    private static void openTimeFrame(String time){
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
    public boolean openMenu(){
        if (menu == true){
            menu = false;
            return true;
        } else {
            return false;
        }
    }
    public static void makeAppointmentLeBron(int date, String time, String nameDoctor) {
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














//        if (((7 * i) + j) == 1) {button1 = tempButton; button1.addActionListener(actionListener);}
//        if (((7 * i) + j) == 2) {button2 = tempButton; button2.addActionListener(actionListener);}
//        if (((7 * i) + j) == 3) {button3 = tempButton; button3.addActionListener(actionListener);}
//        if (((7 * i) + j) == 4) {button4 = tempButton; button4.addActionListener(actionListener);}
//        if (((7 * i) + j) == 5) {button5 = tempButton; button5.addActionListener(actionListener);}
//        if (((7 * i) + j) == 6) {button6 = tempButton; button6.addActionListener(actionListener);}
//        if (((7 * i) + j) == 7) {button7 = tempButton; button7.addActionListener(actionListener);}
//        if (((7 * i) + j) == 8) {button8 = tempButton; button8.addActionListener(actionListener);}
//        if (((7 * i) + j) == 9) {button9 = tempButton; button9.addActionListener(actionListener);}
//        if (((7 * i) + j) == 10) {button10 = tempButton; button10.addActionListener(actionListener);}
//        if (((7 * i) + j) == 11) {button11 = tempButton; button11.addActionListener(actionListener);}
//        if (((7 * i) + j) == 12) {button12 = tempButton; button12.addActionListener(actionListener);}
//        if (((7 * i) + j) == 13) {button13 = tempButton; button13.addActionListener(actionListener);}
//        if (((7 * i) + j) == 14) {button14 = tempButton; button14.addActionListener(actionListener);}
//        if (((7 * i) + j) == 15) {button15 = tempButton; button15.addActionListener(actionListener);}
//        if (((7 * i) + j) == 16) {button16 = tempButton; button16.addActionListener(actionListener);}
//        if (((7 * i) + j) == 17) {button17 = tempButton; button17.addActionListener(actionListener);}
//        if (((7 * i) + j) == 18) {button18 = tempButton; button18.addActionListener(actionListener);}
//        if (((7 * i) + j) == 19) {button19 = tempButton; button19.addActionListener(actionListener);}
//        if (((7 * i) + j) == 20) {button20 = tempButton; button20.addActionListener(actionListener);}
//        if (((7 * i) + j) == 21) {button21 = tempButton; button21.addActionListener(actionListener);}
//        if (((7 * i) + j) == 22) {button22 = tempButton; button22.addActionListener(actionListener);}
//        if (((7 * i) + j) == 23) {button23 = tempButton; button23.addActionListener(actionListener);}
//        if (((7 * i) + j) == 24) {button24 = tempButton; button24.addActionListener(actionListener);}
//        if (((7 * i) + j) == 25) {button25 = tempButton; button25.addActionListener(actionListener);}
//        if (((7 * i) + j) == 26) {button26 = tempButton; button26.addActionListener(actionListener);}
//        if (((7 * i) + j) == 27) {button27 = tempButton; button27.addActionListener(actionListener);}
//        if (((7 * i) + j) == 28) {button28 = tempButton; button28.addActionListener(actionListener);}
//        if (((7 * i) + j) == 29) {button29 = tempButton; button29.addActionListener(actionListener);}
//        if (((7 * i) + j) == 30) {button30 = tempButton; button30.addActionListener(actionListener);}
//        if (((7 * i) + j) == 31) {button31 = tempButton; button31.addActionListener(actionListener);}
//    static JButton button1;
//    static JButton button2;
//    static JButton button3;
//    static JButton button4;
//    static JButton button5;
//    static JButton button6;
//    static JButton button7;
//    static JButton button8;
//    static JButton button9;
//    static JButton button10;
//    static JButton button11;
//    static JButton button12;
//    static JButton button13;
//    static JButton button14;
//    static JButton button15;
//    static JButton button16;
//    static JButton button17;
//    static JButton button18;
//    static JButton button19;
//    static JButton button20;
//    static JButton button21;
//    static JButton button22;
//    static JButton button23;
//    static JButton button24;
//    static JButton button25;
//    static JButton button26;
//    static JButton button27;
//    static JButton button28;
//    static JButton button29;
//    static JButton button30;
//    static JButton button31;
