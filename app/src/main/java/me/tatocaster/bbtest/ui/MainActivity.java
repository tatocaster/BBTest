package me.tatocaster.bbtest.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.tatocaster.bbtest.R;
import me.tatocaster.bbtest.model.City;
import me.tatocaster.bbtest.util.CitiesComparator;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ObjectMapper mapper = new ObjectMapper();
    private List<City> mParsedCitiesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        addFragment(new MainActivityFragment(), "main_fragment");

        parseCities();
    }

    private void parseCities() {
        JsonFactory jsonFactory = new JsonFactory();
        List<City> cities = null;

        try {
            JsonParser jp = jsonFactory.createParser(getAssets().open("cities.json"));
            cities = mapper.readValue(jp, new TypeReference<List<City>>() {
            });
            Collections.sort(cities, new CitiesComparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.mParsedCitiesList = cities;
    }

    public List<City> getParsedCities() {
        return this.mParsedCitiesList;
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
    }

    protected void addFragment(@NonNull Fragment fragment, @NonNull String fragmentTag) {
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                .add(R.id.fragmentWrapper, fragment)
                .addToBackStack(fragmentTag)
                .commit();
    }
}
