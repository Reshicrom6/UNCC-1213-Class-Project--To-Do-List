/**
 * TODO Write a one-sentence summary of your class here.
 * TODO Follow it with additional details about its purpose, what abstraction
 * it represents, and how to use it.
 *
 * @author  TODO Your Name
 * @version Sep 24, 2025
 */
package DateClasses;
public class Date {
    private int day;
    private int year;
    private Month month;

    // Default constructor
    public Date() {
        this.day = 1;
        this.month = Month.JANUARY;
        this.year = 2025;
    }

    // Parameterized constructor
    public Date(int day, Month month, int year) {
        this.year = year;
        this.month = month;
        setDay(day); // validates day for month/year
    }

    // Copy constructor
    public Date(Date other) {
        this.day = other.day;
        this.month = other.month;
        this.year = other.year;
    }

    // Getters
    public int getDay() {
        return day;
    }

    public Month getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    // Setters
    public void setDay(int day) {
        int maxDay;
        switch (month) {
            case FEBRUARY:
                maxDay = isLeapYear(year) ? 29 : 28;
                break;
            case APRIL: case JUNE: case SEPTEMBER: case NOVEMBER:
                maxDay = 30;
                break;
            default:
                maxDay = 31;
        }
        if (day < 1 || day > maxDay) {
            throw new IllegalArgumentException("Day must be between 1 and " + maxDay + " for month " + month);
        }
        this.day = day;

        }

        // Helper method to check leap year
        private boolean isLeapYear(int year) {
            return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    public void setMonth(Month month) {
        if (month == null) {
            throw new IllegalArgumentException("Month cannot be null");
        }
        this.month = month;
        // Optionally, re-validate the day in case month changes
        setDay(this.day);
    }

    public void setYear(int year) {
        this.year = year;
    }
    @Override
    public String toString() {
        return String.format("%04d-%02d-%02d", year, month.ordinal() + 1, day);
    }
}
