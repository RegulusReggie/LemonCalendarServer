package Entity;

import Controller.EventFactory;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Calendar implements Serializable {
    private static final long serialVersionUID = 1L;
    private IntegerProperty calendarid;
    private List<Integer> eventids;
    private IntegerProperty year;
    private IntegerProperty month;
    private Map<Integer, List<Event>> eventsMap;

    public Calendar() {
        this.calendarid = new SimpleIntegerProperty();
        this.eventids = new ArrayList<>();
        this.year = new SimpleIntegerProperty();
        this.month = new SimpleIntegerProperty();
        this.eventsMap = new HashMap<>();
    }

    //calendarid
    public int getCalendarId() {
        return calendarid.get();
    }
    public void setCalendarId(int calendarId) {
        this.calendarid.set(calendarId);
    }
    public IntegerProperty calendarIdProperty() {
        return calendarid;
    }

    //eventid
    public List<Integer> getEventIds() {
        return eventids;
    }
    public void setEventIds(List<Integer> eventId) {
        eventids = eventId;
        for (int id : eventId) {
            Event e;
            try {
                e = EventFactory.searchEventByEID(id);
                addEvent(e);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    //year
    public int getYear() {
        return year.get();
    }
    public void setYear(int year) {
        this.year.set(year);
    }
    public IntegerProperty yearProperty() {
        return year;
    }

    //month
    public int getMonth() {
        return month.get();
    }
    public void setMonth(int month) {
        this.month.set(month);
    }
    public IntegerProperty monthProperty() {
        return month;
    }

    public void addEvent(Event eve) {
        List<Event> eveList = eventsMap.get(eve.getDay());
        if (eveList == null) {
            eveList = new ArrayList<>();
            eveList.add(eve);
            eventsMap.put(eve.getDay(), eveList);
        } else {
            eveList.add(eve);
        }
    }

    public List<Event> getEventListByDay(int day) {
        return eventsMap.get(day);
    }
}