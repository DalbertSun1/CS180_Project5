import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Project 4
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

    public String viewCalendar() {
        String result = "";

//        result += ("--------------------JULY 2023-------------------\n");
//        result += ("[ MO ] [ TU ] [ WE ] [ TH ] [ FR ] [ SA ] [ SU ]\n");
//        result += ("------------------------------------------------\n");
//        result += ("[ -- ] [ -- ] [ -- ] [ -- ] [ -- ] [ 01 ] [ 02 ]\n");
//        result += ("[ 03 ] [ 04 ] [ 05 ] [ 06 ] [ 07 ] [ 08 ] [ 09 ]\n");
//        result += ("[ 10 ] [ 11 ] [ 12 ] [ 13 ] [ 14 ] [ 15 ] [ 16 ]\n");
//        result += ("[ 17 ] [ 18 ] [ 19 ] [ 20 ] [ 21 ] [ 22 ] [ 23 ]\n");
//        result += ("[ 24 ] [ 25 ] [ 26 ] [ 27 ] [ 28 ] [ 29 ] [ 30 ]\n");
//        result += ("[ 31 ] [ -- ] [ -- ] [ -- ] [ -- ] [ -- ] [ -- ]\n");
        int maxLength = Integer.MIN_VALUE;

        result += (month + " " + year) + "\n";
        int maxLength2 = 1;

        for (int i = 0; i < 7; i++) {

            String temp = "";
            temp += "[        ";
            if ((i + 1) < 10) {
                temp += "0" + (i + 1) + "        ]  ";
            } else {
                temp += (i + 1) + "        ]  ";

            }
            result += temp.substring(0, temp.length() - 1);
        }
        result += "\n";
        for (int j = 0; j < maxLength2; j++) {
            for (int i = 0; i < 7; i++) {
                if (days[i] != null && days[i].getDoctors() != null) {
                    if (days[i].listAppts().size() == j) {
                        result += "                     ";
                    } else {
                        result += " " + days[i].listAppts().get(j) + " ";
                        if (days[i].listAppts().size() > maxLength2) {
                            maxLength2 = days[i].listAppts().size();
                        }
                    }
                } else {
                    result += "                     ";
                }
            }
            result += "\n";
        }
        for (int i = 7; i < 14; i++) {

            String temp = "";
            temp += "[        ";
            if ((i + 1) < 10) {
                temp += "0" + (i + 1) + "        ]  ";
            } else {
                temp += (i + 1) + "        ]  ";

            }
            result += temp.substring(0, temp.length() - 1);
        }
        result += "\n";
        for (int j = 0; j < maxLength2; j++) {
            for (int i = 7; i < 14; i++) {
                if (days[i] != null && days[i].getDoctors() != null) {
                    if (days[i].listAppts().size() == j) {
                        result += "                     ";
                    } else {
                        result += " " + days[i].listAppts().get(j) + " ";
                        if (days[i].listAppts().size() > maxLength2) {
                            maxLength2 = days[i].listAppts().size();
                        }
                    }
                } else {
                    result += "                     ";
                }
            }
            result += "\n";
        }
        for (int i = 14; i < 21; i++) {

            String temp = "";
            temp += "[        ";
            if ((i + 1) < 10) {
                temp += "0" + (i + 1) + "        ]  ";
            } else {
                temp += (i + 1) + "        ]  ";

            }
            result += temp.substring(0, temp.length() - 1);
        }
        result += "\n";
        for (int j = 0; j < maxLength2; j++) {
            for (int i = 14; i < 21; i++) {
                if (days[i] != null && days[i].getDoctors() != null) {
                    if (days[i].listAppts().size() == j) {
                        result += "                     ";
                    } else {
                        result += " " + days[i].listAppts().get(j) + " ";
                        if (days[i].listAppts().size() > maxLength2) {
                            maxLength2 = days[i].listAppts().size();
                        }
                    }
                } else {
                    result += "                     ";
                }
            }
            result += "\n";
        }
        for (int i = 21; i < 28; i++) {

            String temp = "";
            temp += "[        ";
            if ((i + 1) < 10) {
                temp += "0" + (i + 1) + "        ]  ";
            } else {
                temp += (i + 1) + "        ]  ";

            }
            result += temp.substring(0, temp.length() - 1);
        }
        result += "\n";
        for (int j = 0; j < maxLength2; j++) {
            for (int i = 21; i < 28; i++) {
                if (days[i] != null && days[i].getDoctors() != null) {
                    if (days[i].listAppts().size() == j) {
                        result += "                     ";
                    } else {
                        result += " " + days[i].listAppts().get(j) + " ";
                        if (days[i].listAppts().size() > maxLength2) {
                            maxLength2 = days[i].listAppts().size();
                        }
                    }
                } else {
                    result += "                     ";
                }
            }
            result += "\n";
        }
        int hehe = numberMonth - 28;
        for (int i = 28; i < numberMonth; i++) {

            String temp = "";
            temp += "[        ";
            if ((i + 1) < 10) {
                temp += "0" + (i + 1) + "        ]  ";
            } else {
                temp += (i + 1) + "        ]  ";

            }
            result += temp.substring(0, temp.length() - 1);
        }
        result += "\n";
        for (int j = 0; j < maxLength2; j++) {
            for (int i = 28; i < numberMonth; i++) {
                if (days[i] != null && days[i].getDoctors() != null) {
                    if (days[i].listAppts().size() == j) {
                        result += "                     ";
                    } else {
                        result += " " + days[i].listAppts().get(j) + " ";
                        if (days[i].listAppts().size() > maxLength2) {
                            maxLength2 = days[i].listAppts().size();
                        }
                    }
                } else {
                    result += "                     ";
                }
            }
            result += "\n";
        }


        return result;
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
