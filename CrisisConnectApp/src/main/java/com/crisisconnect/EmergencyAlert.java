package CrisisConnectApp.src.main.java.com.crisisconnect;

import java.time.LocalDateTime;

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

