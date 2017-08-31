package me.tatocaster.bbtest.ui;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

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
    private ProgressBar mLoadingData;

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
            }

            @Override
            public void afterTextChanged(final Editable s) {
                mLoadingData.setVisibility(View.VISIBLE);
                // stop any current search thread
                if (mSearchTask != null && !mSearchTask.isCancelled()) {
                    mSearchTask.cancel(false);
                }
                if (s.length() == 0) {
                    mListAdapter.updateData(mDisplayData);
                    mLoadingData.setVisibility(View.INVISIBLE);
                } else {
                    mSearchTask = new SearchTask();
                    mSearchTask.execute(s.toString().toLowerCase());
                }
            }
        });

        return rootView;
    }

    private void findViewsById(View rootView) {
        mCitiesRV = rootView.findViewById(R.id.citiesList);
        mInputSearch = rootView.findViewById(R.id.inputSearch);
        mEmptyView = rootView.findViewById(R.id.emptyView);
        mLoadingData = rootView.findViewById(R.id.loading);
    }

    private void setUpRecyclerView() {
        mListAdapter = new CitiesListAdapter(mDisplayData, new CitiesListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(City cityItem) {
                InputMethodManager imm = (InputMethodManager) mainActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
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
            String searchTerm = params[0];

            temp.addAll(search(mCitiesData, searchTerm));

            return temp;
        }

        protected void onPostExecute(List<City> list) {
            mListAdapter.updateData(list);
            mLoadingData.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * find the first and last occurrences in the list of the search term
     *
     * @param data
     * @param searchTerm
     * @return List
     */
    public List<City> search(List<City> data, String searchTerm) {
        int n = data.size();
        int first = -1, last = -1;
        for (int i = 0; i < n; i++) {
            if (!data.get(i).name.toLowerCase().startsWith(searchTerm))
                continue;
            if (first == -1)
                first = i;
            last = i;
        }
        if (first != -1)
            return data.subList(first, last + 1);
        return new ArrayList<>();
    }
}
