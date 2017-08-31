package me.tatocaster.bbtest;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.tatocaster.bbtest.model.City;
import me.tatocaster.bbtest.ui.MainActivityFragment;
import me.tatocaster.bbtest.util.CitiesComparator;

/**
 * Created by tatocaster on 8/31/17.
 */
public class SearchUnitTest {
    private static List<City> searchList = new ArrayList<>();

    private static MainActivityFragment fragmentClass;

    @BeforeClass
    public static void populateData() {
        for (int i = 0; i < 209000; i++) {
            City city = new City();
            city.name = "city_" + i;
            searchList.add(city);
        }
        Collections.sort(searchList, new CitiesComparator());
        fragmentClass = new MainActivityFragment();
    }


    @Test
    public void search_isNotNull() {
        List<City> result = fragmentClass.search(searchList, "city_55");
        Assert.assertNotNull(result);
    }

    @Test
    public void search_isEqualFirstObject() {
        String searchTerm = "city_55";
        List<City> result = fragmentClass.search(searchList, searchTerm);
        Assert.assertEquals(result.get(0).name, searchTerm);
    }

    @Test
    public void search_isNoResults() {
        String searchTerm = "city_0123124";
        List<City> result = fragmentClass.search(searchList, searchTerm);
        Assert.assertEquals(result, new ArrayList<City>());
    }

    @Test
    public void search_isInvalidInput() {
        String searchTerm = "$asda=-1,das";
        List<City> result = fragmentClass.search(searchList, searchTerm);
        Assert.assertEquals(result, new ArrayList<City>());
    }
}
