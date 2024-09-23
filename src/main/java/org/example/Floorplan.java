package org.example;

public class Floorplan {
    private int totalAffinity;
    private Station[] stations;

    public Floorplan(Station[] stations) {
        this.stations = stations;
    }

    public int getTotalAffinity() {
        return totalAffinity;
    }

    public void setTotalAffinity(int totalAffinity) {
        this.totalAffinity = totalAffinity;
    }
}
