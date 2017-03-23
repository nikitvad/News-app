package com.example;

import com.example.nikit.news.database.util;

import java.util.ArrayList;

public class MyClass {
    public static void main(String[] args) {
        String[] strings = new String[]{"latest", "top", "popular"};

        String sortBy = "top\nlatest\npopular";

        System.out.println(util.getListOfStringsFromString(sortBy)[0]);
        System.out.println("sdfsdf");
        System.out.println(util.getStringFromArrayOfString(strings));

    }

}
