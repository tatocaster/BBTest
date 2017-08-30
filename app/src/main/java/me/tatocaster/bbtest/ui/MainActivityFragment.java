package me.tatocaster.bbtest.ui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import me.tatocaster.bbtest.R;
import me.tatocaster.bbtest.adapter.CitiesListAdapter;
import me.tatocaster.bbtest.model.City;
import me.tatocaster.bbtest.util.EmptyRecyclerView;

public class MainActivityFragment extends BaseFragment {

    private static final String TAG = "MainActivityFragment";
    private EmptyRecyclerView mCitiesRV;
    private EditText mInputSearch;
    private CitiesListAdapter mListAdapter;
    private List<City> mCitiesData = new ArrayList<>();
    private LinearLayout mEmptyView;
    private SearchTask mSearchTask;
    private List<City> mDisplayData = new ArrayList<>();

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        mCitiesData.addAll(mainActivity.getParsedCities());
        if (mCitiesData.size() > 0)
            mDisplayData.addAll(mCitiesData.subList(0, 300));

        findViewsById(rootView);
        setUpRecyclerView();

        mInputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count == 0) mListAdapter.updateData(mDisplayData);
            }

            @Override
            public void afterTextChanged(final Editable s) {
                if (s != null && s.length() > 0) {
                    // stop any current search thread
                    if (mSearchTask != null && !mSearchTask.isCancelled()) {
                        mSearchTask.cancel(false);
                    }
                    mSearchTask = new SearchTask();
                    mSearchTask.execute(s.toString());
                }
            }
        });

        return rootView;
    }

    private void findViewsById(View rootView) {
        mCitiesRV = rootView.findViewById(R.id.citiesList);
        mInputSearch = rootView.findViewById(R.id.inputSearch);
        mEmptyView = rootView.findViewById(R.id.emptyView);
    }

    private void setUpRecyclerView() {
        mListAdapter = new CitiesListAdapter(mDisplayData, new CitiesListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(City cityItem) {
                mainActivity.addFragment(MapFragment.newInstance(cityItem.coord), "map_fragment");
            }
        });
        mCitiesRV.setAdapter(mListAdapter);
        mCitiesRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        mCitiesRV.setEmptyView(mEmptyView);
    }

    private class SearchTask extends AsyncTask<String, City, List<City>> {
        protected List<City> doInBackground(String... params) {
            List<City> temp = new ArrayList<>();
            if (params.length == 0) {
                return temp;
            }
            List<City> dataForFilter = new ArrayList<>(mCitiesData);
            for (City item : dataForFilter) {
                if (item.name.toLowerCase().startsWith(params[0])) {
                    temp.add(item);
                }
            }
            return temp;
        }

        protected void onPostExecute(List<City> list) {
            mListAdapter.updateData(list);
        }
    }
}
