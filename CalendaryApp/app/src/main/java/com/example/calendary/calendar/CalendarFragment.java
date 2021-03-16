package com.example.calendary.calendar;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.example.calendary.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class CalendarFragment extends Fragment {

    private CalendarView calendarView;
    private Calendar clickDayCalendar;
    private List<EventDay> eventDays = new ArrayList<>();


    public CalendarFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        init(view);

        calendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                clickDayCalendar = eventDay.getCalendar();


            }
        });

        return view;
    }

    private void init(View view) {
        clickDayCalendar = Calendar.getInstance();
        calendarView = (CalendarView) view.findViewById(R.id.calendarView);
        calendarView.setEvents(eventDays);
    }


}
