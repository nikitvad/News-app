package com.example;

import com.example.nikit.news.database.util;

import java.util.ArrayList;

public class MyClass {
    public static void main(String[] args) {
        String s = " http://www.bbc.co.uk/news/uk-#po*[lit]ics-39422353";
        String s2 = s.replaceAll("[.\\[\\]#*]", "_");

        System.out.println(s2);
    }

}
