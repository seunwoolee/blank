package com.shuneesoft.blanker.fragment;

import android.content.Context;
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
    private String mText;


    public MainFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mText = getArguments().getString("text");
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

        String[] words = mText.split("\n");
        for (String word : words) {
            String[] ws = word.split(" ");
            for (String w : ws) {
                TextView textView = new TextView(getActivity());
                textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                textView.setText(String.format("%s ", w));
                textView.setClickable(true);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(), textView.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
                layout.addView(textView);
            }
        }
        return root_view;
    }

}