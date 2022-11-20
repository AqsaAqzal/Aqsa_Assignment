package domain;

import com.google.gson.annotations.SerializedName;

public class ItemLocation {

    @SerializedName(value = "id")
    private int id;

    @SerializedName(value = "location_name")
    private String location_name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocation_name() {
        return location_name;
    }

    public void setLocation_name(String location_name) {
        this.location_name = location_name;
    }

}
