class Producer extends Thread {
    // ToDo: Add required instance variables

    public Producer(String name, Buffer<String> buffer, int prodTime) {
        super(name);
        // ToDo implement Constructor
    }

    public void run() {
        // ToDo: Continuously produce Strings using in prodTime intervall
    }
}

class Consumer extends Thread {
    // ToDo: Add required instance variables

    public Consumer(String name, Buffer<String> buffer, int consTime) {
        super(name);
        // ToDo implement Constructor
    }

    public void run() {
        // ToDo: Continuously consume Strings using in prodTime intervall
    }
}

public class CircBufferTest {
    public static void main(String[] args) {
        final int capacity = 15; // Number of buffer items
        final int prodCount = 4; // Number of producer threads
        final int consCount = 2; // Number of consumer threads
        final int maxProdTime = 1000;// max. production time for one item
        final int maxConsTime = 500; // max. consumption time for one item

        try {
            Buffer<String> buffer = new CircularBuffer<String>(String.class,
                    capacity);

            Consumer[] consumers = new Consumer[consCount];
            for (int i = 0; i < consCount; i++) {
                consumers[i] = new Consumer("Consumer_" + i, buffer,
                        maxConsTime);
                consumers[i].start();
            }
            Producer[] producers = new Producer[prodCount];
            for (int i = 0; i < prodCount; i++) {
                producers[i] = new Producer("Producer_" + i, buffer,
                        maxProdTime);
                producers[i].start();
            }

            while (true) {
                buffer.print();
                // buffer.print2();
                Thread.sleep(1000);
            }
        } catch (Exception logOrIgnore) {
            ;
        }
    }
}