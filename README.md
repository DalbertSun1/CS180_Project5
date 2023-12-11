# CS180_Project5
CS 18000 Project 5

1. In order to compile and run our project, you need to first compile each of the classes on Vocareum. Then open Login.java and run it. Once you run it you can go through the prompts that pop up and follow our project as a user.
2. Dalbert Sun submitted the Vocareum Workspace. Vihaan Chadha sumbitted the report.
3. Detailed description of each class:

**Appointment Class**

The Appointment class manages individual appointments, has attributes such as time for the specific slot, customerName representing the booking of an appointment, and isBooked indicating availability. Its constructor initializes appointments as unbooked, setting default values for customerName and isBooked. We made getters and setters to help access and modify appointment details like time, customer name, and maximum number of attendees allowed. The class enables booking and cancellation through methods like bookAppointment and cancelAppointment. The toString() method presents a clear status of whether an appointment is booked or available.

**Relationship:**  The Appointment class and its methods are referenced in our Doctor, Patient, DentistOffice, Day, and Login classes. In terms of a hierarchy, appointment classes is at the bottom so it is used in a lot of the other classes. Whenever we reference booking, canceling, and listing an appointment we use the class.

**Day**

The Day class works towards scheduling for various doctors and their appointments.First we made getters and setters with the following methods: getDate, setDate, getDoctors, setDoctors. These methods manage the date and the doctors available on that day. Then we created methods to make new doctors. The methods were: addDoctor, removeDoctor, getIndividualDoctor, getIndividualDoctorIndex: These methods allowed us to handle the addition, removal, and access of individual doctors within the daily schedule. Then we created listAppts and showDoctorList. These methods display the available appointments for all doctors on that day. listAppts assembles a list of appointments, while showDoctorList shows that list. 

**Relationship:**
The methods created in Day are refrenced in Doctor, DentistOffice, Patient, and in Login. This is very similar class to Appointment. It is built upon by all the bigger classes. This is the very base class for Doctors, and is used to create the doctors and then stores them in a list. We then refrence that list of doctors in the other classes, which is important for when we want to update them in our files and in dentistOffice. 

**Doctor Class**
   
The Doctor class has functions that help manage a doctor's appointments within the scheduling system. Each doctor, equivalent to a store in our project, is specified by a name and maintains a list of appointments. The class is initialized with the doctor's name and an empty list of appointments. We made `addAppointment` and `removeAppointment`, to add and remove appointments from the doctor's schedule. We then coded the  `isAvailable` method to check the availability of a specific time slot for a doctor. This method ensures that a doctor's schedule isn't double-booked at a given time. We then created `loadBookedAppointments` method to load them from a file. It loads appointments assigned to the specific doctor from the `approved.txt` file, storing them in a separate list. Lastly, the `getStatistics` method is designed to provide insights into the doctor's appointment statistics. It constructs two hash maps. One maps customer names to the number of their appointments, and the other maps appointment times to their frequency.

**Relationship:** The doctor class is referenced in patient, dentistOffice, and Login. It isn't referenced in appointment because in our class hierarchy appointment was created first and then used in everything class. Similarly, doctor was created second and then used in the following classes. It is used in Login to reference its methods in the case. It is also used in dentistOffice as the office consists of all the doctors. It is also used in Day because we add doctors there, and add them to a doctorList so we can see all the doctors working in the dentistOffice.

**Patient Class**
  
The Patient class manages patient appointments. The methods enable making, canceling, and viewing appointments. Patients can view their approved appointments, make new appointments, or cancel existing ones. The class uses file I/O for reading and writing appointments, plus user inputs to select appointments and manage the booking process. The go method shows how we created a menu interface for these actions. The cancelAppointment method helps patients remove appointments, while rescheduleAppointment allows the patient to change an existing appointment to a different time or day. The readFile method helps in reading and displaying approved appointments from a file. The printAppointments method aids in displaying available time slots for appointments.

**Relationship:** The patient class is in a way the parent class for all the methods related to the patient. It has the menu and case statements for all the methods and prompts the patient with all the prompts to do what they need to in the dentist office. It is referenced in the Login class when it is called to be run by the user. 

**DentistOffice**

The DentistOffice class oversees all the doctor-related operations and appointment creations. The addDoctor, deleteDoctor, readDoctors methods: handle the addition and removal of doctors from the pending.txt and approved.txt files and manage the reading of existing doctor details. For Appointments the methods approveAppointment, declineAppointment allow for the approval or rejection of pending appointments, and updated them to the approved.txt file as they become approved by the doctor. Then we have viewApprovedAppointments, and viewPending. These methods allow the user to view both approved and pending appointments from their respective files. Then we have the rescheduleAppointment method for sellers. This function allows the seller to go through their approved appointments and change the time and day of a specifc customer's appointment to a new time that works better for them. 

**Relationship:** DentistOffice uses methods from Doctor, Patient, Day, and Appointment in order to facilliate doing the operations of a doctor. It also then is referenced by Login when we create a menu and do case statements that call all the methods from DentistOffice and use them to run the operations from the seller(doctor) side. It is one of our main classes and is similar to patient in the way it handles everything for the doctor.

**Login**

The Login class focuses on first helping the doctor or the patient creating a new account and then signing in. We used file I/O to help us do the logging in and creating new accounts. The we created a login Menu: The method provides a structured menu, allowing users to log in or create new accounts. It guides the user through the login or account creation process, checking for valid input along the way and showing and error if they input something wrong. Then I created the createAccount, checkAccount methods which handle the creation of new accounts and verify the existence of user credentials within the system. Accounts are created by gathering user details and storing them in a designated file. The checkAccount method verifies the user's login credentials by cross-referencing them with stored account details. Once the user sucesffuly logs in the user interaction takes over and the method directs users to the appropriate interface, either as a patient or a member of the dentist office staff. It provides a menu-driven interface allowing for different actions, that we worked on in the various classes.

Lastly, the methods viewApproved, viewPending, approveAppointment, declineAppointment, rescheduleAppointment enable the dentist office staff to view pending and approved appointments, approve, decline, or reschedule them. 

**Relationship:**
The Login class uses methods from all of our previous classes as it refrences them when ensuring a sucesfull main method. When compiling and running this method you can create an account and follow through the menu to use any of the methods we created in this or other classes. It shows that we have met all the requirements and handles all exceptions and errors for users inputting invalid things. 

**MyCalendar Class**
   
This class contains the main calendar of our project. When the patient creates an account and logins in they then have the option of creating an appointment. When they select this option they will be able to see a calendar that shows all the days in the month. They can then type in what day they want to book the appointment and they will be shown a list of times, and the program continues. Once several appointments are made, this class updates the calendar. When the next person wants to book an appointment and get to the prompt which shows them the calendar they will be able to see all the days in the month again, but specific days will have times underneath them. These times symbolize appointments that have been booked already on said days. Thus, the class ensures that when looking at a calendar, users with a specific time range for when they are available can see what days they could take an appointment and when they can't.

**Relationship:**  MyCalendar has a relation to our main method class which is Login. When we run Login and login as a user and choose to make an appointment, myCalender is called and the calendar is shown to the user. 

**OurStatistics** 

OurStatistics covers data processing and printing for the statistics module. In patientDashboard() and dentistOfficeDashboard(), it first calls Doctor.getStatistics() to load all the statistics into memory. Then, the method processes the data into two HashMaps: doctorPatientData, which maps doctors to # of patients, and doctorTimeData, which maps doctors to their most frequent time slot. Later, the method calls printPatientDashboard, which handles the user interface for the dashboard. Using string formatting, it prints the data in an appealing way. Lastly, the print method sorts the data as chosen by the user. There are relatively few differences between patientDashboard() and dentistOfficeDashboard(), except for small differences in strings. Namely, whether the strings mention 'doctor' or 'patient.' There is little difference otherwise.

**Relationship:** OurStatistics is a relatively independent class. In the user interface, the main menus call OurStatistics.patientDashboard() and OurStatistics.dentistOfficeDashboard(). OurStatistics calls only Doctor.getStatistics() for each doctor in the system, then processes the data returned. Other than this, there is little interaction with other classes.

**Server Class** 
The server side is accessed through the Server class. The class creates a new thread of DentistServer for each and every client that attempts to connect, thus creating concurrency.

**Relationship** Server is used to initialize multiple instances of DentistServer on separate threads.

**DentistServer Class** 
This contains switch cases for every instance that a txt file must be accessed. To begin, the add and delete doctor cases access a client side method that sends the name of the doctor that needs to be added and deleted, then edits it server-side. Next, the read doctor, show approved, and show pending appointments function in a similar manner. The server side accesses the respective txt files and sends the information in an array list back to the client side, which can then print it out. The process is slightly different when dealing with the patient side of the client. In this case, the patient must enter their name, which is then sent to the server, and the server returns the list of approved appointments with the patient’s name. 
**Relationship** DentistServer communicates with DentistClient, serving as part of the bridge between the user and the serverside files. DentistServer receives commands from the client and sends back information from its files.

**DentistClient Class** 
DentistClient serves as a proxy before calling our Login method, essentially recreating the functionality of Project 4. Client first prompts the user for a hostname, which will default to “localhost” if no other is specified. Then, it connects to the server and calls Login method while passing the client as a parameter. The client serves as the bridge between our Login Method / GUIs and the server. When a GUI wants to contact the server, it calls client.println(String), allowing the message to transmit to the server. This is done in a specific format. For example, in makeAppointment, the String is as follows: makeAppointment::name, date, doctor.getName(), appointment.getTime().
“makeAppointment” represents the method name for the server to execute, “::” separates the method name from the parameters, and the rest are all parameters, separated by commas.
**Relationship** DentistClient is part of the bridge between the user GUI and the serverside files. The GUIs in Class Patient & Class Login all send requests through DentistClient to the server. 

**Testing Done** 
Our testing done is covered comprehensively in Tests.MD, but the following is a general summary. There are 28 separate routes through our program that must be tested. These include logging in, making and removing appointments, viewing approved and pending appointments, rescheduling, canceling, viewing statistics, and importing .csv files.

**Note:** 
We turned in the project with the template empty txt files. However, the project also works without these txt files too.