package com.shuneesoft.blanker.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
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
    private List<TextView> mTextViews;

    public MainFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        mText = getArguments().getString("text", "");
        mTextViews  = new ArrayList<TextView>();
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

        if (!mText.equals("")) {
            button.setVisibility(View.VISIBLE);
        }

        button.setOnClickListener(v -> {
            CreateTitleDialog(layout, button);
        });

//        if (mTextViews.size() > 0) {
//            for (TextView textView : mTextViews) {
//                ((ViewGroup) textView.getParent()).removeView(textView);
////                layout.removeView(textView);
//                layout.addView(textView);
//            }
//            return root_view;
//        }

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

    private void CreateTitleDialog(FlexboxLayout layout, Button button) {
        TextView textView = new TextView(mContext);
        textView.setText("제목 입력");
        textView.setPadding(20, 40, 20, 40);
        textView.setTextSize(20F);
        textView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        textView.setTextColor(Color.WHITE);

        final EditText editText = new EditText(mContext);
        FrameLayout container = new FrameLayout(mContext);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 70, 0, 0);
        editText.setLayoutParams(params);
        container.addView(editText);

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setCustomTitle(textView);
        builder.setView(container);

        builder.setPositiveButton("확인", (dialog, which) -> {
            String title = editText.getText().toString();

            if (title.equals("")) {
                Toast.makeText(getContext(), "제목을 입력해주세요", Toast.LENGTH_SHORT).show();
                return;
            }

            mRealm.beginTransaction();

            Number currentArticleId = mRealm.where(Article.class).max("id");
            int nextId = currentArticleId == null ? 1 : currentArticleId.intValue() + 1;
            Article article = mRealm.createObject(Article.class);
            StringBuilder stringBuilder = new StringBuilder();

            for (TextView view : mTextViews) {
                int colorCode = ((ColorDrawable) view.getBackground()).getColor();
                if (colorCode != 0) {
                    Number currentBlankId = mRealm.where(Blank.class).equalTo("article.id", article.getId()).max("id");
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
            article.setTitle(title);
            mRealm.commitTransaction();
            Toast.makeText(getContext(), "저장 완료", Toast.LENGTH_SHORT).show();
            dialog.dismiss();

            layout.removeAllViews();
            mTextViews.clear();
            mText = "";
            button.setVisibility(View.GONE);

        });

        builder.setNegativeButton("취소", (dialog, which) -> dialog.dismiss());
        AlertDialog dialog = builder.show();
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.colorPrimary));
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.red_500));

    }

}