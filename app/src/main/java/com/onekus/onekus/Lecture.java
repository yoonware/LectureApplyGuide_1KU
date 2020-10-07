package com.onekus.onekus;

public class Lecture {

    private String code;
    private String title;
    private String professor;
    private String schedule;

    private int present;
    private int limit;

    public Lecture(String code, String title, String professor, String schedule) {
        this.code = code;
        this.title = title;
        this.professor = professor;
        this.schedule = schedule;
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public String getProfessor() {
        return professor;
    }

    public String getSchedule() {
        return schedule;
    }

    public int getPresent() {
        return present;
    }

    public int getLimit() {
        return limit;
    }

    public void setPresent(int present) {
        this.present = present;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

}