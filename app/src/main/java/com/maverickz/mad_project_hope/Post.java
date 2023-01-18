package com.maverickz.mad_project_hope;

public class Post {
    private String heading;
    private String description;
    private String imageUri;
    private String ID;
    private String uid;

    public String getuid() {
        return uid;
    }

    public void setuid(String UID) { this.uid = UID; }

    public Post() {
    }

    public String getHeading() {
        return heading;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public Post(String imageUri){
        this.imageUri = imageUri;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getID() { return ID; }

    public void setID(String ID) { this.ID = ID; }
}
