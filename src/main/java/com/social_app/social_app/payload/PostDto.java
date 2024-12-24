package com.social_app.social_app.payload;

public class PostDto {
    private Integer id;
    private String caption;
    private String image;
    private String video;

    public PostDto(Integer id, String caption, String image, String video) {
        this.id = id;
        this.caption = caption;
        this.image = image;
        this.video = video;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }
}
