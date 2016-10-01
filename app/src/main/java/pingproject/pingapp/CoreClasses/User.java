package pingproject.pingapp.CoreClasses;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SuperEEJ on 10/1/2016.
 */

public class User {
    private int UserId;
    private String Username;
    private String Password;
    private String Fullname;
    private Location CurrentLoc;
    private List<Group> GroupList;
    private List<History> PingHistory;
    private List<User> FriendsList;

    public User() {
        GroupList = new ArrayList<Group>();
        PingHistory = new ArrayList<History>();
        FriendsList = new ArrayList<User>();
    }

    /////////////////////////////Setter Methods////////////////////////////

    public void setUsername (String name) {

        //need to find a way to check if name is already in database
        //return false if name already in database
        Username = name;
    }

    public boolean setPassword (String password) {
        Password = password;
        return true;
    }

    public boolean setUserId (int userId) {
        UserId = userId;
        return true;
    }

    public void setFullname (String name) {
        Fullname = name;
    }

    public void addFriend(User friend) {
        FriendsList.add(friend);
    }

    ///////////////////////////Getter Methods////////////////////////////

    public String getUser() {
        return Username;
    }

    public int getUserId() {
        return UserId;
    }

    public String getFullname() {
        return Fullname;
    }

    public void joinGroup( Group newGroup ) {
        GroupList.add(newGroup);
    }

    public void updatePingHistory( History newPing ) {
        PingHistory.add(newPing);
    }


    public Location getLocation() {
        return CurrentLoc;
    }

    public List<Group> getGroupMembers() {
        return GroupList;
    }

    public List<History> getPingHistory() {
        return PingHistory;
    }

    public List<User> getFriendsList() {
        return FriendsList;
    }
}
