/**
 * TODO Write a one-sentence summary of your class here.
 * TODO Follow it with additional details about its purpose, what abstraction
 * it represents, and how to use it.
 *
 * @author  TODO Your Name
 * @version Sep 23, 2025
 */
package TimeClasses;
public class Time {

    private int hour;
    private int minute;
    private ClockType clockType; 
    private HourPeriod hourPeriod; 

    
    public Time() {
        this.hour = 0;
        this.minute = 0;
        this.clockType = ClockType.TWENTY_FOUR_HOUR;
        this.hourPeriod = null;
    }

    
    public Time(int hour, int minute) {
        this.clockType = ClockType.TWENTY_FOUR_HOUR;
        setHour(hour);
        setMinute(minute);
        this.hourPeriod = null;
    }

    // 12-hour constructor
    public Time(int hour, int minute, HourPeriod hourPeriod) {
        this.clockType = ClockType.TWELVE_HOUR;
        setHour(hour);
        setMinute(minute);
        setHourPeriod(hourPeriod);
    }

    // Full constructor
    public Time(int hour, int minute, ClockType clockType, HourPeriod hourPeriod) {
        this.clockType = clockType;
        setHour(hour);
        setMinute(minute);
        if (clockType == ClockType.TWELVE_HOUR) {
            setHourPeriod(hourPeriod);
        } else {
            this.hourPeriod = null;
        }
    }

    // Copy constructor
    public Time(Time other) {
        this.hour = other.hour;
        this.minute = other.minute;
        this.clockType = other.clockType;
        this.hourPeriod = other.hourPeriod;
    }

    // Getters
    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public ClockType getClockType() {
        return clockType;
    }

    public HourPeriod getHourPeriod() {
        return hourPeriod;
    }

    // Setters
    public void setHour(int hour) {
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

    public void setMinute(int minute) {
        if (minute < 0 || minute > 59) {
            throw new IllegalArgumentException("Minute must be 0-59");
        }
        this.minute = minute;
    }

    public void setClockType(ClockType clockType) {
        this.clockType = clockType;
        // Reset hourPeriod if switching to 24-hour
        if (clockType == ClockType.TWENTY_FOUR_HOUR) {
            this.hourPeriod = null;
        }
    }

    public void setHourPeriod(HourPeriod hourPeriod) {
        if (clockType != ClockType.TWELVE_HOUR) {
            throw new IllegalStateException("HourPeriod only applies to 12-hour clock");
        }
        if (hourPeriod == null) {
            throw new IllegalArgumentException("HourPeriod cannot be null for 12-hour clock");
        }
        this.hourPeriod = hourPeriod;
    }

    @Override
    public String toString() {
        if (clockType == ClockType.TWELVE_HOUR) {
            return String.format("%02d:%02d %s", hour, minute, hourPeriod);
        } else {
            return String.format("%02d:%02d", hour, minute);
        }
    }
}
