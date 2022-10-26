package domain;

import com.google.gson.annotations.SerializedName;

public class Item {

    @SerializedName(value = "itemId")
    private int itemId;

    @SerializedName(value = "itemName")
    private String itemName;

    @SerializedName(value = "itemQuantity")
    private int itemQuantity;

    @SerializedName(value = "itemCategoryId")
    private int itemCategoryId;

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public int getItemCategoryId() {
        return itemCategoryId;
    }

    public void setItemCategoryId(int itemCategoryId) {
        this.itemCategoryId = itemCategoryId;
    }

    public int getItemLocationId() {
        return itemLocationId;
    }

    public void setItemLocationId(int itemLocationId) {
        this.itemLocationId = itemLocationId;
    }

    @SerializedName(value = "itemLocationId")
    private int itemLocationId;

}
