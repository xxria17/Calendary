package com.example.calendary.calendar;

import android.util.Log;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BaseCalendar {

    public static final int DAYS_OF_WEEK = 7;
    public static final int LOW_OF_CALENDAR = 6;

    Calendar calendar = Calendar.getInstance();

    int prevMonthTailOffset = 0;
    int nextMonthHeadOffset = 0;
    int currentMonthMaxDate = 0;

    List<Integer> data;
    private Object ParseException;
    private Object ParsePosition;

    public void init(){
        data = new ArrayList<>();
        calendar = Calendar.getInstance(Locale.getDefault());

    }

    public void initBaseCalendar(){
        try {
            makeMonthDate();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
    }

    public void changeToPrevMonth(){
        if(calendar.get(Calendar.MONTH) == 0){
            calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - 1);
            calendar.set(Calendar.MONTH, Calendar.DECEMBER);
        } else {
            calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
        }
        try {
            makeMonthDate();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        Log.e("changeToPrev:data check", String.valueOf(data.size()));
    }

    public void changeToNextMonth(){
        if(calendar.get(Calendar.MONTH) == Calendar.DECEMBER){
            calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + 1);
            calendar.set(Calendar.MONTH, 0);
        } else{
            calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
        }
        try {
            makeMonthDate();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
    }

    private void makeMonthDate() throws ParseException {
        data.clear();
        calendar.set(Calendar.DATE, 1);
        currentMonthMaxDate = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        prevMonthTailOffset = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        Calendar changedate = Calendar.getInstance();

        makePrevMonthTail(changedate);

        makeCurrentMonth(calendar);

        nextMonthHeadOffset = LOW_OF_CALENDAR * DAYS_OF_WEEK - (prevMonthTailOffset + currentMonthMaxDate);
        makeNextMonthHead();

    }

    private void makePrevMonthTail(Calendar calendar){
        calendar.set(Calendar.MONTH , calendar.get(Calendar.MONTH) - 1);
        int maxDate = calendar.getActualMaximum(Calendar.DATE);
        int maxoffsetDate = maxDate - prevMonthTailOffset;

        for(int i=1; i<prevMonthTailOffset; i++){
            data.add(maxoffsetDate);
        }
    }

    private void makeCurrentMonth(Calendar calendar){
//        for (변수 선언; 조건문, 증감식)
            int currentMaxDay = calendar.getActualMaximum(Calendar.DATE);
        for (int i=1; i< currentMaxDay;i++){
            data.add(i);
        }
    }

    private void makeNextMonthHead(){
        int date = 1;
        for(int i = 1; i<nextMonthHeadOffset; i++){
            data.add(date);
        }
    }
}
