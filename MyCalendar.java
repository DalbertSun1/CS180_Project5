import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MyCalendar {
    private Day[] days;
    private String file;

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
    
    public MyCalendar(String file) {
    	this.file = file;
    }
    
    public String importCalendar() {
    	String result = "";
    	File f = new File(file);
    	FileReader fr;
    	String line = "";
		try {
			fr = new FileReader(f);
			BufferedReader bf = new BufferedReader(fr);
			int d = 0;
			int i = 0;
			while ((line = bf.readLine()) != null) {
				String[] entry = line.split(",");
				checkMonth(Integer.parseInt(entry[1].substring(0, entry[1].indexOf('/'))));
				
				d = Integer.parseInt(entry[1].substring(entry[1].indexOf('/') + 1, entry[1].indexOf('/') + 3));
				if (checkDuplicateDoc(days[d], entry[0]) == true) {
					days[d].getIndividualDoctor(days[d].getIndividualDoctorIndex(entry[0])).addAppointment(new Appointment(new Time(entry[2] + " - " + entry[3], true)));
				}	
				if (days[d] != null) {
					if (checkDuplicateDoc(days[d], entry[0]) == false) {

						days[d].addDoctor(new Doctor(entry[0]));

						Appointment a = new Appointment(new Time(entry[2] + " - " + entry[3], true));
						a.setMaxAttendees(Integer.parseInt(entry[4]));
						days[d].getIndividualDoctor(i).addAppointment(a);
					}
				} else {
					days[d] = new Day(d);
					if (checkDuplicateDoc(days[d], entry[0]) == false) {
						days[d].addDoctor(new Doctor(entry[0]));
						Appointment a = new Appointment(new Time(entry[2] + " - " + entry[3], true));
						a.setMaxAttendees(Integer.parseInt(entry[4]));
						days[d].getIndividualDoctor(i).addAppointment(a);
					}
				}
				
				
				
				
				i++;
				
			}
			//System.out.print(days[d].showDoctorList());
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return result;
    }
    
    public void checkMonth(int month) {
    	if (days == null) {
    		if (month == 1) { days = new Day[31]; }
        	if (month == 2) { days = new Day[28]; }
        	if (month == 3) { days = new Day[31]; }
        	if (month == 4) { days = new Day[30]; }
        	if (month == 5) { days = new Day[31]; }
        	if (month == 6) { days = new Day[30]; }
        	if (month == 7) { days = new Day[31]; }
        	if (month == 8) { days = new Day[31]; }
        	if (month == 9) { days = new Day[30]; }
        	if (month == 10) { days = new Day[31]; }
        	if (month == 11) { days = new Day[30]; }
        	if (month == 12) { days = new Day[31]; }
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

    public String viewCalendar() {
        String result = "";

        result += ("--------------------JULY 2023-------------------\n");
        result += ("[ MO ] [ TU ] [ WE ] [ TH ] [ FR ] [ SA ] [ SU ]\n");
        result += ("------------------------------------------------\n");
        result += ("[ -- ] [ -- ] [ -- ] [ -- ] [ -- ] [ 01 ] [ 02 ]\n");
        result += ("[ 03 ] [ 04 ] [ 05 ] [ 06 ] [ 07 ] [ 08 ] [ 09 ]\n");
        result += ("[ 10 ] [ 11 ] [ 12 ] [ 13 ] [ 14 ] [ 15 ] [ 16 ]\n");
        result += ("[ 17 ] [ 18 ] [ 19 ] [ 20 ] [ 21 ] [ 22 ] [ 23 ]\n");
        result += ("[ 24 ] [ 25 ] [ 26 ] [ 27 ] [ 28 ] [ 29 ] [ 30 ]\n");
        result += ("[ 31 ] [ -- ] [ -- ] [ -- ] [ -- ] [ -- ] [ -- ]\n");

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
