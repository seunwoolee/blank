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
import com.shuneesoft.blanker.helper.SwipeItemTouchHelper;
import com.shuneesoft.blanker.model.Article;
import com.shuneesoft.blanker.utils.Tools;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

public class AdapterListSwipe extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements SwipeItemTouchHelper.SwipeHelperAdapter {

    private List<Article> items = new ArrayList<>();
//    private List<Article> items_swiped = new ArrayList<>();

    private Context ctx;
    private OnItemClickListener mOnItemClickListener;
    private Realm mRealm;

    public interface OnItemClickListener {
        void onItemClick(View view, Article obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public AdapterListSwipe(Context context, List<Article> items, Realm realm) {
        this.items = items;
        ctx = context;
        mRealm = realm;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder implements SwipeItemTouchHelper.TouchViewHolder {
        public TextView title;
        public ImageButton bt_move;
        public Button bt_undo;
        public View lyt_parent;

        public OriginalViewHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.title);
            bt_move = (ImageButton) v.findViewById(R.id.bt_move);
            bt_undo = (Button) v.findViewById(R.id.bt_undo);
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

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_swipe, parent, false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
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

//            view.bt_undo.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    mRealm.beginTransaction();
//                    items.get(position).swiped = false;
////                    items_swiped.remove(items.get(position));
//                    notifyItemChanged(position);
//                    mRealm.commitTransaction();
//
//                }
//            });

        }
    }

//    @Override
//    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
////                for (Article s : items_swiped) {
////                    int index_removed = items.indexOf(s);
////                    if (index_removed != -1) {
////                        items.remove(index_removed);
////                        notifyItemRemoved(index_removed);
////                    }
////                }
////                items_swiped.clear();
////                super.onScrollStateChanged(recyclerView, newState);
//            }
//        });
//        super.onAttachedToRecyclerView(recyclerView);
//    }

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

    }

}