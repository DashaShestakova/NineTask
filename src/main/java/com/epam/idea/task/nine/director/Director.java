package com.epam.idea.task.nine.director;

import com.epam.idea.task.nine.entity.Ship;
import com.epam.idea.task.nine.entity.port.PortStorage;
import com.epam.idea.task.nine.parser.ShipCreator;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Director {
    private static final String SHIPS_DATA_FILE = "src/test/resources/input.json";
    private static final int DOCK_AMOUNT = 3;
    private static final int STORAGE_CAPACITY = 20;
    private static final int CONCURRENT_AMOUNT = 3;

    public static void main(String[]args){
        initPort();
        process(SHIPS_DATA_FILE);
    }

    private static void initPort(){
        PortStorage contentGenerator = new PortStorage();
        contentGenerator.generatePort(DOCK_AMOUNT,STORAGE_CAPACITY);
    }

    public static void process(String shipsPath){
        ShipCreator shipCreator = new ShipCreator();
        List<Ship> ships = shipCreator.createShips(shipsPath);

        ExecutorService executorService = Executors.newFixedThreadPool(CONCURRENT_AMOUNT);
        ships.forEach(executorService::execute);
        executorService.shutdown();
    }


}
