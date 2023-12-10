package com.ggit.simulation;

import java.util.*;

public class WorldMap extends AbstractWorldMap {
    private Map<Vector2D, Plant> plants;
    private AnimalsMapping animals;
    private int dayNumber = 1;
    private static final int noOfPlants = 50;
    private static final int noOfAnimals = 15;
    private static final int animalEnergy = 20;
    private static final int plantEnergy = 10;
    private static final int dayEnergy = 5;
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
    public void startDay() {
        System.out.println("Nowy dzień numer: " + dayNumber);
    }

    @Override
    public void run() {
        MapDirection[] directions = MapDirection.values();
        animals.list.forEach(animal -> {
            animal.move(directions[random.nextInt(directions.length)]);
            animals.placeAnimalOnMap(animal);
        });
    }

    @Override
    public void eat() {
        animals.mapping.forEach((pos, animals) -> {
            if (isOccupiedByPlant(pos)) {
                animals.stream().max(Animal::compareTo).ifPresent(animal -> {
                    System.out.println("Zwierzę " + animal.getId() + " zjadło roślinę");
                    animal.withChangedEnergy(animal.getEnergy() + plantEnergy);
                    plants.remove(animal.getPosition());
                    addPlant();
                });
            }
        });
    }

    @Override
    public void endDay() {
        dayNumber++;
        int animalsCount = animals.list.size();
        animals.updateAnimals(
                animals.list.stream()
                        .map(animal -> animal.withChangedEnergy(animal.getEnergy() - dayEnergy))
                        .filter(animal -> animal.getEnergy() >= 0)
                        .map(Animal::dayOlder)
                        .toList()
        );
        System.out.printf("Zwierząt było %d, pozostało %d\n", animalsCount, animals.list.size());
    }

    private class AnimalsMapping {
        List<Animal> list;
        Map<Vector2D, List<Animal>> mapping;

        AnimalsMapping() {
            mapping = new HashMap<>();
            list = new LinkedList<>();
            for (int i = 0; i < noOfAnimals; i++) addAnimal();
        }

        void addAnimal() {
            Animal animal = new Animal(getRandomPosition(), animalEnergy);
            placeAnimalOnMap(animal);
            list.add(animal);
        }

        void placeAnimalOnMap(Animal animal) {
            mapping.computeIfAbsent(animal.getPosition(), pos -> new LinkedList<>()).add(animal);
        }

        void updateAnimals(List<Animal> newAnimals) {
            list = newAnimals;
            mapping.clear();
        }
    }
}
