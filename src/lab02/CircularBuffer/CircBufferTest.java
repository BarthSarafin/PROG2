
package lab02.CircularBuffer;


public class CircBufferTest {
    public static void main(String[] args) {
        final int capacity = 15; // Number of buffer items
        final int prodCount = 4; // Number of producer threads
        final int consCount = 2; // Number of consumer threads
        final int maxProdTime = 1000;// max. production time for one item
        final int maxConsTime = 500; // max. consumption time for one item

        try {
            Buffer<String> buffer = new GuardedCircularBuffer<String>(String.class,
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