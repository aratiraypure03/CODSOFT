import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

class Course {
    private String courseCode;
    private String title;
    private String description;
    private int capacity;
    private String schedule;
    private int availableSlots;

    public Course(String courseCode, String title, String description, int capacity, String schedule) {
        this.courseCode = courseCode;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.schedule = schedule;
        this.availableSlots = capacity;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getAvailableSlots() {
        return availableSlots;
    }

    public boolean registerStudent() {
        if (availableSlots > 0) {
            availableSlots--;
            return true;
        }
        return false;
    }

    public void dropStudent() {
        if (availableSlots < capacity) {
            availableSlots++;
        }
    }

    @Override
    public String toString() {
        return "Course Code: " + courseCode +
               "\nTitle: " + title +
               "\nDescription: " + description +
               "\nSchedule: " + schedule +
               "\nAvailable Slots: " + availableSlots + "/" + capacity + "\n";
    }
}

class Student {
    private int studentID;
    private String name;
    private List<Course> registeredCourses;

    public Student(int studentID, String name) {
        this.studentID = studentID;
        this.name = name;
        this.registeredCourses = new ArrayList<>();
    }

    public int getStudentID() {
        return studentID;
    }

    public String getName() {
        return name;
    }

    public List<Course> getRegisteredCourses() {
        return registeredCourses;
    }

    public void registerCourse(Course course) {
        if (course.registerStudent()) {
            registeredCourses.add(course);
        }
    }

    public void dropCourse(Course course) {
        course.dropStudent();
        registeredCourses.remove(course);
    }

    @Override
    public String toString() {
        StringBuilder coursesList = new StringBuilder();
        for (Course course : registeredCourses) {
            coursesList.append(course.getTitle()).append(", ");
        }
        return "Student ID: " + studentID +
               "\nName: " + name +
               "\nRegistered Courses: " + (coursesList.length() > 0 ? coursesList.substring(0, coursesList.length() - 2) : "None") + "\n";
    }
}

public class StudentCourseRegistrationSystem extends JFrame {
    private List<Student> students;
    private List<Course> courses;

    public StudentCourseRegistrationSystem() {
        students = new ArrayList<>();
        courses = new ArrayList<>();
        initUI();
    }

    private void initUI() {
        setTitle("Student Course Registration System");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        JButton addStudentButton = new JButton("Add Student");
        addStudentButton.setBackground(new Color(0,128,128));
        addStudentButton.setForeground(Color.WHITE);
        addStudentButton.addActionListener(e -> showAddStudentDialog());

        JButton addCourseButton = new JButton("Add Course");
        addCourseButton.setBackground(new Color(0,128,128));
        addCourseButton.setForeground(Color.WHITE);
        addCourseButton.addActionListener(e -> showAddCourseDialog());

        JButton registerButton = new JButton("Register Course");
        registerButton.setBackground(new Color(0,128,128));
        registerButton.setForeground(Color.WHITE);
        registerButton.addActionListener(e -> showRegisterCourseDialog());

        JButton dropButton = new JButton("Drop Course");
        dropButton.setBackground(new Color(0,128,128));
        dropButton.setForeground(Color.WHITE);
        dropButton.addActionListener(e -> showDropCourseDialog());

        JButton displayButton = new JButton("Display Student Info");
        displayButton.setBackground(new Color(0,128,128));
        displayButton.setForeground(Color.WHITE);
        displayButton.addActionListener(e -> showDisplayStudentDialog());

        add(addStudentButton);
        add(addCourseButton);
        add(registerButton);
        add(dropButton);
        add(displayButton);

        setVisible(true);
    }

    private void showAddStudentDialog() {
        JTextField studentIDField = new JTextField(5);
        JTextField nameField = new JTextField(10);

        JPanel panel = new JPanel();
        panel.add(new JLabel("Student ID:"));
        panel.add(studentIDField);
        panel.add(new JLabel("Name:"));
        panel.add(nameField);

        int result = JOptionPane.showConfirmDialog(null, panel, 
                 "Add New Student", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            int studentID = Integer.parseInt(studentIDField.getText());
            String name = nameField.getText();
            students.add(new Student(studentID, name));
            JOptionPane.showMessageDialog(this, "Student added successfully.");
        }
    }

    private void showAddCourseDialog() {
        JTextField courseCodeField = new JTextField(5);
        JTextField titleField = new JTextField(10);
        JTextField descriptionField = new JTextField(15);
        JTextField capacityField = new JTextField(5);
        JTextField scheduleField = new JTextField(10);

        JPanel panel = new JPanel();
        panel.add(new JLabel("Course Code:"));
        panel.add(courseCodeField);
        panel.add(new JLabel("Title:"));
        panel.add(titleField);
        panel.add(new JLabel("Description:"));
        panel.add(descriptionField);
        panel.add(new JLabel("Capacity:"));
        panel.add(capacityField);
        panel.add(new JLabel("Schedule:"));
        panel.add(scheduleField);

        int result = JOptionPane.showConfirmDialog(null, panel, 
                 "Add New Course", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String courseCode = courseCodeField.getText();
            String title = titleField.getText();
            String description = descriptionField.getText();
            int capacity = Integer.parseInt(capacityField.getText());
            String schedule = scheduleField.getText();
            courses.add(new Course(courseCode, title, description, capacity, schedule));
            JOptionPane.showMessageDialog(this, "Course added successfully.");
        }
    }

    private void showRegisterCourseDialog() {
        JTextField studentIDField = new JTextField(5);
        JTextField courseCodeField = new JTextField(5);

        JPanel panel = new JPanel();
        panel.add(new JLabel("Student ID:"));
        panel.add(studentIDField);
        panel.add(new JLabel("Course Code:"));
        panel.add(courseCodeField);

        int result = JOptionPane.showConfirmDialog(null, panel, 
                 "Register Course", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            int studentID = Integer.parseInt(studentIDField.getText());
            String courseCode = courseCodeField.getText();

            Student student = findStudentByID(studentID);
            Course course = findCourseByCode(courseCode);

            if (student != null && course != null) {
                student.registerCourse(course);
                JOptionPane.showMessageDialog(this, "Course registered successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "Student or course not found.");
            }
        }
    }

    private void showDropCourseDialog() {
        JTextField studentIDField = new JTextField(5);
        JTextField courseCodeField = new JTextField(5);

        JPanel panel = new JPanel();
        panel.add(new JLabel("Student ID:"));
        panel.add(studentIDField);
        panel.add(new JLabel("Course Code:"));
        panel.add(courseCodeField);

        int result = JOptionPane.showConfirmDialog(null, panel, 
                 "Drop Course", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            int studentID = Integer.parseInt(studentIDField.getText());
            String courseCode = courseCodeField.getText();

            Student student = findStudentByID(studentID);
            Course course = findCourseByCode(courseCode);

            if (student != null && course != null) {
                student.dropCourse(course);
                JOptionPane.showMessageDialog(this, "Course dropped successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "Student or course not found.");
            }
        }
    }

    private void showDisplayStudentDialog() {
        JTextField studentIDField = new JTextField(5);

        JPanel panel = new JPanel();
        panel.add(new JLabel("Student ID:"));
        panel.add(studentIDField);

        int result = JOptionPane.showConfirmDialog(null, panel, 
                 "Display Student Info", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            int studentID = Integer.parseInt(studentIDField.getText());

            Student student = findStudentByID(studentID);

            if (student != null) {
                JOptionPane.showMessageDialog(this, student.toString());
            } else {
                JOptionPane.showMessageDialog(this, "Student not found.");
            }
        }
    }

    private Student findStudentByID(int studentID) {
        for (Student student : students) {
            if (student.getStudentID() == studentID) {
                return student;
            }
        }
        return null;
    }

    private Course findCourseByCode(String courseCode) {
        for (Course course : courses) {
            if (course.getCourseCode().equals(courseCode)) {
                return course;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StudentCourseRegistrationSystem::new);
    }
}
