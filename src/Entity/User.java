package Entity;
import Util.Commons;
import Util.JSONObject;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.io.Serializable;

public class User implements Serializable{
    private IntegerProperty uid;
    private String username;
    private String password;
    public User() {
        this.uid = new SimpleIntegerProperty();
        this.username = new String();
        this.password = new String();
    }

    // uid
    public int getUserId() {
        return uid.get();
    }
    public void setUserId(int userId) {
        this.uid.set(userId);
    }
    public IntegerProperty userIdProperty() {
        return uid;
    }

    // username
    public String getUserName() {
        return username;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    // password
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        obj.putField(Commons.GROUP_ID, String.valueOf(getUserId()));
        obj.putField(Commons.GROUPNAME, getUserName());
        obj.putField(Commons.GROUPNAME, getPassword());
        return obj;
    }
}
