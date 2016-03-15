package lab02.Philosophers;

import java.util.*;

enum PhiloState {
    thinking, hungry, eating;
}

class PhilosopherTable extends Observable {
    private int philoCount;
    private Philosopher[] philosophers;
    private ForkManager forkManager;

    public PhilosopherTable(int philoCount) {
        System.out.println("creating table ...");
        this.philoCount = philoCount;
        philosophers = new Philosopher[philoCount];
        forkManager = new ForkManager(philoCount);

        for (int i = philoCount - 1; i >= 0; i--) {
            philosophers[i] = new Philosopher(this, i);
        }
    }

    public ForkManager getForkManager() {
        return forkManager;
    }

    public void notifyStateChange(Philosopher sender) {
        setChanged();
        notifyObservers(sender);
    }

    public void start() {
        notifyStateChange(null);
        for (int i = philoCount - 1; i >= 0; i--) {
            philosophers[i].start();
            philosophers[i].setPriority(Thread.MIN_PRIORITY);
        }
    }

    public Philosopher getPhilo(int i) {
        return philosophers[i];
    }

    public int left(int i) {
        return (philoCount + i - 1) % philoCount;
    }

    public int right(int i) {
        return (i + 1) % philoCount;
    }

}
