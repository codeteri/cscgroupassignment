import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.concurrent.TimeUnit;
import java.util.*;
import java.text.SimpleDateFormat;
import com.github.javafaker.Faker;

public class DBPopulator {

    public static void main(String[] args) {
        // Class.forName("com.mysql.cj.jdbc.Driver");
        // Establish database connection
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/transfers", "root", "password")) {
            System.out.println("Seeding Database :)");
            
            // Faker instance for generating fake data
            Faker faker = new Faker();
            // Generate and insert fake data for Student entity
            insertFakeStudents(conn, faker, 20); // Insert 20 fake students

            // Generate and insert fake data for Major entity
            insertFakeMajors(conn, faker, 20); // Insert 10 fake majors

            // Generate and insert fake data for Course entity
            insertFakeCourses(conn, faker, 10); // Insert 50 fake courses

            // Generate and insert fake data for Transcript entity
            insertFakeTranscripts(conn, faker, 20); // Insert 1000 fake transcripts

            System.out.println("Data insertion completed successfully ;)");



        } catch (SQLException e) {
            e.printStackTrace();
        }

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/transfers", "root", "password")) {
            Statement stmt = conn.createStatement();
            
            // Query to select all students
            String query = "SELECT * FROM Student";
            
            // Execute the query and process the result set
            try (ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    int studentID = rs.getInt("StudentID");
                    String firstName = rs.getString("FirstName");
                    String lastName = rs.getString("LastName");
                    String address = rs.getString("Address");
                    String email = rs.getString("Email");
                    String phoneNumber = rs.getString("PhoneNumber");
                    int yearOfStudy = rs.getInt("YearOfStudy");
                    String currentFaculty = rs.getString("CurrentFaculty");

                    // Print student information
                    System.out.println("StudentID: " + studentID);
                    System.out.println("Name: " + firstName + " " + lastName);
                    System.out.println("Address: " + address);
                    System.out.println("Email: " + email);
                    System.out.println("Phone Number: " + phoneNumber);
                    System.out.println("Year of Study: " + yearOfStudy);
                    System.out.println("Current Faculty: " + currentFaculty);
                    System.out.println("----------------------------------");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void insertFakeStudents(Connection conn, Faker faker, int count) throws SQLException {
        String sql = "INSERT INTO Student (FirstName, LastName, Address, Email, PhoneNumber, YearOfStudy, CurrentFaculty) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (int i = 0; i < count; i++) {
                pstmt.setString(1, faker.name().firstName());
                pstmt.setString(2, faker.name().lastName());
                pstmt.setString(3, faker.address().fullAddress());
                pstmt.setString(4, faker.internet().emailAddress());
                pstmt.setString(5, faker.phoneNumber().phoneNumber());
                pstmt.setInt(6, faker.number().numberBetween(1, 5));
                pstmt.setString(7, faker.university().name());
                pstmt.executeUpdate();
            }
        }
    }

    private static void insertFakeTranscripts(Connection conn, Faker faker, int count) throws SQLException {
        String sql = "INSERT INTO Transcript (ResultID, InstitutionName, StartDate, EndDate) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (int i = 0; i < count; i++) {
                pstmt.setString(1, faker.numerify("T#######"));
                pstmt.setString(2, faker.university().name());
                
                // Format start date to MySQL date format
                Date startDate = faker.date().past(10, TimeUnit.DAYS);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String formattedStartDate = sdf.format(startDate);
                pstmt.setString(3, formattedStartDate);
                
                // Format end date to MySQL date format
                Date endDate = faker.date().past(5, TimeUnit.DAYS);
                String formattedEndDate = sdf.format(endDate);
                pstmt.setString(4, formattedEndDate);
                
                pstmt.executeUpdate();
            }
        }
    }


    private static void insertFakeMajors(Connection conn, Faker faker, int count) throws SQLException {
        String sql = "INSERT INTO Major (MajorName, Description, Specialisations, Department, Prerequisites, RequiredCourses, GraduationRequirements) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (int i = 0; i < count; i++) {
                pstmt.setString(1, generateFakeMajorName());
                pstmt.setString(2, faker.lorem().sentence());
                pstmt.setString(3, faker.lorem().words(3).toString());
                pstmt.setString(4, faker.company().industry());
                pstmt.setString(5, faker.lorem().words(3).toString());
                pstmt.setString(6, faker.lorem().words(3).toString());
                pstmt.setString(7, faker.lorem().sentence());
                pstmt.executeUpdate();
            }
        }
    }

    private static void insertFakeCourses(Connection conn, Faker faker, int count) throws SQLException {
        String sql = "INSERT INTO Course (CourseCode, Year, CourseName, CourseDescription, Period, Prerequisites, Corequisites, NQFCredits) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (int i = 0; i < count; i++) {
                pstmt.setString(1, faker.regexify("[A-Z]{3}[0-9]{3}"));
                pstmt.setInt(2, faker.number().numberBetween(1, 4));
                pstmt.setString(3, faker.educator().course());
                pstmt.setString(4, faker.lorem().sentence());
                pstmt.setString(5, faker.lorem().word());
                pstmt.setString(6, faker.lorem().words(3).toString());
                pstmt.setString(7, faker.lorem().words(3).toString());
                pstmt.setInt(8, faker.number().numberBetween(5, 20));
                pstmt.executeUpdate();
            }
        }
    }
    

    private static String generateFakeMajorName() {
        String[] majors = {
            "Applied Mathematics", "Applied Statistics", "Archaeology", "Astrophysics", 
            "Biochemistry", "Biology", "Chemistry", "Computer Science", "Computer Engineering", 
            "Business Computing", "Environmental & Geographical Science", "Genetics", "Geology", 
            "Human Anatomy & Physiology", "Marine Biology", "Mathematics", "Mathematical Statistics", 
            "Ocean & Atmosphere Science", "Physics", "Quantitative Biology", "Statistics & Data Science"
        };
        Random random = new Random();
        return majors[random.nextInt(majors.length)];
    }
}
