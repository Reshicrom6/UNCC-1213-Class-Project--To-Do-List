/**
 * Represents the date of a task in the task class
 * Saves the day and year represented as integers, and the month as an enum for type safety
 *
 * @author  Jed Duncan
 * @version Sep 29, 2025
 */
package DateClasses;
public class Date {
    //fields
    private int day;   //day of the month (1-31)
    private int year;  //year (full 4-digit year)
    private Month month; //month using Month enum for type safety

    //constructors
    public Date() { //default constructor - creates date set to January 1, 2025
        this.day = 1;
        this.month = Month.JANUARY;
        this.year = 2025;
    }

    public Date(int day, Month month, int year) { //parameterized constructor - creates date with validation
        this.year = year;
        this.month = month;
        setDay(day); //validates day for the given month/year combination
    }

    public Date(Date other) { //copy constructor - creates deep copy of another date
        this.day = other.day;
        this.month = other.month;
        this.year = other.year;
    }

    //getters
    public int getDay() { //returns the day of the month
        return day;
    }

    public Month getMonth() { //returns the month as Month enum
        return month;
    }

    public int getYear() { //returns the year
        return year;
    }

    //setters
    public void setDay(int day) { //sets the day with validation for the current month/year
        int maxDay;
        switch (month) {
            case FEBRUARY:
                maxDay = isLeapYear(year) ? 29 : 28; //when isLeapYear(year) is true, maxDay = 29, when false, maxDay = 28
                break;
            case APRIL, JUNE, SEPTEMBER, NOVEMBER:
                maxDay = 30;
                break;
            default:
                maxDay = 31;
        }
        if (day < 1 || day > maxDay) 
            throw new IllegalArgumentException("Day must be between 1 and " + maxDay + " for month " + month);
        
        this.day = day;
        }

        //helper method to check if a year is a leap year
        private boolean isLeapYear(int year) {
            return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    public void setMonth(Month month) { //sets the month and re-validates the day
        if (month == null) {
            throw new IllegalArgumentException("Month cannot be null");
        }
        this.month = month;
        setDay(this.day); //re-validate day in case month changes (e.g., Jan 31 -> Feb)
    }

    public void setYear(int year) { //sets the year
        this.year = year;
    }
    
    //toString method - returns date in DD-MM-YYYY format

    public String toString() {
        return String.format("%02d-%02d-%04d", day, month.ordinal() + 1, year);
    }

    public static Date parse(String text) {
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException("Date text cannot be null or empty");
        }
        
        text = text.trim();

        if (!text.matches("\\d{2}-\\d{2}-\\d{4}")) {
            throw new IllegalArgumentException("Date format does not follow DD-MM-YYYY format: " + text);
        }
        String[] parts = text.split("-");

        try {
            int day = Integer.parseInt(parts[0]);
            int monthNumber = Integer.parseInt(parts[1]);
            int year = Integer.parseInt(parts[2]);

            if (monthNumber < 1 || monthNumber > 12) {
                throw new IllegalArgumentException("Month out of range: " + monthNumber);
            }
            
            if (year < 1) {
                throw new IllegalArgumentException("Year must be positive: " + year);
            }
            
            Month month = Month.values()[monthNumber -1];

            return new Date(day, month, year);

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid numeric values in date: " + text);
        }
    }
}