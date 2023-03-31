package com.manokero.vividly.model;

public class ImageModel {

    private final UrlModel urls;
    private final UserModel user;
    private final String alt_description;

    public ImageModel(UrlModel urls, UserModel user, String alt_description) {
        this.urls = urls;
        this.user = user;
        this.alt_description = alt_description;
    }

    public String getFormattedAltDescription() {
        if (alt_description == null || alt_description.isEmpty()) {
            return "";
        } else {
            return alt_description.substring(0, 1).toUpperCase() + alt_description.substring(1);
        }
    }

    public UrlModel getUrls() {
        return urls;
    }

    public UserModel getUser() {
        return user;
    }

}
