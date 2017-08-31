package me.tatocaster.bbtest.model;

import android.support.annotation.NonNull;

/**
 * Created by tatocaster on 8/30/17.
 */

public class City implements Comparable<String> {
    public String country;
    public String name;
    public int _id;
    public Coordinate coord;

    @Override
    public String toString() {
        return "City{" +
                "country='" + country + '\'' +
                ", name='" + name + '\'' +
                ", _id='" + _id + '\'' +
                ", coord=" + coord +
                '}';
    }

    @Override
    public int compareTo(@NonNull String s) {
        return name.toLowerCase().startsWith(s.toLowerCase()) ? 0 : -1;
    }
}