package lab02.CircularBuffer;

public class Producer extends Thread {
    // ToDo: Add required instance variables
    private String name;
    private Buffer<String> buffer;
    private int prodTime;

    public Producer(String name, Buffer<String> buffer, int prodTime) {
        super(name);
        this.name = name;
        this.buffer = buffer;
        this.prodTime = prodTime;
// ToDo implement Constructor
    }

    public void run() {
// ToDo: Continuously produce Strings using in prodTime intervall
        while (true){
            try {
                sleep((int)(Math.random()*prodTime));
                buffer.put(name);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
