package pingproject.pingapp.CoreClasses;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by SuperEEJ on 10/1/2016.
 */

public class History {
    private int HistoryId;
    private int EventId;
    private int UserId;
    private Calendar DateTime;
    private List<Double> PingedUsers;

    public History() {
        PingedUsers = new ArrayList<Double>();
    }

    /////////////////////////////Setter Methods////////////////////////////

    public void setHistoryId(int id) {
        HistoryId = id;
    }

    public void setEventId(int id) {
        EventId = id;
    }

    public void setUserId(int id) {
        UserId = id;
    }

    ///////////////////////////Getter Methods////////////////////////////

    public int getHistoryId() {
        return HistoryId;
    }

    public int getEventId() {
        return EventId;
    }

    public int getUserId() {
        return UserId;
    }

    public Calendar getDateTime() {
        return DateTime;
    }

    public void addPing(Double ping) {
        PingedUsers.add(ping);
    }

    public List<Double> getPingedUsers() {
        return PingedUsers;
    }
}
