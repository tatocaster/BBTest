package me.tatocaster.bbtest.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import me.tatocaster.bbtest.R;
import me.tatocaster.bbtest.adapter.CitiesListAdapter;
import me.tatocaster.bbtest.model.City;
import me.tatocaster.bbtest.util.EmptyRecyclerView;
import me.tatocaster.bbtest.util.Utils;

public class MainActivityFragment extends BaseFragment {

    private static final String TAG = "MainActivityFragment";
    private EmptyRecyclerView mCitiesRV;
    private EditText mInputSearch;
    private CitiesListAdapter mListAdapter;
    private List<City> mCitiesData = new ArrayList<>();
    private LinearLayout mEmptyView;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        findViewsById(rootView);
        setUpRecyclerView();
        loadDataForRV();

        mInputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(final Editable s) {

            }
        });

        return rootView;
    }


    private void loadDataForRV() {
        try {
            JsonReader reader = new JsonReader(new InputStreamReader(Utils.readFileFromAssets(getActivity(), "cities.json"), "UTF-8"));
            Gson gson = new GsonBuilder().create();

            // Read file in stream mode
            reader.beginArray();
            int i = 0;
            while (reader.hasNext() && i < 200) {
                // Read data into object model
                City city = gson.fromJson(reader, City.class);
                mCitiesData.add(city);
                i++;
            }
            mListAdapter.notifyDataSetChanged();
            reader.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void findViewsById(View rootView) {
        mCitiesRV = rootView.findViewById(R.id.citiesList);
        mInputSearch = rootView.findViewById(R.id.inputSearch);
        mEmptyView = rootView.findViewById(R.id.emptyView);
    }

    private void setUpRecyclerView() {
        mListAdapter = new CitiesListAdapter(mCitiesData, new CitiesListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(City cityItem) {
                mainActivity.addFragment(MapFragment.newInstance(cityItem.getCoord()), "map_fragment");
            }
        });
        mCitiesRV.setAdapter(mListAdapter);
        mCitiesRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        mCitiesRV.setEmptyView(mEmptyView);
    }
}
