/**
 * TODO Write a one-sentence summary of your class here.
 * TODO Follow it with additional details about its purpose, what abstraction
 * it represents, and how to use it.
 *
 * @author  TODO Your Name
 * @version Sep 23, 2025
 */
package TaskClasses;

public class Category {
    //fields
    private TaskCategory categoryType;
    private String customCategory = "";

    //constructors
    public Category() { //default
        this.categoryType = TaskCategory.EVENT;
    }

    public Category(TaskCategory categoryType) { //parameterized
        this.categoryType = categoryType;
        
    }
    
    public Category(TaskCategory categoryType, String customCategory) { //parameterized. used when categoryType is set to TaskCategory.OTHER
        this.categoryType = categoryType;
        this.customCategory = (categoryType == TaskCategory.OTHER) ? customCategory : ""; //when category type is set to other, customCategory is set to the passed String or set to empty
    }

    public Category(Category other) { //copy
        this.categoryType = other.categoryType;
        this.customCategory = other.customCategory;
    }

    public void setCategoryType(TaskCategory categoryType) {
        this.categoryType = categoryType;
        if (categoryType != TaskCategory.OTHER) {
            this.customCategory = ""; // Clear custom category if not OTHER
        }
    }

    public TaskCategory getCategoryType() {
        return categoryType;
    }

    public String getCustomCategory() {
        return customCategory;
    }

    public void setCustomCategory(String customCategory) {
        this.customCategory = customCategory;
    }

    // Helper method to create a category with custom text
    public static Category createCustomCategory(String customText) {
        Category cat = new Category(TaskCategory.OTHER, customText);
        return cat;
    }

    @Override
    public String toString() {
        if (categoryType == TaskCategory.OTHER && customCategory != null && !customCategory.isEmpty()) {
            return "Category: " + customCategory + " (Other)";
        } else {
            return "Category: " + categoryType;
        }
    }
}
