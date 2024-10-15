package org.example;

import javax.swing.*;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.ReentrantLock;


public class FacilityFloorPlanGenerator extends Thread {

    static final int numberOfStations = 48;//Always a multiple of 4
    static final int typesOfStations = 4;
    static final int stationScale = 25;  //Spacing on the grid
    static final int gridSize = 32;
    static final ReentrantLock lock = new ReentrantLock();

    //Always lock these before using
    static HashMap<Double, FloorPlan> allFloorPlans = new HashMap<>();
    static ArrayList<FloorPlan> floorPlanArraylist = new ArrayList<>();  //used for crossovers
    static JFrame frame = new JFrame("Facilities Layout");


    // ---=== STEP 1: Create  stations ===---
    public static Station[] createStations() {
        Station[] stationsArray = new Station[numberOfStations];
        int[][] coordinates; //2D array to save the coordinate of each station
        int stationIDCounter = 0;

        //1x1 stations
        String stationFunction = "Small";
        for (int i = 0; i < (numberOfStations / typesOfStations); i++) {
            Station s = new Station(stationFunction, stationScale, stationScale, stationIDCounter);
            s.setFunction(stationFunction);
            s.setId(stationIDCounter);
            s.setHeight(stationScale);
            s.setWidth(stationScale);
            setXYCoordinates(s, stationsArray, stationIDCounter);
            stationsArray[stationIDCounter] = s;
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
            setXYCoordinates(s, stationsArray, stationIDCounter);
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
            setXYCoordinates(s, stationsArray, stationIDCounter);
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
            setXYCoordinates(s, stationsArray, stationIDCounter);
            stationsArray[stationIDCounter] = s;
            stationIDCounter++;
        }
        return stationsArray;
    }

    public static void setXYCoordinates(Station s, Station[] stationsArray, int stationIDCounter) {
        while ((s.getxCoordinate() == 0) && (s.getyCoordinate() == 0)) {
            int randomXInt = ThreadLocalRandom.current().nextInt(1, (gridSize + 1));
            int randomYInt = ThreadLocalRandom.current().nextInt(1, (gridSize + 1));
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

    /**
     * Helper methods for createStations
     * Makes sure stations do not overlap when being placed into the floor plan before setting
     * their x and y coordinates
     */
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

    public static double calculateFPAffinity(Station[] stations) {
        double totalFPAffinity = 0;
        for (Station s : stations) {
            totalFPAffinity += stationAffinity(s, stations);
        }
        return totalFPAffinity;
    }

    /**
     * Helper methods for the calculateFPAffinity() method
     * Calculates the affinity total from one specific stations to all the other stations in the floor plan
     */
    public static double stationAffinity(Station s, Station[] stations) {
        double totalIndividualAffinity = 0;
        //go through the array and calculate the affinity of the station s to the other stations in the array.
        int sX = s.getxCoordinate();
        int sY = s.getyCoordinate();
        int otherX;
        int otherY;
        for (Station otherStation : stations) {
            otherX = otherStation.getxCoordinate();
            otherY = otherStation.getyCoordinate();
            double affinityBetweenStations = 0;
            double affinityResult;
            double xSquaredDifference = Math.pow((sX - otherX), 2);
            double ySquaredDifference = Math.pow((sY - otherY), 2);
            double sum = xSquaredDifference + ySquaredDifference;
            double distance = Math.sqrt(sum);
            //determine the affinity between the two stations
            if (s.getFunction().equals("Small")) {
                affinityBetweenStations = switch (otherStation.getFunction()) {
                    case "Small" -> 10.0;
                    case "Wide" -> -10.0;
                    case "Long" -> -10.0;
                    case "Big" -> -10.0;
                    default -> affinityBetweenStations;
                };
            } else if (s.getFunction().equals("Wide")) {
                affinityBetweenStations = switch (otherStation.getFunction()) {
                    case "Small" -> -10.0;
                    case "Wide" -> 10.0;
                    case "Long" -> 5.0;
                    case "Big" -> 10.0;
                    default -> affinityBetweenStations;
                };
            } else if (s.getFunction().equals("Long")) {
                affinityBetweenStations = switch (otherStation.getFunction()) {
                    case "Small" -> -10.0;
                    case "Wide" -> 5.0;
                    case "Long" -> 10.0;
                    case "Big" -> 0.0;
                    default -> affinityBetweenStations;
                };
            } else if (s.getFunction().equals("Big")) {
                affinityBetweenStations = switch (otherStation.getFunction()) {
                    case "Small" -> -10.0;
                    case "Wide" -> 10.0;
                    case "Long" -> 0.0;
                    case "Big" -> 5.0;
                    default -> affinityBetweenStations;
                };
            }
            affinityResult = affinityBetweenStations * distance;
            totalIndividualAffinity += affinityResult;

        }
        return totalIndividualAffinity;
    }

    /**
     * Create a completely new floor plan
     */
    public static FloorPlan mutation() {
        //Create stations and calculate their affinity
        Station[] sts = createStations();
        double affinity = calculateFPAffinity(sts);
        //Make new floor plan
        FloorPlan floorplan = new FloorPlan(sts, affinity);
        floorplan.setStations(sts);
        floorplan.setFloorPlanAffinity(affinity);
        System.out.println("Floor plan affinity: " + affinity);
        //Add the floor plan to the arraylist and hashmap --> USE LOCKS
        lock.lock();
        try {
            allFloorPlans.put(floorplan.getFloorPlanAffinity(), floorplan);
            floorPlanArraylist.add(floorplan); //use for crossovers and sorting
            return floorplan;
        } finally {
            lock.unlock();
        }

    }

    /**
     * Pick two random floor plans from the array list and take half of each then merge them (move things if need be)
     */
    public static FloorPlan crossover() {
        ArrayList<FloorPlan> fakeArrayList;
        lock.lock();
        try {
            fakeArrayList = new ArrayList<>(floorPlanArraylist);
        } finally {
            lock.unlock();
        }
        //  1: Get two random integers (idx1, idx2) bound by the size of the array of floor plans
        int idx1 = ThreadLocalRandom.current().nextInt(0, (fakeArrayList.size() - 1));
        int idx2 = ThreadLocalRandom.current().nextInt(0, (fakeArrayList.size() - 1));

        //  2: Get the arrays of stations (sts1, sts2) of the two floor plans at idx1 and idx2 --> USE THE LOCK
        Station[] sts1 = fakeArrayList.get(idx1).getStations();
        Station[] sts2 = fakeArrayList.get(idx2).getStations();

        //  3: merge sts1 and sts2 --> make a new array of stations
        Station[] mergedStations = new Station[numberOfStations];

        //  4: get half of the stations in sts1 (indexes 0-23) which will be all the small and long stations
        for (int i = 0; i <= ((sts1.length / 2 - 1)); i++) {
            mergedStations[i] = sts1[i];
        }

        //  5: get half of the stations in sts2 (indexes 24 - 47) which will be all the wide and big stations
        for (int p = (sts2.length / 2); p <= ((sts2.length) - 1); p++) {
            mergedStations[p] = sts2[p];
        }

        //  6: Make sure the stations don't overlap --> if they overlap, move them so that they don't
        adjustOverlap(mergedStations);

        //  7: calculate new affinity
        double affinity = calculateFPAffinity(mergedStations);

        //  8: make new floor plan
        FloorPlan floorplan = new FloorPlan(mergedStations, affinity);

        //  9: set the stations for the new floor plan
        floorplan.setStations(mergedStations);

        // 10: set the affinity for the new floor plan
        floorplan.setFloorPlanAffinity(affinity);
        System.out.println("Floor plan affinity: " + affinity);

        // 11: Add the new floor plan to the Hashmap and the array of floor plans --> USE LOCKS
        lock.lock();
        try {
            allFloorPlans.put(floorplan.getFloorPlanAffinity(), floorplan);
            floorPlanArraylist.add(floorplan); // use for crossovers and sorting
            // 12: return the new floor plan
            return floorplan;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Adjusts the positions of stations in the merged array to ensure there are no overlaps.
     */
    public static void adjustOverlap(Station[] mergedStations) {
        for (int i = 0; i < mergedStations.length; i++) {
            Station stationA = mergedStations[i];

            for (int j = i + 1; j < mergedStations.length; j++) {
                Station stationB = mergedStations[j];

                // Check if stationA and stationB overlap
                if (crossoverIsOverlapping(stationA, stationB)) {
                    // Move stationB to a new non-overlapping position
                    moveStation(stationB, mergedStations);
                }
            }
        }
    }

    /**
     * Checks if two stations overlap based on their x, y coordinates and size.
     */
    public static boolean crossoverIsOverlapping(Station a, Station b) {
        // Bounding box for station a
        int aXMin = a.getxCoordinate();
        int aXMax = a.getxCoordinate() + a.getWidth();
        int aYMin = a.getyCoordinate();
        int aYMax = a.getyCoordinate() + a.getHeight();

        // Bounding box for station b
        int bXMin = b.getxCoordinate();
        int bXMax = b.getxCoordinate() + b.getWidth();
        int bYMin = b.getyCoordinate();
        int bYMax = b.getyCoordinate() + b.getHeight();

        // Check if their bounding boxes overlap
        return (aXMin < bXMax && aXMax > bXMin) && (aYMin < bYMax && aYMax > bYMin);
    }

    /**
     * Moves the station to a nearby position that doesn't overlap with any other stations.
     */
    public static void moveStation(Station station, Station[] mergedStations) {
        boolean placed = false;

        while (!placed) {
            // Generate a new random position for the station
            int newX = ThreadLocalRandom.current().nextInt(1, 33) * stationScale;
            int newY = ThreadLocalRandom.current().nextInt(1, 33) * stationScale;

            // Make sure the new position keeps the station within the grid boundaries
            newX = Math.min(newX, (32 * stationScale) - station.getWidth());  // Ensure the station doesn't go beyond the right edge
            newY = Math.min(newY, (32 * stationScale) - station.getHeight()); // Ensure the station doesn't go beyond the bottom edge

            station.setxCoordinate(newX);
            station.setyCoordinate(newY);

            // Check again if this new position overlaps with any other station
            placed = true; // Assume placement is successful
            for (Station otherStation : mergedStations) {
                if (otherStation != station && crossoverIsOverlapping(station, otherStation)) {
                    placed = false; // Overlap detected, try again
                    break;
                }
            }
        }
    }

    /**
     * Choose either mutation or cross over approach in a controlled "random" way.
     */
    public static FloorPlan createFloorplan() {
        FloorPlan floorplan;
        ArrayList<FloorPlan> fakeArrayList;
        lock.lock(); // Locking to safely access shared resources
        try {
            fakeArrayList = new ArrayList<>(floorPlanArraylist);
        } finally {
            lock.unlock();
        }
        /*
         * CHOOSE EITHER MUTATION OR CROSSOVER
         * If MORE than 5 floor plans have been created
         *  - Generate a thread local random number
         *      - If the random is EVEN do cross over
         *      - If the random is ODD do mutation
         * If LESS than 5 floor plans have been created
         *  - do mutation
         */
        if (fakeArrayList.size() > 5) {
            int random = ThreadLocalRandom.current().nextInt(1, (fakeArrayList.size() - 1));
            if (random % 2 == 0) {
                System.out.println("\nCrossover");
                floorplan = crossover();
            } else {
                System.out.println("\nMutation");
                floorplan = mutation();
            }
        } else {
            System.out.println("\nMutation");
            floorplan = mutation();
        }
        return floorplan;
    }

    /**
     * FOR GUI
     * Go through arrayList and find the one with the highest affinity value
     */
    public static FloorPlan pickBestFloorPlan() {
        double bestAffinity = 0.0;
        ArrayList<FloorPlan> fakeArrayList; //Defensive copy
        lock.lock();
        try {
            fakeArrayList = new ArrayList<>(floorPlanArraylist);
        } finally {
            lock.unlock();
        }
        for (FloorPlan floorplan : fakeArrayList) {
            if (bestAffinity <= floorplan.getFloorPlanAffinity()) {
                bestAffinity = floorplan.getFloorPlanAffinity();
            }
        }
        FloorPlan bestFloorplan;
        lock.lock();
        try {
            bestFloorplan = allFloorPlans.get(bestAffinity);
        } finally {
            lock.unlock();
        }
        return bestFloorplan;
    }

    /**
     * Tells each thread what to do
     */
    @Override
    public void run(){
        //Set up gui display
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(850, 900);
        // Each thread makes 30 floor plans
        for (int i = 0; i < 30; i++) {
            FloorPlan newFloorplan = createFloorplan();
            System.out.println("Thread: " + Thread.currentThread().getName() + " created a new floorplan with affinity: " + newFloorplan.getFloorPlanAffinity());
            // Call on GUI to display whatever the best pick is
            frame.add(new GUI());
            frame.setVisible(true);
            // Simulate some delay so that threads don't step on each other
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(300, 500));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {

        //  1: Create threads
        FacilityFloorPlanGenerator threadOne = new FacilityFloorPlanGenerator();
        FacilityFloorPlanGenerator threadTwo = new FacilityFloorPlanGenerator();
        FacilityFloorPlanGenerator threadThree = new FacilityFloorPlanGenerator();
        FacilityFloorPlanGenerator threadFour = new FacilityFloorPlanGenerator();
        FacilityFloorPlanGenerator threadFive = new FacilityFloorPlanGenerator();

        //  2: Start the threads
        threadOne.start();
        threadTwo.start();
        threadThree.start();
        threadFour.start();
        threadFive.start();

        //  3: Wait for all threads to complete
        try {
            threadOne.join();
            threadTwo.join();
            threadThree.join();
            threadFour.join();
            threadFive.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 4: Display final results
        System.out.println("\n\nNumber of Floor Plans created: " + floorPlanArraylist.size());
        System.out.println("All threads completed. Best floor plan affinity: " + pickBestFloorPlan().getFloorPlanAffinity());

        // 5: Terminate program
        try { //--> Makes it wait 5 seconds before disposing the frame
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        frame.dispose();
        System.exit(0);
    }
}