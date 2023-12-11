Test 1

Steps:

1. User launches application.
2. User selects the "Log in" button.
3. User selects the "Patient" dropdown.
4. User selects the username textbox. 
5. User selects username via the keyboard. 
6. User selects the password textbox. 
7. User selects the "Ok" button.

Expected result: Application verifies the user's username and password and displays a welcome message. It then loads their patient homepage automatically.

Test Status: Passed.

Test 2

Steps:

1. User launches application.
2. User selects the "Log in" button.
3. User selects the "Doctor" dropdown.
4. User selects the username textbox.
5. User selects username via the keyboard.
6. User selects the password textbox.
7. User selects the "Ok" button.

Expected result: Application verifies the user's username and password and displays a welcome message. It then loads their dentist office homepage automatically.

Test Status: Passed.

Test 3

Steps:

1. User launches application.
2. User selects the "Create Account" button.
3. User selects the "Patient" dropdown. 
4. User enters their full name, username, password, email, and phone number. 
5. User selects the "OK" button.

Expected result: Application checks if the username and password are already taken. If it is taken, an error message pops up and the user is redirected back to log in. If not, a message pops up stating the patient account has been created and the user is redirected back to the login menu.

Test Status: Passed.

Test 4

Steps:

1. User launches application.
2. User selects the "Create Account" button.
3. User selects the "Dentist Office" dropdown.
4. User enters their full name, username, password, email, and phone number.
5. User selects the "OK" button.

Expected result: Application checks if the username and password are already taken. If it is taken, an error message pops up and the user is redirected back to log in. If not, a message pops up stating the dentist office account has been created and the user is redirected back to the login menu.

Test Status: Passed.

Test 5

Steps:

1. User launches application.
2. User selects the "Exit" button.

Expected result: Pop-up will appear thanking the user for using the program, then the GUI will close and the program will end.

Test Status: Passed.

Test 6

Steps:

1. User launches application.
2. User selects the "Log In" button.
3. User selects the "Cancel" button.

Expected Result: User will be redirected back to the login menu.

Test Status: Passed.

Test 7

Steps:

1. User launches application.
2. User selects the "Create Account" button.
3. User selects the "Cancel" button.

Expected Result: User will be redirected back to the login menu.

Test Status: Passed.

Test 8

Steps:

1. User logs in as a patient through test 1.
2. User selects the "Make Appointment" dropdown.
3. User selects the day they want to choose.
4. User selects the doctor they want to choose.
5. User selects the time they want to choose.
6. User enters the name for the appointment

Expected Result: An appointment will be created and added to pending.txt.

Test Status: Passed.

Test 9

Steps:

1. User logs in as a patient through test 1.
2. User selects the "Cancel Appointment" dropdown.
3. User enters their name.
4. User selects the appointment they wish to cancel.

Expected Result: If the user has approved appointments, a list of them should appear for the user. Once an option is selected, this appointment will be cancelled and removed from approved.txt. Otherwise, an error will appear stating that the patient has no approved appointments.

Test Status: Passed.

Test 10

Steps:

1. User logs in as a patient through test 1.
2. User selects the "View Approved Appointments" dropdown.
3. User enters their name.

Expected Result: If the entered name has any approved appointments, these approved appointments will be displayed to the user. Otherwise, an error will pop up stating that the user has no approved appointments.

Test Status: Passed.

Test 11

Steps:

1. User logs in as a patient through test 1.
2. User selects the "Reschedule Appointment" dropdown.
3. User enters their name.
4. User selects the appointment they want to reschedule.
5. User selects the day they want to reschedule the appointment to.
6. User selects the time they want to reschedule the appointment to.

Expected Result: If the user enters a name without any approved appointments, an error will appear stating the user has no approved appointments. Otherwise, the respective appointment will be updated in approved.txt.

Test Status: Passed.

Test 12

Steps:

1. User logs in as a patient through test 1.
2. User selects the "View Statistics" dropdown.

Expected Result: The statistics menu will show up, listing the number of appointments and most popular time for each doctor.

Test Status: Passed.

Test 13

Steps:

1. User logs in as a patient through test 1.
2. User selects the "View Statistics" dropdown.
3. User selects the "Sort by doctor's name" button.

Expected Result: The statistics will sort by the doctor's name alphabetically.

Test Status: Passed.

Test 14

Steps:

1. User logs in as a patient through test 1.
2. User selects the "View Statistics" dropdown.
3. User selects the "Sort by # of patients" button.

Expected Result: The statistics will sort by the number of patients from most to least.

Test Status: Passed.

Test 15

Steps:

1. User logs in as a patient through test 1.
2. User selects the "View Log Out" dropdown.
3. User selects the "OK" button.

Expected Result: A message will appear telling the user they have successfully logged out. They will then be returned to the homepage.

Test Status: Passed.

Test 16

Steps:

1. User logs in as a dentist office through test 2.
2. User selects the "Add Doctor" dropdown.
3. User enters the doctor's name.

Expected Result: A new doctor with the doctor's name is added to doctors.txt.

Test Status: Passed.

Test 17

Steps:

1. User logs in as a dentist office through test 2.
2. User selects the "Remove Doctor" dropdown.
3. User enters the doctor's name.

Expected Result: If the doctor exists, that doctor will be removed from doctors.txt. Otherwise, an error will appear stating that the doctor does not exist.

Test Status: Passed.

Test 18

Steps:

1. User logs in as a dentist office through test 2.
2. User selects the "View Approved Appointments" dropdown.

Expected Result: All approved appointments should be displayed. If there aren't any approved appointments, an error will appear stating that there are no approved appointments.

Test Status: Passed.

Test 19

Steps:

1. User logs in as a dentist office through test 2.
2. User selects the "View Pending Appointments" dropdown.

Expected Result: All pending appointments should be displayed. If there aren't any approved appointments, an error will appear stating that there are no pending appointments.

Test Status: Passed.

Test 20

Steps:

1. User logs in as a dentist office through test 2.
2. User selects the "View Pending Appointments" dropdown.

Expected Result: All pending appointments should be displayed. If there aren't any approved appointments, an error will appear stating that there are no pending appointments.

Test Status: Passed.

Test 21

Steps:

1. User logs in as a dentist office through test 2.
2. User selects the "Approve Appointment" dropdown.
3. User selects the appointment they wish to approve.

Expected Result: The approved appointment is removed from pending.txt and added to approved.txt. If there are no pending appointments, an error will be displayed stating so.

Test Status: Passed.

Test 22

Steps:

1. User logs in as a dentist office through test 2.
2. User selects the "Decline Appointment" dropdown.
3. User selects the appointment they wish to decline.

Expected Result: The approved appointment is removed from pending.txt. If there are no pending appointments, an error will be displayed stating so.

Test Status: Passed.

Test 23

Steps:

1. User logs in as a dentist office through test 2.
2. User selects the "Reschedule Appointment" dropdown.
3. User enters the patient's name.
4. User selects the appointment they wish to reschedule.
5. User selects the day they wish to reschedule the appointment to.
6. User selects the time they wish to reschedule the appointment to.

Expected Result: If the patient's name does not have any approved appointments, an error will appear stating so. Otherwise, the rescheduled appointment will changed to the new day and time.

Test Status: Passed.

Test 24

Steps:

1. User logs in as a dentist office through test 2.
2. User selects the "View Statistics".

Expected Result: The statistics menu will show up, listing the number of appointments per patient and most popular time for each doctor.

Test Status: Passed.

Test 25

Steps:

1. User logs in as a patient through test 1.
2. User selects the "View Statistics" dropdown.
3. User selects the "Sort by patient's name" button.

Expected Result: The statistics will sort by the patient's name alphabetically.

Test Status: Passed.

Test 26

Steps:

1. User logs in as a patient through test 1.
2. User selects the "View Statistics" dropdown.
3. User selects the "Sort by # of appointments" button.

Expected Result: The statistics will sort by the number of appointments per patient from most to least.

Test Status: Passed.






