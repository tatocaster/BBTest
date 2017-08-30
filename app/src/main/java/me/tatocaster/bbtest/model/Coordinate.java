package me.tatocaster.bbtest.model;

import java.io.Serializable;

/**
 * Created by tatocaster on 8/30/17.
 */

public class Coordinate implements Serializable {
    public long lon;
    public long lat;

    @Override
    public String toString() {
        return "Coordinate{" +
                "lon=" + lon +
                ", lat=" + lat +
                '}';
    }
}