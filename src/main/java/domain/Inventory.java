package domain;

import com.google.gson.annotations.SerializedName;

public class Inventory {

    @SerializedName(value = "itemId")
    private int itemId;

    @SerializedName(value = "itemName")
    private String itemName;

    @SerializedName(value = "itemQuantity")
    private int itemQuantity;

    @SerializedName(value = "itemCategory")
    private ItemCategory itemCategory;

    @SerializedName(value = "itemLocation")
    private ItemLocation itemLocation;

    public ItemLocation getItemLocation() {
        return itemLocation;
    }

    public void setItemLocation(ItemLocation itemLocation) {
        this.itemLocation = itemLocation;
    }

    public ItemCategory getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(ItemCategory itemCategory) {
        this.itemCategory = itemCategory;
    }

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

}
