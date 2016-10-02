package pingproject.pingapp.CoreClasses;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

/**
 * Created by SuperEEJ on 10/1/2016.
 */

public class User {
    private String UserId;
    private String Username;
    private String Password;
    private String Fullname;
    private Location CurrentLoc;
    private List<Group> GroupList;
    private List<History> PingHistory;
    private List<HashMap<String, Integer>> FriendsList;

    public User() {
        GroupList = new ArrayList<Group>();
        PingHistory = new ArrayList<History>();
        FriendsList = new ArrayList<HashMap<String, Integer>>();
    }

    /////////////////////////////Setter Methods////////////////////////////

    public void setUsername (String name) {

        //need to find a way to check if name is already in database
        //return false if name already in database
        Username = name;
    }

    public void setPassword (String password) {
        Password = password;
    }

    public boolean setUserId (String userId) {
        UserId = userId;
        return true;
    }

    public void setFullname (String name) {
        Fullname = name;
    }

    public void setGroupList(List<Group> updatedGroupList) {
        GroupList = updatedGroupList;
    }

    public void setPingHistory(List<History> updatedPingHistory) {
        PingHistory = updatedPingHistory;
    }

    public void setFriendsList(List<HashMap<String, Integer>> updatedFriends) {
        FriendsList = updatedFriends;
    }

    public void addFriend(String friendName, int status) {
        HashMap<String, Integer> friendPair = new HashMap<String, Integer>();
        friendPair.put(friendName, status);
        FriendsList.add(friendPair);
    }

    public void setLocation(double latitude, double longitude) {
        CurrentLoc = new Location(latitude, longitude);
    }

    ///////////////////////////Getter Methods////////////////////////////

    public String getUser() {
        return Username;
    }

    public String getUserId() {
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

    public List<Group> getGroupList() {
        return GroupList;
    }

    public List<History> getPingHistory() {
        return PingHistory;
    }

    public List<HashMap<String, Integer>> getFriendsList() {
        return FriendsList;
    }

}
