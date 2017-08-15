package Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Commons {
    private Commons() {}

    public static final int REQ_SEARCH_CALENDAR_BY_ID = 0;
    public static final int REQ_USER = 1;
    public static final int REQ_GROUP = 2;
    public static final int REQ_EVENT = 3;
    public static final int REQ_GROUPTOCALENDAR = 4;
    public static final int REQ_GROUPTOUSER = 5;

    public static final String TYPE = "Type";
    public static final String CALENDAR_ID = "cid";

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

    public static JSONObject parseJSONObjectFromString(String objString) {
        String[] sets = objString.split("&");
        JSONObject obj = new JSONObject();
        for (String set : sets) {
            String[] pair = set.split("=");
            obj.putField(pair[0], pair[1]);
        }
        return obj;
    }
}
