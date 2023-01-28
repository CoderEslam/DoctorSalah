package com.doubleclick.doctor.model;

public class Description {

    private String sub_title;
    private String sub_description;
    private String sub_parent;

    public Description(String sub_title, String sub_description, String sub_parent) {
        this.sub_title = sub_title;
        this.sub_description = sub_description;
        this.sub_parent = sub_parent;
    }

    public Description() {
    }

    public String getSub_title() {
        return sub_title;
    }

    public void setSub_title(String sub_title) {
        this.sub_title = sub_title;
    }

    public String getSub_description() {
        return sub_description;
    }

    public void setSub_description(String sub_description) {
        this.sub_description = sub_description;
    }

    public String getSub_parent() {
        return sub_parent;
    }

    public void setSub_parent(String sub_parent) {
        this.sub_parent = sub_parent;
    }
}
