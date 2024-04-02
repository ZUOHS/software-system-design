package org.example.utils;

import java.util.ArrayList;
import java.util.List;

public class Convert {
    public static List<Integer> convertStringToIntArrayList(String input) {
        List<Integer> output = new ArrayList<>();

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            output.add(c - 'A');
        }

        return output;
    }

}
