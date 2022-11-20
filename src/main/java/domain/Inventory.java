package domain;

import com.google.gson.annotations.SerializedName;

public class Inventory {
    //todo change fields name as per assignment
    @SerializedName(value = "id")
    private int id;

    @SerializedName(value = "item_name")
    private String item_name;

    @SerializedName(value = "item_quantity")
    private int item_quantity;

    @SerializedName(value = "item_category")
    private ItemCategory item_category;

    @SerializedName(value = "item_location")
    private ItemLocation item_location;

    public ItemLocation getItem_location() {
        return item_location;
    }

    public void setItem_location(ItemLocation item_location) {
        this.item_location = item_location;
    }

    public ItemCategory getItem_category() {
        return item_category;
    }

    public void setItem_category(ItemCategory item_category) {
        this.item_category = item_category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public int getItem_quantity() {
        return item_quantity;
    }

    public void setItem_quantity(int item_quantity) {
        this.item_quantity = item_quantity;
    }

}
