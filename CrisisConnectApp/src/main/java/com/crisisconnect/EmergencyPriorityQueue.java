package CrisisConnectApp.src.main.java.com.crisisconnect;

import java.util.PriorityQueue;

class EmergencyPriorityQueue {
    private PriorityQueue<EmergencyAlert> queue;

    public EmergencyPriorityQueue() {
        queue = new PriorityQueue<>();
    }

    public void addAlert(EmergencyAlert alert) {
        queue.add(alert);
    }

    public EmergencyAlert getNextAlert() {
        return queue.poll();
    }

    public String displayAlerts() {
        return queue.toString();
    }
}

class TaskPriorityQueue {
    private PriorityQueue<Task> queue;

    public TaskPriorityQueue() {
        queue = new PriorityQueue<>();
    }

    public void addTask(Task task) {
        queue.add(task);
    }

    public Task getNextTask() {
        return queue.poll();
    }

    public String displayTasks() {
        return queue.toString();
    }
}

