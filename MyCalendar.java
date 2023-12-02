import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.*;
import java.util.ArrayList;
/**
 * Project 5
 * Dentist Office Calendar Marketplace
 *
 * @author Dalbert Sun, Vihaan Chadha, Jack White, Himaja Narajala, Aaryan Bondre
 * @version November 13th, 2023
 */

public class MyCalendar {
    private Day[] days;
    private String file;
    private String month;
    private int numberMonth;
    private int year;
    static JFrame frame;;
    static JFrame newFrame = new JFrame();

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

            ArrayList<String> doctors = new ArrayList<String>();
            Container content = newFrame.getContentPane();
            content.removeAll();
            newFrame.setTitle("Make An Appointment");
            newFrame.setSize(600, 400);

            JLabel label = new JLabel("July " + date + ", 2023");
            label.setHorizontalAlignment(SwingConstants.CENTER);
            newFrame.add(label, BorderLayout.NORTH);

            JPanel buttons = new JPanel();
            buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS));



            String line;
            while ((line = bf.readLine()) != null) {
                doctors.add("Dr. " + line);
            }

            int height = 300/doctors.size();

            for (int i = 0; i < doctors.size(); i++) {
                JButton button = new JButton(doctors.get(i));
                button.setPreferredSize(new Dimension(600, height));
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

    public String format(Day day, ArrayList<Doctor> doctors) {
        int maxLength = Integer.MIN_VALUE;
        String result = "[";
//    	for (int i = 0; i < doctors.size(); i++) {
//    		if (("Dr." + doctors.get(i).getName()).length() > maxLength) {
//    			maxLength = ("Dr." + doctors.get(i).getName()).length();
//    		}
//    	}
//    	maxLength+=2;
//    	if (maxLength % 2 == 1) {
//    		maxLength++;
//    	}
//    	if (maxLength > 4) {
//    		for (int i = 0; i<(maxLength-4)/2; i++) {
//        		result += " ";
//        	}
//
//    	}
//    	if (day.getDate() < 10) {
//    		result += "0" + day.getDate();
//    	} else {
//    		result += day.getDate();
//    	}
//    	if (maxLength > 4) {
//    		for (int i = 0; i<(maxLength-4)/2; i++) {
//        		result += " ";
//        	}
//
//    	}
//    	result += "]\n";
//    	for (int i = 0; i < doctors.size(); i++) {
//    		result += "[Dr." + doctors.get(i);
//    		for (int j = 0; j < (maxLength-2) - ("Dr." + doctors.get(i)).length(); j++) {
//    			result += " ";
//
//    		}
//    		result += "]\n";
//    	}

        return result;
    }

//    public void initializeDay(int dayIndex) {
//        if (dayIndex >= 0 && dayIndex <= days.length) {
//            String selectedDay = days[dayIndex];
//            showDoctor(selectedDay);
//        } else {
//            System.out.println("Invalid day selection");
//        }
//    }


    public void showDoctor(String selectedDay) {
        System.out.println("The doctors avaliabile on " + selectedDay + ":");
    }

}
