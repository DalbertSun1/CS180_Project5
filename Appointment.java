/**
 * Project 4
 * Dentist Office Calendar Marketplace
 *
 * @author Dalbert Sun, Vihaan Chadha, Jack White, Himaja Narajala, Aaryan Bondre
 * @version November 13th, 2023
 */

public class Appointment {
    private String time;
    private boolean isBooked;
    private String customerName;
    private String doctor;
    private int day;

    public Appointment(String time) {
        this.time = time;
        this.isBooked = false;
        this.customerName = null;
        this.doctor = null;
        this.day = -1;
    }

    public String getDoctor() {
        return doctor;
    }

    public int getDay() {
        return day;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void setIsBooked(boolean isBooked) {
        this.isBooked = isBooked;
    }

    public void bookAppointment(String customerName) {
        this.customerName = customerName;
        this.isBooked = true;
    }

    public void cancelAppointment() {
        this.customerName = null;
        this.isBooked = false;
    }

    @Override
    public String toString() {
        if (isBooked) {
            return "Booked: " + time + " with " + customerName;
        } else {
            return "Available: " + time;
        }
    }
}
