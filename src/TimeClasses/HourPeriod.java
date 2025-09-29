/**
 * Enumeration representing AM/PM periods for 12-hour time format.
 * Used by the Time class when ClockType is set to TWELVE_HOUR
 * to indicate whether the time is before noon (AM) or after noon (PM).
 *
 * @author  Jed Duncan
 * @version Sep 29, 2025
 */
package TimeClasses;
public enum HourPeriod {
    AM, //ante meridiem (before noon) - 12:00 AM to 11:59 AM
    PM; //post meridiem (after noon) - 12:00 PM to 11:59 PM
}