package CrisisConnectApp.src.main.java.com.crisisconnect;

import java.time.LocalDateTime;

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

