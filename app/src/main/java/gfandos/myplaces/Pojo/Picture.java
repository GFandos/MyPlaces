package gfandos.myplaces.Pojo;

/**
 * Created by Gerard on 05/03/2017.
 */

public class Picture {

    private String pictureID;
    private String name;
    private String path;
    private double latitude;
    private double longitude;

    public Picture(String _name, String _path, String _pID, double lat, double lon) {
        name = _name;
        path = _path;
        pictureID = _pID;
        latitude = lat;
        longitude = lon;
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
}
