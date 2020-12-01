package com.shuneesoft.blanker.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.shuneesoft.blanker.R;
import com.shuneesoft.blanker.activity.ArticleDetailActivity;
import com.shuneesoft.blanker.adapter.AdapterListSwipe;
import com.shuneesoft.blanker.adapter.AdapterSearch;
import com.shuneesoft.blanker.helper.SwipeItemTouchHelper;
import com.shuneesoft.blanker.model.Article;
import com.shuneesoft.blanker.utils.Tools;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.realm.Realm;

public class ListFragment extends Fragment {
    private final String TAG = "MainFragment";
    private Context mContext;
    private List<Article> mArticles;
    private Realm mRealm;
    private AdapterListSwipe mAdapter;
    private final AdapterSearch mAdapterSearch;


    public ListFragment(AdapterSearch adapterSearch) {
        mAdapterSearch = adapterSearch;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRealm = Tools.initRealm(mContext);
        mArticles = mRealm.where(Article.class).findAll();
        mAdapter = new AdapterListSwipe(mContext, mArticles, mRealm, mAdapterSearch);
        mAdapter.setOnItemClickListener(new AdapterListSwipe.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Article obj, int position) {
                Intent intent = new Intent(mContext, ArticleDetailActivity.class);
                intent.putExtra("articleId", obj.getId());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup root_view = (ViewGroup) inflater.inflate(R.layout.fragment_list, container, false);
        RecyclerView recyclerView = (RecyclerView) root_view.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setHasFixedSize(true);
        ItemTouchHelper.Callback callback = new SwipeItemTouchHelper(mAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        return root_view;
    }

}