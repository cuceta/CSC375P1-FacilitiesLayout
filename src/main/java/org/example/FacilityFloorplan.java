package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class FacilityFloorplan extends Thread{

    final int numberOfStations = 48;
    final int typesOfStations = 4;

    // ---=== STEP 1: Create  stations ===---


    // ---=== STEP 2: Create 4 threads ===---
    public static Thread threadOne = new Thread();
    public static Thread threadTwo = new Thread();
    public static Thread threadThree = new Thread();
    public static Thread threadFour = new Thread();


    // ---=== STEP 3: Give each thread a defensive copy of the stations ===---

    // ====== INSIDE EACH OF EACH OF THE THREADS ======

        // ---=== STEP 4: Create floor plans ===---

            // ---=== OPTION 1: Produce a floorplan with random placements of stations ===---

            // ---=== OPTION 2.1: Copy half of an existing floor plan ===---

            // ---=== OPTION 2.2: Create other half of the floor plan ===---


        // ---=== STEP 5: Calculate Affinity total score and add it to the global poll of solutions ===---

    // ====== OUTSIDE EACH OF EACH OF THE THREADS ======

        // ---=== STEP 6: Sort global poll of solutions ===---





    public static void main(String[] args) {
        //Start threads
        threadOne.start();
        threadTwo.start();
        threadThree.start();
        threadFour.start();
    }
}