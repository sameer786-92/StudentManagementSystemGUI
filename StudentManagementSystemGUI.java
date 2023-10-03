import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class StudentManagementSystemGUI {

    JFrame frame;
    JTextField nameField, idField, courseField, semField, yearField, univField, addressField, contactField;
    JList<String> studentList;
    DefaultListModel<String> studentListModel;
    ArrayList<Student> studentDetailsList;

    public StudentManagementSystemGUI() {
        studentListModel = new DefaultListModel<>();
        studentList = new JList<>(studentListModel);
        studentDetailsList = new ArrayList<>();
        addLoginScreen();
    }

    public void addLoginScreen() {
        frame = new JFrame("Student Management System");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();

        JLabel label = new JLabel("Login as:");
        label.setFont(new Font("Arial", Font.BOLD, 24));

        JRadioButton studentButton = new JRadioButton("Student");
        studentButton.setFont(new Font("Arial", Font.PLAIN, 20));

        JRadioButton teacherButton = new JRadioButton("Teacher");
        teacherButton.setFont(new Font("Arial", Font.PLAIN, 20));

        ButtonGroup group = new ButtonGroup();
        group.add(studentButton);
        group.add(teacherButton);

        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 30));
        loginButton.setBackground(Color.BLUE);
        loginButton.setForeground(Color.BLACK);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (studentButton.isSelected()) {
                    frame.dispose();
                    showStudentDashboard();
                } else if (teacherButton.isSelected()) {
                    frame.dispose();
                    showTeacherDashboard();
                }
            }
        });

        panel.add(label);
        panel.add(studentButton);
        panel.add(teacherButton);
        panel.add(loginButton);

        frame.add(panel);
        frame.setVisible(true);
    }

    public void showStudentDashboard() {
        JFrame studentFrame = new JFrame("Student Dashboard");
        studentFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        studentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel(new GridLayout(8, 2));
        JPanel buttonPanel = new JPanel(new FlowLayout());

        JLabel nameLabel = new JLabel("Name: *");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        nameField = new JTextField();
        nameField.setFont(new Font("Arial", Font.PLAIN, 20));

        JLabel idLabel = new JLabel("Student ID: *");
        idLabel.setFont(new Font("Arial", Font.BOLD, 20));
        idField = new JTextField();
        idField.setFont(new Font("Arial", Font.PLAIN, 20));

        JLabel courseLabel = new JLabel("Course: *");
        courseLabel.setFont(new Font("Arial", Font.BOLD, 20));
        courseField = new JTextField();
        courseField.setFont(new Font("Arial", Font.PLAIN, 20));

        JLabel semLabel = new JLabel("Semester: *");
        semLabel.setFont(new Font("Arial", Font.BOLD, 20));
        semField = new JTextField();
        semField.setFont(new Font("Arial", Font.PLAIN, 20));

        JLabel yearLabel = new JLabel("Year: *");
        yearLabel.setFont(new Font("Arial", Font.BOLD, 20));
        yearField = new JTextField();
        yearField.setFont(new Font("Arial", Font.PLAIN, 20));

        JLabel univLabel = new JLabel("University: *");
        univLabel.setFont(new Font("Arial", Font.BOLD, 20));
        univField = new JTextField();
        univField.setFont(new Font("Arial", Font.PLAIN, 20));

        JLabel addressLabel = new JLabel("Address: *");
        addressLabel.setFont(new Font("Arial", Font.BOLD, 20));
        addressField = new JTextField();
        addressField.setFont(new Font("Arial", Font.PLAIN, 20));

        JLabel contactLabel = new JLabel("Contact: *");
        contactLabel.setFont(new Font("Arial", Font.BOLD, 20));
        contactField = new JTextField();
        contactField.setFont(new Font("Arial", Font.PLAIN, 20));

        JButton submitButton = new JButton("Submit");
        submitButton.setFont(new Font("Arial", Font.BOLD, 20));
        submitButton.setBackground(Color.GREEN);
        submitButton.setForeground(Color.BLACK);

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (areFieldsValid()) {
                    String name = nameField.getText();
                    String id = idField.getText();
                    String course = courseField.getText();
                    String sem = semField.getText();
                    String year = yearField.getText();
                    String univ = univField.getText();
                    String address = addressField.getText();
                    String contact = contactField.getText();

                    Student student = new Student(name, id, course, sem, year, univ, address, contact);
                    studentDetailsList.add(student);
                    studentListModel.addElement(name);

                    String confirmationMessage = "Student details submitted:\n\n" + student.getDetails();
                    JOptionPane.showMessageDialog(null, confirmationMessage, "Submission Successful", JOptionPane.INFORMATION_MESSAGE);

                    resetFields();
                } else {
                    JOptionPane.showMessageDialog(null, "Please enter valid data in all required fields.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton showDetailsButton = new JButton("Show Details");
        showDetailsButton.setFont(new Font("Arial", Font.BOLD, 20));
        showDetailsButton.setBackground(Color.ORANGE);
        showDetailsButton.setForeground(Color.BLACK);

        showDetailsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = studentList.getSelectedIndex();
                if (selectedIndex >= 0) {
                    String name = studentListModel.getElementAt(selectedIndex);
                    for (Student s : studentDetailsList) {
                        if (s.getName().equals(name)) {
                            JOptionPane.showMessageDialog(null, s.getDetails());
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Select a student from the list to show details.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton resetButton = new JButton("Reset");
        resetButton.setFont(new Font("Arial", Font.BOLD, 20));
        resetButton.setBackground(Color.RED);
        resetButton.setForeground(Color.BLACK);

        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetFields();
            }
        });

        JButton saveButton = new JButton("Save");
        saveButton.setFont(new Font("Arial", Font.BOLD, 20));
        saveButton.setBackground(Color.BLUE);
        saveButton.setForeground(Color.BLACK);

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!studentDetailsList.isEmpty()) {
                    saveStudentDataToFile();
                } else {
                    JOptionPane.showMessageDialog(null, "No student data to save.", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        Dimension buttonSize = new Dimension(150, 40);
        submitButton.setPreferredSize(buttonSize);
        showDetailsButton.setPreferredSize(buttonSize);
        resetButton.setPreferredSize(buttonSize);
        saveButton.setPreferredSize(buttonSize);

        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(idLabel);
        inputPanel.add(idField);
        inputPanel.add(courseLabel);
        inputPanel.add(courseField);
        inputPanel.add(semLabel);
        inputPanel.add(semField);
        inputPanel.add(yearLabel);
        inputPanel.add(yearField);
        inputPanel.add(univLabel);
        inputPanel.add(univField);
        inputPanel.add(addressLabel);
        inputPanel.add(addressField);
        inputPanel.add(contactLabel);
        inputPanel.add(contactField);

        buttonPanel.add(submitButton);
        buttonPanel.add(showDetailsButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(saveButton);

        mainPanel.add(inputPanel, BorderLayout.WEST);
        mainPanel.add(new JScrollPane(studentList), BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        studentFrame.add(mainPanel);
        studentFrame.setVisible(true);
    }

    public void showTeacherDashboard() {
        JFrame teacherFrame = new JFrame("Teacher Dashboard");
        teacherFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        teacherFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();

        JLabel label = new JLabel("Student List");
        label.setFont(new Font("Arial", Font.BOLD, 24));

        JScrollPane scrollPane = new JScrollPane(studentList);
        studentList.setFont(new Font("Arial", Font.PLAIN, 20));

        studentList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                JList list = (JList) evt.getSource();
                if (evt.getClickCount() == 2) {
                    int index = list.locationToIndex(evt.getPoint());
                    String name = studentList.getModel().getElementAt(index);

                    for (Student s : studentDetailsList) {
                        if (s.getName().equals(name)) {
                            JOptionPane.showMessageDialog(null, s.getDetails());
                        }
                    }
                }
            }
        });

        panel.add(label);
        panel.add(scrollPane);

        teacherFrame.add(panel);
        teacherFrame.setVisible(true);
    }

    public void resetFields() {
        nameField.setText("");
        idField.setText("");
        courseField.setText("");
        semField.setText("");
        yearField.setText("");
        univField.setText("");
        addressField.setText("");
        contactField.setText("");
    }

    private boolean areFieldsValid() {
        return isValidName(nameField.getText()) &&
                isValidStudentID(idField.getText()) &&
                isValidCourse(courseField.getText()) &&
                isValidSemester(semField.getText()) &&
                isValidYear(yearField.getText()) &&
                isValidUniversity(univField.getText()) &&
                isValidAddress(addressField.getText()) &&
                isValidContact(contactField.getText());
    }

    private boolean isValidName(String name) {
        return Pattern.matches("^[a-zA-Z ]{1,60}$", name);
    }

    private boolean isValidStudentID(String id) {
        return Pattern.matches("^\\d+$", id);
    }

    private boolean isValidCourse(String course) {
        return Pattern.matches("^[a-zA-Z ]{1,50}$", course);
    }

    private boolean isValidSemester(String sem) {
        return Pattern.matches("^[1-9]{1}$", sem);
    }

    private boolean isValidYear(String year) {
        return Pattern.matches("^\\d{4}$", year);
    }

    private boolean isValidUniversity(String univ) {
        return univ.length() <= 100;
    }

    private boolean isValidAddress(String address) {
        return address.length() <= 160;
    }

    private boolean isValidContact(String contact) {
        return Pattern.matches("^\\d{10}$", contact);
    }

    private void saveStudentDataToFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Text Files (*.txt)", "txt"));

        int result = fileChooser.showSaveDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            try {
                FileWriter writer = new FileWriter(selectedFile);

                for (Student student : studentDetailsList) {
                    writer.write(student.getDetails() + "\n\n");
                }

                writer.close();

                JOptionPane.showMessageDialog(null, "Student data saved to " + selectedFile.getName(),
                        "Save Successful", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error saving data to the file: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new StudentManagementSystemGUI();
        });
    }
}

class Student {

    private String name, id, course, sem, year, univ, address, contact;

    public Student(String name, String id, String course, String sem, String year, String univ, String address,
                    String contact) {
        this.name = name;
        this.id = id;
        this.course = course;
        this.sem = sem;
        this.year = year;
        this.univ = univ;
        this.address = address;
        this.contact = contact;
    }

    public String getName() {
        return name;
    }

    public String getDetails() {
        return "Name: " + name + "\nID: " + id + "\nCourse: " + course +
                "\nSem: " + sem + "\nYear: " + year + "\nUniversity: " + univ +
                "\nAddress: " + address + "\nContact: " + contact;
    }
}
