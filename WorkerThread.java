import java.util.List;
import java.util.concurrent.BlockingQueue;

public class WorkerThread extends Thread {
    public WorkerThread(BlockingQueue<Task> taskQueue, List<String> outputList) {
        this.taskQueue = taskQueue;
        this.outputList = outputList;
    }

    final BlockingQueue<Task> taskQueue;
    final List<String> outputList;

    @Override
    public void run() {
        while (!taskQueue.isEmpty()) {
            try {
                Task task = taskQueue.take();
                task.process();
                synchronized (outputList) {
                    outputList.add("Completed task: " + task.id);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
