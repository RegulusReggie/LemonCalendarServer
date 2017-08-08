package Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Commons {
    private Commons() {}

    public static List<Integer> convertStringToList(String s) {
        if (s != null && !s.isEmpty()) {
            return Arrays.asList(s.split(" ")).stream().map(p -> Integer.valueOf(p)).collect(Collectors.toList());
        }
        else {
            return new ArrayList<>();
        }
    }

    public static List<String> convertStringToStringList(String s) {
        if (s != null && !s.isEmpty()) {
            return Arrays.asList(s.split(" "));
        }
        else {
            return new ArrayList<>();
        }
    }

    public static String convertListToString(List<Integer> list) {
        String s = new String();
        for (Integer ele : list) {
            s = s + ele.toString() + " ";
        }
        return s;
    }
}
