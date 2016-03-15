package lab02.CircularBuffer;

public class Consumer extends Thread {
// ToDo: Add required instance variables

    private String name;
    private Buffer<String> buffer;
    private int consTime;

    public Consumer(String name, Buffer<String> buffer, int consTime) {
        super(name);
        this.name = name;
        this.buffer = buffer;
        this.consTime = consTime;
    }

    public void run() {
// ToDo: Continuously consume Strings using in prodTime intervall
        while(true){
            try {
                sleep((int)(Math.random() * consTime));
                buffer.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
