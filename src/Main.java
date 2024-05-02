import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {
  public static void main(String[] args) {
    try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/transfers", "root", "password")) {
      Statement stmt = conn.createStatement();
      
      // Query to select all majors
      String query = "SELECT * FROM Major";
      
      // Execute the query and process the result set
      try (ResultSet rs = stmt.executeQuery(query)) {
          System.out.println("Showing all Majors In Science Faculty");
          while (rs.next()) {
              int majorID = rs.getInt("MajorID");
              String majorName = rs.getString("MajorName");
              String description = rs.getString("Description");
              String department = rs.getString("Department");
              String prerequisites = rs.getString("Prerequisites");
              // Print major information
              System.out.println("MajorID: " + majorID);
              System.out.println("MajorName: " + majorName);
              System.out.println("Description: " + description);
              System.out.println("Department: " + department);
              System.out.println("Prerequisites: " + prerequisites);
              System.out.println("----------------------------------");
          }
      }

      String query2 = "SELECT Student.StudentID, firstName, lastName, GPA FROM Student, Transcript "+
                        "WHERE Transcript.StudentID = Student.StudentID ORDER BY GPA DESC LIMIT 3";

      try (ResultSet rs = stmt.executeQuery(query2)) {
        System.out.println("Top 3 Students:");
        while (rs.next()) {
            int studentID = rs.getInt("StudentID");
            String firstName = rs.getString("FirstName");
            String lastName = rs.getString("LastName");
            double gpa = rs.getDouble("GPA");
            
            // Print student information
            System.out.println("StudentID: " + studentID);
            System.out.println("Name: " + firstName + " " + lastName);
            System.out.println("GPA: " + gpa);
            System.out.println("----------------------------------");
        }
    }
  } catch (SQLException e) {
      e.printStackTrace();
  }
  }
}
