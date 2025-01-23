package hospitalManagementSystem;

import java.sql.*;
import java.util.Scanner;

public class HospitalManagementSystem {
//    Database connectivity
    private  static  final String url = "jdbc:mysql://localhost:3306/Hospital";
    private static  final  String username = "root";
    private static  final String password = "";

    public static void main(String[] args) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Scanner scanner = new Scanner(System.in);
        try{
            Connection connection = DriverManager.getConnection(url, username, password);
             Patients patients = new Patients(connection,scanner);
             Doctors doctors = new Doctors(connection, scanner);

             while(true){
                 System.out.println("HOSPITAL MANAGEMENT SYSTEM");
                 System.out.println("1. Add Patient");
                 System.out.println("1. View Patient");
                 System.out.println("3. Add Doctors");
                 System.out.println("4. View Doctors");
                 System.out.println("5. Book Appointment");
                 System.out.println("6. Exit");

                 System.out.println("Enter Your Choice: ");
                 int choice =  scanner.nextInt();

                 switch (choice){
                     case 1:
//                         add patient
                         patients.addPatients();
                         break;
                     case 2:
//                         view patients
                         patients.viewPatient();
                         break;
                     case 3:
//                         add doctors
                         doctors.addDoctors();
                         break;
                     case 4:
//                         view doctors
                         doctors.viewDoctors();
                         break;
                     case 5:
//                         book appointments
                         bookAppointment(patients,doctors, connection,scanner);
                         System.out.println();
                         break;
                     case 6:
                         System.out.println("THANK FOR USING HOSPITAL MANAGEMENT SYSTEM!");
                         return;
                     default:
                         System.out.println("Enter Valid Choice!!!");
                 }
             }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void bookAppointment(Patients patients, Doctors doctors, Connection connection, Scanner scanner){
        System.out.println("Enter Patient Id: ");
        int patientId = scanner.nextInt();
        scanner.next();
        System.out.println("Enter Doctor Id: ");
        String doctorId = scanner.next();
        System.out.println("Enter Appointment Date (YYYY-MM-DD): ");
        String appointmentDate = scanner.next();

        if (patients.getPatientById(patientId) && doctors.getDoctorsById(doctorId)){
            if (checkDoctorAvailability(doctorId, appointmentDate,connection)){
                String appointmentQuery = "INSERT INTO Appointments(patient_Id, doctor_Id, appointed_date) VALUES(?, ?, ?)";
                try{
                    PreparedStatement preparedStatement = connection.prepareStatement(appointmentQuery);
                    preparedStatement.setInt(1, patientId);
                    preparedStatement.setString(2,doctorId);
                    preparedStatement.setString(3, appointmentDate);

                    int rowsAffected =preparedStatement.executeUpdate();

                    if (rowsAffected>0){
                        System.out.println("Appointment Booked!");
                    }else {
                        System.out.println("Failed to Book Appointment!");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }else {
                System.out.println("Doctor not available on this date. ");
            }
        }else {
            System.out.println("Wrong Patient or Doctor Id.");
        }

    }

    public static boolean checkDoctorAvailability(String doctorId, String appointmentDate, Connection connection){
        String query = "SELECT COUNT(*) FROM Appointments where doctor_Id = ? AND appointed_date = ?";

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, doctorId);
            preparedStatement.setString(2, appointmentDate);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                int count = resultSet.getInt(1);
                return count == 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
