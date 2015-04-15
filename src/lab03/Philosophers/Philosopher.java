package lab03.Philosophers;

public class Philosopher extends Thread {
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
            sleep((int) (Math.random() * time * 50));
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
