package com.example.calendary.calendar;

public class Holidays implements Comparable<Holidays> {
    private String year;
    private String date;
    private String name;

    public Holidays(){
    }

    public Holidays(String year, String date, String name){
        this.year = year;
        this.date = date;
        this.name = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(Holidays o) {
        return this.date.compareTo(o.date);
    }
}
