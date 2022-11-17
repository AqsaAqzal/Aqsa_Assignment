package domain;

import com.google.gson.annotations.SerializedName;

public class ItemLocation {

    @SerializedName(value = "locationId")
    private int locationId;

    @SerializedName(value = "locationName")
    private String locationName;

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

}
