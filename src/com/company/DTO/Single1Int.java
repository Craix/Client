package com.company.DTO;


public class Single1Int {

    private int mark;

    public Single1Int(int mark) {
        this.mark = mark;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    @Override
    public String toString() {
        return "Single1Int{" +
                "mark=" + mark +
                '}';
    }
}