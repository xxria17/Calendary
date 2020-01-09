package com.example.calendary.calendar;


import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calendary.ItemDecoration;
import com.example.calendary.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;



public class CalendarFragment extends Fragment {

    TextView current_Month;
    ImageView prevMonth;
    ImageView nextMonth;
    RecyclerView calendar;

    private CalendarAdapter adapter;

    private Calendar cal;

    RecyclerView.LayoutManager layoutManager;

    public CalendarFragment() {
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
        calendar.addItemDecoration(new ItemDecoration(view.getContext()));

        cal = Calendar.getInstance();
        adapter.refreshView(cal);


        prevMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.changeToPrevMonth();
//                adapter.refreshView(cal);
            }
        });

        nextMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.changeToNextMonth();
//                adapter.refreshView(cal);
            }
        });
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

    private void showToast(final String arg) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                String showTxt = nullToEmptyString(arg);
                Toast.makeText(getContext(), showTxt, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String nullToEmptyString(String arg) {
        if(arg == null) {
            return "";
        }

        return arg;
    }

    private void init(View view){
        current_Month = view.findViewById(R.id.currentMonth);
        prevMonth = view.findViewById(R.id.prevMonth);
        nextMonth = view.findViewById(R.id.nextMonth);
        calendar = view.findViewById(R.id.calendar);
    }

    public void refreshCurrentMonth(Calendar calendar){
        SimpleDateFormat format = new SimpleDateFormat("MM월", Locale.KOREAN);
        current_Month.setText(format.format(calendar.getTime()));
    }

}
