package pingproject.pingapp;
import android.provider.ContactsContract;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Hashtable;
import java.util.Map;
import java.util.HashMap;

import pingproject.pingapp.CoreClasses.*;
/**
 * Created by SuperEEJ on 10/1/2016.
 */

public class UserController extends android.app.Application{

    @Override public void onCreate()
    {
        super.onCreate();
        Firebase.setAndroidContext(this);     // other setup code
    }

    /////////////////////////////////////Post Methods///////////////////////////////////////////

    public boolean CreateNewUser(final String username, final String password, final String fullname) {

        final Firebase myFirebaseRef = new Firebase("https://pingapp-c427f.firebaseio.com/");

        final boolean[] exist = {false};

        //check if the user is already in the databse
        final Firebase userRef = myFirebaseRef.child("User");

        userRef.addValueEventListener((new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot child: dataSnapshot.getChildren()) {

                    if (child.hasChild(username)) {
                        exist[0] = true;
                    }
                }
                if (exist[0] == false) {
                    Map<Object, Object> newUserObj = new HashMap<Object, Object>();
                    newUserObj.put("Username", username);
                    newUserObj.put("Password", password);
                    newUserObj.put("Fullname", fullname);
                    newUserObj.put("Location", null);
                    newUserObj.put("Friends", null);
                    newUserObj.put("GroupList", null);
                    newUserObj.put("PingHistory", null);
                    newUserObj.put("FriendsList", null);
                    userRef.push().setValue(newUserObj);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }

        }));

        return !(exist[0]);
    }
}
