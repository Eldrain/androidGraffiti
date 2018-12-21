
package ru.artnnv.graffititask.data;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ArtWork {
    private static final String NOT_SPECIFIED = "не указано";
    public static final String KEY_LABEL = "KEY_LABEL";
    public static final String KEY_AUTHOR = "KEY_AUTHOR";
    public static final String KEY_ADDRESS = "KEY_ADDRESS";
    public static final String KEY_DESCRIPTION = "KEY_DESCRIPTION";
    public static final String KEY_IMAGE_URL = "KEY_IMAGE_URL";

    @SerializedName("updatedAt")
    @Expose
    private long updatedAt;
    @SerializedName("createdAt")
    @Expose
    private long createdAt;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("deployDate")
    @Expose
    private Integer deployDate;
    @SerializedName("photos")
    @Expose
    private List<Photo> photos = null;
    @SerializedName("artists")
    @Expose
    private List<Artist> artists = null;
    @SerializedName("status")
    @Expose
    private Status status;
    @SerializedName("location")
    @Expose
    private Location location;
    @SerializedName("id")
    @Expose
    private String id;

    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        String description = this.description;

        if(description == null
                || description.equals("")) {
            description = NOT_SPECIFIED;
        }
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDeployDate() {
        return deployDate;
    }

    public void setDeployDate(Integer deployDate) {
        this.deployDate = deployDate;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getAuthor() {
        String author = NOT_SPECIFIED;

        if(getArtists() != null
                && getArtists().get(0).getName() != null
                && !getArtists().get(0).getName().equals("")) {
            author = getArtists().get(0).getName();
        }
        return author;
    }

    public String getImageUrl() {
        String imageUrl = null;

        if(getPhotos() != null) {
            imageUrl = getPhotos().get(0).getImageUrl();
        }
        return imageUrl;
    }

    public String getAddress() {
        String address = NOT_SPECIFIED;

        if(getLocation() != null
                && getLocation().getAddress() != null) {
            address = getLocation().getAddress();
        }

        return address;
    }
}
