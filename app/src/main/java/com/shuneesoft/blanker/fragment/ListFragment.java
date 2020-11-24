package com.shuneesoft.blanker.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.flexbox.FlexboxLayout;
import com.shuneesoft.blanker.R;

public class ListFragment extends Fragment {
    private final String TAG = "MainFragment";
    private Context mContext;
    private String mText;


    public ListFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mText = getArguments().getString("text");
//        Log.d(TAG, mText);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup root_view = (ViewGroup) inflater.inflate(R.layout.fragment_list, container, false);
        FlexboxLayout layout = root_view.findViewById(R.id.layout);
        return root_view;
    }

}