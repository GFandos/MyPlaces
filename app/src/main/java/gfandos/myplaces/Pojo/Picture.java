package gfandos.myplaces.Pojo;

/**
 * Created by Gerard on 05/03/2017.
 */

public class Picture {

    private String pictureID;

    private String path;
    private double latitude;
    private double longitude;

    //Restaurant, Monument, Other
    private String type;
    private String name;
    private String description;

    public Picture(String _path, String _pID, double lat, double lon) {
        path = _path;
        pictureID = _pID;
        latitude = lat;
        longitude = lon;
        name = "NO NAME";
        type = "NO TYPE";
        description = "NO DESCRIPTION";
    }

    public Picture() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPictureID() {
        return pictureID;
    }

    public void setPictureID(String pictureID) {
        this.pictureID = pictureID;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
