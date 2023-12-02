package com.ggit.simulation;

import java.util.*;

public class WorldMap extends AbstractWorldMap {
    private Map<Vector2D, Plant> plants;
    private AnimalsMapping animals;
    private static final int noOfPlants = 50;
    private static final int noOfAnimals = 15;
    private static final Random random = new Random();

    public WorldMap(int width, int height) {
        super(width, height);
        plants = new HashMap<>();
        for (int i = 0; i < noOfPlants; i++) addPlant();
        animals = new AnimalsMapping();
    }

    private void addPlant() {
        if (width * height == plants.size()) return;

        Vector2D position = getRandomPosition();
        while (isOccupiedByPlant(position)) {
            position = getRandomPosition();
        }
        plants.put(position, new Plant(position));
    }

    private boolean isOccupiedByPlant(Vector2D position) {
        return plants.containsKey(position);
    }

    private Vector2D getRandomPosition() {
        return new Vector2D(random.nextInt(width), random.nextInt(height));
    }

    @Override
    public void run() {
        animals.moveAnimals();
    }

    @Override
    public void eat() {
        for (Animal animal : animals.list) {
            if (isOccupiedByPlant(animal.getPosition())) {
                System.out.println("Zwierzę " + animal.getId() + " zjadło roślinę");
                plants.remove(animal.getPosition());
                addPlant();
            }
        }
    }

    private class AnimalsMapping {
        List<Animal> list;
        Map<Vector2D, List<Animal>> mapping;

        AnimalsMapping() {
            mapping = new HashMap<>();
            for (int i = 0; i < noOfAnimals; i++) addAnimal();
            list = new LinkedList<>();
        }

        void addAnimal() {
            Animal animal = new Animal(getRandomPosition());
            placeAnimalOnMap(animal);
            list.add(animal);
        }

        void placeAnimalOnMap(Animal animal) {
            List<Animal> animalsOnPosition = mapping.get(animal.getPosition());
            if (animalsOnPosition == null) mapping.put(animal.getPosition(), new LinkedList<>(List.of(animal)));
            else animalsOnPosition.add(animal);
        }

        void moveAnimals() {
            mapping.clear();
            MapDirection[] directions = MapDirection.values();
            for (Animal animal : list) {
                animal.move(directions[random.nextInt(directions.length)]);
                placeAnimalOnMap(animal);
            }
        }
    }
}
