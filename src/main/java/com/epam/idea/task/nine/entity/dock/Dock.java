package com.epam.idea.task.nine.entity.dock;

import java.util.ArrayList;
import java.util.List;

public class Dock {
    private int dockId;
    private List<Integer> unloadedContainersId = new ArrayList<>();

    public Dock(int id) {
        this.dockId = id;
    }

    public void notifyUnloaded(int unloadedId) {
        unloadedContainersId.add(unloadedId);
    }

    public void clearUnloadedList() {
        unloadedContainersId.clear();
    }

    public int getDockId() {
        return dockId;
    }

    public List<Integer> getUnloadedContainersId() {
        return unloadedContainersId;
    }
}
