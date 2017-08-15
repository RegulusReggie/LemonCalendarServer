package Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Commons {
    private Commons() {}

    //REQUESTS
    //CALENDAR
    public static final int REQ_SEARCH_CALENDAR_BY_ID = 0;
    public static final int REQ_SEARCH_CALENDAR_BY_GROUP_YEAR_MONTH = 1;
    public static final int REQ_UPDATE_CALENDAR_EVENT = 2;
    public static final int REQ_DELETE_CALENDAR = 3;
    public static final int REQ_INSERT_CALENDAR = 4;
    //EVENT
    public static final int REQ_SEARCH_EVENT_BY_ID = 5;
    public static final int REQ_SEARCH_EVENT_BY_DATE = 6;
    public static final int REQ_UPDATE_EVENT = 7;
    public static final int REQ_DELETE_EVENT = 8;
    public static final int REQ_INSERT_EVENT = 9;
    //GROUP
    public static final int REQ_SEARCH_GROUP_BY_ID = 10;
    public static final int REQ_SEARCH_GROUP_BY_NAME = 11;
    public static final int REQ_UPDATE_GROUP_MEMBER = 12;
    public static final int REQ_INSERT_GROUP = 13;
    //USER
    public static final int REQ_SEARCH_USER_BY_ID = 14;
    public static final int REQ_SEARCH_USER_BY_NAME = 15;
    public static final int REQ_INSERT_USER = 16;
    public static final int REQ_CHECK_USER_LOGIN = 17;

    //RESPOND
    public static final int RESPOND_CALENDAR = 50;
    public static final int RESPOND_CALENDAR_ID = 51;
    public static final int RESPOND_EVENT = 52;
    public static final int RESPOND_EVENT_ID = 53;
    public static final int RESPOND_GROUP = 54;
    public static final int RESPOND_GROUP_ID = 55;
    public static final int RESPOND_USER = 56;
    public static final int RESPOND_USER_ID = 57;

    public static final String TYPE = "Type";
    //CALENDAR
    public static final String CALENDAR_ID = "cid";
    public static final String MONTH = "month";
    public static final String YEAR = "year";
    public static final String CALENDAR_EVENT_IDS = "eventids";
    //EVENT
    public static final String EVENT_ID = "eid";
    public static final String DAY = "day";
    public static final String DESCRIPTION = "description";
    //GROUP
    public static final String GROUP_ID = "gid";
    public static final String GROUPNAME = "groupname";
    public static final String MEMBERS_ID = "membersid";
    public static final String OWNERS_ID = "ownersid";
    //USER
    public static final String USER_ID = "uid";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";

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
