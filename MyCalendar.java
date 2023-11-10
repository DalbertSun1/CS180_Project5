import java.util.ArrayList;

public class MyCalendar {
    private Day[] days;
    //private String doctor; <- why tf do we need this

//    public Calendar(String doctor) {
//        this.days = new String[31];
//        for (int i = 0; i < 31; i++) {
//            int dayNumber = i++;
//            this.days[i] = String.format("%02d", dayNumber);
//        }
//    }
    public MyCalendar(int howManyDays) {
    	days = new Day[howManyDays];
    	for (int i = 1; i <= howManyDays; i++) {
    		days[i - 1] = new Day(i);
    	}
    	
    }

    public Day[] getDays() {
        return days;
    }
    
    public Day getIndividualDay(int day) {
    	return days[day + 1];
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
