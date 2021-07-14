package com.sjbit.ereport.object;

public class DateFormatConverter {
    private String date;
    private String month;
    private String year;
    public DateFormatConverter(String date, String month, String year){
        this.date = date;
        this.month = month;
        this.year = year;
    }
    public String getDateInFormat(){
        return date + "/" + month + "/" + year;
    }

}
