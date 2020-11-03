package com.epam.idea.task.nine.entity.dock;

import com.epam.idea.task.nine.exception.DataException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DockStorage {
    private static final Logger logger = LogManager.getLogger(DockStorage.class);
    private static Lock lock = new ReentrantLock();
    private Semaphore semaphore;
    private Queue<Dock> docks = new LinkedList<>();

    public DockStorage(Queue<Dock> docks){
        this.docks.addAll(docks);
        semaphore = new Semaphore(docks.size(),true);
    }

    public Dock getBerth() throws DataException {
        lock.lock();
        try {
            semaphore.acquire();
            Dock dock = docks.poll();
            if(dock == null){
                throw new RuntimeException("Dock has not been received");
            }
            logger.info("Dock "+ dock.getDockId() +" engaged");
            System.out.println("Dock "+ dock.getDockId() +" engaged");
            return dock;
        } catch (InterruptedException e) {
            throw new DataException(e.getMessage());
        }finally {
            lock.unlock();
        }
    }

    public void returnBerth(Dock dock){
        docks.add(dock);
        semaphore.release();
    }
}
