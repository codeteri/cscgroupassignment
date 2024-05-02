-- Table: Student
CREATE TABLE Student (
    StudentID INT AUTO_INCREMENT PRIMARY KEY,
    FirstName VARCHAR(50),
    LastName VARCHAR(50),
    Address VARCHAR(255),
    Email VARCHAR(100),
    PhoneNumber VARCHAR(50),
    YearOfStudy INT,
    CurrentFaculty VARCHAR(100)
);

-- Table: Major
CREATE TABLE Major (
    MajorID INT AUTO_INCREMENT PRIMARY KEY,
    MajorName VARCHAR(100),
    Description VARCHAR(255),
    Specialisations VARCHAR(255),
    Department VARCHAR(100),
    Prerequisites VARCHAR(255),
    RequiredCourses VARCHAR(255),
    GraduationRequirements TEXT
);



-- Table: Transcript
CREATE TABLE Transcript (
    TranscriptID INT AUTO_INCREMENT PRIMARY KEY,
    StudentID INT,
    ResultID VARCHAR(20),
    InstitutionName VARCHAR(100),
    StartDate DATE,
    EndDate DATE,
    FOREIGN KEY (StudentID) REFERENCES Student(StudentID)
);

-- Table: Course
CREATE TABLE Course (
    CourseID INT AUTO_INCREMENT PRIMARY KEY,
    CourseCode VARCHAR(10),
    Year INT,
    CourseName VARCHAR(100),
    CourseDescription VARCHAR(255),
    Period VARCHAR(50),
    Prerequisites TEXT,
    Corequisites TEXT,
    NQFCredits INT,
    MajorID INT,
    FOREIGN KEY (MajorID) REFERENCES Major(MajorID)
);
