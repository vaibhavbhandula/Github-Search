package com.test.githubsearch.utils;

/**
 * @author Vaibhav Bhandula on 23-02-2017.
 */

public class Utils {

    public static String firstLetterCapital(String name) {
        if (name == null || name.isEmpty()) {
            return "";
        }
        return name.substring(0, 1).toUpperCase() + name.substring(1, name.length());
    }
}
