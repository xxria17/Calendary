package com.example.calendary.calendar;

public class CalendarModel {
    private  String year;
    private  String month;
    private  String day;
    private  int weekday;
    private  String color;
    private String event;
    private String key;


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public CalendarModel(String year, String month, String day, int weekday, String color, String event, String key) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.weekday = weekday;
        this.color = color;
        this.event = event;
        this.key = key;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getWeekday() {
        return weekday;
    }

    public void setWeekday(int weekday) {
        this.weekday = weekday;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }
}
