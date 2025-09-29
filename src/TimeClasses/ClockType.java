/**
 * Enumeration representing different time display formats.
 * Used by the Time class to determine whether time should be displayed
 * in 12-hour format (with AM/PM) or 24-hour format (military time).
 *
 * @author  Jed Duncan
 * @version Sep 29, 2025
 */
package TimeClasses;
public enum ClockType {
    TWELVE_HOUR,     //12-hour format with AM/PM (1:00 PM, 11:30 AM)
    TWENTY_FOUR_HOUR; //24-hour military format (13:00, 23:30)
}
