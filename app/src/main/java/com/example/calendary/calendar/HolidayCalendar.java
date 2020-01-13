package com.example.calendary.calendar;

import android.icu.util.ChineseCalendar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class HolidayCalendar {
    static ArrayList<Holidays> holidaysArrayList = new ArrayList<>();

    public static String LunarToSolar(String yyyymmdd) {
        ChineseCalendar chineseCalendar = new ChineseCalendar();
        Calendar calendar = Calendar.getInstance();

        if (yyyymmdd == null) {
            return "";
        }

        String date = yyyymmdd.trim();
        if (date.length() != 8) {
            if (date.length() == 4) {
                date = date + "0101"; }
            else if (date.length() == 6) {
                date = date + "01"; }
            else if (date.length() > 8){
                date = date.substring(0 , 8); }
            else return "";
        }

        chineseCalendar.set(ChineseCalendar.EXTENDED_YEAR, Integer.parseInt(date.substring(0, 4)) + 2637);
        chineseCalendar.set(ChineseCalendar.MONTH, Integer.parseInt(date.substring(4,6)) - 1);
        chineseCalendar.set(ChineseCalendar.DAY_OF_MONTH, Integer.parseInt(date.substring(6)));

        calendar.setTimeInMillis(chineseCalendar.getTimeInMillis());

        int y = calendar.get(Calendar.YEAR);
        int m = calendar.get(Calendar.MONTH);
        int d = calendar.get(Calendar.DAY_OF_MONTH);

        StringBuffer buffer = new StringBuffer();
        buffer.append(String.format("%04d",y));
        buffer.append(String.format("%02d",m));
        buffer.append(String.format("%02d",d));

        return buffer.toString();
    }

    public static String SolarToLunar(String yyyymmdd){
        ChineseCalendar chineseCalendar = new ChineseCalendar();
        Calendar calendar = Calendar.getInstance();

        if(yyyymmdd == null) return "";

        String date = yyyymmdd.trim();
        if (date.length() != 8){
            if(date.length() == 4) date += "0101";
            else if(date.length() == 6) date += "01";
            else if(date.length() > 8) date = date.substring(0,8);
            else return "";
        }

        calendar.set(Calendar.YEAR , Integer.parseInt(date.substring(0,4)));
        calendar.set(Calendar.MONTH , Integer.parseInt(date.substring(4,6)) - 1);
        calendar.set(Calendar.DAY_OF_MONTH , Integer.parseInt(date.substring(6)));

        chineseCalendar.setTimeInMillis(calendar.getTimeInMillis());

        int y = chineseCalendar.get(ChineseCalendar.EXTENDED_YEAR) - 2637;
        int m = chineseCalendar.get(ChineseCalendar.MONTH) + 1;
        int d = chineseCalendar.get(ChineseCalendar.DAY_OF_MONTH);

        StringBuffer buffer = new StringBuffer();
        if (y < 1000) buffer.append("0");
        else if (y < 100) buffer.append("00");
        else if(y < 10) buffer.append("000");
        buffer.append(y);

        if(m<10) buffer.append("0");
        buffer.append(m);

        if (d < 10) buffer.append("0");
        buffer.append(d);

        return buffer.toString();
    }

    public static ArrayList<Holidays> holidaysArrayList(String yyyy){
        holidaysArrayList.clear();

        // 양력 휴일
        addHolidaysItem(yyyy,"0101" ,"신정");
        addHolidaysItem(yyyy,"0301" ,"삼일절");
        addHolidaysItem(yyyy,"0505" ,"어린이날");
        addHolidaysItem(yyyy,"0606" ,"현충일" );
        addHolidaysItem(yyyy,"0815" ,"광복절");
        addHolidaysItem(yyyy,"1003" ,"개천절");
        addHolidaysItem(yyyy,"1009" ,"한글날");
        addHolidaysItem(yyyy,"1225" ,"성탄절");

        // 음력 휴일
        String prev_seol = String.valueOf(Integer.parseInt(LunarToSolar(yyyy+"0101")) -1);
        addHolidaysItem(yyyy,prev_seol.substring(4) ,"");
        addHolidaysItem(yyyy,SolarDays(yyyy, "0101"),"설날");
        addHolidaysItem(yyyy,SolarDays(yyyy, "0102"),"");
        addHolidaysItem(yyyy,SolarDays(yyyy, "0408"),"석탄일");
        addHolidaysItem(yyyy,SolarDays(yyyy, "0814"),"");
        addHolidaysItem(yyyy,SolarDays(yyyy, "0815"),"추석");
        addHolidaysItem(yyyy,SolarDays(yyyy, "0816"),"");

        try {
            // 어린이날 대체공휴일 검사 : 어린이날은 토요일, 일요일인 경우 그 다음 평일을 대체공휴일로 지정
            int childDayChk = WeekendValue(yyyy+"0505");
            if(childDayChk == 1) addHolidaysItem(yyyy,"0506" ,"대체공휴일");
            if(childDayChk == 7) addHolidaysItem(yyyy,"0507" ,"대체공휴일");

            // 설날 대체공휴일 검사
            if(WeekendValue(LunarToSolar(yyyy+"0101"))==1) addHolidaysItem(yyyy,SolarDays(yyyy, "0103"),"대체공휴일");
            if(WeekendValue(LunarToSolar(yyyy+"0101"))==2) addHolidaysItem(yyyy,SolarDays(yyyy, "0103"),"대체공휴일");
            if(WeekendValue(LunarToSolar(yyyy+"0102"))==1) addHolidaysItem(yyyy,SolarDays(yyyy, "0103"),"대체공휴일");

            // 추석 대체공휴일 검사
            if(WeekendValue(LunarToSolar(yyyy+"0814"))==1) addHolidaysItem(yyyy,SolarDays(yyyy, "0817"),"대체공휴일");
            if(WeekendValue(LunarToSolar(yyyy+"0815"))==1) addHolidaysItem(yyyy,SolarDays(yyyy, "0817"),"대체공휴일");
            if(WeekendValue(LunarToSolar(yyyy+"0816"))==1) addHolidaysItem(yyyy,SolarDays(yyyy, "0817"),"대체공휴일");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Collections.sort(holidaysArrayList); // 오름차순 정렬

        return holidaysArrayList;
    }

    private static String SolarDays(String yyyy, String date){
        return LunarToSolar(yyyy+date).substring(4);
    }

    private static void addHolidaysItem(String year, String date, String name ){
        Holidays item = new Holidays();
        item.setYear(year);
        item.setDate(date);
        item.setName(name);
        holidaysArrayList.add(item);
    }

    private static int WeekendValue(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(date));
        return cal.get(Calendar.DAY_OF_WEEK);
    }
}
