package CrisisConnectApp.src.main.java.com.crisisconnect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDateTime;

public class CrisisConnectApp extends JFrame {
    private EmergencyPriorityQueue emergencyQueue;
    private TaskPriorityQueue taskQueue;

    private JTextArea alertArea;
    private JTextArea taskArea;

    public CrisisConnectApp() {
        super("CrisisConnect");
        emergencyQueue = new EmergencyPriorityQueue();
        taskQueue = new TaskPriorityQueue();

        initializeUI();
        populateDummyData();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());
        setSize(800, 600);

        alertArea = new JTextArea();
        taskArea = new JTextArea();

        JButton refreshAlerts = new JButton("Refresh Alerts");
        refreshAlerts.addActionListener(this::refreshAlerts);

        JButton refreshTasks = new JButton("Refresh Tasks");
        refreshTasks.addActionListener(this::refreshTasks);

        JPanel topPanel = new JPanel();
        topPanel.add(refreshAlerts);
        topPanel.add(refreshTasks);

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(alertArea), BorderLayout.CENTER);
        add(new JScrollPane(taskArea), BorderLayout.SOUTH);
    }

    private void refreshAlerts(ActionEvent event) {
        alertArea.setText(emergencyQueue.displayAlerts());
    }

    private void refreshTasks(ActionEvent event) {
        taskArea.setText(taskQueue.displayTasks());
    }

    private void populateDummyData() {
        emergencyQueue.addAlert(new EmergencyAlert(1, "Flood Warning", 9, "Downtown"));
        taskQueue.addTask(new Task(1, "Evacuate Zone A", 10, LocalDateTime.now().plusHours(1)));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CrisisConnectApp app = new CrisisConnectApp();
            app.setDefaultCloseOperation(EXIT_ON_CLOSE);
            app.setVisible(true);
        });
    }
}
