package com.doubleclick.doctor.model;

public class Sub {

    private String image;
    private String id;
    private String name;

    private String nameParent;

    public Sub(String image, String id, String name, String nameParent) {
        this.image = image;
        this.id = id;
        this.name = name;
        this.nameParent = nameParent;
    }

    public String getNameParent() {
        return nameParent;
    }

    public void setNameParent(String nameParent) {
        this.nameParent = nameParent;
    }


    public Sub() {
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
