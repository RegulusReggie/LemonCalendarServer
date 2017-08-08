package Controller;

import Entity.Calendar;
import Util.Commons;
import Util.DBAccess;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CalendarFactory {

    private static Map<Integer, Calendar> calMap = new HashMap<>();

    private CalendarFactory() {}

    public static Calendar searchCalendar (int cid) throws SQLException, ClassNotFoundException {
        String selectStmt = "SELECT * FROM calendar WHERE CALENDAR_ID ="+cid+";";

        try {
            ResultSet rsCal = DBAccess.getDBA().executeQuery(selectStmt);
            return getCalendarFromResultSet(rsCal);
        } catch (SQLException e) {
            System.out.println("While searching a calendar with " + cid + " id, an error occurred: " +e);
            throw e;
        }
    }
    private static Calendar getCalendarFromResultSet(ResultSet rs) throws SQLException {
        Calendar cal = null;
        if (rs.next()) {
            cal = new Calendar();
            cal.setCalendarId(rs.getInt("CALENDAR_ID"));
            cal.setEventIds(Commons.convertStringToList(rs.getString("EVENT_IDS")));
            cal.setYear(rs.getInt("YEAR"));
            cal.setMonth(rs.getInt("MONTH"));
        }
        return cal;
    }

    public static ObservableList<Calendar> searchCalendars () throws SQLException, ClassNotFoundException {
        String selectStmt = "SELECT * FROM CALENDAR;";

        try {
            ResultSet rsCals = DBAccess.getDBA().executeQuery(selectStmt);
            return (ObservableList<Calendar>) getCalendarFromResultSet(rsCals);
        } catch (SQLException e) {
            System.out.println("SQL select operation has failed: "+ e);
            throw e;
        }
    }

    private static ObservableList<Calendar> getCalendarList(ResultSet rs) throws SQLException, ClassNotFoundException {
        ObservableList<Calendar> calList = FXCollections.observableArrayList();

        while (rs.next()) {
            Calendar cal = new Calendar();
            cal.setCalendarId(rs.getInt("CALENDAR_ID"));
            cal.setEventIds(Commons.convertStringToList(rs.getString("EVENT_IDS")));
            cal.setYear(rs.getInt("YEAR"));
            cal.setMonth(rs.getInt("MONTH"));

            calList.add(cal);
        }
        return calList;
    }

    public static void updateCalEvent (int calId, String calEvent) throws SQLException, ClassNotFoundException {
        String updateStmt =
                "UPDATE CALENDAR SET EVENT_IDS = '" + calEvent + "' " + "WHERE CALENDAR_ID = " + calId + ";";

        DBAccess.getDBA().executeUpdate(updateStmt);
    }

    public static void deleteCalWithId (int calId) throws SQLException, ClassNotFoundException {
        String updateStmt =
                "DELETE FROM CALENDAR WHERE CALENDAR_ID =" + calId +";";
        DBAccess.getDBA().executeUpdate(updateStmt);
    }

    public static int insertCal (List<Integer> eventids, int year, int month) throws SQLException, ClassNotFoundException {
        String updateStmt =
                "INSERT INTO CALENDAR (EVENT_IDS, YEAR, MONTH) VALUES ('"
                        + Commons.convertListToString(eventids)+"', '"+year+"', '"+month+"');";

        Statement stmt = DBAccess.getDBA().getConnection().createStatement();
        stmt.executeUpdate(updateStmt, Statement.RETURN_GENERATED_KEYS);
        ResultSet generatedKeys = stmt.getGeneratedKeys();
        int id = -1;
        if (generatedKeys.next()) {
            id = generatedKeys.getInt(1);
        }
        return id;
    }
    public static String toString(Calendar cal) {
        String s;
        s = "Calendar_id: " +cal.getCalendarId() + ", Year: " +cal.getYear()+ ", Month: " +cal.getMonth()+ ", Event_ids: " +cal.getEventIds();
        return s;
    }

    public static Calendar generateTestingCalendar(int cid) {
        Calendar cal = new Calendar();
        cal.setCalendarId(0);
        cal.setEventIds(Commons.convertStringToList("0 1 2"));
        cal.setYear(2017);
        cal.setMonth(8);
        calMap.put(0, cal);

        Calendar cal1 = new Calendar();
        cal1.setCalendarId(1);
        cal1.setEventIds(Commons.convertStringToList("3 4 5 6"));
        cal1.setYear(2017);
        cal1.setMonth(8);
        calMap.put(1, cal1);

        Calendar cal2 = new Calendar();
        cal2.setCalendarId(2);
        cal2.setEventIds(Commons.convertStringToList("7"));
        cal2.setYear(2017);
        cal2.setMonth(8);
        calMap.put(2, cal2);

        Calendar cal3 = new Calendar();
        cal3.setCalendarId(3);
        cal3.setEventIds(Commons.convertStringToList("8 9 10 13 51"));
        cal3.setYear(2017);
        cal3.setMonth(8);
        calMap.put(3, cal3);
        return calMap.get(cid);
    }

}
