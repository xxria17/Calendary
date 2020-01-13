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

    List<String> data;
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
//        calendarView.set(Calendar.MONTH, calendarView.get(Calendar.MONTH) + 1);
        calendar.add(Calendar.MONTH, 1);

        try {
            makeMonthDate();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
    }

    private void makeMonthDate() throws ParseException {

        data.clear();

        currentMonthMaxDate = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        prevMonthTailOffset = calendar.get(Calendar.DAY_OF_WEEK ) - 1;

        getFirstDate(calendar);
        getCurrentMonth(calendar);

    }

    /*
    * 해당하는 달 1일의 요일 구해서 전 달의 마지막 날짜 넣어주기
    * */
    private void getFirstDate(Calendar cal) throws ParseException{
        Date date = cal.getTime();
        Calendar currCal = Calendar.getInstance();
        currCal.setTime(date);

        String year = String.valueOf(cal.get(Calendar.YEAR));
        String month = String.valueOf(cal.get(Calendar.MONTH) + 1);
        String day = "0" + String.valueOf(cal.getActualMinimum(Calendar.DAY_OF_MONTH));

        if(month.length() == 1) {
            month = "0" + month;
        } else {
            month = String.valueOf(cal.get(Calendar.MONTH) + 1);
        }

        currCal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        String firstDate = year + month + day ;
        Log.d("!!!!!!!!!!!!!" , firstDate);

//        if("" != null) {
//            currCal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.getActualMinimum(Calendar.DAY_OF_MONTH));
//
//            String tmpYear = String.valueOf(cal.get(Calendar.YEAR));
//            String tmpMonth = String.valueOf(cal.get(Calendar.MONTH)) + 1;
//            String tmpday = "01";
//
//            if (tmpMonth.length() == 1) {
//                tmpMonth = "0" + tmpMonth;
//            }
//
//            firstDate = String.format("%s%s%s", tmpYear, tmpMonth, tmpday);
//        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Date first = dateFormat.parse(firstDate);


        currCal.setTime(first);

        int weekDate = currCal.get(Calendar.DAY_OF_WEEK); //1일의 요일
        int prevMonthDays = currCal.getActualMaximum(Calendar.DAY_OF_MONTH); //전 달의 일수
        int addCount = weekDate - 1; //채워줘야 할 요일의 갯수
        int prevDays = prevMonthDays - addCount; //채워질 날짜



        if(DAYS_OF_WEEK - weekDate != -1) {
            for(int i = 1; i <= addCount; i++){
//                prevDays += 1;
                String empty = " ";
                data.add(empty);
            }
        } else{
            return;
        }
    }

    private void getCurrentMonth(Calendar cal){
        Date date = cal.getTime();
        Calendar currCal = Calendar.getInstance();
        currCal.setTime(date);

        int currMonthDays = currCal.getActualMaximum(Calendar.DAY_OF_MONTH); // 해당 달의 일수

        for(int i = 1; i <= currMonthDays; i++){
            data.add(String.valueOf(i));
        }
    }


}
