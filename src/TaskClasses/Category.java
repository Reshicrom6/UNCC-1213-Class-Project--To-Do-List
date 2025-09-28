/**
 * TODO Write a one-sentence summary of your class here.
 * TODO Follow it with additional details about its purpose, what abstraction
 * it represents, and how to use it.
 *
 * @author  TODO Your Name
 * @version Sep 23, 2025
 */
package TaskClasses;

import java.util.Scanner;

public class Category {
    private TaskCategory categoryType;
    private String customCategory = "";

    public Category() {
        this.categoryType = TaskCategory.EVENT;
    }

    public Category(TaskCategory categoryType) {
        this.categoryType = categoryType;
        if (categoryType == TaskCategory.OTHER) {
            promptForCustomCategory();
        }
    }

    public Category(Category other) {
        this.categoryType = other.categoryType;
        this.customCategory = other.customCategory;
    }

    public void setCategoryType(TaskCategory categoryType) {
        this.categoryType = categoryType;
        if (categoryType == TaskCategory.OTHER) {
            promptForCustomCategory();
        } else {
            this.customCategory = "";
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

    // public void promptForCustomCategory() {
    //     System.out.print("Enter custom category: ");

    // }

    @Override
    public String toString() {
        if (categoryType == TaskCategory.OTHER && customCategory != null && !customCategory.isEmpty()) {
            return "Category: " + customCategory + " (Other)";
        } else {
            return "Category: " + categoryType;
        }
    }
}
