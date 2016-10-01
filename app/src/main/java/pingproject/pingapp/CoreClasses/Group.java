package pingproject.pingapp.CoreClasses;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SuperEEJ on 10/1/2016.
 */

public class Group {

    private int GroupId;
    private String GroupName;
    private String UserId;
    private String Fullname;
    private List<User> GroupMembers;
    private List<User> PingableMembers;

    public Group() {
        GroupMembers = new ArrayList<User>();
        PingableMembers = new ArrayList<User>();
    }

    /////////////////////////////Setter Methods////////////////////////////

    public void setGroupId(int groupId) {
        GroupId = groupId;
    }

    public void setGroupName(String groupName) {
        GroupName = groupName;
    }

    public void setFullname(String name){
        Fullname = name;
    }

    public void addGroupMember(User newMember) {
        GroupMembers.add(newMember);
    }

    public void setPingableMembers(User newFriend) {
        PingableMembers.add(newFriend);
    }

    ///////////////////////////Getter Methods////////////////////////////

    public int getGroupId() {
        return GroupId;
    }

    public String getGroupName() {
        return GroupName;
    }

    public String getUserId() {
        return UserId;
    }

    public String getFullname() {
        return Fullname;
    }

    public List<User> getGroupMembers() {
        return GroupMembers;
    }

    public List<User> getPingableMembers() {
        return PingableMembers;
    }
}
