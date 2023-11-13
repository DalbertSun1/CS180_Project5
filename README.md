# CS180_Project4
CS 18000 Project 4

1. In order to compile and run our project, you need to first compile each of the classes on vocaremum. Then open Login.java and run it. Once you run it you can go through the prompts that pop up and follow our project as a user.
2. Dalbert Sun sumbitted every part of our project. He sumbitted the Report and the Vocaremum Workspace.
3. Detailed description of each class:

 **MyCalendar Class**
   
This class contains the main calendar of our project. When the patient creates an account and logins in they then have the option of creating an appointment. When they select this option they will be able to see a calendar which shows all the days in the month. They can then type in what day they want to book the appointment and the will be shown a list of times, and the program continues. Once several appointments are made, this class updates the calendar. When the next person wants to book an appoitnment and get to the prompt which shows them the calendar they will be able to see all the days in the month again, but specific days will have times underneath them. These times symbolize appointments that have been booked already on said days. Thus, the class ensures that when looking at a calendar, users who have a specific time range for when they are avalaible can see what days they could take an appointment and when they couldn't.
**Testing done:**
**Relationship:**  MyCalendar has a relation to our main method class which is Login. When we run Login and login as a user and choose to make an appointment, myCalender is called and the calendar is shown to the user. 

**Appointment Class**

The Appointment class manages individual appointments, has attributessuch as time for the specific slot, customerName representing the booking of an appointment, and isBooked indicating availability. Its constructor initializes appointments as unbooked, setting default values for customerName and isBooked. We made getters and setters to help in the accessing and modifying of appointment details like time, customer name, and maximum attendees allowed. The class enables booking and cancellation through methods like bookAppointment and cancelAppointment. The toString() method presents a clear status whether an appointment is booked or available.
**Testing done:**
**Relationship:**  The Appointment class and its methods are refrenced in our Doctor, Patient, DentistOffice, and Login classes. In terms of a hierarchy, appointment classes is at the bottom so it is used in a lot of the other classes. Whenever we refrence booking or canceling an appointment we use the class.

**Doctor Class**
   
The Doctor class has functions that help manage a doctor's appointments within the scheduling system. Each doctor, equivalent to a store in our project, is specified by a name and maintains a list of appointments. The class initializes with the doctor's name and an empty list of appointments. We made `addAppointment` and `removeAppointment`, to add and remove appointments from the doctor's schedule. We then coded the  `isAvailable` method to check the availability of a specific time slot for a doctor. This method ensures that a doctor's schedule isn't double-booked at a given time. We then created `loadBookedAppointments` method to load them from a file. It loads appointments assigned to the specific doctor from the `approved.txt` file, storing them in a separate list. Lastly, the `getStatistics` method is designed to provide insights into the doctor's appointment statistics. It constructs two hash maps. One maps customer names to the number of their appointments, and the other maps appointment times to their frequency.
**Testing done:**
**Relationship:** The doctor class is refrenced in patient, dentistOffice, and Login. It isin't refrenced in appointment because in our class hierarchy appointment was created first and then used in everything class. Similarly, doctor was created second and then used in the following classes. It is used in Login to refrences its methods in the case. It is also used in dentistOffice as the office consists of all the doctors.

**Patient Class**
  
The Patient class manages patient appointments. The methods enable making, canceling, and viewing appointments. Patients can view their approved appointments, make new appointments, or cancel existing ones. The class uses file I/O for reading and writing appointments, plus user inputs to select appointments and manage the booking process. The go method shows how we created a menu interface for these actions. The cancelAppointment method helps patients remove appointments, while rescheduleAppointment allows the patient to change an existing appointment to a different time or day. The readFile method helps in reading and displaying approved appointments from a file. The printAppointments method aids in displaying available time slots for appointments.
**Testing Done:**
**Relationship:** The patient class is in a way the parent class for all the methods related with the patient. It has the menu and case statements for all the methods and prompts the patient with all the prompts to do what they need to in the dentist office. It is refrenced in the Login class when it is called to be run by the user. 

**DentistOffice**



