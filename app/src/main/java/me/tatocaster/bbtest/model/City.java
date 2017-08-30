package me.tatocaster.bbtest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by tatocaster on 8/30/17.
 */

public class City {

    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("_id")
    @Expose
    private int id;
    @SerializedName("coord")
    @Expose
    private Coord coord;

    public City() {
    }

    /**
     * @param coord
     * @param id
     * @param name
     * @param country
     */
    public City(String country, String name, int id, Coord coord) {
        super();
        this.country = country;
        this.name = name;
        this.id = id;
        this.coord = coord;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    @Override
    public String toString() {
        return "City{" +
                "country='" + country + '\'' +
                ", name='" + name + '\'' +
                ", id=" + id +
                ", coord=" + coord +
                '}';
    }
}
