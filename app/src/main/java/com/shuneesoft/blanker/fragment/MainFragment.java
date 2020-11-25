package com.shuneesoft.blanker.fragment;

import android.annotation.SuppressLint;
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
    private Context mContext;
    private String mText = "";


    public MainFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mText = getArguments().getString("text", "");
        Log.d(TAG, mText);
    }

    @Override
    public void onAttach(Context context) {
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

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), mText, Toast.LENGTH_SHORT).show();
            }
        });

        String[] words = mText.split("\n");
        for (String word : words) {
            String[] ws = word.split(" ");
            for (String w : ws) {
                TextView wordTextView = new TextView(getActivity());
                wordTextView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                wordTextView.setBackgroundColor(0);
                wordTextView.setText(String.format("%s ", w));
                wordTextView.setClickable(true);
                wordTextView.setTextSize(18);
                wordTextView.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onClick(View v) {
                        TextView v1 = (TextView) v;
                        ColorDrawable cd = (ColorDrawable) v1.getBackground();
                        int colorCode = cd.getColor();

                        if(colorCode == 0){
                            v1.setBackgroundColor(Color.parseColor("#000000"));
//                            v1.setTextColor(Color.parseColor("#000000"));
                        } else {
                            v1.setBackgroundColor(0);
                        }
                    }
                });
                layout.addView(wordTextView);
            }
        }
        return root_view;
    }

}