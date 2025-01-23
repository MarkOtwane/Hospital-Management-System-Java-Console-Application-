package hospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Doctors {
    private final Connection connection;
    private final Scanner scanner;

    public Doctors(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }

    public void addDoctors() {
        System.out.println("Enter Doctor Id: ");
        String doctor_id = scanner.next();
        System.out.println("Enter Doctor Name :  ");
        String name = scanner.next();
        System.out.println("Enter Doctor Specialization : ");
        String specialization = scanner.next();

        try {
            String query = " INSERT INTO Doctors(doctor_Id, name,specialization) VALUES(?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, doctor_id);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, specialization);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Doctor Added Successfully!!");
            } else {
                System.out.println("Failed to add Doctor!!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void viewDoctors() {
        String query = "SELECT * FROM Doctors";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("+------------+------------------+---------------+");
            System.out.println("| Doctor Id  | Name             | Specialization|");
            System.out.println("+------------+------------------+---------------+");

            while (resultSet.next()) {
                String doctor_id = resultSet.getString("doctor_Id");
                String name = resultSet.getString("name");
                String specialization = resultSet.getString("specialization");
                System.out.println("+------------+-------------------+---------------+");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean getDoctorsById(String id) {
        String query = "SELECT * FROM Doctors where doctor_Id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

//            modified if statement
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
