import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.PriorityQueue;
import java.util.LinkedList;
import java.util.Queue;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class EmergencyAlert implements Comparable<EmergencyAlert> {
    private int alertId;
    private String description;
    private int severity;
    private LocalDateTime timestamp;
    private String location;
    private String status;

    public EmergencyAlert(int alertId, String description, int severity, String location) {
        this.alertId = alertId;
        this.description = description;
        this.severity = severity;
        this.timestamp = LocalDateTime.now();
        this.location = location;
        this.status = "Pending";
    }

    // Getters and setters
    public void setStatus(String status) { this.status = status; }
    public String getStatus() { return status; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public String getLocation() { return location; }
    public int getAlertId() { return alertId; }
    public String getDescription() { return description; }
    public int getSeverity() { return severity; }

    @Override
    public int compareTo(EmergencyAlert other) {
        return Integer.compare(other.severity, this.severity);
    }

    @Override
    public String toString() {
        return String.format("ID: %d | Severity: %d | Location: %s | Status: %s\nDescription: %s", 
            alertId, severity, location, status, description);
    }
}

class Task implements Comparable<Task> {
    private int taskId;
    private String name;
    private int priority;
    private String assignedTeam;
    private String status;
    private LocalDateTime deadline;

    public Task(int taskId, String name, int priority, LocalDateTime deadline) {
        this.taskId = taskId;
        this.name = name;
        this.priority = priority;
        this.deadline = deadline;
        this.status = "Unassigned";
        this.assignedTeam = "None";
    }

    // Getters and setters
    public void setStatus(String status) { this.status = status; }
    public void setAssignedTeam(String team) { this.assignedTeam = team; }
    public String getStatus() { return status; }
    public String getAssignedTeam() { return assignedTeam; }
    public LocalDateTime getDeadline() { return deadline; }
    public int getTaskId() { return taskId; }
    public String getName() { return name; }
    public int getPriority() { return priority; }

    @Override
    public int compareTo(Task other) {
        return Integer.compare(other.priority, this.priority);
    }

    @Override
    public String toString() {
        return String.format("ID: %d | Priority: %d | Team: %s | Status: %s\nTask: %s", 
            taskId, priority, assignedTeam, status, name);
    }
}

class ResponseTeam {
    private int teamId;
    private String name;
    private String status;
    private String currentTask;
    private String specialization;

    public ResponseTeam(int teamId, String name, String specialization) {
        this.teamId = teamId;
        this.name = name;
        this.specialization = specialization;
        this.status = "Available";
        this.currentTask = "None";
    }

    // Getters and setters
    public void setStatus(String status) { this.status = status; }
    public void setCurrentTask(String task) { this.currentTask = task; }
    public String getStatus() { return status; }
    public String getCurrentTask() { return currentTask; }
    public int getTeamId() { return teamId; }
    public String getName() { return name; }
    public String getSpecialization() { return specialization; }

    @Override
    public String toString() {
        return String.format("ID: %d | Team: %s | Status: %s | Task: %s\nSpecialization: %s", 
            teamId, name, status, currentTask, specialization);
    }
}

public class CrisisConnectApp extends JFrame {
    private EmergencyPriorityQueue emergencyQueue;
    private TaskPriorityQueue taskQueue;
    private ResponseTeamQueue teamQueue;
    
    private JTextArea alertArea;
    private JTextArea taskArea;
    private JTextArea teamArea;
    private JPanel mainPanel;
    private JTabbedPane tabbedPane;
    
    private static final Color HEADER_COLOR = new Color(44, 62, 80);
    private static final Color BACKGROUND_COLOR = new Color(236, 240, 241);
    private static final Color BUTTON_COLOR = new Color(52, 152, 219);
    private static final Font HEADER_FONT = new Font("Arial", Font.BOLD, 16);
    private static final Font CONTENT_FONT = new Font("Arial", Font.PLAIN, 14);

    public CrisisConnectApp() {
        super("CrisisConnect Emergency Management System");
        initializeComponents();
        initializeData();
        createGUI();
    }

    private void initializeComponents() {
        emergencyQueue = new EmergencyPriorityQueue();
        taskQueue = new TaskPriorityQueue();
        teamQueue = new ResponseTeamQueue();
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1000, 800));
        
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BACKGROUND_COLOR);
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(HEADER_FONT);
    }

    private void initializeData() {
        // Sample emergency alerts
        emergencyQueue.addAlert(new EmergencyAlert(1, "Major flooding in downtown area", 9, "Downtown"));
        emergencyQueue.addAlert(new EmergencyAlert(2, "Building collapse at construction site", 8, "Industrial Zone"));
        emergencyQueue.addAlert(new EmergencyAlert(3, "Chemical spill on highway", 7, "Highway 101"));

        // Sample tasks
        LocalDateTime now = LocalDateTime.now();
        taskQueue.addTask(new Task(1, "Coordinate evacuation of flood zone", 10, now.plusHours(1)));
        taskQueue.addTask(new Task(2, "Deploy search and rescue teams", 9, now.plusHours(2)));
        taskQueue.addTask(new Task(3, "Set up emergency medical station", 8, now.plusHours(3)));

        // Sample response teams
        teamQueue.addTeam(new ResponseTeam(1, "Alpha Team", "Search and Rescue"));
        teamQueue.addTeam(new ResponseTeam(2, "Beta Team", "Medical Emergency"));
        teamQueue.addTeam(new ResponseTeam(3, "Gamma Team", "Hazmat Response"));
    }

    private void createGUI() {
        // Create the header panel
        JPanel headerPanel = createHeaderPanel();
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Create tabs
        createTabs();
        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        // Create the status bar
        JPanel statusBar = createStatusBar();
        mainPanel.add(statusBar, BorderLayout.SOUTH);

        add(mainPanel);
        pack();
        setLocationRelativeTo(null);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(HEADER_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("CrisisConnect Emergency Management System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.WEST);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);

        JButton refreshButton = createStyledButton("Refresh");
        JButton addAlertButton = createStyledButton("New Alert");
        JButton addTaskButton = createStyledButton("New Task");
        JButton addTeamButton = createStyledButton("Add Team");

        buttonPanel.add(refreshButton);
        buttonPanel.add(addAlertButton);
        buttonPanel.add(addTaskButton);
        buttonPanel.add(addTeamButton);

        headerPanel.add(buttonPanel, BorderLayout.EAST);
        return headerPanel;
    }

    private void createTabs() {
        // Alerts Tab
        JPanel alertsPanel = createContentPanel("Emergency Alerts");
        alertArea = createStyledTextArea();
        alertsPanel.add(new JScrollPane(alertArea), BorderLayout.CENTER);
        tabbedPane.addTab("Alerts", null, alertsPanel, "View and manage emergency alerts");

        // Tasks Tab
        JPanel tasksPanel = createContentPanel("Active Tasks");
        taskArea = createStyledTextArea();
        tasksPanel.add(new JScrollPane(taskArea), BorderLayout.CENTER);
        tabbedPane.addTab("Tasks", null, tasksPanel, "View and manage tasks");

        // Teams Tab
        JPanel teamsPanel = createContentPanel("Response Teams");
        teamArea = createStyledTextArea();
        teamsPanel.add(new JScrollPane(teamArea), BorderLayout.CENTER);
        tabbedPane.addTab("Teams", null, teamsPanel, "View and manage response teams");

        updateDisplays();
    }

    private JPanel createContentPanel(String title) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(HEADER_FONT);
        panel.add(titleLabel, BorderLayout.NORTH);

        return panel;
    }

    private JTextArea createStyledTextArea() {
        JTextArea textArea = new JTextArea();
        textArea.setFont(CONTENT_FONT);
        textArea.setEditable(false);
        textArea.setMargin(new Insets(10, 10, 10, 10));
        return textArea;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(CONTENT_FONT);
        button.setBackground(BUTTON_COLOR);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.addActionListener(e -> handleButtonClick(text));
        return button;
    }

    private JPanel createStatusBar() {
        JPanel statusBar = new JPanel(new BorderLayout());
        statusBar.setBorder(new EmptyBorder(5, 10, 5, 10));
        statusBar.setBackground(HEADER_COLOR);

        JLabel statusLabel = new JLabel("System Status: Active | Last Updated: " + 
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        statusLabel.setForeground(Color.WHITE);
        statusBar.add(statusLabel, BorderLayout.WEST);

        return statusBar;
    }

    private void handleButtonClick(String buttonText) {
        switch (buttonText) {
            case "Refresh":
                updateDisplays();
                break;
            case "New Alert":
                showNewAlertDialog();
                break;
            case "New Task":
                showNewTaskDialog();
                break;
            case "Add Team":
                showNewTeamDialog();
                break;
        }
    }

    private void showNewAlertDialog() {
        JDialog dialog = new JDialog(this, "New Emergency Alert", true);
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Add form fields
        JTextField descField = new JTextField(20);
        JSpinner severitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
        JTextField locationField = new JTextField(20);

        gbc.gridx = 0; gbc.gridy = 0;
        dialog.add(new JLabel("Description:"), gbc);
        gbc.gridx = 1;
        dialog.add(descField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        dialog.add(new JLabel("Severity (1-10):"), gbc);
        gbc.gridx = 1;
        dialog.add(severitySpinner, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        dialog.add(new JLabel("Location:"), gbc);
        gbc.gridx = 1;
        dialog.add(locationField, gbc);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            int alertId = emergencyQueue.getNextAlertId();
            EmergencyAlert alert = new EmergencyAlert(
                alertId,
                descField.getText(),
                (Integer) severitySpinner.getValue(),
                locationField.getText()
            );
            emergencyQueue.addAlert(alert);
            updateDisplays();
            dialog.dispose();
        });

        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        dialog.add(submitButton, gbc);

        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void showNewTaskDialog() {
        // Similar to showNewAlertDialog but for tasks
        // Implementation details omitted for brevity
    }

    private void showNewTeamDialog() {
        // Similar to showNewAlertDialog but for teams
        // Implementation details omitted for brevity
    }

    private void updateDisplays() {
        updateAlertDisplay();
        updateTaskDisplay();
        updateTeamDisplay();
    }

    private void updateAlertDisplay() {
        StringBuilder sb = new StringBuilder();
        sb.append("EMERGENCY ALERTS (Priority Order)\n");
        sb.append("================================\n\n");
        
        for (EmergencyAlert alert : emergencyQueue.getAlerts()) {
            sb.append(alert.toString()).append("\n\n");
        }
        
        alertArea.setText(sb.toString());
    }

    private void updateTaskDisplay() {
        StringBuilder sb = new StringBuilder();
        sb.append("ACTIVE TASKS (Priority Order)\n");
        sb.append("============================\n\n");
        
        for (Task task : taskQueue.getTasks()) {
            sb.append(task.toString()).append("\n\n");
        }
        
        taskArea.setText(sb.toString());
    }

    private void updateTeamDisplay() {
        StringBuilder sb = new StringBuilder();
        sb.append("RESPONSE TEAMS\n");
        sb.append("==============\n\n");
        
        for (ResponseTeam team : teamQueue.getTeams()) {
            sb.append(team.toString()).append("\n\n");
        }
        
        teamArea.setText(sb.toString());
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            CrisisConnectApp app = new CrisisConnectApp();
            app.setVisible(true);
        });
    }
}

class EmergencyPriorityQueue {
    private PriorityQueue<EmergencyAlert> queue;
    private int nextAlertId;

    public EmergencyPriorityQueue() {
        queue = new PriorityQueue<>();
        nextAlertId = 1;
    }

    public void addAlert(EmergencyAlert alert) {
        queue.offer(alert);
    }

    public EmergencyAlert getNextAlert() {
        return queue.poll();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public EmergencyAlert[] getAlerts() {
        return queue.toArray(new EmergencyAlert[0]);
    }

    public int getNextAlertId() {
        return nextAlertId++;
    }
}

class TaskPriorityQueue {
    private PriorityQueue<Task> queue;
    private int nextTaskId;

    public TaskPriorityQueue() {
        queue = new PriorityQueue<>();
        nextTaskId = 1;
    }

    public void addTask(Task task) {
        queue.offer(task);
    }

    public Task getNextTask() {
        return queue.poll();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public Task[] getTasks() {
        return queue.toArray(new Task[0]);
    }

    public int getNextTaskId() {
        return nextTaskId++;
    }
}

class ResponseTeamQueue {
    private Queue<ResponseTeam> queue;
    private int nextTeamId;

    public ResponseTeamQueue() {
        queue = new LinkedList<>();
        nextTeamId = 1;
    }

    public void addTeam(ResponseTeam team) {
        queue.offer(team);
    }

    public ResponseTeam getNextTeam() {
        return queue.poll();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public ResponseTeam[] getTeams() {
        return queue.toArray(new ResponseTeam[0]);
    }

    public int getNextTeamId() {
        return nextTeamId++;
    }
}

// Additional utility classes for the dialogs

class TaskDialog extends JDialog {
    private Task result;
    
    public TaskDialog(JFrame parent) {
        super(parent, "New Task", true);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Task name field
        JTextField nameField = new JTextField(20);
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Task Name:"), gbc);
        gbc.gridx = 1;
        add(nameField, gbc);

        // Priority spinner
        JSpinner prioritySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Priority (1-10):"), gbc);
        gbc.gridx = 1;
        add(prioritySpinner, gbc);

        // Deadline picker (simplified)
        JSpinner hoursSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 72, 1));
        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Deadline (hours from now):"), gbc);
        gbc.gridx = 1;
        add(hoursSpinner, gbc);

        // Submit button
        JButton submitButton = new JButton("Create Task");
        submitButton.addActionListener(e -> {
            LocalDateTime deadline = LocalDateTime.now().plusHours((Integer)hoursSpinner.getValue());
            result = new Task(
                -1, // ID will be assigned by queue
                nameField.getText(),
                (Integer)prioritySpinner.getValue(),
                deadline
            );
            dispose();
        });

        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(submitButton, gbc);

        pack();
        setLocationRelativeTo(parent);
    }

    public Task getTask() {
        return result;
    }
}

class TeamDialog extends JDialog {
    private ResponseTeam result;
    
    public TeamDialog(JFrame parent) {
        super(parent, "Add Response Team", true);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Team name field
        JTextField nameField = new JTextField(20);
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Team Name:"), gbc);
        gbc.gridx = 1;
        add(nameField, gbc);

        // Specialization combo box
        String[] specializations = {
            "Search and Rescue",
            "Medical Emergency",
            "Fire Response",
            "Hazmat Response",
            "Evacuation",
            "Logistics"
        };
        JComboBox<String> specBox = new JComboBox<>(specializations);
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Specialization:"), gbc);
        gbc.gridx = 1;
        add(specBox, gbc);

        // Submit button
        JButton submitButton = new JButton("Add Team");
        submitButton.addActionListener(e -> {
            result = new ResponseTeam(
                -1, // ID will be assigned by queue
                nameField.getText(),
                (String)specBox.getSelectedItem()
            );
            dispose();
        });

        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(submitButton, gbc);

        pack();
        setLocationRelativeTo(parent);
    }

    public ResponseTeam getTeam() {
        return result;
    }
}