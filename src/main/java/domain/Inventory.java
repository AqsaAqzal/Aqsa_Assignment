package domain;

import com.google.gson.annotations.SerializedName;

import javax.ws.rs.BadRequestException;

public class Inventory {
    @SerializedName(value = "id")
    private Integer id;
    //todo treat as class not primitive

    @SerializedName(value = "item_name")
    private String item_name;

    @SerializedName(value = "item_quantity")
    private Integer item_quantity;

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

    public Integer getId() {
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

    public Integer getItem_quantity() {
        return item_quantity;
    }

    public void setItem_quantity(int item_quantity) {
        this.item_quantity = item_quantity;
    }

    public  static void validate(Inventory item) {
        if(item.getItem_name() == null) {
            throw new BadRequestException("Item name field is null");
        }
        if(item.getItem_quantity() == null) {
            throw new BadRequestException("Item quantity field is null");
        }
        if(item.getItem_category() == null) {
            throw new BadRequestException("Item category field is null");
        }
        if(item.getItem_location() == null) {
            throw new BadRequestException("Item location field is null");
        }
    }
}
