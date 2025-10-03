/**
 * Represents a task category with support for predefined types and custom categories.
 * This class encapsulates both standard categories (from TaskCategory enum) and
 * user-defined custom categories when the type is set to OTHER.
 *
 * @author  Jed Duncan
 * @version Sep 29, 2025
 */
package TaskClasses;

public class Category {
    //fields
    private TaskCategory categoryType; //the predefined category type from TaskCategory enum
    private String customCategory = ""; //stores custom category name when categoryType is OTHER

    //constructors
    public Category() { //default constructor - sets category to EVENT
        this.categoryType = TaskCategory.EVENT;
    }

    public Category(TaskCategory categoryType) { //parameterized constructor for standard categories
        this.categoryType = categoryType;
        //custom category will be set separately if needed
    }
    
    public Category(TaskCategory categoryType, String customCategory) { //parameterized constructor with custom text - used when categoryType is OTHER
        this.categoryType = categoryType;
        this.customCategory = (categoryType == TaskCategory.OTHER) ? customCategory : ""; //when category type is set to other, customCategory is set to the passed String or set to empty
    }

    public Category(Category other) { //copy constructor - creates deep copy of another Category
        this.categoryType = other.categoryType;
        this.customCategory = other.customCategory;
    }

    //setters
    public void setCategoryType(TaskCategory categoryType) {
        this.categoryType = categoryType;
        if (categoryType != TaskCategory.OTHER) {
            this.customCategory = ""; // Clear custom category if not OTHER
        }
    }

    public void setCustomCategory(String customCategory) {
        this.customCategory = customCategory;
    }

    //getters
    public TaskCategory getCategoryType() {
        return categoryType;
    }

    public String getCustomCategory() {
        return customCategory;
    }

    

    // Helper method to create a category with custom text
    public static Category createCustomCategory(String customText) {
        Category cat = new Category(TaskCategory.OTHER, customText);
        return cat;
    }

    //toString method
    public String toString() {
        if (categoryType == TaskCategory.OTHER && customCategory != null && !customCategory.isEmpty()) {
            return "Category: " + customCategory + " (Other)";
        } else {
            return "Category: " + categoryType;
        }
    }

    public static Category parse(String text) {
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException("Text cannot be null or empty");
        } 
        text = text.trim();

        if (!text.startsWith("Category: ")) {
            throw new IllegalArgumentException("Invalid category format. Expected 'Category: ...'");
        }

        String categoryPart = text.substring(10);

        if (categoryPart.endsWith(" (Other)")) {
            String customText = categoryPart.substring(0, categoryPart.length() - 8); // Remove " (Other)"
            return new Category(TaskCategory.OTHER, customText);
        }

        try {
            TaskCategory category = TaskCategory.valueOf(categoryPart);
            return new Category(category);
        } catch (Exception e) {
            throw new IllegalArgumentException("Unknown category type: " + categoryPart);
        }
    }
}
