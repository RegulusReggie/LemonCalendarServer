package Controller;

import Util.DBAccess;

import java.sql.SQLException;

public class GroupToCalendarDB {

    private GroupToCalendarDB() {}

    public static void insertG2C (int gid, int cid) throws SQLException, ClassNotFoundException {
        String updateStmt =
                "INSERT INTO `lemoncalendar`.`GROUPTOCALENDAR`\n" +
                        "(`GROUP_ID`, `CALENDAR_ID`)\n" +
                        "VALUES\n" +
                        "(" + gid +", " + cid + ");";

        DBAccess.getDBA().executeUpdate(updateStmt);
    }
}
