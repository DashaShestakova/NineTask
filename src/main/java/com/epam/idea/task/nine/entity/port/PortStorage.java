package com.epam.idea.task.nine.entity.port;

import com.epam.idea.task.nine.entity.container.Container;
import com.epam.idea.task.nine.entity.container.ContainerRegistrator;
import com.epam.idea.task.nine.entity.dock.Dock;
import com.epam.idea.task.nine.entity.dock.DockStorage;

import java.util.LinkedList;

public class PortStorage {
    private Port port = Port.getInstance();

    public void generatePort(final int berthAmount, int storageCapacity) {
        LinkedList<Dock> dockList = new LinkedList<Dock>() {
            {
                for (int id = 0; id < berthAmount; id++) {
                    this.add(new Dock(id));
                }
            }
        };
        DockStorage dockStorage = new DockStorage(dockList);
        port.setDockStorage(dockStorage);
        generateHalfFullStorage(storageCapacity);
    }

    private void generateHalfFullStorage(int capacity) {
        ContainerRegistrator containerRegistrator = ContainerRegistrator.getRegistrator();
        int containerAmount = capacity / 2;
        for (int i = 0; i < containerAmount; i++) {
            Container container = containerRegistrator.getContainer();
            port.offerContainer(container);
        }
    }
}
