package Controller;

import Util.DBAccess;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GroupToUserDB {

    private GroupToUserDB() {}

    public static void insertG2U (int gid, int uid) throws SQLException, ClassNotFoundException {
        String updateStmt =
                "INSERT INTO `lemoncalendar`.`GROUPTOUSER`\n" +
                        "(`GROUP_ID`, `USER_ID`)\n" +
                        "VALUES\n" +
                        "(" + gid +", " + uid + ");";

        DBAccess.getDBA().executeUpdate(updateStmt);
    }

    public static List<Integer> getGroupsByUserId(int uid) {
        String selectStmt = "SELECT * FROM grouptouser WHERE user_id =" + uid + ";";
        List<Integer> gids = new ArrayList<>();

        try {
            ResultSet rs = DBAccess.getDBA().executeQuery(selectStmt);
            while (rs.next()) {
                gids.add(rs.getInt("GROUP_ID"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gids;
    }
}