package org.example;

import java.awt.*;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;


public class FacilityFloorplan extends Thread {

    static final int numberOfStations = 48;//Always a multiple of 4
    static final int typesOfStations = 4;
    static int stationScale = 25;  //Spacing on the grid
    static int gridSize = 32;
    private static final ThreadLocal<Station[]> stations = new ThreadLocal<>(); //Array of stations


    HashMap<int[][], Station> coordinatesToStations;        //


    // ---=== STEP 1: Create  stations ===---
    public static Station[] createFloorplan() {
        Station[] stationsArray = new Station[numberOfStations];
        int[][] coordinates; //2D array to save the coordinate of each station
        int stationIDCounter = 0;

        //1x1 stations
        String stationFunction = "Small";
        for (int i = 0; i < (numberOfStations / typesOfStations); i++) {
            Station s = new Station(stationFunction, stationScale, stationScale, stationIDCounter);
            s.setFunction(stationFunction); s.setId(stationIDCounter); s.setHeight(stationScale); s.setWidth(stationScale);
            setXYCoordinates(s, stationsArray, stationIDCounter);
            stationsArray[stationIDCounter] = s;
            stationIDCounter++;
        }

        //1x2 stations
        stationFunction = "Long";
        for (int i = 0; i < (numberOfStations / typesOfStations); i++) {
            Station s = new Station(stationFunction, stationScale, stationScale * 2, stationIDCounter);
            s.setFunction(stationFunction); s.setId(stationIDCounter); s.setHeight(stationScale * 2); s.setWidth(stationScale);
            setXYCoordinates(s, stationsArray, stationIDCounter);
            stationsArray[stationIDCounter] = s;
            stationIDCounter++;
        }

        //2x1 stations
        stationFunction = "Wide";
        for (int i = 0; i < (numberOfStations / typesOfStations); i++) {
            Station s = new Station(stationFunction, stationScale * 2, stationScale, stationIDCounter);
            s.setFunction(stationFunction); s.setId(stationIDCounter); s.setHeight(stationScale); s.setWidth(stationScale * 2);
            setXYCoordinates(s, stationsArray, stationIDCounter);
            stationsArray[stationIDCounter] = s;
            stationIDCounter++;
        }

        //2x2 stations
        stationFunction = "Big";
        for (int i = 0; i < (numberOfStations / typesOfStations); i++) {
            Station s = new Station(stationFunction, stationScale * 2, stationScale * 2, stationIDCounter);
            s.setFunction(stationFunction); s.setId(stationIDCounter); s.setHeight(stationScale * 2); s.setWidth(stationScale * 2);
            setXYCoordinates(s, stationsArray, stationIDCounter);
            stationsArray[stationIDCounter] = s;
            stationIDCounter++;
        }
        return stationsArray;
    }
    public static void setXYCoordinates(Station s, Station[] stationsArray, int stationIDCounter) {
        while ((s.getxCoordinate() == 0) && (s.getyCoordinate() == 0)) {
            int randomXInt = ThreadLocalRandom.current().nextInt(1, (gridSize + 1));
            int randomYInt = ThreadLocalRandom.current().nextInt(1, (gridSize+1));
            int tryX = randomXInt * stationScale;
            int tryY = randomYInt * stationScale;

            // Ensure the station doesn't go beyond the grid's edge
            if ((tryX < (stationScale * gridSize)) && (tryY < (stationScale * gridSize))) {
                // If no overlap, set coordinates
                if (!isOverlapping(tryX, tryY, s.getWidth(), s.getHeight(), stationsArray, stationIDCounter)) {
                    s.setxCoordinate(tryX);
                    s.setyCoordinate(tryY);
                }
            }
        }
    }

    public static double calculateFPAffinity (Station[] stations ){
        double totalFPAffinity = 0;
        for (Station s :  stations){
            totalFPAffinity += stationAffinity(s, stations);
        }
        return totalFPAffinity;
    }

    public static double stationAffinity(Station s, Station[] stations){
        double totalIndividualAffinity = 0;
        //go through the array and calculate the affinity of the station s to the other stations in the array.
        int sX = s.getxCoordinate();
        int sY = s.getyCoordinate();
        int otherX;
        int otherY;
        for (Station otherStation : stations){
            otherX = otherStation.getxCoordinate();
            otherY = otherStation.getyCoordinate();
            double affinityResult;
            double xSquaredDifference = Math.pow((sX - otherX), 2);
            double ySquaredDifference = Math.pow((sY - otherY), 2);
            double sum = xSquaredDifference + ySquaredDifference;
            affinityResult = Math.sqrt(sum);

            totalIndividualAffinity += affinityResult;
        }
        return totalIndividualAffinity;
    }
    public static boolean isOverlapping(int x, int y, int width, int height, Station[] stationsArray, int stationIDCounter) {
        // Calculate the bounding box for the new station, with padding (1-unit radius)
        int newXMin = x - stationScale;
        int newXMax = x + width + stationScale;
        int newYMin = y - stationScale;
        int newYMax = y + height + stationScale;

        // Loop through all placed stations to check for overlap
        for (int i = 0; i < stationIDCounter; i++) {
            Station placedStation = stationsArray[i];

            // Calculate the bounding box of the existing station, also with 1-unit padding
            int placedXMin = placedStation.getxCoordinate() - stationScale;
            int placedXMax = placedStation.getxCoordinate() + placedStation.getWidth() + stationScale;
            int placedYMin = placedStation.getyCoordinate() - stationScale;
            int placedYMax = placedStation.getyCoordinate() + placedStation.getHeight() + stationScale;

            // Check if the bounding boxes overlap
            boolean xOverlap = newXMax > placedXMin && newXMin < placedXMax;
            boolean yOverlap = newYMax > placedYMin && newYMin < placedYMax;

            if (xOverlap && yOverlap) {
                return true;  // Overlap found
            }
        }

        return false;  // No overlap
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
        Station[] stations1 = createFloorplan();
        for (Station s : stations1){
            System.out.println("\nStation: " + s.getId() +
                    "\nStation function: " + s.getFunction() +
                    "\nStation width: " + s.getWidth() +
                    "\nStation Height: " + s.getHeight() +
                    "\nStation xCor: " + s.getxCoordinate() +
                    "\nStation yCor: " + s.getyCoordinate() + "\n");
        }
        System.out.println(calculateFPAffinity(stations1));
    }
}