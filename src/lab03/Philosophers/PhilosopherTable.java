package lab03.Philosophers;

import java.util.*;
import java.util.concurrent.locks.*;

enum PhiloState {
    thinking, hungry, eating;
}

class Philosopher extends Thread {
    private PhilosopherTable table;
    private PhiloState philoState = PhiloState.thinking;
    private int id;

    public Philosopher(PhilosopherTable table, int id) {
        this.id = id;
        this.table = table;
    }

    public PhiloState getPhiloState() {
        return philoState;
    }

    public long getId() {
        return id;
    }

    public int getIdOfLeftNeighbour() {
        return table.left(id);
    }

    public int getIdOfRightNeighbour() {
        return table.right(id);
    }

    private void think() {
        try {
            philoState = PhiloState.thinking;
            table.notifyStateChange(this);
            int time = 5;
            sleep((int) (Math.random() * time * 100));
        } catch (InterruptedException e) {
        }
    }

    private void eat() {
        try {
            philoState = PhiloState.eating;
            table.notifyStateChange(this);
            int time = 1;
            sleep((int) (Math.random() * time * 1000));
        } catch (InterruptedException e) {
        }
    }

    private void takeForks() {
        philoState = PhiloState.hungry;
        table.notifyStateChange(this);

        ForkManager mgr = table.getForkManager();
        mgr.aquirePairOfForks(id);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
        }
//        mgr.acquireFork(mgr.right(id));
    }

    private void putForks() {
        ForkManager mgr = table.getForkManager();
        mgr.releaseForks(id);
//        mgr.releaseFork(mgr.right(id));

    }

    public void run() {
        yield();
        while (true) {
            think();
            takeForks();
            eat();
            putForks();
        }
    }
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
