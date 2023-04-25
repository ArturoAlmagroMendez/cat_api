package org.example;

public class Cat {
    String id;
    String url;
    String apikey = System.getenv().get("catapikey");
    //String apikey =;
    String image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Cat{" +
                "id='" + id + '\'' +
                ", url='" + url + '\'' +
                ", apikey='" + apikey + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
