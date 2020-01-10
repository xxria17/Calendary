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

//        makePrevMonthTail(calendar);
        getFirstDate(calendar);
//        makeCurrentMonth(calendar);
        getCurrentMonth(calendar);

        nextMonthHeadOffset = LOW_OF_CALENDAR * DAYS_OF_WEEK - (prevMonthTailOffset + currentMonthMaxDate);
//        makeNextMonthHead();
        getNextMonthHead(calendar);

    }

    /*
    * 달력에서 보이는 전 달 표시
    * */
    private void makePrevMonthTail(Calendar calendar){
        // TODO: 2020-01-09 객체 참조
//        calendarView.set(Calendar.MONTH , calendarView.get(Calendar.MONTH) - 1);
        Date targetDate = calendar.getTime();
        Calendar prevCal = Calendar.getInstance();
        prevCal.setTime(targetDate);

        int maxDate = prevCal.getActualMaximum(Calendar.DATE);
        int maxoffsetDate = maxDate - prevMonthTailOffset + 1;

        for(int i=2; i<prevMonthTailOffset; i++){
            maxoffsetDate += 1;
           data.add(maxoffsetDate);
//            data.add(0, maxoffsetDate);
        }
    }

    /*
    * 해당하는 달 1일의 요일 구해서 전 달의 마지막 날짜 넣어주기
    * */
    private void getFirstDate(Calendar cal) throws ParseException{
        Date date = cal.getTime();
        Calendar currCal = Calendar.getInstance();
        currCal.setTime(date);

        String firstDate =String.valueOf(cal.get(Calendar.YEAR)) + "0" + String.valueOf(cal.get(Calendar.MONTH) + 1) + "01";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Date first = dateFormat.parse(firstDate);
        currCal.setTime(first);

        int weekDate = currCal.get(Calendar.DAY_OF_WEEK); //1일의 요일
        int prevMonthDays = currCal.getActualMaximum(Calendar.DAY_OF_MONTH); //전 달의 일수
        int addCount = weekDate - 1; //채워줘야 할 요일의 갯수
        int prevDays = prevMonthDays - addCount; //채워질 날짜


        if(DAYS_OF_WEEK - weekDate != 0) {
            for(int i = 1; i <= addCount; i++){
                prevDays += 1;
                data.add(prevDays);
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
            data.add(i);
        }
    }

    private void getNextMonthHead(Calendar cal) throws ParseException{
        Date date = cal.getTime();
        Calendar currCal = Calendar.getInstance();
        currCal.setTime(date);

        int lastDay = currCal.getMaximum(Calendar.DAY_OF_MONTH); // 해당 달의 마지막 날짜

        String lastDate =String.valueOf(cal.get(Calendar.YEAR)) + "0" + String.valueOf(cal.get(Calendar.MONTH) + 1) + "" + lastDay;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Date last = dateFormat.parse(lastDate);
        currCal.setTime(last);

        int weekDate = currCal.get(Calendar.DAY_OF_WEEK); //마지막 날짜의 요일
        int monthDays = currCal.getActualMaximum(Calendar.DAY_OF_MONTH); //해당 달의 일수
        int nextDays = DAYS_OF_WEEK - weekDate; //채워질 날짜

        if(nextDays != 0) {
            for(int i = 1; i <= nextDays; i++){
                data.add(i);
            }
        } else{
            return;
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
        for(int i = 1; i<= nextMonthHeadOffset; i++){
            data.add(i);
        }
    }
}
