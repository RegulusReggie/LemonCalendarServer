package Entity;

import Util.Commons;
import Util.JSONObject;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.List;

public class Group {
    private IntegerProperty groupid;
    private StringProperty groupname;
    private List<Integer> membersid;
    private IntegerProperty ownerid;

    public Group() {
        this.groupid = new SimpleIntegerProperty();
        this.groupname = new SimpleStringProperty();
        this.membersid = new ArrayList<Integer>();
        this.ownerid = new SimpleIntegerProperty();
    }
    //groupid
    public int getGroupId() {
        return groupid.get();
    }
    public void setGroupId(int groupId) {
        this.groupid.set(groupId);
    }
    public IntegerProperty groupIdProperty() {
        return groupid;
    }

    //groupname
    public String getGroupName() {
        return groupname.get();
    }
    public void setGroupName(String groupName) {
        this.groupname.set(groupName);
    }

    //membersid
    public List<Integer> getMembersId() {
        return membersid;
    }
    public void setMembersId(List<Integer> membersId) {
        membersid = membersId;
    }

    //ownersid
    public int getOwnerId() {
        return ownerid.get();
    }
    public void setOwnerId(int ownerId) {
        this.ownerid.set(ownerId);
    }
    public IntegerProperty ownerIdProperty() {
        return ownerid;
    }

    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        obj.putField(Commons.GROUP_ID, String.valueOf(getGroupId()));
        obj.putField(Commons.GROUPNAME, getGroupName());
        obj.putField(Commons.MEMBERS_ID, String.valueOf(Commons.convertListToString(getMembersId())));
        obj.putField(Commons.OWNERS_ID, String.valueOf(getOwnerId()));
        return obj;
    }
}