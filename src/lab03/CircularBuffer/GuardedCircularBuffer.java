package lab03.CircularBuffer;

import java.util.concurrent.locks.*;

/**
 * Created by Fabian on 14.04.15.
 */
public class GuardedCircularBuffer<T> extends CircularBuffer<T> {

    private Lock lock = new ReentrantLock();
    private Condition waitForProducerToProduce = lock.newCondition();
    private Condition waitForConsumerToConsume = lock.newCondition();


    public GuardedCircularBuffer(Class<T> clazz, int bufferSize) {
        super(clazz, bufferSize);
    }


    @Override
    public boolean put(T item) {

        lock.lock();
        boolean result =false;
        try {
            while(this.full()){
                waitForConsumerToConsume.await();
            }
             result = super.put(item);
            waitForProducerToProduce.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            lock.unlock();
        }

        return result;

    }


    @Override
    public T get(){
        lock.lock();
        T result = null;
        try {
            while(this.empty()){
                waitForProducerToProduce.await();
            }
            result = super.get();
            waitForConsumerToConsume.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            lock.unlock();
        }

        return result;
    }
}

