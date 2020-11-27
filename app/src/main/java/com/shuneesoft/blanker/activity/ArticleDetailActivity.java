package com.shuneesoft.blanker.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.shuneesoft.blanker.R;
import com.shuneesoft.blanker.helper.TextViewHelper;
import com.shuneesoft.blanker.model.Article;
import com.shuneesoft.blanker.model.Blank;
import com.shuneesoft.blanker.utils.Tools;

import io.realm.Realm;

public class ArticleDetailActivity extends AppCompatActivity {
    private static final String TAG = ArticleDetailActivity.class.getSimpleName();
    TextViewHelper mTextViewHelper = TextViewHelper.getInstance();
    Realm mRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);

        initToolbar();
        TextView textViewTitle = findViewById(R.id.title);
        FlexboxLayout layout = findViewById(R.id.layout);

        long articleId = getIntent().getLongExtra("articleId", 0);
        mRealm = Tools.initRealm(this);
        Article article = mRealm.where(Article.class).equalTo("id", articleId).findFirst();

        textViewTitle.setText(article.getTitle());
        String[] words = article.getContent().split(" ");
        for (String word : words) {
            TextView wordTextView = mTextViewHelper.createWordTextView(this, word);
            String s = (String) wordTextView.getText();
            if (s.startsWith("뷁")) {
                int length = s.length();
                Blank blank = mRealm
                        .where(Blank.class)
                        .equalTo("article.id", article.getId())
                        .equalTo("id", length - 2) // 마지막 공백 및 시작 index 0
                        .findFirst();
                wordTextView.setBackgroundColor(Color.parseColor("#000000"));
                wordTextView.setText(blank.getWord());
            }
            layout.addView(wordTextView);
        }
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.action_save:
                Log.d(TAG, "save function");
//                onBackPressed();
                break;
//            case R.id.action_refresh:
//                setAdapter();
//                break;
//            case R.id.action_mode:
//                showSingleChoiceDialog();
//                break;
        }
        return super.onOptionsItemSelected(item);
    }

}