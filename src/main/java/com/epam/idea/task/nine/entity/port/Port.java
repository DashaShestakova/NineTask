package com.epam.idea.task.nine.entity.port;

import com.epam.idea.task.nine.entity.container.Container;
import com.epam.idea.task.nine.entity.dock.Dock;
import com.epam.idea.task.nine.entity.dock.DockStorage;
import com.epam.idea.task.nine.exception.DataException;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Port {
    private static AtomicBoolean initialized = new AtomicBoolean(false);
    private static Port instance = null;
    private static Lock lock = new ReentrantLock();

    private DockStorage dockStorage;
    private Queue<Container> containers = new LinkedList<>();

    private Port() {
    }

    public static Port getInstance() {
        if (!initialized.get()) {
            try {
                lock.lock();
                if (!initialized.get()) {
                    instance = new Port();
                    initialized.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    public Dock getDock() throws DataException {
        try {
            return dockStorage.getBerth();
        } catch (DataException e) {
            throw new DataException(e.getMessage());
        }
    }

    public void returnDock(Dock dock) {
        dockStorage.returnBerth(dock);
    }

    public boolean offerContainer(Container container) {
        lock.lock();
        try {
            return containers.offer(container);
        } finally {
            lock.unlock();
        }
    }

    public Container getNewContainer(List<Integer> oldContainersId) {
        lock.lock();
        try {
            for (Container container : containers) {
                int containerId = container.getRegistrationNumber();
                if (!oldContainersId.contains(containerId)) {
                    containers.remove(container);
                    return container;
                }
            }
            return null;
        } finally {
            lock.unlock();
        }
    }

    public void setDockStorage(DockStorage dockStorage) {
        this.dockStorage = dockStorage;
    }
}
