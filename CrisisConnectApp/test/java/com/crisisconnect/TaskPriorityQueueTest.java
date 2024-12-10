package CrisisConnectApp.test.java.com.crisisconnect;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TaskPriorityQueueTest {
    @Test
    void testAddTaskAndRetrieve() {
        TaskPriorityQueue queue = new TaskPriorityQueue();
        Task task = new Task(1, "Test Task", 10, LocalDateTime.now().plusDays(1));
        queue.addTask(task);

        assertEquals(task, queue.getNextTask());
    }
}