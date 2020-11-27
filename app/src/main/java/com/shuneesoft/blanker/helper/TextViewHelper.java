package com.shuneesoft.blanker.helper;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.common.base.Strings;
import com.shuneesoft.blanker.R;
import com.shuneesoft.blanker.model.Article;
import com.shuneesoft.blanker.model.Blank;

import io.realm.Realm;

public class TextViewHelper {
    private static final TextViewHelper textViewHelper = new TextViewHelper();

    public static TextViewHelper getInstance() {
        return textViewHelper;
    }

    public TextView createWordTextView(Context context, String word) {
        TextView wordTextView = new TextView(context);
        wordTextView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        wordTextView.setBackgroundColor(0);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            wordTextView.setTextAppearance(R.style.TextAppearance_AppCompat_Subhead);
        } else {
            wordTextView.setTextAppearance(context, R.style.TextAppearance_AppCompat_Subhead);
        }

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
        return wordTextView;
    }

    public String createBlank(TextView view, Realm realm, Article article) {
        int colorCode = ((ColorDrawable) view.getBackground()).getColor();
        String result;
        if (colorCode != 0) {
            Number currentBlankId = realm
                    .where(Blank.class)
                    .equalTo("article.id", article.getId()).max("id");
            int nextBlankId = currentBlankId == null ? 0 : currentBlankId.intValue() + 1;
            Blank blank = realm.createObject(Blank.class);
            blank.setId(nextBlankId);
            blank.setWord((String) view.getText());
            article.getBlanks().add(blank);
            result = String.format("%s ", Strings.repeat("뷁", nextBlankId + 1));
        } else {
            result = (String) view.getText();
        }
        return result;

    }

//    public String
}
