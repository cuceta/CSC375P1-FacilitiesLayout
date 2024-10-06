package org.example;

import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class FacilityFloorplan extends Thread {

    static final int numberOfStations = 16; //Always a multiple of 4
    static final int typesOfStations = 4;
    static int stationScale = 50; //Spacing on the grid


    // ---=== STEP 1: Create  stations ===---
    public static Station[] createFloorplan() {
        Station[] stationsArray = new Station[numberOfStations];
        int stationIDCounter = 0;

        //1x1 stations
        String stationFunction = "A";
        for (int i = 0; i < (numberOfStations / typesOfStations); i++) {
            int randomXInt = ThreadLocalRandom.current().nextInt(1, 17);
            System.out.println(randomXInt);
            int randomYInt = ThreadLocalRandom.current().nextInt(1, 17);
            System.out.println(randomYInt);
            Station s = new Station(stationFunction, stationScale, stationScale, stationIDCounter);
            s.setFunction(stationFunction);
            s.setId(stationIDCounter);
            s.setHeight(stationScale);
            s.setWidth(stationScale);

            while ( (s.getyCoordinate() == 0) && (s.getxCoordinate() == 0) ) {
                if (stationIDCounter == 0) {
                    s.setxCoordinate(stationScale * randomXInt);
                    s.setyCoordinate(stationScale * randomYInt);
                } else {
                    for (int p = 0; p < stationIDCounter; p++) {
                        if ((stationsArray[p].getxCoordinate() != (stationScale * randomXInt)) &&
                                ((stationsArray[p].getyCoordinate() != (stationScale * randomYInt)))) {
                            s.setxCoordinate(stationScale * randomXInt);
                            s.setyCoordinate(stationScale * randomYInt);
                        }
                    }


                }
            }

            stationsArray[stationIDCounter] = s;

            System.out.println("\nStation: " + stationIDCounter +
                    "\nStation function: " + stationsArray[stationIDCounter].getFunction() +
                    "\nStation width: " + stationsArray[stationIDCounter].getWidth() +
                    "\nStation Height: " + stationsArray[stationIDCounter].getHeight() +
                    "\nStation xCor: " + stationsArray[stationIDCounter].getxCoordinate() +
                    "\nStation yCor: " + stationsArray[stationIDCounter].getyCoordinate() + "\n");


            stationIDCounter++;


        }


        //1x2 stations
        stationFunction = "B";
        for (int i = 0; i < (numberOfStations / typesOfStations); i++) {
            Station s = new Station(stationFunction, stationScale, stationScale * 2, stationIDCounter);
            s.setFunction(stationFunction);
            s.setId(stationIDCounter);
            s.setHeight(stationScale * 2);
            s.setWidth(stationScale);

            while ( (s.getyCoordinate() == 0) && (s.getxCoordinate() == 0) ) {
                int randomXInt = ThreadLocalRandom.current().nextInt(1, 17);
                int randomYInt = ThreadLocalRandom.current().nextInt(1, 17);
                if (stationIDCounter == 0) {
                    s.setxCoordinate(stationScale * randomXInt);
                    s.setyCoordinate(stationScale * randomYInt);
                } else {
                    for (int p = 0; p < stationIDCounter; p++) {
                        if ( (stationsArray[p].getxCoordinate() != (stationScale * randomXInt) ) &&

                                ((stationsArray[p].getyCoordinate() != (stationScale * randomYInt)) &&
                                ((stationsArray[p].getyCoordinate() != ((stationScale * 2) * randomYInt))) &&
                                (((stationScale * 2) * randomYInt) < (stationScale * 16)))) {
                            s.setxCoordinate(stationScale * randomXInt);
                            s.setyCoordinate(stationScale * randomYInt);
                        }
                    }
                }
            }
            stationsArray[stationIDCounter] = s;
            System.out.println("\nStation: " + stationIDCounter +
                    "\nStation function: " + stationsArray[stationIDCounter].getFunction() +
                    "\nStation width: " + stationsArray[stationIDCounter].getWidth() +
                    "\nStation Height: " + stationsArray[stationIDCounter].getHeight() +
                    "\nStation xCor: " + stationsArray[stationIDCounter].getxCoordinate() +
                    "\nStation yCor: " + stationsArray[stationIDCounter].getyCoordinate() + "\n");

            stationIDCounter++;
        }

        //2x1 stations
        stationFunction = "C";
        for (int i = 0; i < (numberOfStations / typesOfStations); i++) {

            Station s = new Station(stationFunction, stationScale * 2, stationScale, stationIDCounter);
            s.setFunction(stationFunction);
            s.setId(stationIDCounter);
            s.setHeight(stationScale);
            s.setWidth(stationScale * 2);
            while ( (s.getyCoordinate() == 0) && (s.getxCoordinate() == 0) ) {
                int randomXInt = ThreadLocalRandom.current().nextInt(1, 17);
                int randomYInt = ThreadLocalRandom.current().nextInt(1, 17);
                if (stationIDCounter == 0) {
                    s.setxCoordinate(stationScale * randomXInt);
                    s.setyCoordinate(stationScale * randomYInt);
                } else {
                    for (int p = 0; p < stationIDCounter; p++) {
                        if ((((stationsArray[p].getxCoordinate() != (stationScale * randomXInt)) &&
                                ((stationsArray[p].getxCoordinate() != ((stationScale * 2) * randomXInt)))) &&
                                (((stationScale * 2) * randomXInt) < (stationScale * 16))) &&

                                (stationsArray[p].getyCoordinate() != (stationScale * randomYInt)) ){

                            s.setxCoordinate(stationScale * randomXInt);
                            s.setyCoordinate(stationScale * randomYInt);
                        }
                    }
                }
            }
            stationsArray[stationIDCounter] = s;
            System.out.println("\nStation: " + stationIDCounter +
                    "\nStation function: " + stationsArray[stationIDCounter].getFunction() +
                    "\nStation width: " + stationsArray[stationIDCounter].getWidth() +
                    "\nStation Height: " + stationsArray[stationIDCounter].getHeight() +
                    "\nStation xCor: " + stationsArray[stationIDCounter].getxCoordinate() +
                    "\nStation yCor: " + stationsArray[stationIDCounter].getyCoordinate() + "\n");

            stationIDCounter++;
        }

        //2x2 stations
        stationFunction = "D";
        for (int i = 0; i < (numberOfStations / typesOfStations); i++) {
            Station s = new Station(stationFunction, stationScale * 2, stationScale * 2, stationIDCounter);
            s.setFunction(stationFunction);
            s.setId(stationIDCounter);
            s.setHeight(stationScale * 2);
            s.setWidth(stationScale * 2);

            while ( (s.getyCoordinate() == 0) && (s.getxCoordinate() == 0) ) {
                int randomXInt = ThreadLocalRandom.current().nextInt(1, 17);
                int randomYInt = ThreadLocalRandom.current().nextInt(1, 17);
                if (stationIDCounter == 0) {
                    s.setxCoordinate(stationScale * randomXInt);
                    s.setyCoordinate(stationScale * randomYInt);
                } else {
                    for (int p = 0; p < stationIDCounter; p++) {
                        if(  ((((stationScale * 2) * randomXInt) < (stationScale * 16)) && (((stationScale * 2) * randomYInt) < (stationScale * 16))) &&
                                ((stationsArray[p].getxCoordinate() != (stationScale * randomXInt)) && ((stationsArray[p].getyCoordinate() != (stationScale * randomYInt)))) &&
                                (((stationsArray[p].getxCoordinate() != ((stationScale * 2) * randomXInt))) && ((stationsArray[p].getyCoordinate() != ((stationScale * 2) * randomYInt)))) &&
                                ((stationsArray[p].getxCoordinate() != (stationScale * randomXInt)) && ((stationsArray[p].getyCoordinate() != ((stationScale * 2) * randomYInt)))) &&
                                (((stationsArray[p].getxCoordinate() != ((stationScale * 2) * randomXInt))) && ((stationsArray[p].getyCoordinate() != (stationScale * randomYInt))))
                        ) {
                            if(stationsArray[p].getFunction().equals("B")){
                                if( ((stationsArray[p].getxCoordinate() != (stationScale * randomXInt)) && ((stationsArray[p].getyCoordinate() != (stationScale * (randomYInt-1) )))) &&
                                        (((stationsArray[p].getxCoordinate() != ((stationScale * 2) * randomXInt))) && ((stationsArray[p].getyCoordinate() != (stationScale * (randomYInt-1) ))))
                                        )  {
                                    s.setxCoordinate(stationScale * randomXInt);
                                    s.setyCoordinate(stationScale * randomYInt);
                                }
                            } else if (stationsArray[p].getFunction().equals("C")) {
                                if( ((stationsArray[p].getxCoordinate() != (stationScale * (randomXInt-1))) &&  ((stationsArray[p].getyCoordinate() != (stationScale * randomYInt)))) &&
                                        ((stationsArray[p].getxCoordinate() != (stationScale * (randomXInt-1))) && ((stationsArray[p].getyCoordinate() != ((stationScale * 2) * randomYInt))))
                                ){
                                    s.setxCoordinate(stationScale * randomXInt);
                                    s.setyCoordinate(stationScale * randomYInt);
                                }
                            } else {
                                s.setxCoordinate(stationScale * randomXInt);
                                s.setyCoordinate(stationScale * randomYInt);
                            }
                        }
                    }
                }
            }

            stationsArray[stationIDCounter] = s;

            System.out.println("\nStation: " + stationIDCounter +
                    "\nStation function: " + stationsArray[stationIDCounter].getFunction() +
                    "\nStation width: " + stationsArray[stationIDCounter].getWidth() +
                    "\nStation Height: " + stationsArray[stationIDCounter].getHeight() +
                    "\nStation xCor: " + stationsArray[stationIDCounter].getxCoordinate() +
                    "\nStation yCor: " + stationsArray[stationIDCounter].getyCoordinate() + "\n");


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
        Station[] stations = createFloorplan();
    }
}