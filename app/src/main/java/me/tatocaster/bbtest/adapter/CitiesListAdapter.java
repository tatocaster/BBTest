package me.tatocaster.bbtest.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import me.tatocaster.bbtest.R;
import me.tatocaster.bbtest.model.City;

/**
 * Created by tatocaster on 8/30/17.
 */

public class CitiesListAdapter extends RecyclerView.Adapter<CitiesListAdapter.ViewHolder> {
    private List<City> mListData;
    private OnItemClickListener mListener;

    public CitiesListAdapter(List<City> data, OnItemClickListener listener) {
        this.mListData = data;
        this.mListener = listener;
    }

    public void updateData(List<City> data) {
        this.mListData = data;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        City item = mListData.get(position);
        holder.setCityName(String.format(Locale.getDefault(), "%s, %s", item.name, item.country));
        holder.setClickListener(item, mListener);
    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView cityName;

        ViewHolder(View itemView) {
            super(itemView);
            cityName = itemView.findViewById(R.id.cityName);
        }

        void setCityName(String name) {
            this.cityName.setText(name);
        }

        void setClickListener(final City item, final OnItemClickListener clickListener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onItemClick(item);
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(City cityItem);
    }
}
