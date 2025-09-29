/**
 * Enumeration of predefined task categories available in the to-do list system.
 * Provides standard categories for organizing tasks, with OTHER category
 * allowing for custom user-defined categories through the Category class.
 *
 * @author  Jed Duncan
 * @version Sep 29, 2025
 */
package TaskClasses;
public enum TaskCategory {
    SCHOOL,     //academic or educational tasks
    WORK,       //professional or job-related tasks
    APPOINTMENT,//scheduled meetings or appointments
    FAMILY,     //family-related activities and responsibilities
    EVENT,      //special events or occasions
    CHORE,      //household or maintenance tasks
    OTHER       //custom user-defined category (requires custom text)
}
