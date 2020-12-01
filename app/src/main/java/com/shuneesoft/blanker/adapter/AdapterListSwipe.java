package com.shuneesoft.blanker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.shuneesoft.blanker.R;
import com.shuneesoft.blanker.activity.MainActivity;
import com.shuneesoft.blanker.helper.SwipeItemTouchHelper;
import com.shuneesoft.blanker.model.Article;
import com.shuneesoft.blanker.utils.Tools;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

public class AdapterListSwipe extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements SwipeItemTouchHelper.SwipeHelperAdapter {

    private List<Article> items = new ArrayList<>();

    private Context ctx;
    private OnItemClickListener mOnItemClickListener;
    private Realm mRealm;
    private final AdapterSearch mAdapterSearch;

    public interface OnItemClickListener {
        void onItemClick(View view, Article obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public AdapterListSwipe(Context context, List<Article> items, Realm realm, AdapterSearch adapterSearch) {
        this.items = items;
        ctx = context;
        mRealm = realm;
        mAdapterSearch = adapterSearch;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder implements SwipeItemTouchHelper.TouchViewHolder {
        public TextView title;
        public ImageButton bt_move;
        public View lyt_parent;

        public OriginalViewHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.title);
            bt_move = (ImageButton) v.findViewById(R.id.bt_move);
            lyt_parent = (View) v.findViewById(R.id.lyt_parent);
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(ctx.getResources().getColor(R.color.grey_5));
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }
    }

    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_swipe, parent, false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NotNull final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof OriginalViewHolder) {
            final OriginalViewHolder view = (OriginalViewHolder) holder;

            final Article article = items.get(position);
            view.title.setText(article.getTitle());
            view.lyt_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(view, items.get(position), position);
                    }
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onItemDismiss(int position) {
        deleteArticle(position);
        notifyItemRemoved(position);
    }

    private void deleteArticle(int position) {
        mRealm.beginTransaction();
        Article article = items.get(position);
        article.deleteFromRealm();
        mRealm.commitTransaction();
        List<AdapterSearch.Search> searches = MainActivity.createSearch(mRealm);
        mAdapterSearch.setFiltered_items(searches);
        mAdapterSearch.setItems(searches);

    }

}