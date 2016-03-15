package lab01.Printer;

/**
 * @author Stefan R. Bachmann on  03.02.2016
 * @version v0.1 - lab01
 */
public class Printer2 implements Runnable {

    private char ch;

    public Printer2(char ch, int sleepTime) {
        this.ch = ch;
        this.sleepTime = sleepTime;

    }
    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+" run laueft an");
        for (int i = 1; i < 100; i++) {
            System.out.print(ch);
            Thread.yield();
        }
        System.out.println('\n' + Thread.currentThread().getName() + " run  fertig");

    }


    int sleepTime;
    public static void main(String[] arg) {
        Printer2 a = new Printer2('.', 0);
        Printer2 b = new Printer2('*', 0);
        Thread t1 = new Thread(a, "PrinterA");
        Thread t2 = new Thread(b, "PrinterB");
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Main ended");
    }

}
