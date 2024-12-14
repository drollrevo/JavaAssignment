package com.mycompany.javaassignment;

import java.time.LocalDateTime;

public class CurrentTime {
    private int year;
    private int month;
    private int date;
    private int hour;
    private int minute;
    private int second;

    // Constructor
    public CurrentTime() {
        LocalDateTime now = LocalDateTime.now();
        this.year = now.getYear();
        this.month = now.getMonthValue();
        this.date = now.getDayOfMonth();
        this.hour = now.getHour();
        this.minute = now.getMinute();
        this.second = now.getSecond();
    }

    // Getters and Setters
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    /*Methods*/
    public String toDateFormat() {
        return String.format("%04d-%02d-%02d", year, month, date);
    }

    public String toDateTimeFormat() {
        return String.format("%04d-%02d-%02d;%02d:%02d:%02d", year, month, date, hour, minute, second);
    }

    @Override
    public String toString() {
        return "CurrentTime{" +
                "year=" + year +
                ", month=" + month +
                ", date=" + date +
                ", hour=" + hour +
                ", minute=" + minute +
                ", second=" + second +
                '}';
    }
}