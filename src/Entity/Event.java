package Entity;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Event {
    private IntegerProperty eid;
    private IntegerProperty cal_id;
    private IntegerProperty year;
    private IntegerProperty month;
    private IntegerProperty day;
    private StringProperty description;

    public Event() {
        this.eid = new SimpleIntegerProperty();
        this.year = new SimpleIntegerProperty();
        this.month = new SimpleIntegerProperty();
        this.day = new SimpleIntegerProperty();
        this.description = new SimpleStringProperty();
        this.cal_id = new SimpleIntegerProperty();
    }
    public String getEventDate() {
        return String.format("%d-%d-%d", year, month, day);
    }
    public int getID() { return eid.get(); }
    public void setID(int id) { this.eid.set(id); }
    public int getYear() { return year.get(); }
    public void setYear(int year) { this.year.set(year); }
    public int getMonth() { return this.month.get(); }
    public void setMonth(int month) {this.month.set(month); }
    public int getDay() { return this.day.get(); }
    public void setDay(int day) { this.day.set(day); }
    public String getDescription() { return this.description.get(); }
    public void setDescription(String description) { this.description.set(description); }
    public int getCalID() { return this.cal_id.get(); }
    public void setCalID(int id) { this.cal_id.set(id); }
}
