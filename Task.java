public class Task {
    public Task(int id, int millis) {
        this.id = id;
        this.millis = millis;
    }

    int id;
    int millis;

    public void process() {
        System.out.println("Started process: " + id);
        try {
            Thread.sleep(millis); // Delay for 3 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Task " + id + " completed");
    }

}