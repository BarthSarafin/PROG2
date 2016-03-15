package lab02.Philosophers;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// ForkManager manages the resources (=forks), used by the philosophers
public class ForkManager {

    enum ForkState {
        free, awaited, occupied
    }

    class Fork {
        public ForkState forkState;
        public Condition cond;

        public Fork(Lock m) {
            cond = m.newCondition();
            forkState = ForkState.free;
        }
    }

    private int nrForks;
    private Fork[] forks;
    private Lock mutex;
    private Lock[] pairLock;

    public ForkManager(int nrForks) {
        mutex = new ReentrantLock();
        /* ---- */
        pairLock = new Lock[5];
        for (int i = 0; i < nrForks; i++)
            pairLock[i] = new ReentrantLock() ;
        /* --- */
        this.nrForks = nrForks;
        forks = new Fork[nrForks];

        for (int i = 0; i < nrForks; i++)
            forks[i] = new Fork(mutex);
    }

    public void acquireFork(int i) {
        try {
            mutex.lock();
            while (forks[i].forkState == ForkState.occupied)
                forks[i].cond.await();
            forks[i].forkState = ForkState.occupied;
        } catch (InterruptedException e) {
        } finally {
            mutex.unlock();
        }
    }

    public void aquirePairOfForks(int i){

        int rightNeighbor = ((i+1)>=nrForks)?(i+1)-nrForks:i+1;
        pairLock[i].lock();
        acquireFork(i);
        acquireFork(rightNeighbor);

    }

    public void releaseForks(int i) {
        int rightNeighbor = ((i+1)>=nrForks)?(i+1)-nrForks:i+1;
        releaseFork(i);
        releaseFork(rightNeighbor);
        //TODO: fehlerhafter monitor. signal() einbauen
        forks[i].cond.signal();
        forks[rightNeighbor].cond.signal();
        pairLock[i].unlock();
    }
    public void releaseFork(int i) {
        try {
            mutex.lock();
            forks[i].forkState = ForkState.free;
            forks[i].cond.signal();
        } finally {
            mutex.unlock();
        }
    }

    public int left(int i) {
        return (nrForks + i - 1) % nrForks;
    }

    public int right(int i) {
        return (i + 1) % nrForks;
    }
}
