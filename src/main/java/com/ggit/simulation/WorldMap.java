package com.ggit.simulation;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class WorldMap extends AbstractWorldMap {
    private List<Animal> animals;
    private List<Plant> plants;
    private static final int noOfPlants = 50;
    private static final int noOfAnimals = 15;
    private static final Random random = new Random();
    public WorldMap(int width, int height) {
        super(width, height);
        animals = new LinkedList<>();
        for (int i = 0; i < noOfAnimals; i++) addAnimal();
        plants = new LinkedList<>();
        for (int i = 0; i < noOfPlants; i++) addPlant();
    }

    private void addAnimal() {
        animals.add(new Animal(getRandomPosition()));
    }

    private void addPlant() {
        if (width * height == plants.size()) return;

        Vector2D position = getRandomPosition();
        while (isOccupiedByPlant(position)) {
            position = getRandomPosition();
        }
        plants.add(new Plant(position));
    }

    private boolean isOccupiedByPlant(Vector2D position) {
        for (Plant plant : plants) {
            if (plant.getPosition().equals(position)) return true;
        }
        return false;
    }

    private Vector2D getRandomPosition() {
        return new Vector2D(random.nextInt(width), random.nextInt(height));
    }

    @Override
    public void run() {
        MapDirection[] directions = MapDirection.values();
        for (Animal animal : animals) {
            animal.move(directions[random.nextInt(directions.length)]);
        }
    }

    @Override
    public void eat() {
        for (Animal animal : animals) {
            Plant plantToRemove = null;
            for (Plant plant : plants) {
                if (animal.getPosition().equals(plant.getPosition())) {
                    System.out.println("Zwierzę " + animal.getId() + " zjadło roślinę");
                    plantToRemove = plant;
                    break;
                }
            }
            if (plantToRemove != null) {
                plants.remove(plantToRemove);
                addPlant();
            }
        }
    }
}
