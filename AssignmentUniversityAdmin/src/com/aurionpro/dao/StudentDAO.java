package com.aurionpro.dao;


public class StudentDAO {

    private final Connection connection;

    public StudentDAO() {
        this.connection = Database.getInstance().getConnection();
    }

    public void addStudent(Student student) throws SQLException {
        String sql = "INSERT INTO student (name, roll_number) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, student.getName());
            statement.setString(2, String.valueOf(student.getRollNumber()));
            statement.executeUpdate();

            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    student.setStudentId(resultSet.getInt(1));
                }
            }
        }
    }

    public List<Student> getAllStudents() throws SQLException {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM student WHERE isActive = TRUE";

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Student student = new Student(
                    resultSet.getInt("student_id"),
                    resultSet.getString("name"),
                    Integer.parseInt(resultSet.getString("roll_number")),
                    0,
                    0.0,
                    new ArrayList<>()
                );
                students.add(student);
            }
        }

        return students;
    }

    public Student getStudentById(int id) throws SQLException {
        String sql = "SELECT * FROM student WHERE student_id = ? AND isActive = TRUE";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new Student(
                    resultSet.getInt("student_id"),
                    resultSet.getString("name"),
                    Integer.parseInt(resultSet.getString("roll_number")),
                    0,
                    0.0,
                    new ArrayList<>()
                );
            }
        }

        return null;
    }

    public void deleteStudent(int id) throws SQLException {
        String sql = "UPDATE student SET isActive = FALSE WHERE student_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    public void assignCourse(int studentId, int courseId) throws SQLException {
        String sql = "INSERT INTO student_course (student_id, course_id) VALUES (?, ?) " +
                     "ON DUPLICATE KEY UPDATE isActive = TRUE";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, studentId);
            statement.setInt(2, courseId);
            statement.executeUpdate();
        }
    }

    public void createStudentProfile(StudentProfile profile) throws SQLException {
        String sql = "INSERT INTO student_profile (student_id, address, phone, dob) VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, profile.getStudentId());

            // Defensive defaults
            statement.setString(2, profile.getAddress() != null ? profile.getAddress() : "");
            statement.setString(3, profile.getPhone() != null ? profile.getPhone() : "");
            statement.setDate(4, profile.getDob() != null ? Date.valueOf(profile.getDob()) : Date.valueOf("1970-01-01"));

            statement.executeUpdate();

            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    profile.setProfileId(resultSet.getInt(1));
                }
            }
        }
    }

    public Student getStudentByRollNumber(String rollNumber) throws SQLException {
        String sql = "SELECT * FROM student WHERE roll_number = ? AND isActive = TRUE";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, rollNumber);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new Student(
                    resultSet.getInt("student_id"),
                    resultSet.getString("name"),
                    Integer.parseInt(resultSet.getString("roll_number")), // Convert back if needed
                    0,
                    0.0,
                    new ArrayList<>()
                );
            }
        }

        return null;
    }
    
    public void createProfile(StudentProfile profile) throws SQLException {
        String sql = "INSERT INTO student_profile (student_id, address, phone, dob) VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, profile.getStudentId());
            statement.setString(2, profile.getAddress());
            statement.setString(3, profile.getPhone());
            statement.setDate(4, Date.valueOf(profile.getDob()));

            statement.executeUpdate();
        }
    }


}
