package com.example.common_view.bean;

public class DateBean {

    private int year, month, day;

    public DateBean(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DateBean dateBean = (DateBean) o;
        return year == dateBean.year &&
                month == dateBean.month &&
                day == dateBean.day;
    }

}
