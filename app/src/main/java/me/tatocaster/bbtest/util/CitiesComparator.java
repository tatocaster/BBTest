package me.tatocaster.bbtest.util;

import java.util.Comparator;

import me.tatocaster.bbtest.model.City;

/**
 * Created by tatocaster on 8/30/17.
 */
public class CitiesComparator implements Comparator<City> {
    @Override
    public int compare(City o1, City o2) {
        return o1.name.compareToIgnoreCase(o2.name);
    }
}

