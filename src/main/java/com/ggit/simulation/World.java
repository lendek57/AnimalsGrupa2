package com.ggit.simulation;

public class World {
    private static final int defaultNoOfDays = 10;
    public static void main(String[] args) {
        int noOfDays = args.length > 0 ? Integer.parseInt(args[0]) : defaultNoOfDays;
        for (int i = 0; i < noOfDays; i++) {
            Simulation.simulateDay();
        }
    }
}
