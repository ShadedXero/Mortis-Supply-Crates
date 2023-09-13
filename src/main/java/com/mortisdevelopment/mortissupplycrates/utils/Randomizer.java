package com.mortisdevelopment.mortissupplycrates.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Randomizer<T> {

    private class Entry {
        double accumulatedWeight;
        T object;
    }

    private final List<Entry> entries = new ArrayList<>();
    private double accumulatedWeight;
    private final Random random = new Random();

    public List<T> getEntries() {
        List<T> objects = new ArrayList<>();
        for (Entry entry : entries) {
            objects.add(entry.object);
        }
        return objects;
    }

    public void addEntry(T object, double weight) {
        accumulatedWeight += weight;
        Entry e = new Entry();
        e.object = object;
        e.accumulatedWeight = accumulatedWeight;
        entries.add(e);
    }

    public T getRandom() {
        double r = random.nextDouble() * accumulatedWeight;

        for (Entry entry: entries) {
            if (entry.accumulatedWeight >= r) {
                return entry.object;
            }
        }
        return null; //should only happen when there are no entries
    }
}
