package com.slyfoxdevelopment.c196.utils;

import androidx.room.Ignore;
import androidx.room.TypeConverter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {

    @TypeConverter
    public static Date toDate(Long timestamp){
        return timestamp == null ? null : new Date(timestamp);
    }

    @TypeConverter
    public static Long toTimeStamp(Date date){
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static Date convertToDate(String str){
        try{
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

            return dateFormat.parse(str);

        }catch (Exception e){
            //do nothing
            return null;
        }

    }

    public static String convertToString(Date date){
       return date.toString();

    }

    public static String convertDateForView(Date date){
        return new SimpleDateFormat("MM/dd/yyyy").format(date);
    }
}
