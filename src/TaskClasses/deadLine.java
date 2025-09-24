/**
 * TODO Write a one-sentence summary of your class here.
 * TODO Follow it with additional details about its purpose, what abstraction
 * it represents, and how to use it.
 *
 * @author  TODO Your Name
 * @version Sep 24, 2025
 */
package TaskClasses;

import DateClasses.Date;
import TimeClasses.*;
import java.util.*;

public class deadLine {
    private Time time;
    private Date date;

    // Default constructor
    public deadLine() {
        this.date = new Date();
        this.time = new Time();
    }

    // Parameterized constructor
    public deadLine(Date date, Time time) {
        this.date = new Date(date); // copy constructor
        this.time = new Time(time); // copy constructor
    }

    // Copy constructor
    public deadLine(deadLine other) {
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

    @Override
    public String toString() {
        return date.toString() + " " + time.toString();
    }
}
