package org.example;

import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class FacilityFloorplan extends Thread {

    static final int numberOfStations = 16; //Always a multiple of 4
    static final int typesOfStations = 4;
    static int stationScale = 50; //Spacing on the grid
    static Station[] stations;


    // ---=== STEP 1: Create  stations ===---
    public static Station[] createFloorplan() {
        Station[] stationsArray = new Station[numberOfStations];
        int stationIDCounter = 0;

        //1x1 stations
        String stationFunction = "Small";
        for (int i = 0; i < (numberOfStations / typesOfStations); i++) {
            Station s = new Station(stationFunction, stationScale, stationScale, stationIDCounter);
            s.setFunction(stationFunction);
            s.setId(stationIDCounter);
            s.setHeight(stationScale);
            s.setWidth(stationScale);
            while ((s.getxCoordinate() == 0) && (s.getyCoordinate() == 0)) {
                int randomXInt = ThreadLocalRandom.current().nextInt(1, 17);
                int randomYInt = ThreadLocalRandom.current().nextInt(1, 17);
                if (stationIDCounter == 0) {
                    s.setxCoordinate(stationScale * randomXInt);
                    s.setyCoordinate(stationScale * randomYInt);
                } else {
                    for (int p = 0; p < stationIDCounter; p++) {
                        if (stationsArray[p].getFunction().equals("Big")) {
                            if (((stationsArray[p].getxCoordinate() != (stationScale * randomXInt)) && (stationsArray[p].getyCoordinate() != (stationScale * randomYInt))) &&
                                    ((stationsArray[p].getxCoordinate() != (stationScale * randomXInt)) && (stationsArray[p].getyCoordinate() != (stationScale * (randomYInt - 1)))) &&
                                    ((stationsArray[p].getxCoordinate() != (stationScale * (randomXInt - 1))) && (stationsArray[p].getyCoordinate() != (stationScale * randomYInt))) &&
                                    ((stationsArray[p].getxCoordinate() != (stationScale * (randomXInt - 1))) && (stationsArray[p].getyCoordinate() != (stationScale * (randomYInt - 1))))
                            ) {
                                s.setxCoordinate(stationScale * randomXInt);
                                s.setyCoordinate(stationScale * randomYInt);
                            }
                        }
                        else if (stationsArray[p].getFunction().equals("Long")) {
                            if (((stationsArray[p].getxCoordinate() != (stationScale * randomXInt)) && (stationsArray[p].getyCoordinate() != (stationScale * randomYInt))) &&
                                    ((stationsArray[p].getxCoordinate() != (stationScale * randomXInt)) && (stationsArray[p].getyCoordinate() != (stationScale * (randomYInt - 1))))
                            ) {
                                s.setxCoordinate(stationScale * randomXInt);
                                s.setyCoordinate(stationScale * randomYInt);
                            }
                        }
                        else if (stationsArray[p].getFunction().equals("Wide")) {
                            if (((stationsArray[p].getxCoordinate() != (stationScale * randomXInt)) && (stationsArray[p].getyCoordinate() != (stationScale * randomYInt))) &&
                                    ((stationsArray[p].getxCoordinate() != (stationScale * (randomXInt - 1))) && (stationsArray[p].getyCoordinate() != (stationScale * randomYInt)))
                            ) {
                                s.setxCoordinate(stationScale * randomXInt);
                                s.setyCoordinate(stationScale * randomYInt);
                            }
                        }
                        else if (stationsArray[p].getFunction().equals("Small")) {
                            if (((stationsArray[p].getxCoordinate() != (stationScale * randomXInt)) && (stationsArray[p].getyCoordinate() != (stationScale * randomYInt)))
                            ) {
                                s.setxCoordinate(stationScale * randomXInt);
                                s.setyCoordinate(stationScale * randomYInt);
                            }
                        }

                    }
                }
            }
            stationsArray[stationIDCounter] = s;

//

            stationIDCounter++;


        }


        //1x2 stations
        stationFunction = "Long";
        for (int i = 0; i < (numberOfStations / typesOfStations); i++) {
            Station s = new Station(stationFunction, stationScale, stationScale * 2, stationIDCounter);
            s.setFunction(stationFunction);
            s.setId(stationIDCounter);
            s.setHeight(stationScale * 2);
            s.setWidth(stationScale);
//            setXYCoordinates(s, stationsArray, stationIDCounter);

            while ((s.getxCoordinate() == 0) && (s.getyCoordinate() == 0)) {
                int randomXInt = ThreadLocalRandom.current().nextInt(1, 17);
                int randomYInt = ThreadLocalRandom.current().nextInt(1, 17);
                if (stationIDCounter == 0) {
                    s.setxCoordinate(stationScale * randomXInt);
                    s.setyCoordinate(stationScale * randomYInt);
                } else {
                    for (int p = 0; p < stationIDCounter; p++) {
                        if (stationsArray[p].getFunction().equals("Big")) {
                            if (((stationScale * (randomYInt+1)) <= (stationScale * 16)) &&
                                    ((stationsArray[p].getxCoordinate() != (stationScale * randomXInt)) && (stationsArray[p].getyCoordinate() != (stationScale * randomYInt))) &&
                                    ((stationsArray[p].getxCoordinate() != (stationScale * randomXInt)) && (stationsArray[p].getyCoordinate() != (stationScale * (randomYInt + 1)))) &&
                                    ((stationsArray[p].getxCoordinate() != (stationScale * randomXInt)) && (stationsArray[p].getyCoordinate() != (stationScale * (randomYInt - 1)))) &&
                                    ((stationsArray[p].getxCoordinate() != (stationScale * (randomXInt - 1))) && (stationsArray[p].getyCoordinate() != (stationScale * randomYInt))) &&
                                    ((stationsArray[p].getxCoordinate() != (stationScale * (randomXInt - 1))) && (stationsArray[p].getyCoordinate() != (stationScale * (randomYInt + 1)))) &&
                                    ((stationsArray[p].getxCoordinate() != (stationScale * (randomXInt - 1))) && (stationsArray[p].getyCoordinate() != (stationScale * (randomYInt - 1))))
                            ) {
                                s.setxCoordinate(stationScale * randomXInt);
                                s.setyCoordinate(stationScale * randomYInt);
                            }
                        } else if (stationsArray[p].getFunction().equals("Long")) {
                            if (((stationScale * (randomYInt+1)) <= (stationScale * 16)) &&
                                    ((stationsArray[p].getxCoordinate() != (stationScale * randomXInt)) && (stationsArray[p].getyCoordinate() != (stationScale * randomYInt))) &&
                                    ((stationsArray[p].getxCoordinate() != (stationScale * randomXInt)) && (stationsArray[p].getyCoordinate() != (stationScale * (randomYInt + 1)))) &&
                                    ((stationsArray[p].getxCoordinate() != (stationScale * randomXInt)) && (stationsArray[p].getyCoordinate() != (stationScale * (randomYInt - 1))))
                            ) {
                                s.setxCoordinate(stationScale * randomXInt);
                                s.setyCoordinate(stationScale * randomYInt);
                            }
                        }
                        if (stationsArray[p].getFunction().equals("Wide")) {
                            if (((stationScale * (randomYInt+1)) <= (stationScale * 16)) &&
                                    ((stationsArray[p].getxCoordinate() != (stationScale * randomXInt)) && (stationsArray[p].getyCoordinate() != (stationScale * randomYInt))) &&
                                    ((stationsArray[p].getxCoordinate() != (stationScale * randomXInt)) && (stationsArray[p].getyCoordinate() != (stationScale * (randomYInt + 1)))) &&
                                    ((stationsArray[p].getxCoordinate() != (stationScale * (randomXInt - 1))) && (stationsArray[p].getyCoordinate() != (stationScale * randomYInt))) &&
                                    ((stationsArray[p].getxCoordinate() != (stationScale * (randomXInt - 1))) && (stationsArray[p].getyCoordinate() != (stationScale * (randomYInt + 1))))
                            ) {
                                s.setxCoordinate(stationScale * randomXInt);
                                s.setyCoordinate(stationScale * randomYInt);
                            }
                        }
                        if (stationsArray[p].getFunction().equals("Small")) {
                            if (((stationScale * (randomYInt+1)) <= (stationScale * 16)) &&
                                    ((stationsArray[p].getxCoordinate() != (stationScale * randomXInt)) && (stationsArray[p].getyCoordinate() != (stationScale * randomYInt))) &&
                                    ((stationsArray[p].getxCoordinate() != (stationScale * randomXInt)) && (stationsArray[p].getyCoordinate() != (stationScale * (randomYInt + 1))))
                            ) {
                                s.setxCoordinate(stationScale * randomXInt);
                                s.setyCoordinate(stationScale * randomYInt);
                            }
                        }
                    }
                }
            }
            stationsArray[stationIDCounter] = s;
            stationIDCounter++;
        }


        //2x2 stations
        stationFunction = "Big";
        for (int i = 0; i < (numberOfStations / typesOfStations); i++) {
            Station s = new Station(stationFunction, stationScale * 2, stationScale * 2, stationIDCounter);
            s.setFunction(stationFunction);
            s.setId(stationIDCounter);
            s.setHeight(stationScale * 2);
            s.setWidth(stationScale * 2);
//            setXYCoordinates(s, stationsArray, stationIDCounter);

            while ((s.getxCoordinate() == 0) && (s.getyCoordinate() == 0)) {
                int randomXInt = ThreadLocalRandom.current().nextInt(1, 17);
                int randomYInt = ThreadLocalRandom.current().nextInt(1, 17);
                if (stationIDCounter == 0) {
                    s.setxCoordinate(stationScale * randomXInt);
                    s.setyCoordinate(stationScale * randomYInt);
                } else {
                    for (int p = 0; p < stationIDCounter; p++) {

                        if (    ((stationScale *( randomXInt+1 )) <= (stationScale * 16)) &&
                                ((stationScale * ( randomYInt+1 )) <= (stationScale * 16)) &&
                                ((stationsArray[p].getxCoordinate() != (stationScale * randomXInt)) && (stationsArray[p].getyCoordinate() != (stationScale * randomYInt))) &&
                                ((stationsArray[p].getxCoordinate() != (stationScale * randomXInt)) && (stationsArray[p].getyCoordinate() != (stationScale * (randomYInt + 1)))) &&
                                ((stationsArray[p].getxCoordinate() != (stationScale * randomXInt)) && (stationsArray[p].getyCoordinate() != (stationScale * (randomYInt - 1)))) &&
                                ((stationsArray[p].getxCoordinate() != (stationScale * (randomXInt + 1))) && (stationsArray[p].getyCoordinate() != (stationScale * randomYInt))) &&
                                ((stationsArray[p].getxCoordinate() != (stationScale * (randomXInt + 1))) && (stationsArray[p].getyCoordinate() != (stationScale * (randomYInt + 1)))) &&
                                ((stationsArray[p].getxCoordinate() != (stationScale * (randomXInt + 1))) && (stationsArray[p].getyCoordinate() != (stationScale * (randomYInt - 1)))) &&
                                ((stationsArray[p].getxCoordinate() != (stationScale * (randomXInt - 1))) && (stationsArray[p].getyCoordinate() != (stationScale * randomYInt))) &&
                                ((stationsArray[p].getxCoordinate() != (stationScale * (randomXInt - 1))) && (stationsArray[p].getyCoordinate() != (stationScale * (randomYInt + 1)))) &&
                                ((stationsArray[p].getxCoordinate() != (stationScale * (randomXInt - 1))) && (stationsArray[p].getyCoordinate() != (stationScale * (randomYInt - 1))))
                        ) {
                            s.setxCoordinate(stationScale * randomXInt);
                            s.setyCoordinate(stationScale * randomYInt);
                        }

                    }
                }
            }

            stationsArray[stationIDCounter] = s;
            stationIDCounter++;
        }

        //2x1 stations
        stationFunction = "Wide";
        for (int i = 0; i < (numberOfStations / typesOfStations); i++) {

            Station s = new Station(stationFunction, stationScale * 2, stationScale, stationIDCounter);
            s.setFunction(stationFunction);
            s.setId(stationIDCounter);
            s.setHeight(stationScale);
            s.setWidth(stationScale * 2);
//            setXYCoordinates(s, stationsArray, stationIDCounter);

            while ((s.getxCoordinate() == 0) && (s.getyCoordinate() == 0)) {
                int randomXInt = ThreadLocalRandom.current().nextInt(1, 17);
                int randomYInt = ThreadLocalRandom.current().nextInt(1, 17);
                if (stationIDCounter == 0) {
                    s.setxCoordinate(stationScale * randomXInt);
                    s.setyCoordinate(stationScale * randomYInt);
                } else {
                    for (int p = 0; p < stationIDCounter; p++) {
                        if (stationsArray[p].getFunction().equals("Big")) {
                            if (((stationScale *( randomXInt+1 )) <= (stationScale * 16)) &&
                                    ((stationsArray[p].getxCoordinate() != (stationScale * randomXInt)) && (stationsArray[p].getyCoordinate() != (stationScale * randomYInt))) &&
                                    ((stationsArray[p].getxCoordinate() != (stationScale * randomXInt)) && (stationsArray[p].getyCoordinate() != (stationScale * (randomYInt - 1)))) &&
                                    ((stationsArray[p].getxCoordinate() != (stationScale * (randomXInt + 1))) && (stationsArray[p].getyCoordinate() != (stationScale * randomYInt))) &&
                                    ((stationsArray[p].getxCoordinate() != (stationScale * (randomXInt + 1))) && (stationsArray[p].getyCoordinate() != (stationScale * (randomYInt - 1)))) &&
                                    ((stationsArray[p].getxCoordinate() != (stationScale * (randomXInt - 1))) && (stationsArray[p].getyCoordinate() != (stationScale * randomYInt))) &&
                                    ((stationsArray[p].getxCoordinate() != (stationScale * (randomXInt - 1))) && (stationsArray[p].getyCoordinate() != (stationScale * (randomYInt - 1))))
                            ) {
                                s.setxCoordinate(stationScale * randomXInt);
                                s.setyCoordinate(stationScale * randomYInt);
                            }
                        } else if (stationsArray[p].getFunction().equals("Long")) {
                            if (((stationScale *( randomXInt+1 )) <= (stationScale * 16)) &&
                                    ((stationsArray[p].getxCoordinate() != (stationScale * randomXInt)) && (stationsArray[p].getyCoordinate() != (stationScale * randomYInt))) &&
                                    ((stationsArray[p].getxCoordinate() != (stationScale * randomXInt)) && (stationsArray[p].getyCoordinate() != (stationScale * (randomYInt - 1)))) &&
                                    ((stationsArray[p].getxCoordinate() != (stationScale * (randomXInt + 1))) && (stationsArray[p].getyCoordinate() != (stationScale * randomYInt))) &&
                                    ((stationsArray[p].getxCoordinate() != (stationScale * (randomXInt + 1))) && (stationsArray[p].getyCoordinate() != (stationScale * (randomYInt - 1))))
                            ) {
                                s.setxCoordinate(stationScale * randomXInt);
                                s.setyCoordinate(stationScale * randomYInt);
                            }
                        } else if (stationsArray[p].getFunction().equals("Wide")) {
                            if (((stationScale *( randomXInt+1 )) <= (stationScale * 16)) &&
                                    ((stationsArray[p].getxCoordinate() != (stationScale * randomXInt)) && (stationsArray[p].getyCoordinate() != (stationScale * randomYInt))) &&
                                    ((stationsArray[p].getxCoordinate() != (stationScale * (randomXInt + 1))) && (stationsArray[p].getyCoordinate() != (stationScale * randomYInt))) &&
                                    ((stationsArray[p].getxCoordinate() != (stationScale * (randomXInt - 1))) && (stationsArray[p].getyCoordinate() != (stationScale * randomYInt)))
                            ) {
                                s.setxCoordinate(stationScale * randomXInt);
                                s.setyCoordinate(stationScale * randomYInt);
                            }
                        } else if (stationsArray[p].getFunction().equals("Small")) {
                            if (((stationScale * ( randomXInt+1 )) <= (stationScale * 16)) &&
                                    ((stationsArray[p].getxCoordinate() != (stationScale * randomXInt)) && (stationsArray[p].getyCoordinate() != (stationScale * randomYInt))) &&
                                    ((stationsArray[p].getxCoordinate() != (stationScale * (randomXInt + 1))) && (stationsArray[p].getyCoordinate() != (stationScale * randomYInt)))
                            ) {
                                s.setxCoordinate(stationScale * randomXInt);
                                s.setyCoordinate(stationScale * randomYInt);
                            }
                        }
                    }
                }
            }
            stationsArray[stationIDCounter] = s;

            stationIDCounter++;
        }


        return stationsArray;
    }


    // ---=== STEP 2: Create 4 threads ===---
    public static Thread threadOne = new Thread();
    public static Thread threadTwo = new Thread();
    public static Thread threadThree = new Thread();
    public static Thread threadFour = new Thread();


    // ====== OUTSIDE EACH OF EACH OF THE THREADS ======

    // ---=== STEP 6: Sort global poll of solutions ===---


//    @Override
//    public void run() {
//
//        //in here Put what I want each thread to do
//        // - Thread independent
//
//        // ====== INSIDE EACH OF EACH OF THE THREADS ======
//
//        // ---=== STEP 3: Give each thread a defensive copy of the stations ===---
//
//        // ---=== STEP 4: Create floor plans ===---
//
//        // ---=== OPTION 1: Produce a floorplan with random placements of stations ===---
//
//        // ---=== OPTION 2.1: Copy half of an existing floor plan ===---
//
//        // ---=== OPTION 2.2: Create other half of the floor plan ===---
//
//
//        // ---=== STEP 5: Calculate Affinity total score and add it to the global poll of solutions ===---
//
//        super.run();
//    }

    public static void main(String[] args) {
        //Start threads
//        threadOne.start();
//        threadTwo.start();
//        threadThree.start();
//        threadFour.start();
        stations = createFloorplan();
        for (Station s : stations){
            System.out.println("\nStation: " + s.getId() +
                    "\nStation function: " + s.getFunction() +
                    "\nStation width: " + s.getWidth() +
                    "\nStation Height: " + s.getHeight() +
                    "\nStation xCor: " + s.getxCoordinate() +
                    "\nStation yCor: " + s.getyCoordinate() + "\n");
        }
    }
}