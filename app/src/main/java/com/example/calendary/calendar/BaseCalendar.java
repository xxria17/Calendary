package com.example.calendary.calendar;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BaseCalendar {

    public static final int DAYS_OF_WEEK = 7;
    public static final int LOW_OF_CALENDAR = 5;

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
        calendar.add(Calendar.MONTH, -1);

        try {
            makeMonthDate();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        Log.e("changeToPrev:data check", String.valueOf(data.size()));
    }

    public void changeToNextMonth(){
//        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
        calendar.add(Calendar.MONTH, 1);

        try {
            makeMonthDate();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
    }

    private void makeMonthDate() throws ParseException {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String calDate = sdf.format(calendar.getTime());
            Log.d("ddd", String.format("check to calDate : %s", calDate));

        } catch (Exception e) {
            e.printStackTrace();
        }

        data.clear();
//        calendar.set(Calendar.DATE, 2);
        currentMonthMaxDate = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        prevMonthTailOffset = calendar.get(Calendar.DAY_OF_WEEK ) - 1;

        makePrevMonthTail(calendar);
        makeCurrentMonth(calendar);

        nextMonthHeadOffset = LOW_OF_CALENDAR * DAYS_OF_WEEK - (prevMonthTailOffset + currentMonthMaxDate);
        makeNextMonthHead();

    }

    /*
    * 달력에서 보이는 전 달 표시
    * */
    private void makePrevMonthTail(Calendar calendar){
        // TODO: 2020-01-09 객체 참조
//        calendar.set(Calendar.MONTH , calendar.get(Calendar.MONTH) - 1);
        Date targetDate = calendar.getTime();
        Calendar prevCal = Calendar.getInstance();
        prevCal.setTime(targetDate);


        int maxDate = calendar.getActualMaximum(Calendar.DATE);
        int maxoffsetDate = maxDate - prevMonthTailOffset;

        Log.d("ZZZZZZZZZZZZZ",maxoffsetDate+"");
        for(int i=1; i<prevMonthTailOffset; i++){
            data.add(maxoffsetDate);
        }
    }

    /*
    * 달력에 표시되는 현재 달
    * */
    private void makeCurrentMonth(Calendar calendar){
//        for (변수 선언; 조건문, 증감식)
            int currentMaxDay = calendar.getActualMaximum(Calendar.DATE);
        for (int i=1; i<= currentMaxDay;i++){
            data.add(i);
        }
    }

    /*
    * 달력에 표시되는 다음 달 첫 부분
    * */
    private void makeNextMonthHead(){
        int date = 1;
        for(int i = 1; i<= nextMonthHeadOffset; i++){
            data.add(date);
        }
        Log.d("ZZZZZZZZZZZZZ",date+"");
    }
}
