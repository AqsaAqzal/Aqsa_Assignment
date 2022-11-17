package domain;

import com.google.gson.annotations.SerializedName;

public class ItemCategory {

    @SerializedName(value = "categoryId")
    private int categoryId;

    @SerializedName(value = "categoryName")
    private String categoryName;

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
