package com.example.nikit.news.database;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by nikit on 23.03.2017.
 */

public class util {

    public static String getStringFromArrayOfString(String[] strings){
        String result = "";
        for(String item: strings){
            result = result + item + "\n";
        }
        return result;
    }

    public static String[] getListOfStringsFromString(String string){
        return string.split("\n");
    }

}
