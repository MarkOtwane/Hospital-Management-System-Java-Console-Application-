package hospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;


public class Patients {
    private final Connection connection;
    private final Scanner scanner;

    public Patients(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }

    public void addPatients() {
        System.out.println("Enter Patient Name: ");
        String name = scanner.next();
        System.out.println("Enter Patients Age :  ");
        int age = scanner.nextInt();
        System.out.println("Enter Patients Gender : ");
        String gender = scanner.next();


        try {
            String query = " INSERT INTO Patients(name, age, gender) VALUES(?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);
            preparedStatement.setString(3, gender);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Patient Added Successfully!!");
            } else {
                System.out.println("Failed to add Patient!!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void viewPatient() {
        String query = "SELECT * FROM Patients";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("+------------+------------------+--------+--------+");
            System.out.println("| Patient Id | Name             | Age    | Gender |");
            System.out.println("+------------+------------------+--------+--------+");

            while (resultSet.next()) {
                int id = resultSet.getInt("patient_Id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String gender = resultSet.getString("gender");
                System.out.println("+------------+------------------+--------+--------+");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean getPatientById(int id) {
        String query = "SELECT * FROM Patients where patient_Id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

//            modified if statement
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
