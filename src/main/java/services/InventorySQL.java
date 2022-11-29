package services;

abstract public class InventorySQL {

    public static final String ADD_INVENTORY = "insert into inventory(item_name, item_quantity, item_category_id, item_location_id) values (?, ?, ?, ?)";

    public static final String UPDATE_INVENTORY = "UPDATE inventory SET item_name = ?, item_quantity = ?, item_category_id = ?, item_location_id = ? WHERE id = ?";

    public static final String FETCH_BY_ID = "SELECT i.*, c.*, l.*  FROM inventory i, item_category c, item_location l where i.item_category_id = c.id && i.item_location_id = l.id && i.id = ?";

    public static final String FETCH_ALL_INVENTORIES = "SELECT i.*, c.*, l.* FROM inventory i, item_category c, item_location l where i.item_category_id = c.id && i.item_location_id = l.id";

   public static final String FETCH_BY_LOCATION = "select i.*, c.*, l.* from Inventory i, item_category c, item_location l where i.item_category_id = c.id && i.item_location_id = l.id && item_location_id = ?";

    public static final String FETCH_BY_CATEGORY = "select i.*, c.*, l.* from Inventory i, item_category c, item_location l where i.item_category_id = c.id && i.item_location_id = l.id && item_category_id = ?";

    public static final String FETCH_BY_CATEGORY_AND_LOCATION = "select i.*, c.*, l.* from Inventory i, item_category c, item_location l where i.item_category_id = c.id && i.item_location_id = l.id && item_location_id = ? && item_category_id = ?";

    public static final String DELETE_INVENTORY = "DELETE FROM inventory WHERE id = ?";

    public static final String USERS_LIST = "select * from user";

}
