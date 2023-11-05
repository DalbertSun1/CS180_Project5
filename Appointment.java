public class Appointment {
    private Time time; // Time slot for the appointment
    private String customerName; // Name of the customer
    private boolean isBooked; // Indicates whether the appointment is booked

    public Appointment(Time time) {
        this.time = time;
        this.customerName = null; // initialize to null as it's not booked initially
        this.isBooked = false; // initially the appointment is not booked
    }

    public Time getTime() {
        return time;
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

    public void bookAppointment(String customerName) {
        this.customerName = customerName;
        this.isBooked = true;
        time.setAvailable(false); // mark the associated time slot as unavailable
    }

    public void cancelAppointment() {
        this.customerName = null;
        this.isBooked = false;
        time.setAvailable(true); // mark the associated time slot as available
    }

    @Override
    public String toString() {
        if (isBooked) {
            return "Booked: " + time.getTimeslot() + " with " + customerName;
        } else {
            return "Available: " + time.getTimeslot();
        }
    }
}
