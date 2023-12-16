package com.ggit.simulation;

public class SimulationStatistics {
    private final int dayNumber;
    private final int meanAge;
    private final int meanEnergy;
    private final int meanNoOfChildren;
    private final int animalsCount;

    public SimulationStatistics(int dayNumber, int meanAge, int meanEnergy, int meanNoOfChildren, int animalsCount) {
        this.dayNumber = dayNumber;
        this.meanAge = meanAge;
        this.meanEnergy = meanEnergy;
        this.meanNoOfChildren = meanNoOfChildren;
        this.animalsCount = animalsCount;
    }

    @Override
    public String toString() {
        return "STATYSTYKI" +
                "\ndayNumber: " + dayNumber +
                "\nmeanAge: " + meanAge +
                "\nmeanEnergy: " + meanEnergy +
                "\nmeanNoOfChildren: " + meanNoOfChildren +
                "\nanimalsCount: " + animalsCount;
    }
}
