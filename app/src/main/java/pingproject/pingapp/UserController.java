package pingproject.pingapp;
import android.provider.ContactsContract;
import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import pingproject.pingapp.CoreClasses.*;
/**
 * Created by SuperEEJ on 10/1/2016.
 */

public class UserController extends android.app.Application{

    static boolean nameExists = false;


    @Override public void onCreate()
    {
        super.onCreate();
        Firebase.setAndroidContext(this);     // other setup code
    }

    /////////////////////////////////////Post Methods///////////////////////////////////////////

    public static boolean createNewUser(final String username, final String password, final String fullname) {

        final Firebase myFirebaseRef = new Firebase("https://pingapp-c427f.firebaseio.com/");
        boolean temp;

        //check if the user is already in the databse
        final Firebase userRef = myFirebaseRef.child("User");

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildren() != null) {
                    for (DataSnapshot snapChild : dataSnapshot.getChildren()) {

                        if (username.equalsIgnoreCase((String)snapChild.child("Username").getValue())) {
                            Log.e("Truerrr", "THey're equal");
                            nameExists = true;
                        }
                    }
                    Log.e("LoopCheck", "ExitsLoop");
                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }

        });
        temp = nameExists;
        Log.e("VALUEEEEEEEEEEE", Boolean.toString(nameExists));
        if (temp == false) {

            User newUserObj = new User();
            newUserObj.Username = username;
            newUserObj.Password = password;
            newUserObj.Fullname = fullname;

            Map<Object, Object> newUserMap = new HashMap<Object, Object>();
            newUserMap.put("UserObj", newUserObj);
            newUserMap.put("Username", username);
            userRef.push().setValue(newUserMap);
        }

        nameExists = false;
        return !(temp);
    }

    public static boolean addFriend (final String selfUsername, final String selfId, final String friendUsername){

        final Firebase myFirebaseRef = new Firebase("https://pingapp-c427f.firebaseio.com/");
        final boolean[] returnStatement = {false};

        final Firebase userRef = myFirebaseRef.child("User");

        final User self = new User();

        //Getting a reference to the current user
        final Firebase selfRef = userRef.child(selfId).child("UserObj");
        selfRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //snapshot is at "UserObj"
                User refSelf = (User) dataSnapshot.getValue();
                self.setFullname(refSelf.getFullname());
                self.setUsername(refSelf.getUser());
                self.setUserId(selfId);
                HashMap<String, Integer> selfPair = new HashMap<String, Integer>();
                selfPair.put(friendUsername, 0);
                refSelf.getFriendsList().add(selfPair);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        //Getting a reference to the friend
        userRef.addValueEventListener((new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot childSnap: dataSnapshot.getChildren()) {

                    //check if the friend exists in the database
                    //childSnap is at "Id"
                    if (childSnap.child("Username").getValue() == friendUsername) {
                        String uniqueId = childSnap.getKey();

                        //gets a reference to the friend's pending friends list
                        final Firebase friendRef = userRef.child(uniqueId).child("UserObj");

                        friendRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                //snapshot is at "UserObj"
                                User pendingUser = (User) dataSnapshot.getValue();
                                List<HashMap<String, Integer>> friendsList = (List<HashMap<String, Integer>>) pendingUser.getFriendsList();
                                HashMap<String, Integer> selfPair = new HashMap<String, Integer>();
                                selfPair.put(selfUsername, 1);
                                friendsList.add(selfPair);
                                friendRef.setValue(pendingUser);
                                returnStatement[0] = true;
                            }

                            @Override
                            public void onCancelled(FirebaseError firebaseError) {

                            }
                        });
                    }
                    else {
                        returnStatement[0] = false;
                    }
                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }

        }));
        return returnStatement[0];
    }

    public static void acceptFriend(final String selfUsername, final String selfId, final String friendUsername) {
        final Firebase myFirebaseRef = new Firebase("https://pingapp-c427f.firebaseio.com/");
        final boolean[] returnStatement = {false};

        final Firebase userRef = myFirebaseRef.child("User");

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //snapshot at users
                for (DataSnapshot snapChild : dataSnapshot.getChildren()) {
                    if (snapChild.getValue() == selfId) {
                        User selfUser = (User) snapChild.child("UserObj").getValue();
                        List<HashMap<String, Integer>> pendingSelfFriends = selfUser.getFriendsList();
                        for (int i = 0; i < pendingSelfFriends.size(); i++) {
                            for (String key : pendingSelfFriends.get(i).keySet()) {
                                if (key == friendUsername) {
                                    pendingSelfFriends.get(i).remove(key);
                                    pendingSelfFriends.get(i).put(friendUsername, 2);
                                }
                            }
                        }
                    }
                }
                //Get a reference to the friend
                for(DataSnapshot childSnap: dataSnapshot.getChildren()) {
                    //snapshot is at "Id"
                    if (childSnap.child("Username").getValue() == friendUsername) {
                        String uniqueId = childSnap.getKey();

                        final Firebase friendRef = userRef.child(uniqueId).child("UserObj");
                        friendRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                //snapshot in UserObj
                                User friend = (User) dataSnapshot.getValue();
                                List<HashMap<String, Integer>> friendsList = (List<HashMap<String, Integer>>) friend.getFriendsList();
                                HashMap<String, Integer> selfPair = new HashMap<String, Integer>();
                                selfPair.put(selfUsername, 2);
                                friendsList.add(selfPair);
                                friendRef.setValue(friend);
                            }

                            @Override
                            public void onCancelled(FirebaseError firebaseError) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }//method bracket

    /////////////////////////////////Get Methods//////////////////////////////////////

    public static User getUserInformation(final String username) {
        final Firebase myFirebaseRef = new Firebase("https://pingapp-c427f.firebaseio.com/");

        final User self = new User();

        final Firebase userRef = myFirebaseRef.child("Users");

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot childSnap: dataSnapshot.getChildren()) {
                    if (childSnap.child("Username").getValue() == username) {
                        String uniqueId = childSnap.getKey();
                        User temp = (User)childSnap.child("UserObj").getValue();

                        self.setUsername(temp.getUser());
                        self.setFullname(temp.getFullname());
                        self.setFriendsList(temp.getFriendsList());
                        self.setGroupList(temp.getGroupList());
                        self.setPingHistory(temp.getPingHistory());
                        self.setLocation(temp.getLocation().getLat(), temp.getLocation().getLong());
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        return self;
    }
}
