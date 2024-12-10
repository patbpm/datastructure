package CrisisConnectApp.test.java.com.crisisconnect;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EmergencyPriorityQueueTest {
    @Test
    void testAddAlertAndRetrieve() {
        EmergencyPriorityQueue queue = new EmergencyPriorityQueue();
        EmergencyAlert alert = new EmergencyAlert(1, "Test Alert", 5, "Location");
        queue.addAlert(alert);

        assertEquals(alert, queue.getNextAlert());
    }
}
