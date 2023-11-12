public class Appointment {
    private String time; // Time slot for the appointment
    private String customerName; // Name of the customer
    private boolean isBooked; // Indicates whether the appointment is booked
    private int maxAttendees;

    public Appointment(String time) {
        this.time = time;
        this.customerName = null; // initialize to null as it's not booked initially
        this.isBooked = false; // initially the appointment is not booked
    }

    public String getTime() {
        return time;
    }

    public String getCustomerName() {
        return customerName;
    }
    public void setMaxAttendees(int max) {
    	this.maxAttendees = max;
    }
    public int getMaxAttendees() {
    	return maxAttendees;
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
