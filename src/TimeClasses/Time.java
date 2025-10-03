/**
 * Represents a time of day with support for both 12-hour and 24-hour formats.
 * This class handles time validation, format conversion, and provides flexible
 * time representation with AM/PM support for 12-hour format.
 *
 * @author  Jed Duncan
 * @version Sep 29, 2025
 */
package TimeClasses;
public class Time {
    //fields
    private int hour;              //hour component (0-23 for 24-hour, 1-12 for 12-hour)
    private int minute;            //minute component (0-59)
    private ClockType clockType;   //determines 12-hour or 24-hour format
    private HourPeriod hourPeriod; //AM/PM indicator (only used for 12-hour format)

    //constructors
    public Time() { //default constructor - creates time as 00:00 in 24-hour format
        this.hour = 0;
        this.minute = 0;
        this.clockType = ClockType.TWENTY_FOUR_HOUR;
        this.hourPeriod = null;
    }

    public Time(int hour, int minute) { //parameterized constructor for 24-hour format
        this.clockType = ClockType.TWENTY_FOUR_HOUR;
        setHour(hour); //validate hour for 24-hour format
        setMinute(minute); //validate minute
        this.hourPeriod = null;
    }

    public Time(int hour, int minute, HourPeriod hourPeriod) { //constructor for 12-hour format with AM/PM
        this.clockType = ClockType.TWELVE_HOUR;
        setHour(hour); //validate hour for 12-hour format
        setMinute(minute); //validate minute
        setHourPeriod(hourPeriod);
    }

    public Time(int hour, int minute, ClockType clockType, HourPeriod hourPeriod) { //full constructor with all parameters
        this.clockType = clockType;
        setHour(hour); //validate hour based on clock type
        setMinute(minute); //validate minute
        if (clockType == ClockType.TWELVE_HOUR) {
            setHourPeriod(hourPeriod); //set AM/PM for 12-hour format
        } else {
            this.hourPeriod = null; //no AM/PM for 24-hour format
        }
    }

    public Time(Time other) { //copy constructor - creates deep copy of another time
        this.hour = other.hour;
        this.minute = other.minute;
        this.clockType = other.clockType;
        this.hourPeriod = other.hourPeriod;
    }

    //getters
    public int getHour() { //returns the hour component
        return hour;
    }

    public int getMinute() { //returns the minute component
        return minute;
    }

    public ClockType getClockType() { //returns the clock format type (12 or 24 hour)
        return clockType;
    }

    public HourPeriod getHourPeriod() { //returns AM/PM indicator (null for 24-hour format)
        return hourPeriod;
    }

    //setters
    public void setHour(int hour) { //sets the hour with validation based on clock type
        if (clockType == ClockType.TWELVE_HOUR) {
            if (hour < 1 || hour > 12) {
                throw new IllegalArgumentException("Hour must be 1-12 for 12-hour clock");
            }
        } else {
            if (hour < 0 || hour > 23) {
                throw new IllegalArgumentException("Hour must be 0-23 for 24-hour clock");
            }
        }
        this.hour = hour;
    }

    public void setMinute(int minute) { //sets the minute with validation
        if (minute < 0 || minute > 59) {
            throw new IllegalArgumentException("Minute must be 0-59");
        }
        this.minute = minute;
    }

    public void setClockType(ClockType clockType) { //sets the clock format type
        this.clockType = clockType;
        //reset hourPeriod if switching to 24-hour format
        if (clockType == ClockType.TWENTY_FOUR_HOUR) {
            this.hourPeriod = null;
        }
    }

    public void setHourPeriod(HourPeriod hourPeriod) { //sets AM/PM indicator (only for 12-hour format)
        if (clockType != ClockType.TWELVE_HOUR) {
            throw new IllegalStateException("HourPeriod only applies to 12-hour clock");
        }
        if (hourPeriod == null) {
            throw new IllegalArgumentException("HourPeriod cannot be null for 12-hour clock");
        }
        this.hourPeriod = hourPeriod;
    }

    //toString method - returns formatted time string based on clock type
    public String toString() {
        if (clockType == ClockType.TWELVE_HOUR) {
            return String.format("%02d:%02d %s", hour, minute, hourPeriod); //12-hour format with AM/PM
        } else {
            return String.format("%02d:%02d", hour, minute); //24-hour format
        }
    }

    public static Time parse(String text) {
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException("Text cannot be null or empty");
        }

        text = text.trim();
        
        //Format for 12 hour clock HH:MM AM|PM
        if (text.matches("\\d{2}:\\d{2} (AM|PM)")) {
            String[] mainParts = text.split(" ");
            String[] timeParts = mainParts[0].split(":");
            String periodString = mainParts[1]; // Fixed: was mainParts[0]

            int hours = Integer.parseInt(timeParts[0]);
            int minutes = Integer.parseInt(timeParts[1]);
            

            if (hours < 1 || hours > 12) { // Fixed: changed from < 0 to < 1 for 12-hour format
                throw new IllegalArgumentException("Hours out of range for 12-hour format: " + hours);
            }
            if (minutes < 0 || minutes > 59) {
                throw new IllegalArgumentException("Minutes out of range: " + minutes);
            }
            HourPeriod hourPeriod = periodString.equals("AM") ? HourPeriod.AM : HourPeriod.PM;
            return new Time(hours, minutes, hourPeriod);
        }
        //Format for 24 hour clock HH:MM
        else if (text.matches("\\d{2}:\\d{2}")) {
            String[] parts = text.split(":");

            int hours = Integer.parseInt(parts[0]);
            int minutes = Integer.parseInt(parts[1]);

            if (hours < 0 || hours > 23) {
                throw new IllegalArgumentException("Hours out of range: " + hours);
            }
            if (minutes < 0 || minutes > 59) {
                throw new IllegalArgumentException("Minute out of range: " + minutes);
            }
            return new Time(hours, minutes);
        }
        // If neither pattern matches
        else {
            throw new IllegalArgumentException("Invalid time format. Expected HH:MM or HH:MM AM/PM: " + text);
        }
    }
}
