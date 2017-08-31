package me.tatocaster.bbtest.model;

/**
 * Created by tatocaster on 8/30/17.
 */

public class City {
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
}