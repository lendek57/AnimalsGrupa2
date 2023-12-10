package com.ggit.simulation;

public class Animal implements Comparable<Animal> {
    private Vector2D position;
    private int id;
    private int energy;
    private int age = 1;
    private static int counter = 0;

    public Animal(Vector2D position, int initialEnergy) {
        this.position = position;
        id = counter++;
        energy = initialEnergy;
    }

    public int getEnergy() {
        return energy;
    }

    public Animal withChangedEnergy(int energy) {
        this.energy = energy;
        return this;
    }

    public int getAge() {
        return age;
    }

    public Animal dayOlder() {
        age++;
        return this;
    }

    public int getId() {
        return id;
    }

    public Vector2D getPosition() {
        return position;
    }

    public void move(MapDirection direction) {
        position = pbc(position.add(direction.getUnitVector()));
        System.out.println("Zwierzę przesunęło się na pozycję: " + position);
    }

    private Vector2D pbc(Vector2D position) {
        int width = Simulation.getWidth();
        int height = Simulation.getHeight();
        if (position.getY() >= height) return position.subtrack(new Vector2D(0, height));
        if (position.getY() < 0) return position.add(new Vector2D(0, height));
        if (position.getX() >= width) return position.subtrack(new Vector2D(width, 0));
        if (position.getX() < 0) return position.add(new Vector2D(width, 0));
        return position;
    }

    @Override
    public int compareTo(Animal o) {
        return energy == o.getEnergy() ? id - o.id : energy - o.energy;
    }
}
