package com.shuneesoft.blanker.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shuneesoft.blanker.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterSearch extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {
    private List<Search> items;
    private List<Search> filtered_items;

    public void setItems(List<Search> items) {
        this.items = items;
    }

    public void setFiltered_items(List<Search> filtered_items) {
        this.filtered_items = filtered_items;
    }

    public static class Search {
        public long id;
        public String title;

        public Search(long id, String title) {
            this.id = id;
            this.title = title;
        }
    }

    public AdapterSearch(List<Search> items) {
        this.items = items;
        this.filtered_items = items;
    }

    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(long id);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String query = charSequence.toString().toLowerCase();
                if (query.isEmpty()) {
                    filtered_items = items;
                } else {
                    List<Search> filteredList = new ArrayList<>();
                    for (Search search : items) {
                        if (search.title.toLowerCase().contains(query)) {
                            filteredList.add(search);
                        }
                    }
                    filtered_items = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filtered_items;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filtered_items = (List<Search>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class OriginalViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView id;
        public View lyt_parent;

        public OriginalViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.id);
            title = itemView.findViewById(R.id.title);
            lyt_parent = itemView.findViewById(R.id.lyt_parent);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_title_search, parent, false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        OriginalViewHolder view = (OriginalViewHolder) holder;
        view.title.setText(filtered_items.get(position).title);
        view.id.setText(Integer.toString((int) filtered_items.get(position).id));
        view.lyt_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(filtered_items.get(position).id); //TODO title id 넣기
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return filtered_items.size();
    }
}
