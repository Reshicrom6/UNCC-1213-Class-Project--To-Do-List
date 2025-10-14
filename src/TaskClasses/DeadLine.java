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
    public String toString() {
        return date.toString() + " " + time.toString();
    }
    
    public static DeadLine parse(String text) {
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException("Input text cannot be null or empty");
        }

        text = text.trim();
        
        // Find the first space to separate date from time
        int spaceIndex = text.indexOf(' ');
        if (spaceIndex == -1) {
            throw new IllegalArgumentException("Input text must contain a date and time separated by space");
        }

        try {
            // Parse date part (everything before first space)
            String datePart = text.substring(0, spaceIndex);
            Date date = Date.parse(datePart);
            
            // Parse time part (everything after first space - handles "HH:MM" or "HH:MM AM/PM")
            String timePart = text.substring(spaceIndex + 1);
            Time time = Time.parse(timePart);
            
            return new DeadLine(date, time);
            
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to parse deadline: " + e.getMessage(), e);
        }
    }
    
}
