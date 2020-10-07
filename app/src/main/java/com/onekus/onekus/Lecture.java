package com.onekus.onekus;

public class Lecture {

    String code;
    String title;
    String prof;
    String time;
    int    count;
    int    limit;

    Lecture(String code, String title, String prof, String time) {
        this.code  = code;
        this.title = title;
        this.prof  = prof;
        this.time  = time;
    }

    void setNumber(int count, int limit) {
        this.count = count;
        this.limit = limit;
    }

}