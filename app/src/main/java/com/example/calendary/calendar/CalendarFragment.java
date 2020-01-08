package com.example.calendary.calendar;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calendary.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;



public class CalendarFragment extends Fragment implements View.OnClickListener {

    TextView current_Month;
    ImageView prevMonth;
    ImageView nextMonth;
    RecyclerView calendar;

    private CalendarAdapter adapter;

    private static final String dateTemplate = "yyyy MMMM";

    private ArrayList<CalendarModel> calList = new ArrayList<>();
    private static final int DAY_OFFSET = 1;
    private final String[] weekdays = new String[] { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" };
    private int lastDays;
    private Calendar cal;
    public int cur_month ,cur_year;
    RecyclerView.LayoutManager layoutManager;

    public CalendarFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        log("onCreateView");
        return inflater.inflate(R.layout.fragment_calendar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        log("onViewCreated");
        init(view);

        adapter = new CalendarAdapter(this);
        layoutManager = new GridLayoutManager(getContext(), BaseCalendar.DAYS_OF_WEEK);
        calendar.setLayoutManager(layoutManager);
        calendar.setAdapter(adapter);

        cal = Calendar.getInstance();
//        adapter.refreshView(cal);
    }

    @Override
    public void onResume() {
        log("onResume");
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        log("onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        log("onDestroy");
        super.onDestroy();
    }

    private void log(String arg) {
        Log.d(CalendarFragment.class.getSimpleName(), String.format("check to %s", arg));
    }

    private void init(View view){
        current_Month = view.findViewById(R.id.currentMonth);
        prevMonth = view.findViewById(R.id.prevMonth);
        nextMonth = view.findViewById(R.id.nextMonth);
        calendar = view.findViewById(R.id.calendar);
    }

    public void refreshCurrentMonth(Calendar calendar){
        SimpleDateFormat format = new SimpleDateFormat("yyyy MM", Locale.KOREAN);
        current_Month.setText(format.format(cal.getTime()));
    }

    @Override
    public void onClick(View v) {
        if(v == prevMonth){
            adapter.changeToPrevMonth();
        } else if(v == nextMonth) {
            adapter.changeToNextMonth();
        } else{
            return;
        }
    }
}
