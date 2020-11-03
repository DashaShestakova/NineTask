package com.epam.idea.task.nine.entity;

import com.epam.idea.task.nine.entity.container.Container;
import com.epam.idea.task.nine.entity.dock.Dock;
import com.epam.idea.task.nine.entity.port.Port;
import com.epam.idea.task.nine.exception.DataException;
import com.epam.idea.task.nine.parser.ActionType;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Ship implements Runnable{
    private static final Logger logger = LogManager.getLogger(Ship.class);

    private String name;
    private Port port;
    private Dock dock;
    private int capacity;
    private int containerAmount;
    private ActionType actionType;
    private Queue<Container> containers = new LinkedList<>();

    @JsonCreator
    public Ship(@JsonProperty("name") String name, @JsonProperty("capacity") int capacity,
                @JsonProperty("container amount") int containerAmount,
                @JsonProperty("action type") ActionType actionType) {
        this.name = name;
        this.capacity = capacity;
        this.actionType = actionType;
        this.containerAmount = containerAmount;
    }

    public void run() {
        unloading();
        switch (actionType) {
            case UNLOAD:
                unloadShip();
                break;
            case LOAD:
                loadShip();
                break;
            case UNLOAD_LOAD:
                unloadShip();
                loadShip();
                break;
            default:
                logger.error("Illegal port service value. Ship " + name);
                throw new IllegalArgumentException();
        }
        leave();
    }

    private void unloadShip() {
        while (containerAmount > 0) {
            Container container = containers.poll();
            if (container == null) {
                logger.info("Ship" + name + "is empty");
                break;
            }
            if (port.offerContainer(container)) {
                int containerId = container.getRegistrationNumber();
                dock.notifyUnloaded(containerId);
                logger.info("The ship " + name + " has unloaded container # " + containerId);
            } else {
                logger.info("The port storage is full");
                break;
            }
            containerAmount--;
        }
    }

    private void loadShip() {
        List<Integer> unloadedContainersId = dock.getUnloadedContainersId();
        while (containerAmount < capacity) {
            Container container = port.getNewContainer(unloadedContainersId);
            if (container == null) {
                logger.info("Ship " + name + ": Nothing to load");
                break;
            }
            if (containers.offer(container)) {
                containerAmount++;
                int containerId = container.getRegistrationNumber();
                logger.info("The ship " + name + " has loaded container" + containerId);
            } else {
                logger.info("The storage of ship " + name + " is full");
            }
        }
    }

    private void unloading() {
        try {
            dock = port.getDock();
            logger.info("The Ship " + name + " has unloaded to the dock " + dock.getDockId());
        } catch (DataException e) {
            logger.error("An error occurred while unloaded ship " + name, e.getMessage());
        }
    }

    private void leave() {
        dock.clearUnloadedList();
        port.returnDock(dock);
        logger.info("The ship " + name + " has left the berth # " + dock.getDockId());
    }

    public void setPort(Port port) {
        this.port = port;
    }

    public void setContainers(Queue<Container> containers) {
        this.containers = containers;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public int getContainerAmount() {
        return containerAmount;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getName() {
        return name;
    }

}
