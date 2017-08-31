package me.tatocaster.bbtest;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.tatocaster.bbtest.model.City;
import me.tatocaster.bbtest.ui.MainActivityFragment;
import me.tatocaster.bbtest.util.CitiesComparator;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by tatocaster on 8/31/17.
 */
public class SearchUnitTest {
    private static ObjectMapper mapper = new ObjectMapper();
    private static MainActivityFragment fragmentClass;
    private static List<City> mCitiesData;

    @BeforeClass
    public static void createClass() throws Exception {
        mCitiesData = new ArrayList<>();
        fragmentClass = new MainActivityFragment();
    }


    private static File getFileFromPath(Object obj, String fileName) {
        ClassLoader classLoader = obj.getClass().getClassLoader();
        URL resource = classLoader.getResource(fileName);
        return new File(resource.getPath());
    }

    @Before
    public void loadData() throws Exception {
        File file = getFileFromPath(this, "cities.json");
        JsonFactory jsonFactory = new JsonFactory();
        List<City> cities;

        JsonParser jp = jsonFactory.createParser(file);
        cities = mapper.readValue(jp, new TypeReference<List<City>>() {
        });
        Collections.sort(cities, new CitiesComparator());
        mCitiesData.addAll(cities);
    }

    @Test
    public void search_dataNotNull() throws Exception {
        assertThat(mCitiesData, notNullValue());
    }


    @Test
    public void search_isNotNull() throws Exception {
        List<City> result = fragmentClass.search(mCitiesData, "Alabama");
        Assert.assertNotNull(result);
    }

    @Test
    public void search_isEqualFirstObjectCapitalCase() throws Exception {
        String searchTerm = "Tbilisi";
        List<City> result = fragmentClass.search(mCitiesData, searchTerm);
        Assert.assertEquals(result.get(0).name, searchTerm);
    }

    @Test
    public void search_isEqualFirstObjectLowercase() throws Exception {
        String searchTerm = "tbilisi";
        List<City> result = fragmentClass.search(mCitiesData, searchTerm);
        Assert.assertNotEquals(result.get(0).name, searchTerm);
    }

    @Test
    public void search_isNoResults() throws Exception {
        String searchTerm = "city_0123124";
        List<City> result = fragmentClass.search(mCitiesData, searchTerm);
        Assert.assertEquals(result, new ArrayList<City>());
    }

    @Test
    public void search_isInvalidInput() throws Exception {
        String searchTerm = "$asda=-1,das";
        List<City> result = fragmentClass.search(mCitiesData, searchTerm);
        Assert.assertEquals(result, new ArrayList<City>());
    }

}
