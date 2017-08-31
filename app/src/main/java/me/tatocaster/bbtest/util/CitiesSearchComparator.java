package me.tatocaster.bbtest.util;

import java.util.Comparator;

import me.tatocaster.bbtest.model.City;

/**
 * Created by tatocaster on 31.08.17.
 */

public class CitiesSearchComparator implements Comparator<City> {

    @Override
    public int compare(City city, City t1) {
        return city.name.toLowerCase().startsWith(t1.name.toLowerCase()) ? 0 : city.name.compareToIgnoreCase(t1.name);
    }
}
