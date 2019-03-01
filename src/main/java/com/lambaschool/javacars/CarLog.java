package com.lambaschool.javacars;

import lombok.Data;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class CarLog implements Serializable
{

    private final String mgs;
    private final String  formattedDate;

    public CarLog(String mgs) {
        this.mgs = mgs;
        Date date = new Date();
        String strDateFormat = "yyyy-MM-dd hh:mm:ss a";
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        formattedDate = dateFormat.format(date);
    }
}