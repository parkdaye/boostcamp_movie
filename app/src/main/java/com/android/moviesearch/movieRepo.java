package com.android.moviesearch;

import java.util.List;

public class movieRepo {
    private String lastBuildDate;
    private int total;
    private int start;
    private int display;
    private List<items> items;

    public String getLastBuildDate() {
        return lastBuildDate;
    }

    public void setLastBuildDate(String lastBuildDate) {
        this.lastBuildDate = lastBuildDate;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getDisplay() {
        return display;
    }

    public void setDisplay(int display) {
        this.display = display;
    }

    public List<items> getitemsList() {
        return items;
    }

    public void setitemsList(List<items> itmesList) {
        this.items = items;
    }
}
