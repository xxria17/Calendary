package com.example.calendary.Fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.calendary.Model.TrendModel;
import com.example.calendary.R;

import java.util.ArrayList;


public class SearchFragment extends Fragment {
    private ListView trendList;

    public SearchFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        return view;
    }

    private void init(View view){
        trendList = (ListView) view.findViewById(R.id.trend_list);
    }


}
