package org.example;

public class Floorplan {
    private double floorPlanAffinity;
    private Station[] stations;

    public Floorplan(Station[] stations, double floorPlanAffinity) {
        this.floorPlanAffinity = floorPlanAffinity;
        this.stations = stations;
    }

    public Station[] getStations() {
        return stations;
    }
    public double getFloorPlanAffinity() {
        return floorPlanAffinity;
    }
    public void setStations(Station[] s){
        this.stations = s;
    }
    public void setFloorPlanAffinity(double floorPlanAffinity) {
        this.floorPlanAffinity = floorPlanAffinity;
    }
}
