package com.shuneesoft.blanker.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.flexbox.FlexboxLayout;
import com.shuneesoft.blanker.R;
import com.shuneesoft.blanker.model.Article;
import com.shuneesoft.blanker.model.Blank;
import com.shuneesoft.blanker.utils.Tools;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainFragment extends Fragment {
    private final String TAG = "MainFragment";
    private Realm mRealm;
    private Context mContext;
    private String mText = "";
    private final List<TextView> mTextViews = new ArrayList<TextView>();

    public MainFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        mText = getArguments().getString("text", "");
        mRealm = Tools.initRealm(mContext);
    }

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup root_view = (ViewGroup) inflater.inflate(R.layout.fragment_main, container, false);

        FlexboxLayout layout = root_view.findViewById(R.id.layout);
        Button button = root_view.findViewById(R.id.save_btn);
        TextView textView = root_view.findViewById(R.id.intro_text);

        if (!mText.equals("")) {
            textView.setVisibility(View.GONE);
            button.setVisibility(View.VISIBLE);
        }

        button.setOnClickListener(v -> {
            mRealm.beginTransaction();

            Number currentArticleId = mRealm.where(Article.class).max("id");
            int nextId = currentArticleId == null ? 1 : currentArticleId.intValue() + 1;

            Article article = mRealm.createObject(Article.class);

            StringBuilder stringBuilder = new StringBuilder();
            for (TextView view : mTextViews) {
                int colorCode = ((ColorDrawable) view.getBackground()).getColor();
                if (colorCode != 0) {
                    Number currentBlankId = mRealm.where(Blank.class).max("id");
                    int nextBlankId = currentBlankId == null ? 0 : currentBlankId.intValue() + 1;
                    Blank blank = mRealm.createObject(Blank.class);
                    blank.setId(nextBlankId);
                    blank.setWord((String) view.getText());
                    article.getBlanks().add(blank);
                    stringBuilder.append("###");
                } else {
                    stringBuilder.append((String) view.getText());
                }
            }

            article.setId(nextId);
            article.setContent(stringBuilder.toString());
            article.setTitle("테스트1");
            Toast.makeText(getContext(), mText, Toast.LENGTH_SHORT).show();
            mRealm.commitTransaction();
        });

        String[] text = mText.split("\n");
        for (String line : text) {
            String[] words = line.split(" ");
            for (String word : words) {
                TextView wordTextView = new TextView(mContext);
                wordTextView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                wordTextView.setBackgroundColor(0);
                wordTextView.setText(String.format("%s ", word));
                wordTextView.setClickable(true);
                wordTextView.setTextSize(18);
                wordTextView.setOnClickListener(v -> {
                    TextView v1 = (TextView) v;
                    ColorDrawable cd = (ColorDrawable) v1.getBackground();
                    int colorCode = cd.getColor();

                    if (colorCode == 0) {
                        v1.setBackgroundColor(Color.parseColor("#000000"));
                    } else {
                        v1.setBackgroundColor(0);
                    }
                });

                layout.addView(wordTextView);
                mTextViews.add(wordTextView);
            }
        }

        return root_view;
    }

}