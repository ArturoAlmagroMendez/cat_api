package org.example.model.dto;

public class Favorite {
    String id;
    String apikey = "live_nnip2f7abrQhqPChOAM7KUXrZ2Ldbf3mRLQb5gJHRiyj0Dd2510Mo4Iph4uq6tRS";
    String image_id;
    public Image image;

    public Favorite(String id, String apikey, String image_id, Image image) {
        this.id = id;
        this.apikey = apikey;
        this.image_id = image_id;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    public String getImage_id() {
        return image_id;
    }

    public void setImage_id(String image_id) {
        this.image_id = image_id;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
