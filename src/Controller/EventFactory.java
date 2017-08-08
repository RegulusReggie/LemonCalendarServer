package Controller;

import Entity.Event;
import Util.DBAccess;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class EventFactory {
    private EventFactory() {}

    public static Event getEventById(int id, int year, int month, int cid) {
        Random rand = new Random();
        Event e = new Event();
        e.setCalID(cid);
        e.setDay(rand.nextInt(28) + 1);
        e.setDescription(String.valueOf(id));
        e.setMonth(month);
        e.setYear(year);
        e.setID(id);
        return e;
    }

    public static Event searchEventByEID (int eid) throws SQLException, ClassNotFoundException {
        String selectStmt = "SELECT * FROM event WHERE EVENT_ID="+eid;

        try {
            ResultSet rsCal = DBAccess.getDBA().executeQuery(selectStmt);
            return getEventFromResultSetSingle(rsCal);
        } catch (SQLException e) {
            System.out.println("While searching a event with " + eid + " id, an error occurred: " +e);
            throw e;
        }
    }
    private static ObservableList<Event> getEventFromResultSet(ResultSet rs) throws SQLException {
        ObservableList<Event> evelist = FXCollections.observableArrayList();
        while (rs.next()) {
            Event eve = new Event();
            eve.setID(rs.getInt("EVENT_ID"));
            eve.setYear(rs.getInt("YEAR"));
            eve.setMonth(rs.getInt("MONTH"));
            eve.setDay(rs.getInt("DAY"));
            eve.setDescription(rs.getString("DESCRIPTION"));
            eve.setCalID(rs.getInt("CALENDAR_ID"));
            evelist.add(eve);
        }
        return evelist;
    }
    private static Event getEventFromResultSetSingle(ResultSet rs) throws SQLException {
        Event eve = new Event();
        if (rs.next()) {
            eve.setID(rs.getInt("EVENT_ID"));
            eve.setYear(rs.getInt("YEAR"));
            eve.setMonth(rs.getInt("MONTH"));
            eve.setDay(rs.getInt("DAY"));
            eve.setDescription(rs.getString("DESCRIPTION"));
            eve.setCalID(rs.getInt("CALENDAR_ID"));
        }
        return eve;
    }
    public static Event searchEventByDate (int year, int month, int day) throws SQLException, ClassNotFoundException {
        String selectStmt = "SELECT * FROM EVENT WHERE YEAR="+year + " AND MONTH=" + month + " AND DAY=" +day+ ";";

        try {
            ResultSet rsCals = DBAccess.getDBA().executeQuery(selectStmt);
            return getEventFromResultSetSingle(rsCals);
        } catch (SQLException e) {
            System.out.println(String.format("While searching a event with date %d-%d-%d, an error occurred: ",year, month,day) +e);
            throw e;
        }
    }

    public static ObservableList<Event> searchEventByCID (int cid) throws SQLException, ClassNotFoundException {
        String selectStmt = "SELECT * FROM EVENT WHERE CALENDAR_ID="+cid;

        try {
            ResultSet rsCals = DBAccess.getDBA().executeQuery(selectStmt);
            return (ObservableList<Event>) getEventFromResultSet(rsCals);
        } catch (SQLException e) {
            System.out.println(String.format("While searching a event with calendar_id %d, an error occurred: ",cid) +e);
            throw e;
        }
    }

    public static void updateEvent (int eid, int y, int m, int d, String desc) throws SQLException, ClassNotFoundException {
        String updateStmt =
                "UPDATE EVENT SET YEAR = " +y+ ", MONTH = " +m+ ", DAY =" +d+ ", DESCRIPTION = '" + desc + "'" + " WHERE EVENT_ID = " +eid+ ";";

        DBAccess.getDBA().executeUpdate(updateStmt);
    }

    public static void deleteEventWithId (int eid) throws SQLException, ClassNotFoundException {
        String updateStmt =
                "DELETE FROM EVENT " +
                        "       WHERE EVENT_ID =" + eid +"; ";
        DBAccess.getDBA().executeUpdate(updateStmt);
    }

    public static void insertEvent (int year, int month, int day, String description, int cid) throws SQLException, ClassNotFoundException {
        String updateStmt =
                "INSERT INTO EVENT " +
                        "(YEAR, MONTH, DAY, DESCRIPTION, CALENDAR_ID) " +
                        "VALUES " +
                        "("+year+", "+month+", " + day + ", '"+description + "', "+ cid +"); ";

        DBAccess.getDBA().executeUpdate(updateStmt);
    }
}
