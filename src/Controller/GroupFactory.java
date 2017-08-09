package Controller;

import Entity.Group;
import Util.Commons;
import Util.DBAccess;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class GroupFactory {

    public static Group searchGroup (int gpid) throws SQLException, ClassNotFoundException {
        String selectStmt = "SELECT * FROM LEMONCALENDAR.GROUP WHERE GROUP_ID = " +gpid+ ";";

        try {
            ResultSet rsGp = DBAccess.getDBA().executeQuery(selectStmt);
            return getGroupFromResultSet(rsGp);
        } catch (SQLException e) {
            System.out.println("While searching a group with " + gpid + " id, an error occurred: " + e);
            throw e;
        }
    }

    public static Group searchGroup (String name) throws SQLException, ClassNotFoundException {
        String selectStmt = "SELECT * FROM LEMONCALENDAR.GROUP WHERE GROUPNAME = '" + name + "';";

        try {
            ResultSet rsGp = DBAccess.getDBA().executeQuery(selectStmt);
            return getGroupFromResultSet(rsGp);
        } catch (SQLException e) {
            System.out.println("While searching a group with " + name + " groupname, an error occurred: " + e);
            throw e;
        }
    }

    private static Group getGroupFromResultSet(ResultSet rs) throws SQLException {
        Group gp = null;
        if (rs.next()) {
            gp = new Group();
            gp.setGroupId(rs.getInt("GROUP_ID"));
            gp.setGroupName(rs.getString("GROUPNAME"));
            gp.setMembersId(Commons.convertStringToList(rs.getString("MEMBERS_ID")));
            gp.setOwnerId(rs.getInt("OWNER_ID"));
        }
        return gp;
    }

    public static ObservableList<Group> searchGroups () throws SQLException, ClassNotFoundException {
        String selectStmt = "SELECT * FROM LEMONCALENDAR.GROUP";

        try {
            ResultSet rsgp = DBAccess.getDBA().executeQuery(selectStmt);

            ObservableList<Group> gpList = (ObservableList<Group>) getGroupFromResultSet(rsgp);

            return gpList;
        } catch (SQLException e) {
            System.out.println("SQL select operation has been failed: " +e);

            throw e;
        }
    }

    private static ObservableList<Group> getGroupList(ResultSet rs) throws SQLException, ClassNotFoundException {
        ObservableList<Group> gpList = FXCollections.observableArrayList();

        while (rs.next()) {
            Group gp = new Group();
            gp.setGroupId(rs.getInt("GROUP_ID"));
            gp.setGroupName(rs.getString("GROUPNAME"));
            gp.setMembersId(Commons.convertStringToList(rs.getString("MEMBERS_ID")));
            gp.setOwnerId(rs.getInt("OWNER_ID"));
        }
        return gpList;
    }

    public static void updateGpMember (int gpId, List<Integer> memberID) throws SQLException, ClassNotFoundException {
        String updateStmt =
                "UPDATE LEMONCALENDAR.GROUP" +
                        "   SET MEMBERS_ID = '" + Commons.convertListToString(memberID)+ "' WHERE GROUP_ID = " + gpId + ";";

        DBAccess.getDBA().executeUpdate(updateStmt);
    }

    public static int insertGp (String name, List<Integer> members, int owner) throws SQLException, ClassNotFoundException {
        String updateStmt =
                "INSERT INTO LEMONCALENDAR.GROUP" + "(GROUPNAME, MEMBERS_ID, OWNER_ID)" +
                        "VALUES" +
                        "('"+name+"', '"+ Commons.convertListToString(members)+"', '"
                        + owner + "');";

        Statement stmt = DBAccess.getDBA().getConnection().createStatement();
        stmt.executeUpdate(updateStmt, Statement.RETURN_GENERATED_KEYS);
        ResultSet generatedKeys = stmt.getGeneratedKeys();
        int id = -1;
        if (generatedKeys.next()) {
            id = generatedKeys.getInt(1);
        }
        return id;
    }
}
