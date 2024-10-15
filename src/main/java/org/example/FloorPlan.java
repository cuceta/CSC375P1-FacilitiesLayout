package org.example;

public class FloorPlan {
    private double floorPlanAffinity;
    private Station[] stations;

    public FloorPlan(Station[] stations, double floorPlanAffinity) {
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
