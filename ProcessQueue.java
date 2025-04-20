
// Java Program Implementing Queue Interface
import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

class ProcessQueue {
    public static void main(String[] args) {
        BlockingQueue<Task> q = new LinkedBlockingQueue<>();
        LinkedList<String> outputList = new LinkedList<>();
        for (int i = 1; i <= 10; i++) {
            q.add(new Task(i, 100));
        }
        WorkerThread worker1 = new WorkerThread(q, outputList);
        WorkerThread worker2 = new WorkerThread(q, outputList);
        WorkerThread worker3 = new WorkerThread(q, outputList);
        WorkerThread worker4 = new WorkerThread(q, outputList);

        worker1.start();
        worker2.start();
        worker3.start();
        worker4.start();

        try {
            worker1.join();
            worker2.join();
            worker3.join();
            worker4.join();
        } catch (InterruptedException e) {
            System.err.println(e.getStackTrace());

        }
        System.out.println(outputList);
    }
}
