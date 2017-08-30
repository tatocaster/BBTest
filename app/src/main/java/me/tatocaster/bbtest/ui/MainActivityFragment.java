package me.tatocaster.bbtest.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import me.tatocaster.bbtest.R;

public class MainActivityFragment extends Fragment {
    private static final String TAG = "MainActivityFragment";
    private RecyclerView mCitiesRV;
    private EditText mInputSearch;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        findViewsById(rootView);

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

    private void findViewsById(View rootView) {
        mCitiesRV = rootView.findViewById(R.id.citiesList);
        mInputSearch = rootView.findViewById(R.id.inputSearch);
    }

}
