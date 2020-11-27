package com.shuneesoft.blanker.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.shuneesoft.blanker.R;
import com.shuneesoft.blanker.helper.TextViewHelper;
import com.shuneesoft.blanker.model.Article;
import com.shuneesoft.blanker.model.Blank;
import com.shuneesoft.blanker.utils.Tools;

import org.w3c.dom.Text;

import io.realm.Realm;

public class ArticleDetailActivity extends AppCompatActivity {
    TextViewHelper mTextViewHelper = TextViewHelper.getInstance();
    Realm mRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);

        TextView textViewTitle = findViewById(R.id.title);
        TextView textViewContent = findViewById(R.id.content);
        FlexboxLayout layout = findViewById(R.id.layout);
//        Button button = findViewById(R.id.save_btn);

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
                        .equalTo("id", length - 2) // 공백 및 시작 0
                        .findFirst();

                //                mRealm.where(Blank.class)
                wordTextView.setBackgroundColor(Color.parseColor("#000000"));
                wordTextView.setText(blank.getWord());
            }
            layout.addView(wordTextView);
//            mTextViews.add(wordTextView);
        }

//        textViewContent.setText(article.getContent());
    }
}