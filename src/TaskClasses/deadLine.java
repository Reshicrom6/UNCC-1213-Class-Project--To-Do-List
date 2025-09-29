/**
 * Represents a deadline combining both date and time information for tasks.
 * This class encapsulates a specific point in time when a task should be completed,
 * providing a convenient way to handle deadline-related operations.
 *
 * @author  Jed Duncan
 * @version Sep 29, 2025
 */
package TaskClasses;

import DateClasses.Date;
import TimeClasses.*;

public class DeadLine {
    //fields
    private Time time; //the time component of the deadline
    private Date date; //the date component of the deadline

    //constructors
    public DeadLine() { //default constructor - creates deadline with current default date and time
        this.date = new Date();
        this.time = new Time();
    }

    public DeadLine(Date date, Time time) { //parameterized constructor - creates deadline with specified date and time
        this.date = new Date(date); //create defensive copy
        this.time = new Time(time); //create defensive copy
    }

    public DeadLine(DeadLine other) { //copy constructor - creates deep copy of another deadline
        this.date = new Date(other.date);
        this.time = new Time(other.time);
    }

    // Getters
    public Date getDate() {
        return date;
    }

    public Time getTime() {
        return time;
    }

    // Setters
    public void setDate(Date date) {
        this.date = new Date(date);
    }

    public void setTime(Time time) {
        this.time = new Time(time);
    }

    //toString method
    @Override
    public String toString() {
        return date.toString() + " " + time.toString();
    }
}
